package com.lamra.smd.lamra.java;

/**
 * Created by 10 on 4/25/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

    /**
     * Created by ishrat.fatima on 3/8/2018.
     */

    @Dao
    public interface UserDao {

        @Query("SELECT * FROM User")
        List<User> getAll();

        @Query("SELECT * FROM User where name LIKE  :Name")
        User findByName(String Name);

        @Query("SELECT COUNT(*) from User")
        int countUsers();

        @Insert
        void insertAll(User... users);

        @Delete
        void delete(User user);
    }