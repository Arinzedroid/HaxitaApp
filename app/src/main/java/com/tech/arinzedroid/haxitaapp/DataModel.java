package com.tech.arinzedroid.haxitaapp;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


public class DataModel {

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("error_code")
    @Expose
    private Object errorCode;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataModel() {
    }

    /**
     *
     * @param error
     * @param data
     * @param errorCode
     */
    public DataModel(boolean error, Object errorCode, List<Datum> data) {
        super();
        this.error = error;
        this.errorCode = errorCode;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    @Parcel
    public static class Datum {

        @SerializedName("campaign_id")
        @Expose
        private int campaignId;
        @SerializedName("campaign_images")
        @Expose
        private String campaignImages;
        @SerializedName("action_link")
        @Expose
        private String actionLink;
        @SerializedName("view_count")
        @Expose
        private int viewCount;

        /**
         * No args constructor for use in serialization
         *
         */
        public Datum() {
        }

        /**
         *
         * @param actionLink
         * @param campaignId
         * @param campaignImages
         * @param viewCount
         */
        public Datum(int campaignId, String campaignImages, String actionLink, int viewCount) {
            super();
            this.campaignId = campaignId;
            this.campaignImages = campaignImages;
            this.actionLink = actionLink;
            this.viewCount = viewCount;
        }

        public int getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(int campaignId) {
            this.campaignId = campaignId;
        }

        public String getCampaignImages() {
            return campaignImages;
        }

        public void setCampaignImages(String campaignImages) {
            this.campaignImages = campaignImages;
        }

        public String getActionLink() {
            return actionLink;
        }

        public void setActionLink(String actionLink) {
            this.actionLink = actionLink;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

    }

}


