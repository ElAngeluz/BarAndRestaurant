
package com.mobitill.barandrestaurant.data.updatedwaiter.response;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("organization")
    private Organization mOrganization;

    public Organization getOrganization() {
        return mOrganization;
    }

    public void setOrganization(Organization organization) {
        mOrganization = organization;
    }

}
