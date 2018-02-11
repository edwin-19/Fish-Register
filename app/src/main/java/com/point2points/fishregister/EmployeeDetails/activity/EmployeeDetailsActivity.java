package com.point2points.fishregister.EmployeeDetails.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Search.model.Employee;
import com.point2points.fishregister.Utilities.Utilities;

import java.util.Date;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private Employee mEmployee;

    private TextView mTxtName;
    private TextView mTxtGender;
    private TextView mTxtPassport;
    private TextView mTxtDOB;
    private TextView mTxtDOI;
    private TextView mTxtDOE;

    private ImageView mImgProfilePic;

    private Toolbar mToolbar;
    private TextView mTxtTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        setupToolbar();

        mTxtName = findViewById(R.id.txtName);
        mTxtGender = findViewById(R.id.txtGender);
        mTxtPassport = findViewById(R.id.txtPassport);
        mTxtDOB = findViewById(R.id.txtDOB);
        mTxtDOE = findViewById(R.id.txtExpire);
        mTxtDOI = findViewById(R.id.txtStartDate);
        mImgProfilePic = findViewById(R.id.imgProfilePic);

        mEmployee = getIntent().getParcelableExtra("DATA");
        mTxtName.setText(mEmployee.getName());
        mTxtGender.setText(mEmployee.getGender());
        mTxtPassport.setText(mEmployee.getPassport());
        mTxtDOB.setText(Utilities.convertStringToDate(new Date(mEmployee.getDob() * 1000)));
        mTxtDOI.setText(Utilities.convertStringToDate(new Date(mEmployee.getDoi() * 1000)));
        mTxtDOE.setText(Utilities.convertStringToDate(new Date(mEmployee.getDoe() * 1000)));

        Glide.with(this)
                .asBitmap()
                .load("")
                .apply(new RequestOptions()
                        .circleCrop()
                        .placeholder(R.drawable.empty_photo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mImgProfilePic);
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mTxtTitleToolbar = findViewById(R.id.txtTitle);
        mTxtTitleToolbar.setText(getString(R.string.employee_details));
    }
}
