package com.lamra.smd.lamra.java;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NimraArif on 4/27/2018.
 */

public class Post implements Serializable{
    private String uid;
    private String pid;
    private String userName;
    private String profilePic;
    private String picture;
    private String description;
    private ArrayList<Comment> comments;
    public ArrayList<Like> likes;

    public Post() {
    }

    public Post(String name, String profilePic,String UID, String pid, String picture)
    {
        this.userName=name;
        if(profilePic!=null)
            this.profilePic=profilePic;
        else
            this.profilePic=null;
        this.uid=UID;
        this.pid=pid;
        this.picture=picture;
    }

    public Post(UserF u, String pid, String picture,String description)
    {
        uid=u.getUid();
        userName=u.getName();
        profilePic=u.getProfilepic();
        this.pid=pid;
        this.picture=picture;
        this.description=description;
    }


    public Post(String uid, String pid, String userName, String profilePic, String picture, String description, ArrayList<Comment> comments, ArrayList<Like> likes) {
        this.uid = uid;
        this.pid = pid;
        this.userName = userName;
        this.profilePic = profilePic;
        this.picture = picture;
        this.description = description;
        this.comments = comments;
        this.likes = likes;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}