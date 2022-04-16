package com.aryomtech.nsabilaspur;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class reportData {

    private String reportTo;
    private String reportedBy;
    private String uidofreporteduser;
    private String uidofreportedbyuser;
    private String deviceTokenofreportedByuser;
    private String deviceTokenofreporteduser;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private String caption;
    private String location;
    private String uniqueid;
    private String time;

    public reportData() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeviceTokenofreporteduser() {
        return deviceTokenofreporteduser;
    }

    public void setDeviceTokenofreporteduser(String deviceTokenofreporteduser) {
        this.deviceTokenofreporteduser = deviceTokenofreporteduser;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getReportTo() {
        return reportTo;
    }

    public void setReportTo(String reportTo) {
        this.reportTo = reportTo;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getUidofreporteduser() {
        return uidofreporteduser;
    }

    public void setUidofreporteduser(String uidofreporteduser) {
        this.uidofreporteduser = uidofreporteduser;
    }

    public String getUidofreportedbyuser() {
        return uidofreportedbyuser;
    }

    public void setUidofreportedbyuser(String uidofreportedbyuser) {
        this.uidofreportedbyuser = uidofreportedbyuser;
    }

    public String getDeviceTokenofreportedByuser() {
        return deviceTokenofreportedByuser;
    }

    public void setDeviceTokenofreportedByuser(String deviceTokenofreportedByuser) {
        this.deviceTokenofreportedByuser = deviceTokenofreportedByuser;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
