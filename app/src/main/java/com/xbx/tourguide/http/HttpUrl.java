package com.xbx.tourguide.http;


/**
 * Created by shuzhen on 2016/4/5.
 */
public class HttpUrl {

    public static String URL = "http://192.168.1.27/yueyou/Api/";
//    public static String URL = "http://192.168.1.24/yueyou/Api/";

    //网络服务器
//    public static String URL = "http://123.56.194.208/yueyou/index.php/Api/";
    //測試
//    public static String URL = "http://demo.xbx121.com/Api/";
//    public static String IMAGE_URL = "http://192.168.1.24/yueyou";

    //注册
    public static String REGISTER = URL + "user/register.json";
    //注册导游基本信息
    public static String REGISTER_GUIDE_INFO = URL + "user/register_guide_info.json";
    //获取验证码
    public static String GET_VERIFYCODE = URL + "Index/send_verifyCode.json";
    //登录
    public static String LOGIN = URL + "user/login.json";
    //登出
    public static String LOGIN_OUT = URL + "user/login_out.json";
    //找回密码
    public static String UPDATE_PW = URL + "user/retrieve_password.json";
    //服务时间设置
    public static String SETTING_SERVICETIME = URL + "user/free_time.json";
    //获取服务时间
    public static String GET_SERVICETIME = URL + "user/free_time.json";
    //开始服务
    public static String START_SERVICE = URL + "index/online.json";
    //我的订单
    public static String MY_ORDER = URL + "Order/guide_my_order.json";
    //订单详情
    public static String MY_ORDER_DETAIL = URL + "Order/guide_order_detail.json";
    //上传经纬度
    public static String POST_LON_LAT = URL + "user/upload_address.json";
    //导游订单过程
    public static String ORDER_PROCESS = URL + "Order/guide_order_process.json";
    //开始服务
    public static String START_SERVER = URL + "Index/start_server.json";
    //结束服务
    public static String END_SERVER = URL + "Index/end_server.json";
    //确认/拒绝订单
    public static String CONFIRM_ORDER = URL + "Order/guide_confirm_order.json";
    //选择省市
    public static String SELECT_PROVINCE_CITY = URL + "Index/city_list.json";
    //确认修改
    public static String UPDATE_INFO = URL + "user/change_head_image.json";
    //意见反馈
    public static String FEED_BACK = URL + "Index/upload_feedback.json";
    //获取导游详情
    public static String GUIDE_DETAIL = URL + "user/guide_main.json";
    //个人主页修改
    public static String GUIDE_MAIN = URL + "user/guide_main.json";
    //版本更新
    public static String VERSION_UPDATE = URL + "Index/version_update.json";
}
