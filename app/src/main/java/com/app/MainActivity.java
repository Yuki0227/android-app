package com.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.bbs.BBSFragment;
import com.app.email.EmailFragment;
import com.app.email.controls.Controls;
import com.app.exercise.ExerciseFragment;
import com.app.login.LoginActivity;
import com.app.task.TaskAddActivity;
import com.app.task.TaskFactory;
import com.app.task.TaskFragment;
import com.app.userPage.UserFragment;
import com.app.userPage.UserOnlineFragment;
import com.app.util.CommonUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout taskLinear;
    LinearLayout emailLinear;
    LinearLayout bbsLinear;
    LinearLayout exerciseLinear;
    LinearLayout userLinear;

    TaskFragment taskFragment;
    EmailFragment emailFragment;
    BBSFragment bbsFragment;
    ExerciseFragment exerciseFragment;
    UserFragment userFragment;
    UserOnlineFragment useronFragment;
    private FragmentManager mfragmentManger;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("","Oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskLinear = findViewById(R.id.navigation_task);
        emailLinear = findViewById(R.id.navigation_email);
        bbsLinear = findViewById(R.id.navigation_bbs);
        exerciseLinear = findViewById(R.id.navigation_exercise);
        userLinear = findViewById(R.id.navigation_user);
        taskLinear.setOnClickListener(this);
        emailLinear.setOnClickListener(this);
        bbsLinear.setOnClickListener(this);
        exerciseLinear.setOnClickListener(this);
        userLinear.setOnClickListener(this);
        mfragmentManger = getSupportFragmentManager();
        taskLinear.performClick();

        //获得所有用户(预处理)
        TaskFactory.getAllUsers();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = mfragmentManger.beginTransaction();
        //只能是局部变量，不能为全局变量，否则不能重复commit
        //FragmentTransaction只能使用一次
        hideAllFragment(fragmentTransaction);
        switch (view.getId()) {
            case R.id.navigation_task:
                setAllFalse();
                taskLinear.setSelected(true);
                if (taskFragment == null) {
                    taskFragment = new TaskFragment("Task");
                    fragmentTransaction.add(R.id.fragment_frame, taskFragment);
                } else {
                    fragmentTransaction.show(taskFragment);
                }
                break;
            case R.id.navigation_email:
                setAllFalse();
                emailLinear.setSelected(true);
                if (emailFragment == null) {
                    emailFragment = new EmailFragment();
                    fragmentTransaction.add(R.id.fragment_frame, emailFragment);
                } else {
                    fragmentTransaction.show(emailFragment);
                }
                break;
            case R.id.navigation_bbs:
                setAllFalse();
                bbsLinear.setSelected(true);
                if (bbsFragment == null) {
                    bbsFragment = new BBSFragment("BBS");
                    fragmentTransaction.add(R.id.fragment_frame, bbsFragment);
                } else {
                    fragmentTransaction.show(bbsFragment);
                }
                break;
            case R.id.navigation_exercise:
                setAllFalse();
                exerciseLinear.setSelected(true);
                if (exerciseFragment == null) {
                    exerciseFragment = new ExerciseFragment("Exercise");
                    fragmentTransaction.add(R.id.fragment_frame, exerciseFragment);
                } else {
                    fragmentTransaction.show(exerciseFragment);
                }
                break;
            case R.id.navigation_user:
                setAllFalse();
                userLinear.setSelected(true);
                if(MyApplication.getUser()== null){
                    if (userFragment == null) {
                        userFragment = new UserFragment("User");
                        fragmentTransaction.add(R.id.fragment_frame, userFragment);
                    } else {
                        fragmentTransaction.show(userFragment);
                    }
                }else {
                    if (useronFragment == null) {
                        useronFragment = new UserOnlineFragment(MyApplication.getUser().getName());
                        fragmentTransaction.add(R.id.fragment_frame, useronFragment);
                    } else {
                        fragmentTransaction.show(useronFragment);
                    }
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();//记得必须要commit,否则没有效果
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (taskFragment != null) {
            fragmentTransaction.hide(taskFragment);
        }
        if (emailFragment != null) {
            fragmentTransaction.hide(emailFragment);
        }
        if (bbsFragment != null) {
            fragmentTransaction.hide(bbsFragment);
        }
        if (exerciseFragment != null) {
            fragmentTransaction.hide(exerciseFragment);
        }
        if (userFragment != null) {
            fragmentTransaction.hide(userFragment);
        }
        if (useronFragment != null) {
            fragmentTransaction.hide(useronFragment);
        }
    }

    private void setAllFalse() {
        taskLinear.setSelected(false);
        emailLinear.setSelected(false);
        bbsLinear.setSelected(false);
        exerciseLinear.setSelected(false);
        userLinear.setSelected(false);
    }



    @Override
    protected void onRestart() {

        super.onRestart();
        if(userLinear.isSelected()){
        //模拟点击User
        getWindow().getDecorView().setId(R.id.navigation_user);
        onClick(getWindow().getDecorView());}

    }
    public void finish(){
        super.finish();
        MyApplication.setUser(null);
        userFragment=null;
        useronFragment = null;
    }

}
