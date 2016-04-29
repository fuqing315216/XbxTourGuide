package com.xbx.tourguide.app;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.ui.MyOrderListActivity;
import com.xbx.tourguide.ui.OrderRemainActivity;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xbx on 2016/4/25.
 */
public class LocalOrderReceiver extends BroadcastReceiver {

    private OrderNumberDao orderNumberDao;

    @Override
    public void onReceive(Context context, Intent intent) {
//        orderNumberDao = new OrderNumberDao(context);
//        String action = intent.getAction();
//        if (action.equals(XbxTGApplication.BROADCAST)) {
//            Intent orderIntent = new Intent(context, OrderRemainActivity.class);
//            String orderNum = intent.getStringExtra("orderNumber");
//            String serverType = intent.getStringExtra("serverType");
//
//            String orderTime = SystemClock.currentThreadTimeMillis() + "";
//
//            LogUtils.e("---order:" + orderNum + "--" + serverType + "--" + orderTime);
//
//            if ("2".equals(serverType)) {//即时服务 服务未开始 用户却取消服务
//                context.startActivity(new Intent(context, MyOrderListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                ToastUtils.showShort(context,"您有个订单已被用户取消！！！请注意查看");
//                return;
//            }
//
//            if (serverType.equals("0")) {//新的及時服務
//                ToastUtils.showShort(context, "新的及時服務");
//                //将新接受的及时订单添加到sqlite
//                ContentValues values = new ContentValues();
//                values.put("num", orderNum);
//                values.put("date", orderTime);
//                orderNumberDao.insertLast(values);
//                orderNumberDao.selectAll();
//            } else if (serverType.equals("1")) {//新的預約服務
//                if (!Cookie.getAppointmentOrder(context)) {
//                    ToastUtils.showShort(context, "新的預約服務");
//                    Cookie.putAppointmentOrder(context, true);
//                } else {
//                    return;
//                }
//            }
//
//            LogUtils.e("---Cookie.getIsDialog(context):" + Cookie.getIsDialog(context));
//            //dialog是否显示
//            if (!Cookie.getIsDialog(context)) {
//                if (serverType.equals("0")) {
//                    SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
//                    if (sqLiteOrderBean.getNum() != null) {
//                        if (Util.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
//                            orderNumberDao.clear();
//                            return;
//                        }
//                        orderNum = sqLiteOrderBean.getNum();
//                        orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
//                    } else {
//                        if (Cookie.getAppointmentOrder(context)) {
//                            orderIntent.putExtra("serverType", "1");
//                            orderIntent.putExtra("orderNumber", "");
//                            orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(orderIntent);
//                        }
//                    }
//                }
//
//                Cookie.putIsDialog(context, true);
//                orderIntent.putExtra("serverType", serverType);
//                orderIntent.putExtra("orderNumber", orderNum);
//                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(orderIntent);
//            }
//        }
    }
}
