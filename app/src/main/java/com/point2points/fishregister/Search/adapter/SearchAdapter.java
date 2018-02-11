package com.point2points.fishregister.Search.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.point2points.fishregister.Login.model.Token;
import com.point2points.fishregister.Profile.model.Profile;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Search.holder.SearchHolder;
import com.point2points.fishregister.Search.model.Employee;

import java.util.ArrayList;

/**
 * Created by Edwin on 10/2/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    private Context mContext;
    private Cursor mCursor;
    private LayoutInflater mInflater;

    public SearchAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_employee_row, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        if (mCursor != null) {
            Employee employee = Employee.getEmployee(mCursor, position);
            holder.getmTxtUserName().setText(employee.getName());

            Glide.with(mContext)
                    .asBitmap()
                    .load("http://brandchannel.com/wp-content/uploads/2013/11/guy-fawkes-300.jpg")
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.empty_photo))
                    .into(holder.getmImgProfilePic());
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public Cursor swapCursor(Cursor cursor) {
        if (this.mCursor == cursor) {
            return null;
        }

        Cursor oldCursor = this.mCursor;
        this.mCursor = cursor;
        if (cursor != null) {
            notifyDataSetChanged();
        }

        return oldCursor;
    }
}
