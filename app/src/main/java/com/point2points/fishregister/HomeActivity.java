package com.point2points.fishregister;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.point2points.fishregister.Login.database.CRUDHelper;
import com.point2points.fishregister.Profile.activity.ProfileActivity;
import com.point2points.fishregister.Profile.model.Profile;
import com.point2points.fishregister.Utilities.Utilities;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private Profile mToken;
    private String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToken = CRUDHelper.getToken(HomeActivity.this);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mToken != null) {
                    if (Utilities.isConnectingToInternet(HomeActivity.this)) {
                       /* HttpHelper.getIsTokenValid(SplashScreenActivity.this, TAG, mToken.getUserToken());*/
                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        finish();
                    }
                } else {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);

    }
}
