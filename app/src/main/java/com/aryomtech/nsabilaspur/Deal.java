package com.aryomtech.nsabilaspur;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class Deal {

    private String addressshop;
    private String desciptionshop;
    private String shopcategory;
    private String photo;
    private String listername;
    private String latitude;
    private String longitude;

    public Deal() {
    }

    public Deal(String addressshop, String desciptionshop, String shopcategory, String photo,String latitude,String longitude,String listername) {
        this.addressshop = addressshop;
        this.desciptionshop = desciptionshop;
        this.listername=listername;
        this.shopcategory = shopcategory;
        this.photo = photo;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getListername() {
        return listername;
    }

    public void setListername(String listername) {
        this.listername = listername;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddressshop() {
        return addressshop;
    }

    public void setAddressshop(String addressshop) {
        this.addressshop = addressshop;
    }

    public String getDesciptionshop() {
        return desciptionshop;
    }

    public void setDesciptionshop(String desciptionshop) {
        this.desciptionshop = desciptionshop;
    }

    public String getShopcategory() {
        return shopcategory;
    }

    public void setShopcategory(String shopcategory) {
        this.shopcategory = shopcategory;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
