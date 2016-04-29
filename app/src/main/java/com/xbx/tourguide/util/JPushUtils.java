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
        if (!Cookie.getIsDialog(context)) {
            OrderNumberDao orderNumberDao = new OrderNumberDao(context);
            SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
            if (sqLiteOrderBean.getNum() != null) {//有缓存
                if (Util.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
                    orderNumberDao.clear();
                    return;
                }
                Cookie.putIsDialog(context, true);
                Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                orderIntent.putExtra("serverType", "0");
                orderIntent.putExtra("orderNumber", sqLiteOrderBean.getNum());
                orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderIntent);
            } else {//查看是否有预约服务
                if (!VerifyUtil.isNullOrEmpty(Cookie.getAppointmentOrder(context))) {
                    Cookie.putIsDialog(context, true);
                    Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                    orderIntent.putExtra("serverType", "1");
                    orderIntent.putExtra("orderNumber", "");
                    orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(orderIntent);
                }
            }
        }
    }
}
