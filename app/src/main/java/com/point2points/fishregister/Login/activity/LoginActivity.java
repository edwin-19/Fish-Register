package com.point2points.fishregister.Login.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.point2points.fishregister.Login.webapi.HttpHelper;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Utilities.Utilities;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTxtToolbarTitle;
    private RelativeLayout mBtnLogin;
    private RelativeLayout mFadeProgressBar;
    private ProgressBar mProgressBar;

    private EditText mEditEmail;
    private EditText mEditPass;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mTxtToolbarTitle = findViewById(R.id.txtTitle);
        mTxtToolbarTitle.setText(getString(R.string.title_activity_login));

        mBtnLogin = findViewById(R.id.btnCreateAccount);

        mEditEmail = findViewById(R.id.editEmail);
        mEditPass = findViewById(R.id.editPass);
        mFadeProgressBar =  findViewById(R.id.fadeProgressBar);
        mProgressBar =  findViewById(R.id.progressBar);

        mBtnLogin.setOnClickListener(mBtnLoginClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(TAG);
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(mBroadCastReceiver, intentFilter);
    }

    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(TAG)) {
                if (intent.getBooleanExtra("failed", false)) {
                    Utilities.setFadeProgressBarVisibility(false, mFadeProgressBar, mProgressBar);
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(mBroadCastReceiver);
    }

    private View.OnClickListener mBtnLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mEditEmail.getText().length() != 0) {
                if (mEditPass.getText().length() != 0) {
                    Utilities.setFadeProgressBarVisibility(true, mFadeProgressBar, mProgressBar);
                    HttpHelper.loginUser(LoginActivity.this,"edwin@gmail.com", "123", TAG);
                }
            }

        }
    };
}
