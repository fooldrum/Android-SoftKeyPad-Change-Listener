# Android-SoftKeyPad-Change-Listener
Android's SoftKeyPad Change Listener 

[ ![Download](https://api.bintray.com/packages/daehee/maven/softkeypad-detector/images/download.svg) ](https://bintray.com/daehee/maven/softkeypad-detector/_latestVersion)

You can detect Android SoftKeyPad's show/hide. Also, you can get SoftKeyPad's height.

    public class MainActivity extends Activity {
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

##Download

Configure your project-level build.gradle include repositories jcenter:

    buildscript {
      repositories {
        jcenter()
      }
      ...
    }
    
Then, your module-level build.gradle add the SoftKeyPad-Detector dependencies:

    dependencies {
      ...
      compile 'com.zzang.android:softkeypad-detector:1.0.2'
      ...
    }
    

##Licence

    Copyright 2016 DaeHee Jang
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
