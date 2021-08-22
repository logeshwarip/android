package com.example.netflix;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.wang.avi.AVLoadingIndicatorView;


public class CustomDialog extends ProgressDialog {

    AVLoadingIndicatorView avi;

    public CustomDialog(Context context) {
        super(context);
    }
    public CustomDialog(Context context, String strMessage) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setIndeterminate(true);
        setMessage(strMessage);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.show();

    }
}
