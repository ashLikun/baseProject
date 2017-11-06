package com.doludolu.baseproject.mode.javabean.base;

/**
 * Created by Administrator on 2016/6/20.
 */
public class AppUpdata {
    /**
     * ID 		int		自增ID
     * VersionNum 	int		版本号
     * VersionName	varchar(50)	版本名
     * UpdateTiime	datetime	更新时间
     * <p/>
     * AppPath		varchar(100)	路径
     * IsUPdate                     是否强制升级
     * VersionIntro    nvarchar(200)   版本介绍
     */
    private boolean IsUPdate;
    private int VersionNum;
    private String AppPath;
    private String VersionIntro;
    private String VersionName;

    public boolean isUPdate() {
        return IsUPdate;
    }

    public void setIsUPdate(boolean UPdate) {
        IsUPdate = UPdate;
    }

    public int getVersionNum() {
        return VersionNum;
    }

    public void setVersionNum(int versionNum) {
        VersionNum = versionNum;
    }

    public String getAppPath() {
        return AppPath;
    }

    public void setAppPath(String appPath) {
        AppPath = appPath;
    }

    public String getVersionIntro() {
        return VersionIntro;
    }

    public void setVersionIntro(String versionIntro) {
        VersionIntro = versionIntro;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }
}
