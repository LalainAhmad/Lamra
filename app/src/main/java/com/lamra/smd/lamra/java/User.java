package com.lamra.smd.lamra.java;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by 10 on 4/25/2018.
 */

@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int Uid;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "bio")
    private String Bio;

    @ColumnInfo(name = "email")
    private String Email;

    @ColumnInfo(name = "phoneNo")
    private String phoneNo;

    @ColumnInfo(name = "ProfilePic")
    private String ProfilePic;

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }
//    ArrayList<String> posts;
//    Feed feed;
//    ArrayList <String> Followers;
//    ArrayList<String> Following;

}

