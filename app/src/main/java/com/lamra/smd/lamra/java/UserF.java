package com.lamra.smd.lamra.java;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NimraArif on 4/27/2018.
 */

public class UserF {
    private int Uid;
    private String username;
    private String Name;
    private String Bio;
    private String Email;
    private String phoneNo;
    private String ProfilePic;
    private ArrayList<Post> posts;
    private ArrayList<String> Followers;
    private ArrayList<String> Following;



    public UserF() {
    }

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

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<String> getFollowers() {
        return Followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        Followers = followers;
    }

    public ArrayList<String> getFollowing() {
        return Following;
    }

    public void setFollowing(ArrayList<String> following) {
        Following = following;
    }
}
