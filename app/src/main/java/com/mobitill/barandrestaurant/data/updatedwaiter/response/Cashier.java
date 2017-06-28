
package com.mobitill.barandrestaurant.data.updatedwaiter.response;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Cashier {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("roles")
    private String mRoles;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
//        mPassword = 12345;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getRoles() {
        return mRoles;
    }

    public void setRoles(String roles) {
        mRoles = roles;
    }

}
