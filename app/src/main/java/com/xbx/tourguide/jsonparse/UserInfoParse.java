package com.xbx.tourguide.jsonparse;

import android.content.Context;

import com.xbx.tourguide.beans.GoingBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xbx on 2016/4/26.
 */
public class UserInfoParse {
    /**
     * 解析loign返回数据中的uid
     *
     * @param responseResult
     * @return
     */
    public static String getUid(String responseResult) {
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "uid")) {
                return jsonObject.getString("uid");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解析login返回的user_info
     *
     * @param responseResult
     * @return
     */
    public static TourGuideInfoBeans getUserInfo(String responseResult) {
        TourGuideInfoBeans tourGuideInfoBeans = null;

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "user_info")) {
                tourGuideInfoBeans = JsonUtils.object(jsonObject.getString("user_info"), TourGuideInfoBeans.class);
                return tourGuideInfoBeans;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tourGuideInfoBeans;
    }

    /**
     * 解析loign返回数据中的token
     *
     * @param responseResult
     * @return
     */
    public static String getLogToken(String responseResult) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "login_token")) {
                return jsonObject.getString("login_token");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解析loign返回数据中的mobile
     *
     * @param responseResult
     * @return
     */
    public static String getMobile(String responseResult) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "user_info")) {
                JSONObject jsonObject2 = new JSONObject(jsonObject.getString("user_info"));
                if (UtilParse.checkTag(jsonObject2, "mobile")) {
                    return jsonObject2.getString("mobile");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVerifyCode(String responseResult) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "vierfy_code")) {
                return jsonObject.getString("vierfy_code");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDataType(String responseResult) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "user_unfinished_order")) {
                JSONObject jsonObject2 = new JSONObject(jsonObject.getString("user_unfinished_order"));
                if (UtilParse.checkTag(jsonObject2, "data_type")) {
                    return jsonObject2.getString("data_type");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static GoingBeans getGoing(String responseResult) {
        GoingBeans goingBeans = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "user_unfinished_order")) {
                JSONObject jsonObject2 = new JSONObject(jsonObject.getString("user_unfinished_order"));
                if (UtilParse.checkTag(jsonObject2, "going")) {
                    goingBeans = JsonUtils.object(jsonObject2.getString("going"), GoingBeans.class);
                    return goingBeans;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goingBeans;
    }

    public static void putUserInfo(Context context, String responseResult, TourGuideInfoBeans tourGuideInfoBeans) {
        try {
            JSONObject jsonObject = new JSONObject(responseResult);
            if (UtilParse.checkTag(jsonObject, "user_info")) {
                jsonObject.put("user_info", JsonUtils.toJson(tourGuideInfoBeans));
                SPUtils.put(context, Constant.USER_INFO,jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
