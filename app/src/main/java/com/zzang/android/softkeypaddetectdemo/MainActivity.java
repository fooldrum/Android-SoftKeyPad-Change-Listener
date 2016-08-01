package com.zzang.android.softkeypaddetectdemo;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zzang.android.softkeypaddetect.OnSoftKeyPadListener;
import com.zzang.android.softkeypaddetect.SoftKeyPadDetector;

public class MainActivity extends AppCompatActivity {

    private SoftKeyPadDetector mSoftKeyPadDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSoftKeyPadDetector = new SoftKeyPadDetector(this);
        mSoftKeyPadDetector.setOnSoftInputListener(mSoftKeyPadChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoftKeyPadDetector.startDetect();
    }

    @Override
    protected void onPause() {
        mSoftKeyPadDetector.stopDetect();
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // receive Orientation changed.

    }

    private OnSoftKeyPadListener mSoftKeyPadChangeListener = new OnSoftKeyPadListener() {
        @Override
        public void onSoftKeyPadChanged(boolean visible, int height) {
            if(visible) {
                Toast.makeText(MainActivity.this, "SoftKeyPad show.\nheight:" + height, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "SoftKeyPad hide.", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
