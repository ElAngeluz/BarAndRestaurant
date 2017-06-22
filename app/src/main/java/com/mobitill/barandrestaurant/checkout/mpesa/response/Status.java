
package com.mobitill.barandrestaurant.checkout.mpesa.response;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Status {

    @SerializedName("proucts")
    private Long mProucts;

    public Long getProucts() {
        return mProucts;
    }

    public void setProucts(Long proucts) {
        mProucts = proucts;
    }

}
