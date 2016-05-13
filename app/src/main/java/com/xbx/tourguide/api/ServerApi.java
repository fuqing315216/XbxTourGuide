package com.xbx.tourguide.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.LogUtils;

/**
 * Created by rudne on 2016/4/25.
 */
public class ServerApi {
    private Context context;
    private SendShowMessage sendShowMessage = null;

    public ServerApi(Context context, Handler handler) {
        this.context = context;
        sendShowMessage = new SendShowMessage(context, handler);
    }

    /**
     * 设置首页是否开始接单
     *
     * @param uid
     */
    public void setIsOnline(String uid) {
        String url = HttpUrl.START_SERVICE + "?uid=" + uid;
        IRequest.get(context, url, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }


    /**
     * 检查是否有版本更新
     */
    public void checkUpdate() {
        if (context == null)
            return;
        String url = HttpUrl.VERSION_UPDATE;
        IRequest.get(context, url, new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                LogUtils.i("----版本更新:" + json);
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTWO, json);
            }
        });
    }

    /**
     * 获取订单详情
     *
     * @param order_number
     */
    public void getOrderDetail(String order_number) {
        String url = HttpUrl.ORDER_PROCESS + "?order_number=" + order_number;
        IRequest.get(context, url, new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 开始服务
     *
     * @param order_number
     */
    public void startServer(String order_number) {
        RequestParams params = new RequestParams();
        params.put("order_number", order_number);
        IRequest.post(context, HttpUrl.START_SERVER, params, new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTWO, json);
            }
        });
    }

    /**
     * 结束服务
     *
     * @param order_number
     */
    public void endServer(String order_number) {
        RequestParams params = new RequestParams();
        params.put("order_number", order_number);
        IRequest.post(context, HttpUrl.END_SERVER, params, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTHREE, json);
            }
        });
    }

    /**
     * 获取我的订单列表
     *
     * @param uid
     * @param now_page    分页
     * @param page_number
     * @param taskFlag    返回标记
     */
    public void getMyOrderData(String uid, int now_page, int page_number, final int taskFlag) {
        String url = HttpUrl.MY_ORDER + "?uid=" + uid + "&now_page=" + now_page + "&page_number=" + page_number;
        IRequest.get(context, url, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.getmHandler().sendMessage(sendShowMessage.getmHandler().obtainMessage(taskFlag, UtilParse.getRequestData(json)));
            }

            @Override
            public void requestError(VolleyError e) {
//                sendShowMessage.sendMsg(TaskFlag.REQUESTERROR, "");
                sendShowMessage.getmHandler().sendMessage(sendShowMessage.getmHandler().obtainMessage(TaskFlag.REQUESTERROR));
            }
        });
    }

    /**
     * 获取订单状态详情
     *
     * @param order_number
     */
    public void getOrderStatusDetail(String order_number) {
        String url = HttpUrl.MY_ORDER_DETAIL + "?order_number=" + order_number;
        IRequest.get(context, url, context.getString(R.string.waitting), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTWO, json);
            }
        });
    }
}
