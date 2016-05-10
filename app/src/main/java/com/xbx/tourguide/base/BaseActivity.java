package com.xbx.tourguide.base;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.ui.MyOrderListActivity;
import com.xbx.tourguide.ui.OrderRemainActivity;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;

/**
 * Created by shuzhen on 2016/3/29.
 */
public class BaseActivity extends FragmentActivity {

    protected Intent intent = new Intent();
    private OrderNumberDao orderNumberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    protected void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        JPushInterface.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        JPushInterface.onPause(this);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (orderReceiver != null)
//            unregisterReceiver(orderReceiver);
    }

    /**
     * @param cls
     * @param isFinish true 关闭
     */
    protected void startIntent(Class<?> cls, boolean isFinish) {
        intent.setClass(this, cls);
        startActivity(intent);
        if (isFinish)
            finish();
    }

//    public class OrderReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            orderNumberDao = new OrderNumberDao(context);
//            Intent orderIntent = new Intent(context, OrderRemainActivity.class);
//            String serverType = intent.getStringExtra("serverType");
//            String orderNum = intent.getStringExtra("orderNum");
//            switch (Integer.valueOf(serverType)) {//100-即时 101-预约 102-取消 103-用户已支付
//                case 100:
//                    //将新接受的及时订单添加到sqlite
//                    ContentValues values = new ContentValues();
//                    values.put("num", orderNum);
//                    values.put("date", System.currentTimeMillis() + "");
//                    orderNumberDao.insertLast(values);
//                    break;
//                case 101:
//                    if (VerifyUtil.isNullOrEmpty(Cookie.getAppointmentOrder(context))) {
//                        Cookie.putAppointmentOrder(context, orderNum);
//                    } else {
//                        return;
//                    }
//                    break;
//                case 102:
//                    if (Util.isAction(context)) {
//                        BaseActivity.this.startActivity(new Intent(context, MyOrderListActivity.class));
//                        ToastUtils.showShort(context, "有订单被用户取消,请注意查看");
//                        return;
//                    }
//                    break;
//                case 103://只有预约服务有推送
//                    if (Util.isAction(context)) {
//                        BaseActivity.this.startActivity(new Intent(context, MyOrderListActivity.class));
//                        ToastUtils.showShort(context, "有预约订单用户支付定金，请注意查看");
//                        return;
//                    }
//                    break;
//            }
//
//            if (Util.isAction(context)) {
//                //dialog是否显示
//                if (!Cookie.getIsDialog(context)) {
//                    SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
//                    if (sqLiteOrderBean.getNum() != null) {
//                        if (Util.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
//                            orderNumberDao.clear();
//                            return;
//                        }
//                        orderNum = sqLiteOrderBean.getNum();
//                        orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
//                    } else {
//                        if (!VerifyUtil.isNullOrEmpty(Cookie.getAppointmentOrder(context))) {//预约
//                            Cookie.putIsDialog(context, true);
//                            orderIntent.putExtra("serverType", "1");
//                            orderIntent.putExtra("orderNumber", Cookie.getAppointmentOrder(context));
//                            BaseActivity.this.startActivity(orderIntent);
//                        }
//                    }
//                    //
//                    Cookie.putIsDialog(context, true);
//                    orderIntent.putExtra("serverType", "0");
//                    orderIntent.putExtra("orderNumber", orderNum);
//                    BaseActivity.this.startActivity(orderIntent);
//                }
//            }
//        }
//    }
}
