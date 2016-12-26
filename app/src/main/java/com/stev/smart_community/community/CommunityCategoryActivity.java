package com.stev.smart_community.community;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.stev.smart_community.CommunityApplication;
import com.stev.smart_community.Constants;
import com.stev.smart_community.R;
import com.stev.smart_community.net.DataRequest;
import com.stev.smart_community.server.ServerCategoryActivity;
import com.stev.smart_community.ui.WebviewUI;
import com.stev.smart_community.widget.DataInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommunityCategoryActivity extends Activity {
    private static final String TAG = "ComCategoryActivity";
    private ImageView mBackBtn;
    private TextView mTitle;
    private TextView mNoData;
    private LinearLayout mContainer;

    private ListView mListView;
    private CommunityAdapter mAdapter;
    private CommunityApplication mAppInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunity_category);

        mAppInstance = (CommunityApplication) getApplication();
        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);
        mNoData = (TextView) findViewById(R.id.tv_data_empty);
        mContainer = (LinearLayout) findViewById(R.id.com_container);

        Intent intent = getIntent();
        String category = intent.getStringExtra(Constants.CommunityInfo.COMMUNITY_CATEGORY);
        mTitle.setText(category);

        initData(category);
    }

    private void initData(final String category) {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new CommunityAdapter(this);
//        for (int i = 0; i < 5; i++) {
//            DataInfo info = new DataInfo();
//            info.logo = BitmapFactory.decodeResource(getResources(), R.drawable.shop_logo);
//            Random random = new Random();
//            info.name = random.nextInt(1000) + 1000 + "";
//            info.price = "￥" + (random.nextInt(100) + 100);
//            info.ratingBar = random.nextInt(9) + 1;
//            info.id = i + "";
//            mCommunityInfoList.add(i, info);
//        }
//        mAdapter.updateData(mCommunityInfoList);
        mListView.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataRequest dataRequest = new DataRequest(getApplicationContext());
                final List<DataInfo> dataInfoList = dataRequest.getData(category, mAppInstance.mLatitude, mAppInstance.mLongitude);
                Log.d(TAG,"dataInfoList size " + dataInfoList.size());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dataInfoList.size() <= 0) {
                            mNoData.setVisibility(View.VISIBLE);
                            mContainer.setVisibility(View.GONE);
                        }else {
                            mNoData.setVisibility(View.GONE);
                            mAdapter.updateData(dataInfoList);
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        }).start();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CommunityCategoryActivity.this, WebviewUI.class);
                DataInfo dataInfoItem = (DataInfo) mAdapter.getItem(position);
                intent.putExtra(Constants.CommonInfo.DETAIL_URL, dataInfoItem.detailUrl);

//                Intent intent = new Intent(CommunityCategoryActivity.this, CommunityProfileActivity.class);
//                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_ID, dataInfoItem.id);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_LOGO, dataInfoItem.logo);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_NAME, dataInfoItem.name);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_RATING_BAR, dataInfoItem.serviceRating);
//                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_PRICE, dataInfoItem.price);
                startActivity(intent);
            }
        });
    }
}