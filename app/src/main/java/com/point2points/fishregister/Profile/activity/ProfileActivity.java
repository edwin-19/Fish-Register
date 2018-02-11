package com.point2points.fishregister.Profile.activity;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.point2points.fishregister.Login.provider.ProfileProvider;
import com.point2points.fishregister.Profile.model.Profile;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Utilities.Utilities;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;
    private TextView mTxtTitleToolbar;
    private ImageView mImgProfilePic;
    private TextView mTxtUserName;
    private TextView mTxtGender;
    private TextView mTxtDOB;
    private TextView mTxtPassportNum;
    private TextView mTxtStartDate;
    private TextView mTxtExpireDate;

    private int mLoaderId = 3356;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Profile profile = getIntent().getParcelableExtra("DATA");

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mTxtTitleToolbar = findViewById(R.id.txtTitle);
        mTxtTitleToolbar.setText(getString(R.string.title_activity_profile));

        mImgProfilePic = findViewById(R.id.imgProfilePic);
        mTxtUserName = findViewById(R.id.txtName);
        mTxtGender = findViewById(R.id.txtGender);
        mTxtDOB = findViewById(R.id.txtDOB);
        mTxtPassportNum = findViewById(R.id.txtPassport);
        mTxtStartDate = findViewById(R.id.txtStartDate);
        mTxtExpireDate = findViewById(R.id.txtExpire);

        Glide.with(ProfileActivity.this)
                .asBitmap()
                .load("https://i2.cdn.turner.com/cnnnext/dam/assets/140926165711-john-sutter-profile-image-large-169.jpg")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .circleCrop())
                .into(mImgProfilePic);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(mLoaderId, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (mLoaderId == i) {
            return new CursorLoader(ProfileActivity.this, ProfileProvider.CONTENT_URI, null, null, null, null);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (mLoaderId == loader.getId()) {
            if (cursor != null) {
                Profile profile = Profile.getProfile(cursor, 0);
                mTxtUserName.setText(profile.getName());
                mTxtGender.setText(profile.getGender());
                mTxtDOB.setText(Utilities.convertStringToDate(new Date(profile.getDob() * 1000)));
                mTxtPassportNum.setText(profile.getPassport());
                mTxtStartDate.setText(Utilities.convertStringToDate(new Date(profile.getDoi() * 1000)));
                mTxtExpireDate.setText(Utilities.convertStringToDate(new Date(profile.getDoe() * 1000)));
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
