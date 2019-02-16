package com.hsy.study.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 管理所有{@link Activity}
 * @author haosiyuan
 * @date 2019/2/14 3:33 PM
 */
public class AppManager {

    protected final String TAG = this.getClass().getSimpleName();

    private static volatile AppManager mAppManager;

    private Application mApplication;
    /**
     * 管理所有{@link Activity} 集合
     */
    private List<Activity> mActivityList;
    /**
     * 当前前台{@link Activity}
     */
    private Activity mCurrentActivity;

    private AppManager() {}

    /**
     * 单例模式
     * @return
     */
    public static AppManager getInstance(){
        if (mAppManager == null) {
            synchronized (AppManager.class) {
                if (mAppManager == null){
                    mAppManager = new AppManager();
                }
            }
        }
        return mAppManager;
    }

    /**
     * 将当前{@link Activity} 赋值给mCurrentActivity 在{@link Activity#onResume()}中调用
     * @param activity 当前{@link Activity}
     */
    public void setCurrentActivity(@NonNull Activity activity) {
        this.mCurrentActivity = activity;
    }

    /**
     * 返回当前前台的 {@code mCurrentActivity} 或者最后一个{@link Activity#onResume()}的{@link Activity}
     * @return
     */
    @Nullable
    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 获取最后一个{@link #addActivity(Activity)} 的{@link Activity}
     * @return
     */
    @Nullable
    public Activity getTopActivity() {
        if (mActivityList == null || mActivityList.size() <= 0) {
            return null;
        }
        return mActivityList.get(mActivityList.size() - 1);
    }
    /**
     * 返回一个存储所有未销毁的 {@link Activity} 的集合
     * @return
     */
    public List<Activity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 添加 {@link Activity} 到集合
     * @param activity
     */
    public void addActivity(Activity activity) {
        synchronized (AppManager.class) {
            List<Activity> activities = getActivityList();
            if (!activities.contains(activity)) {
                activities.add(activity);
            }
        }
    }

    /**
     * 删除集合中指定的 {@link Activity}实例
     * @param activity
     */
    public void removeActivity(Activity activity){
        if (mActivityList == null) {
            return;
        }
        synchronized (AppManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 关闭指定的 {@link Activity} 的所有实例
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass){
        if (mActivityList == null) {
            return;
        }

        synchronized (AppManager.class) {
            Iterator<Activity> iterator = mActivityList.iterator();
            while (iterator.hasNext()){
                Activity activity = iterator.next();

                if (activity.getClass().equals(activityClass)){
                    iterator.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 指定的 {@link Activity} class 是否存活
     * @param activityClass
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (mActivityList == null) {
            return false;
        }

        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定 {@link Activity} class的所有实例，没有则返回 长度为0的list
     * @param activityClass
     * @return
     */
    public List<Activity> findActivity(Class<?> activityClass) {
        List<Activity> activities = new ArrayList<>();

        if (mActivityList == null){
            return activities;
        }

        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activities.add(activity);
            }
        }
        return activities;
    }

    /**
     * 关闭所有 {@link Activity}
     */
    public void killAll() {
        if (mActivityList == null){
            return;
        }
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = mActivityList.iterator();
            while (iterator.hasNext()){
                Activity activity = iterator.next();
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 关闭所有 {@link Activity},排除指定的 {@link Activity}
     *
     * @param exceptActivityClasses
     */
    public void killAllExcept(Class<?>... exceptActivityClasses) {
        if (mActivityList == null){
            return;
        }
        List<Class<?>> exceptList = Arrays.asList(exceptActivityClasses);
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = mActivityList.iterator();

            while (iterator.hasNext()) {

                Activity activity = iterator.next();
                if (exceptList.contains(activity.getClass())){
                    continue;
                }
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 关闭所有 {@link Activity},排除指定的 {@link Activity}
     *
     * @param exceptActivityClasses
     */
    public void killAllExcept(String... exceptActivityClasses) {
        if (mActivityList == null){
            return;
        }
        List<String> exceptList = Arrays.asList(exceptActivityClasses);
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = mActivityList.iterator();

            while (iterator.hasNext()) {

                Activity activity = iterator.next();
                if (exceptList.contains(activity.getClass().getName())){
                    continue;
                }
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 关闭应用程序
     */
    public void appExit() {
        killAll();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

}
