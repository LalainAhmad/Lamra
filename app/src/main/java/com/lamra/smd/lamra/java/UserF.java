package com.lamra.smd.lamra.java;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NimraArif on 4/27/2018.
 */

public class UserF implements Serializable {
    private String uid;
    private String username;
    private String name;
    private String bio;
    private String email;
    private String phoneNo;
    private String profilepic;
    private String password;
    private ArrayList<Post> posts;
    private ArrayList<String> followers;
    private ArrayList<String> following;

    public UserF(String uid, String username, String name, String bio, String email, String phoneNo, String profilePic, String password, ArrayList<Post> posts, ArrayList<String> followers, ArrayList<String> following) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.phoneNo = phoneNo;
        profilepic = profilePic;
        this.password = password;
        this.posts = posts;
        this.followers = followers;
        this.following = following;
    }

    public UserF(String username, String name, String bio, String email, String phoneNo, String password) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.posts = posts;
    }

    public UserF() {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }
}
