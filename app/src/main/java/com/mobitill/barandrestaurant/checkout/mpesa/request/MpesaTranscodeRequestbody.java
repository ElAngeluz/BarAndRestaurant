package com.mobitill.barandrestaurant.checkout.mpesa.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dataintegrated on 6/12/2017.
 */

public class MpesaTranscodeRequestbody {

    @SerializedName("transcode")
    @Expose
    private String transcode;

    public MpesaTranscodeRequestbody(String transcode) {
        this.transcode = transcode;
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }
}
