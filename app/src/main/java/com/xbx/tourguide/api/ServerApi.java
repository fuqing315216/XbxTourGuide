package com.xbx.tourguide.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xbx.tourguide.R;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UtilParse;

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
        IRequest.get(context, url, context.getString(R.string.loding), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
            }
        });
    }

    /**
     * 获取即时服务详情
     *
     * @param order_number
     */
    public void getDetail(String order_number) {
        String url = HttpUrl.MY_ORDER_DETAIL + "?order_number=" + order_number;
        IRequest.get(context, url, context.getString(R.string.loding), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.REQUESTSUCCESS, json);
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
        IRequest.post(context, HttpUrl.END_SERVER, params,context.getString(R.string.loding),new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                sendShowMessage.sendShowMsg(TaskFlag.PAGEREQUESTHREE, json);
            }
        });
    }

    /**
     * 获取我的订单列表
     * @param uid
     * @param now_page 分页
     * @param page_number
     * @param taskFlag 返回标记
     * @param isPull 是否下拉刷新或下拉加载
     */
    public void getMyOrderData(String uid, int now_page, int page_number, final int taskFlag, final boolean isPull) {
        String url = HttpUrl.MY_ORDER + "?uid=" + uid + "&now_page=" + now_page + "&page_number=" + page_number;
        IRequest.get(context, url, context.getString(R.string.loding), new RequestBackListener(context) {
            @Override
            public void requestSuccess(String json) {
                if (UtilParse.getRequestCode(json) == 0 && isPull) {
                    Message msg = sendShowMessage.getmHandler().obtainMessage();
                    msg.what = TaskFlag.REQUESTERROR;
                    sendShowMessage.getmHandler().sendMessage(msg);
                } else{
                    sendShowMessage.sendShowMsg(taskFlag, json);
                }
            }
        });
    }
}
