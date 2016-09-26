package com.hunter.dribbble.ui;

import android.os.Bundle;

import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.widget.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {

    @BindView(R.id.tag_flow_test)
    TagFlowLayout mTagFlowTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        List<String> list = new ArrayList<>();
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");
        list.add("Android");
        list.add("IOS");

        mTagFlowTest.setTags(list);
    }
}
