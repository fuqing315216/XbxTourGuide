package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.ScreenUtils;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by xbx on 2016/5/17.
 */
public class InviteCodeActivity extends BaseActivity implements View.OnClickListener {
    private TextView personTv, moneyTv;
    private ImageView bgIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitecode);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.invite_code));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bgIv = (ImageView) findViewById(R.id.iv_invitecode_bg);
        personTv = (TextView) findViewById(R.id.tv_invitecode_person);
        moneyTv = (TextView) findViewById(R.id.tv_invitecode_money);

        ViewGroup.LayoutParams params = bgIv.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth(this);
        params.height = params.width / 2;
        bgIv.setLayoutParams(params);

        findViewById(R.id.llyt_invitecode_rule).setOnClickListener(this);
        findViewById(R.id.btn_invitecode_email).setOnClickListener(this);
        findViewById(R.id.btn_invitecode_msg).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llyt_invitecode_rule:
                break;
            case R.id.btn_invitecode_email:
                break;
            case R.id.btn_invitecode_msg:
                break;
        }
    }
}
