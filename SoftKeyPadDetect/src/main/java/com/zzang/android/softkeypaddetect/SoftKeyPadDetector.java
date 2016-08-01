package com.zzang.android.softkeypaddetect;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * SoftKeyPad's Detector for visible or gone.
 *
 * Not guarantee Android N's Multi-Window.
 *
 * @author by jangdaehee on 2016. 7. 29.
 */
public class SoftKeyPadDetector implements ViewTreeObserver.OnGlobalLayoutListener {

    // KeyPad's minimum height. not guarantee under xhdpi. you can change this value.
    private static final int SOFT_INPUT_MIN_HEIGHT = 200;

    // root view
    private final View mView;

    // View's Rect
    private final Rect mVisibleRect = new Rect();
    // View's right position
    private int mRectRight = 0;
    // View's visible height
    private int mVisibleHeight = 0;

    // Is show SoftKeyPad
    private boolean mIsVisibleSoftKeyPad = false;

    // SoftKeyPad's height
    private int mSoftKeyPadHeight = 0;
    // Android StatusBar's height
    private int mStatusBarHeight = 0;

    // detect pause
    private boolean mDetectPaused = false;

    private OnSoftKeyPadListener mOnSoftKeyPadListener;

    /**
     * Creator
     *
     * attention : Use after View inflated
     *
     * @param view View.
     */
    public SoftKeyPadDetector(View view) {
        this.mView = view;
        if (view instanceof OnSoftKeyPadListener) {
            setOnSoftInputListener((OnSoftKeyPadListener) view);
        }
    }

    /**
     * Creator
     *
     * attention : Use after Activity-onCreate()'s setContentView()
     *
     * @param activity Activity
     */
    public SoftKeyPadDetector(Activity activity) {
        this(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
        if (activity instanceof OnSoftKeyPadListener) {
            setOnSoftInputListener((OnSoftKeyPadListener) activity);
        }
    }

    /**
     * Creator
     *
     * attention : Use after Fragment-onCreateView()
     *
     * @param fragment Fragment
     */
    public SoftKeyPadDetector(android.support.v4.app.Fragment fragment) {
        this(fragment.getActivity());
        if (fragment instanceof OnSoftKeyPadListener) {
            setOnSoftInputListener((OnSoftKeyPadListener) fragment);
        }
    }

    /**
     * Set Listener
     *
     * @param onSoftKeyPadListener OnSoftKeyPadListener
     * @return SoftKeyPadDetector
     */
    public SoftKeyPadDetector setOnSoftInputListener(OnSoftKeyPadListener onSoftKeyPadListener) {
        this.mOnSoftKeyPadListener = onSoftKeyPadListener;
        return this;
    }

    public boolean isSoftKeyPadVisible() {
        return this.mIsVisibleSoftKeyPad;
    }

    public int getSoftKeyPadHeight() {
        return this.mSoftKeyPadHeight;
    }

    public int getStatusBarHeight() {
        return this.mStatusBarHeight;
    }

    public View getView() {
        return this.mView;
    }

    public Rect getVisibleRect() {
        return this.mVisibleRect;
    }

    public int getVisibleHeight() {
        return this.mVisibleHeight;
    }

    /**
     * Start Detect SoftKeyPad
     */
    public void startDetect() {
        addOnGlobalLayoutListener(this.mView, this);
        resumeDetect();
    }

    /**
     * Resume Detect SoftKeyPad
     */
    public void resumeDetect() {
        this.mDetectPaused = false;
    }

    /**
     * Pause Detect SoftKeyPad
     */
    public void pauseDetect() {
        this.mDetectPaused = true;
    }

    /**
     * Stop Detect SoftKeyPad
     */
    public void stopDetect() {
        pauseDetect();
        removeOnGlobalLayoutListener(this.mView, this);
    }

    /**
     * Add view to ViewTreeObserver.OnGlobalLayoutListener
     *
     * @param view View
     * @param onGlobalLayoutListener ViewTreeObserver.OnGlobalLayoutListener
     */
    public void addOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    /**
     * Remove view from ViewTreeObserver.OnGlobalLayoutListener
     * @param view View
     * @param onGlobalLayoutListener ViewTreeObserver.OnGlobalLayoutListener
     */
    public void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    @Override
    public void onGlobalLayout() {
        this.mView.getWindowVisibleDisplayFrame(mVisibleRect);

        boolean isChangeOrientation = false;

        if(this.mRectRight == 0) {
            this.mRectRight = this.mVisibleRect.right;
        }
        else{
            if(this.mRectRight != this.mVisibleRect.right) {
                // Orientation's change!
                isChangeOrientation = true;
                this.mRectRight = this.mVisibleRect.right;
            }else{
                isChangeOrientation = false;
            }
        }

        final int visibleHeight = this.mVisibleRect.height();

        if (!isChangeOrientation && this.mVisibleHeight != visibleHeight) {
            this.mVisibleHeight = visibleHeight;
            this.mStatusBarHeight = mVisibleRect.top;

            final int softInputHeight = mView.getRootView().getHeight() - visibleHeight - this.mStatusBarHeight;
            final boolean softInputVisible = softInputHeight > SOFT_INPUT_MIN_HEIGHT;

            if (this.mIsVisibleSoftKeyPad != softInputVisible || this.mSoftKeyPadHeight != softInputHeight) {
                this.mIsVisibleSoftKeyPad = softInputVisible;
                this.mSoftKeyPadHeight = softInputHeight;
                if (this.mOnSoftKeyPadListener != null && !this.mDetectPaused) {
                    this.mOnSoftKeyPadListener.onSoftKeyPadChanged(softInputVisible, softInputHeight);
                }
            }
        }
    }
}
