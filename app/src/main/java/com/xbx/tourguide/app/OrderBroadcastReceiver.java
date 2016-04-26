package com.xbx.tourguide.app;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.ui.OrderRemainActivity;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

/**
 * Created by xbx on 2016/4/25.
 */
public class OrderBroadcastReceiver extends BroadcastReceiver {

    private OrderNumberDao orderNumberDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        orderNumberDao = new OrderNumberDao(context);
        String action = intent.getAction();
        if (action.equals(XbxTGApplication.BROADCAST)) {

            String orderNum = intent.getStringExtra("orderNumber");
            String serverType = intent.getStringExtra("serverType");

            LogUtils.e("---orderNum+serverType:" + orderNum + "--" + serverType);

            if (serverType.equals("0")) {
                ToastUtils.showShort(context, "新的及時服務");
                //将新接受的及时订单添加到sqlite
                ContentValues values = new ContentValues();
                values.put("num", orderNum);
                orderNumberDao.insertLast(values);
                orderNumberDao.selectAll();
            } else if (serverType.equals("1")) {
                if(!Cookie.getAppointmentOrder(context)){
                    Cookie.putAppointmentOrder(context, true);
                    ToastUtils.showShort(context, "新的預約服務");
                }else{
                    return;
                }
            }

            if (!Cookie.getIsDialog(context)) {
                if (serverType.equals("0")) {
                    orderNum = orderNumberDao.selectFirst();
                    if (VerifyUtil.isNullOrEmpty(orderNum)) {
                        if (Cookie.getAppointmentOrder(context)) {
                            Cookie.putIsDialog(context, true);
                            Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                            orderIntent.putExtra("serverType", "1");
                            orderIntent.putExtra("orderNumber", "");
                            orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(orderIntent);
                        }
                    }
                }

                Cookie.putIsDialog(context, true);
                Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                orderIntent.putExtra("serverType", serverType);
                orderIntent.putExtra("orderNumber", orderNum);
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderIntent);
            }
        }
    }
}
