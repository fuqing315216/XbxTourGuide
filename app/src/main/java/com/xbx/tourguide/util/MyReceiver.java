package com.xbx.tourguide.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.xbx.tourguide.app.XbxTGApplication;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.beans.TourGuideBeans;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.ui.HomeActivity;
import com.xbx.tourguide.ui.MyOrderListActivity;
import com.xbx.tourguide.ui.OrderRemainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Bundle bundle;
//    private OrderNumberDao orderNumberDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "----Cookie.getIsJPush(context):" + Cookie.getIsJPush(context));
        if (!Cookie.getIsJPush(context)) {
            return;
        }

        bundle = intent.getExtras();
//            orderNumberDao = new OrderNumberDao(context);
        Log.d(TAG, "[MyReceiver] intent - " + intent.getAction() + intent.toString());
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "----[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Log.d(TAG, "----Util.isAppOnForeground(context): " + Util.isAction(context));
            OrderDetailBeans orderNumber = JsonUtils.object(bundle.getString(JPushInterface.EXTRA_EXTRA), OrderDetailBeans.class);
            Log.i("log", orderNumber.getOrder_number() + "************************");
            Log.i("log", "----JPushInterface==================" + orderNumber.toString());

            Cookie.putUid(context, UserInfoParse.getUid(Cookie.getUserInfo(context)));
            context.sendBroadcast(new Intent().setAction(Constant.BROADCAST)
                    .putExtra("serverType", orderNumber.getServer_type()).putExtra("orderNum", orderNumber.getOrder_number()));

//                orderNumberDao = new OrderNumberDao(context);
////                String action = intent.getAction();
//                Intent orderIntent = new Intent(context, OrderRemainActivity.class);
//                String orderNum = orderNumber.getOrder_number();
//                String serverType = orderNumber.getServer_type();
//                switch (Integer.valueOf(serverType)) {//100-即时 101-预约 102-取消 103-用户已支付
//                    case 100:
//                        //将新接受的及时订单添加到sqlite
//                        ContentValues values = new ContentValues();
//                        values.put("num", orderNum);
//                        values.put("date", System.currentTimeMillis() + "");
//                        orderNumberDao.insertLast(values);
//                        break;
//                    case 101:
//                        if (VerifyUtil.isNullOrEmpty(Cookie.getAppointmentOrder(context))) {
//                            Cookie.putAppointmentOrder(context, orderNum);
//                        } else {
//                            return;
//                        }
//                        break;
//                    case 102:
//                        if (Util.isAction(context)) {
//                            context.startActivity(new Intent(context, MyOrderListActivity.class)
//                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                            ToastUtils.showShort(context, "有订单被用户取消,请注意查看");
//                            return;
//                        }
//                        break;
//                    case 103://只有预约服务有推送
//                        if (Util.isAction(context)) {
//                            context.startActivity(new Intent(context, MyOrderListActivity.class)
//                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                            ToastUtils.showShort(context, "有预约订单用户支付定金，请注意查看");
//                            return;
//                        }
//                        break;
//                }
//
//                LogUtils.i("----JPush_isAction(context)" + Util.isAction(context) + "");
//                LogUtils.i("----JPush_getIsDialog(context)" + Cookie.getIsDialog(context) + "");
//
//                if (Util.isAction(context)) {
//                    //dialog是否显示
//                    if (!Cookie.getIsDialog(context)) {
//                        SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
//                        if (sqLiteOrderBean.getNum() != null) {
//                            if (Util.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
//                                orderNumberDao.clear();
//                                return;
//                            }
//                            orderNum = sqLiteOrderBean.getNum();
//                            orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
//                        } else {
//                            if (!VerifyUtil.isNullOrEmpty(Cookie.getAppointmentOrder(context))) {//预约
//                                Cookie.putIsDialog(context, true);
//                                orderIntent.putExtra("serverType", "1");
//                                orderIntent.putExtra("orderNumber", Cookie.getAppointmentOrder(context));
//                                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(orderIntent);
//                            }
//                        }
//                        //
//                        Cookie.putIsDialog(context, true);
//                        orderIntent.putExtra("serverType", "0");
//                        orderIntent.putExtra("orderNumber", orderNum);
//                        orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(orderIntent);
//                    }
//                }
//        	processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "---[MyReceiver] 接收到推送下来的通知");
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "---[MyReceiver] 接收到推送下来的通知的ID: " + notificationId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "---[MyReceiver] 用户点击打开了通知");
            String serverType = JsonUtils.object(bundle.getString(JPushInterface.EXTRA_EXTRA), OrderDetailBeans.class).getServer_type();
            switch (Integer.valueOf(serverType)) {//100-即时 101-预约 102-取消 103-用户已支付
                case 100:
                case 101:
                    if (!Util.isAction(context)) {
                        //打开自定义的Activity
                        Intent i = new Intent(context, HomeActivity.class);
                        i.putExtras(bundle);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    }
                    break;
                case 102:
                case 103://只有预约服务有推送
                    if (!Util.isAction(context)) {
                        Intent i = new Intent(context, MyOrderListActivity.class);
                        i.putExtras(bundle);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    }
                    break;
            }


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String
    printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
//	}
}
