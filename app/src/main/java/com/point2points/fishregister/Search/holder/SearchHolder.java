package com.point2points.fishregister.Search.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.point2points.fishregister.R;

/**
 * Created by Edwin on 10/2/2018.
 */

public class SearchHolder extends RecyclerView.ViewHolder {
    private ImageView mImgProfilePic;
    private TextView mTxtUserName;

    public SearchHolder(View itemView) {
        super(itemView);
        mImgProfilePic = itemView.findViewById(R.id.imgProfilePic);
        mTxtUserName = itemView.findViewById(R.id.txtName);
    }

    public ImageView getmImgProfilePic() {
        return mImgProfilePic;
    }

    public TextView getmTxtUserName() {
        return mTxtUserName;
    }
}
