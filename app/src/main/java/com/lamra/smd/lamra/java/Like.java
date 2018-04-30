package com.lamra.smd.lamra.java;

import java.io.Serializable;

/**
 * Created by NimraArif on 4/27/2018.
 */

public class Like implements Serializable {
    private String Uid;
    private String name;

    public Like() {
    }

    public Like(String uid, String name) {
        Uid = uid;
        this.name = name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
