package com.aryomtech.nsabilaspur;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class Leader {

    private String email;

    public Leader(String email) {
        this.email = email;
    }

    public Leader() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
