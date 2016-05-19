package com.xbx.tourguide.util;

import android.content.Context;
import android.content.Intent;

import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.ui.OrderRemainActivity;

/**
 * Created by xbx on 2016/4/28.
 */
public class JPushUtils {

    public static void isShowDialog(Context context) {
        if (!(Boolean) SPUtils.get(context, Constant.LOGIN_OUT, false)) {
            OrderNumberDao orderNumberDao = new OrderNumberDao(context);
            SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
            if (sqLiteOrderBean.getNum() != null) {//有缓存
                if (Util.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
                    orderNumberDao.clear();
                    return;
                }
                SPUtils.put(context, Constant.LOGIN_OUT, true);
                Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                orderIntent.putExtra("serverType", "0");
                orderIntent.putExtra("orderNumber", sqLiteOrderBean.getNum());
                orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderIntent);
            } else {//查看是否有预约服务
                String appointOrder = (String) SPUtils.get(context, Constant.APPOINT_ORDER, "");
                if (appointOrder != null && !VerifyUtil.isNullOrEmpty(appointOrder)) {
                    SPUtils.put(context, "isDialog", true);
                    Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                    orderIntent.putExtra("serverType", "1");
                    orderIntent.putExtra("orderNumber", appointOrder);
                    orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(orderIntent);
                }
            }
        }
    }
}
