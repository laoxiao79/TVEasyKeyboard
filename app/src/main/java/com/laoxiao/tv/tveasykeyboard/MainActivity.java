package com.laoxiao.tv.tveasykeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.laoxiao.tv.tveasykeyboard.ui.CustomEasyTVKeyboard;

public class MainActivity extends AppCompatActivity implements CustomEasyTVKeyboard.OnMyTextChangedListener{
    private static String TAG = MainActivity.class.getSimpleName();

    private CustomEasyTVKeyboard customEasyTVKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customEasyTVKeyboard = (CustomEasyTVKeyboard)findViewById(R.id.customInputView);
        customEasyTVKeyboard.setmOnMyTextChangedListener(this);
    }

    @Override
    public void onTextChanged(String text) {
        Log.i(TAG,"-------------SearchActivity onTextChanged------------text ="+text);
        if(!TextUtils.isEmpty(text)){
            //// TODO:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            if (customEasyTVKeyboard.hasFocus()) {
                String text = customEasyTVKeyboard.getSearchText();
                Log.i(TAG, "-------- onKeyDown ----------text="+text);
                if(!TextUtils.isEmpty(text)){
                    customEasyTVKeyboard.deleteSearchText();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
