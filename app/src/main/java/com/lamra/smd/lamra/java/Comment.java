package com.lamra.smd.lamra.java;

import java.io.Serializable;

/**
 * Created by NimraArif on 4/27/2018.
 */

public class Comment implements Serializable {
    private String Cid;
    private String Uid;
    private String description;

    public Comment(String cid, String uid, String description) {
        Cid = cid;
        Uid = uid;
        this.description = description;
    }

    public Comment() {
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
