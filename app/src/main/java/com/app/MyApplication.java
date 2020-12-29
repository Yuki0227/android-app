package com.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.app.task.entity.TaskAssign;
import com.app.util.User;
import com.smailnet.emailkit.EmailKit;
import com.smailnet.microkv.MicroKV;

import org.litepal.LitePal;

import java.util.List;

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static EmailKit.Config config;
    private static User user;
    private static List<TaskAssign> taskList;
    //所有的被指派的任务
    private static List<TaskAssign> allAssignedTask;
    //所有的被自己创建的任务
    private static List<TaskAssign> allCreatedTask;
    //数据库中的所有用户
    private static List<User> allUsers;

    @Override
    public void onCreate() {
        super.onCreate();
        EmailKit.initialize(this);
        MicroKV.initialize(this);
        LitePal.initialize(this);
        user = null;
        allUsers = null;
        taskList = null;
        allAssignedTask = null;
        allCreatedTask = null;
        context = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try {
            restoreLoginStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void restoreLoginStatus() {

        User user = null;
        MicroKV kv = MicroKV.defaultMicroKV();
        if (kv.containsKV("id")) {
            user = new User(kv.getInt("id"), kv.getString("name"), kv.getString("password"));
            Log.e("restoreUser", user.toString());
        }

        MyApplication.setUser(user);
    }

    public static Context getContext() {
        return context;
    }

    public static void setConfig(EmailKit.Config config) {
        MyApplication.config = config;
    }

    public static EmailKit.Config getConfig() {
        return config;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        if (user != null) {
            MicroKV.defaultMicroKV()
                    .setKV("id", user.getId())
                    .setKV("name", user.getName())
                    .setKV("password", user.getPassword())
                    .save();

        } else {
            MicroKV.defaultMicroKV().clear();
        }
        MyApplication.user = user;
    }

    public static void setUser(int id, String name, String password) {
        MyApplication.user = new User(id, name, password);
        MicroKV.defaultMicroKV()
                .setKV("id", user.getId())
                .setKV("name", user.getName())
                .setKV("password", user.getPassword())
                .save();
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static List<TaskAssign> getTaskList() {
        return taskList;
    }

    public static void setTaskList(List<TaskAssign> taskList) {
        MyApplication.taskList = taskList;
    }

    public static List<TaskAssign> getAllAssignedTask() {
        return allAssignedTask;
    }

    public static void setAllAssignedTask(List<TaskAssign> allAssignedTask) {
        MyApplication.allAssignedTask = allAssignedTask;
    }

    public static List<TaskAssign> getAllCreatedTask() {
        return allCreatedTask;
    }

    public static void setAllCreatedTask(List<TaskAssign> allCreatedTask) {
        MyApplication.allCreatedTask = allCreatedTask;
    }

    public static List<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(List<User> allUsers) {
        MyApplication.allUsers = allUsers;
    }
}
