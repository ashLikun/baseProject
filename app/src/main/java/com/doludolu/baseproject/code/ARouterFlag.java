package com.doludolu.baseproject.code;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/8 0008　21:18
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Arouter用到的String标识
 */

public interface ARouterFlag {
    /********************************************************************************************
     *                                           path
     ********************************************************************************************/
    String LOGIN = "/activity/login";
    String WELCOME = "/activity/WELCOME";
    String WEBVIEW = "/activity/WEBVIEW";
    String REGISTER = "/activity/REGISTER";
    String TEST = "/activity/test";
    String HOME = "/activity/home";
    String AMEND_PASSWORD = "/activity/AMEND_PASSWORD";
    String UPDATA_PASSWORD = "/activity/UPDATA_PASSWORD";
    String LOGIN_SELECT = "/activity/LOGIN_SELECT";
    /********************************************************************************************
     *                                           Intent  Flag   int
     ********************************************************************************************/
    String FLAG_TYPE = "FLAG_TYPE";//int
    String FLAG_ID = "FLAG_ID";//int
    String FLAG_USER_ID = "FLAG_USER_ID";//int
    /********************************************************************************************
     *                                           Intent  Flag   boolean
     ********************************************************************************************/
    String FLAG_IS_MULTI = "FLAG_IS_MULTI";//是否是多选
    /********************************************************************************************
     *                                           Intent  Flag   String
     ********************************************************************************************/
    String FLAG_TITLE = "FLAG_TITLE";//String
    String FLAG_URL = "FLAG_URL";//String
    String FLAG_SUB_TITLE = "FLAG_SUB_TITLE";//String
    /********************************************************************************************
     *                                           Intent Flag   object
     ********************************************************************************************/
    String FLAG_DATA = "FLAG_DATA";//object
}
