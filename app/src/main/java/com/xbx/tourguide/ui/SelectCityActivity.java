package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.SelectCityAdapter;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/15.
 * <p/>
 * 选择所在地-市
 */
public class SelectCityActivity extends BaseActivity {

    private ListView cityLv;
    private List<CityBeans> beans;
    private SelectCityAdapter adapter;
    private List<Boolean> checks = null;
    private boolean isSingle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlocation);

        beans = (List<CityBeans>) getIntent().getSerializableExtra("city");
        isSingle = getIntent().getBooleanExtra("isSingle", true);
        cityLv = (ListView) findViewById(R.id.lv_city);

        if (beans != null && beans.size() > 0) {
            adapter = new SelectCityAdapter(this, beans);
            cityLv.setAdapter(adapter);
        }

        if (!isSingle) {
            findViewById(R.id.tv_selectcity_update).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_selectcity_update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<CityBeans> locations = new ArrayList<>();
                    for (int i = 0; i < beans.size(); i++) {
                        if (checks.get(i)) {
                            locations.add(beans.get(i));
                        }
                    }

                    if (locations.size() < 1 || locations == null) {
                        ToastUtils.showShort(SelectCityActivity.this, "请至少选择一个服务地区");
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("locations", (Serializable) locations);
                        setResult(202, intent);
                        SelectCityActivity.this.finish();
                    }
                }
            });

            checks = new ArrayList<>();
            for (int i = 0; i < beans.size(); i++) {
                checks.add(false);
            }
        }

        cityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isSingle) {
                    CityBeans city = (CityBeans) parent.getItemAtPosition(position);
                    LogUtils.e(city.toString());
                    Intent intent = new Intent();
                    intent.putExtra("bean", city);
                    setResult(201, intent);
                    SelectCityActivity.this.finish();
                } else {
                    if (checks.get(position)) {
                        checks.set(position, false);
                        view.setBackgroundColor(ContextCompat.getColor(SelectCityActivity.this, android.R.color.white));
                    } else {
                        checks.set(position, true);
                        view.setBackgroundColor(ContextCompat.getColor(SelectCityActivity.this, R.color.colorTag));
                    }
                }
            }
        });

        findViewById(R.id.ibtn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
