package com.point2points.fishregister.Search.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.point2points.fishregister.Utilities.DBHelper;

/**
 * Created by Edwin on 10/2/2018.
 */

public class Employee implements Parcelable {
    @SerializedName("lastLogin")
    @Expose
    private long lastLogin;
    @SerializedName("userStatus")
    @Expose
    private String userStatus;
    @SerializedName("serialnumber")
    @Expose
    private String serialnumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("socialAccount")
    @Expose
    private String socialAccount;
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
    @SerializedName("___class")
    @Expose
    private String _class;

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
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

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lastLogin);
        dest.writeString(this.userStatus);
        dest.writeString(this.serialnumber);
        dest.writeString(this.gender);
        dest.writeValue(this.created);
        dest.writeString(this.ownerId);
        dest.writeString(this.socialAccount);
        dest.writeString(this.passport);
        dest.writeValue(this.dob);
        dest.writeString(this.name);
        dest.writeString(this.company);
        dest.writeValue(this.doe);
        dest.writeValue(this.updated);
        dest.writeString(this.email);
        dest.writeString(this.objectId);
        dest.writeValue(this.doi);
        dest.writeString(this._class);
    }

    public Employee() {
    }

    protected Employee(Parcel in) {
        this.lastLogin = (long) in.readValue(long.class.getClassLoader());
        this.userStatus = in.readString();
        this.serialnumber = in.readString();
        this.gender = in.readString();
        this.created = (long) in.readValue(long.class.getClassLoader());
        this.ownerId = in.readString();
        this.socialAccount = in.readString();
        this.passport = in.readString();
        this.dob = (long) in.readValue(long.class.getClassLoader());
        this.name = in.readString();
        this.company = in.readString();
        this.doe = (long) in.readValue(long.class.getClassLoader());
        this.updated = (long) in.readValue(long.class.getClassLoader());
        this.email = in.readString();
        this.objectId = in.readString();
        this.doi = (long) in.readValue(long.class.getClassLoader());
        this._class = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public static Employee getEmployee(Cursor cursor, int position) {
        cursor.moveToPosition(position);
        Employee employee = new Employee();
        employee.setName(cursor.getString(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_NAME)));
        employee.setGender(cursor.getString(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_GENDER)));
        employee.setCompany(cursor.getString(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_COMPANY)));
        employee.setSerialnumber(cursor.getString(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_SERIAL_NUMBER)));
        employee.setDob(cursor.getLong(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_DOB)));
        employee.setDoe(cursor.getLong(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_DOE)));
        employee.setDoi(cursor.getLong(cursor.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_DOI)));

        return employee;
    }
}
