package com.xbx.tourguide.base;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.ui.LoginActivity;
import com.xbx.tourguide.ui.MyOrderListActivity;
import com.xbx.tourguide.ui.OrderRemainActivity;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Utils;
import com.xbx.tourguide.util.VerifyUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shuzhen on 2016/3/29.
 */
public class BaseActivity extends FragmentActivity {

    protected Intent intent = new Intent();
    private OrderNumberDao orderNumberDao;

    private Timer timer = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    SQLiteOrderBean sqlBean = new OrderNumberDao(BaseActivity.this).selectFirst();
                    if (sqlBean.getNum() == null || VerifyUtil.isNullOrEmpty(sqlBean.getNum())
                            || getIsDialog()) {
                        timer.cancel();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushOneActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userInfo = (String) SPUtils.get(this, Constant.USER_INFO, "");
        if (userInfo != null && !VerifyUtil.isNullOrEmpty(userInfo)) {
            if ("1".equals(SPUtils.get(this, Constant.ONLINE, ""))
                    && (Boolean) SPUtils.get(this, Constant.IS_JPUSH, false)) {
                setTimerTask();
            }
        }
    }

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        JPushInterface.onPause(this);
//    }

    private void setTimerTask() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Utils.isShowDialog(BaseActivity.this);

                Message message = new Message();
                message.what = 0x123;
                handler.sendMessage(message);
            }
        }, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popOneActivity(this);
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

    public class OrderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            orderNumberDao = new OrderNumberDao(context);
            Intent orderIntent = new Intent(context, OrderRemainActivity.class);
            String serverType = intent.getStringExtra("serverType");
            String orderNum = intent.getStringExtra("orderNum");
            switch (Integer.valueOf(serverType)) {//100-即时 101-预约 102-取消 103-用户已支付 301-审核成功 302-审核失败
                case 100:
                    //将新接受的及时订单添加到sqlite
                    ContentValues values = new ContentValues();
                    values.put("num", orderNum);
                    values.put("date", System.currentTimeMillis() + "");
                    orderNumberDao.insertLast(values);
                    break;
                case 101:
                    if (getAppointmentOrder() == null || VerifyUtil.isNullOrEmpty(getAppointmentOrder())) {
                        SPUtils.put(context, Constant.APPOINT_ORDER, orderNum);
                    } else {
                        return;
                    }
                    break;
                case 102:
                    if (Utils.isAction(context)) {
                        ToastUtils.showShort(context, "有订单被用户取消,请注意查看");
                        if (!getIsDialog()) {
                            startActivity(new Intent(context, MyOrderListActivity.class));
                        }
                        return;
                    }
                    break;
                case 103://只有预约服务有推送
                    if (Utils.isAction(context)) {
                        ToastUtils.showShort(context, "有预约订单用户支付定金，请注意查看");
                        if (!getIsDialog()) {
                            startActivity(new Intent(context, MyOrderListActivity.class));
                        }
                        return;
                    }
                    break;
                case 301:
                    if (Utils.isAction(context)) {
                        startActivity(new Intent(context, LoginActivity.class));
                        ToastUtils.showShort(context, "");
                        return;
                    }

                    break;
                case 302:
                    if (Utils.isAction(context)) {
                        startActivity(new Intent(context, LoginActivity.class));
                        ToastUtils.showShort(context, "");
                        return;
                    }
                    break;

            }

            if (Utils.isAction(context) && !getIsDialog()) {
                //dialog是否显示
                    SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
                    if (sqLiteOrderBean.getNum() != null) {
                        if (Utils.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
                            orderNumberDao.clear();
                            return;
                        }
                        orderNum = sqLiteOrderBean.getNum();
                        orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
                    } else {
                        if (getAppointmentOrder() != null && !VerifyUtil.isNullOrEmpty(getAppointmentOrder())) {//预约
                            putIsDialog(true);
                            orderIntent.putExtra("serverType", "1");
                            orderIntent.putExtra("orderNumber", getAppointmentOrder());
                            startActivity(orderIntent);
                        }
                    }
                    //
                    putIsDialog(true);
                    orderIntent.putExtra("serverType", "0");
                    orderIntent.putExtra("orderNumber", orderNum);
                    startActivity(orderIntent);
            }
        }
    }

    private boolean getIsDialog() {
        return (Boolean) SPUtils.get(this, Constant.IS_DIALOG, false);
    }

    private void putIsDialog(boolean flag) {
        SPUtils.put(this, Constant.IS_DIALOG, flag);
    }

    private String getAppointmentOrder() {
        return (String) SPUtils.get(this, Constant.APPOINT_ORDER, "");
    }
}
