package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.SelectProvinceAdapter;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.beans.ProvinceBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/14.
 * <p/>
 * 选择所在地-省
 */
public class SelectProvinceActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView cityLv;
    private SelectProvinceAdapter adapter;
    private List<ProvinceBeans> beansList = new ArrayList<>();
    private boolean isSingle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlocation);
        isSingle = getIntent().getBooleanExtra("isSingle", true);
        initView();
    }

    private void initView() {

        cityLv = (ListView) findViewById(R.id.lv_city);
        cityLv.setOnItemClickListener(this);

        selectCity();

        findViewById(R.id.ibtn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void selectCity() {
        IRequest.get(this, HttpUrl.SELECT_PROVINCE_CITY, getString(R.string.loding), new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                if (UtilParse.getRequestCode(json) == 1) {
                    List<ProvinceBeans> list = JSON.parseArray(UtilParse.getRequestData(json), ProvinceBeans.class);

                    if (list != null && list.size() > 0) {
                        beansList.addAll(list);
                    }

                    adapter = new SelectProvinceAdapter(SelectProvinceActivity.this, beansList);
                    cityLv.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ProvinceBeans beans = (ProvinceBeans) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, SelectCityActivity.class);
        intent.putExtra("city", (Serializable) beans.getCity());
        intent.putExtra("isSingle", isSingle);
        startActivityForResult(intent, 201);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 201 && resultCode == 201) {
            CityBeans city = (CityBeans) data.getSerializableExtra("bean");
            Intent intent = new Intent();
            intent.putExtra("bean", city);
            setResult(200, intent);
            finish();
        }

        if (requestCode == 201 && resultCode == 202) {
            Intent intent = new Intent();
            intent.putExtra("locations", (Serializable) (List<CityBeans>)data.getSerializableExtra("locations"));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
