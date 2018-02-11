package com.point2points.fishregister.Profile.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.point2points.fishregister.Utilities.DBHelper;

/**
 * Created by Edwin on 6/2/2018.
 */

public class Profile implements Parcelable {

    @SerializedName("userStatus")
    @Expose
    private String userStatus;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("serialnumber")
    @Expose
    private String serialnumber;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("passport")
    @Expose
    private String passport;
    @SerializedName("dob")
    @Expose
    private long dob;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("doe")
    @Expose
    private long doe;
    @SerializedName("updated")
    @Expose
    private long updated;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("doi")
    @Expose
    private long doi;
    @SerializedName("user-token")
    @Expose
    private String userToken;

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getDoe() {
        return doe;
    }

    public void setDoe(long doe) {
        this.doe = doe;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public long getDoi() {
        return doi;
    }

    public void setDoi(long doi) {
        this.doi = doi;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }


    public Profile() {
    }

    public static Profile getProfile(Cursor cursor, int position) {
        cursor.moveToPosition(position);
        Profile profile = new Profile();
        profile.setName(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_NAME)));
        profile.setGender(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_GENDER)));
        profile.setCompany(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_COMPANY)));
        profile.setPassport(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_PASSPORT)));
        profile.setUserToken(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_TOKEN)));
        profile.setDob(cursor.getInt(cursor.getColumnIndex(DBHelper.USER_COLUMN_DOB)));
        profile.setDoi(cursor.getInt(cursor.getColumnIndex(DBHelper.USER_COKUMN_DOI)));
        profile.setDoe(cursor.getInt(cursor.getColumnIndex(DBHelper.USER_COLUMN_DOE)));
        return profile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userStatus);
        dest.writeString(this.gender);
        dest.writeString(this.serialnumber);
        dest.writeString(this.ownerId);
        dest.writeString(this.passport);
        dest.writeLong(this.dob);
        dest.writeString(this.name);
        dest.writeString(this.company);
        dest.writeLong(this.doe);
        dest.writeLong(this.updated);
        dest.writeString(this.email);
        dest.writeString(this.objectId);
        dest.writeLong(this.doi);
        dest.writeString(this.userToken);
    }

    protected Profile(Parcel in) {
        this.userStatus = in.readString();
        this.gender = in.readString();
        this.serialnumber = in.readString();
        this.ownerId = in.readString();
        this.passport = in.readString();
        this.dob = in.readLong();
        this.name = in.readString();
        this.company = in.readString();
        this.doe = in.readLong();
        this.updated = in.readLong();
        this.email = in.readString();
        this.objectId = in.readString();
        this.doi = in.readLong();
        this.userToken = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
