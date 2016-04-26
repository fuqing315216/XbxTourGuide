package com.xbx.tourguide.jsonparse;

import com.xbx.tourguide.util.VerifyUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 2016/2/26.
 */
public class UtilParse {

    public static int getRequestCode(String responseResult) {
        if (VerifyUtil.isEmailNumber(responseResult))
            return -1;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseResult);
            if (checkTag(jsonObject, "code")) {
                return jsonObject.getInt("code");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getRequestMsg(String responseResult) {
        if (VerifyUtil.isEmailNumber(responseResult))
            return "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseResult);
            if (checkTag(jsonObject, "msg")) {
                return jsonObject.getString("msg");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "数据异常";
    }

    public static String getRequestData(String responseResult) {
        if (VerifyUtil.isEmailNumber(responseResult))
            return "";
        try {
            JSONObject jsonObject = new JSONObject(responseResult);
            if (checkTag(jsonObject, "data")) {
                return jsonObject.getString("data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkTag(JSONObject jObject, String jsonTag) throws JSONException {
        if (jObject.has(jsonTag) && !VerifyUtil.isEmailNumber(jObject.getString(jsonTag))) {
            return true;
        }
        return false;
    }
}
