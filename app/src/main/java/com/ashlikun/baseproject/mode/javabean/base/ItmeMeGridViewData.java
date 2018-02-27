package com.ashlikun.baseproject.mode.javabean.base;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ItmeMeGridViewData {
    private int pictureId;//图片id
    private String title;//标题
    private String activityName;//


    public ItmeMeGridViewData(int pictureId, String title, String activityName) {
        this.pictureId = pictureId;
        this.title = title;
        this.activityName = activityName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
