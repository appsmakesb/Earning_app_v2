package com.burhanstore.earningmaster;

public class BaseUrl {

    public static final String EXTRA_PARAMS = "main";
    public static final String BASE_API = "api/";
    //SetUP Admin URL
    public static final String ADMIN_URL = BuildConfig.WEB_URL;//"http://ear2.softwarelabbd.com/";
    //
    public static final String BASE_URL = ADMIN_URL + BASE_API;
    public static final String LOGIN_API_APP = BASE_URL + "login";
    public static final String REGISTER_API_APP = BASE_URL + "registers";
    public static final String UPDATE_PROFILE_APP = BASE_URL + "update_profile.php";
    public static final String FORGOT_PASSWORD_APP = BASE_URL + "forget_pass";
    public static final String UPDATE_POINTS_APP = BASE_URL + "update_points";
    public static final String RESET_PASSWORD_APP = BASE_URL + "rest_pass";
    public static final String TOP_USER = BASE_URL + "top_users";
    public static final String LOAD_USER_IMAGE_APP = BASE_URL + "images/";

    public static final String DATA_MAIN = ADMIN_URL + "someconfig";
    public static final String APP_ROOT = DATA_MAIN + "/app";
    public static final String APP_DATA_API_URL = APP_ROOT + "/api/app-data.php";
    public static final String Everyday_Gift_API_URL = APP_ROOT + "/api/everydaygift.json";

    //
    public static final String LUCKY_WIN_API = BASE_URL + "lucky_win.php";


}