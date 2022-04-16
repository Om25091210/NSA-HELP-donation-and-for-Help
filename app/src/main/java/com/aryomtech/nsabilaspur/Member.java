package com.aryomtech.nsabilaspur;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep

public class Member {

    public String loc;
    public String name;
    public String phone;
    public String category;
    public String key;
    public String task;
    public String uniqueid;
    public String individualKey;
    public String time;
    public String devicetoken;
    public String posted;
    public String imageurl;
    public String count;
    public String pic1;
    public String pic2;
    public String pic3;
    public String pic4;
    public String latitude;
    public String longitude;
    public String done;
    public String dp;
    public String typeyourown;
    public String description;
    public String type;

    public String respondedby;
    public String respondeddp;
    public String respondeduid;
    public String respondedto;
    public String respondedtype;

    public String nameofngo,emailofngo,phoneofngo;

    public Member() {
    }

    public void setRespondedby(String respondedby) {
        this.respondedby = respondedby;
    }

    public void setRespondeddp(String respondeddp) {
        this.respondeddp = respondeddp;
    }

    public void setRespondeduid(String respondeduid) {
        this.respondeduid = respondeduid;
    }

    public void setRespondedto(String respondedto) {
        this.respondedto = respondedto;
    }

    public void setRespondedtype(String respondedtype) {
        this.respondedtype = respondedtype;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public void setIndividualKey(String individualKey) {
        this.individualKey = individualKey;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public void setTypeyourown(String typeyourown) {
        this.typeyourown = typeyourown;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setEmailofngo(String emailofngo) {
        this.emailofngo = emailofngo;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNameofngo(String nameofngo) {
        this.nameofngo = nameofngo;
    }

    public void setPhoneofngo(String phoneofngo) {
        this.phoneofngo = phoneofngo;
    }
}

