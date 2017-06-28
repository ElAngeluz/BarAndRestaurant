
package com.mobitill.barandrestaurant.data.updatedwaiter.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Organization {

    @SerializedName("cashiers")
    private List<Cashier> mCashiers;

    public List<Cashier> getCashiers() {
        return mCashiers;
    }

    public void setCashiers(List<Cashier> cashiers) {
        mCashiers = cashiers;
    }

}
