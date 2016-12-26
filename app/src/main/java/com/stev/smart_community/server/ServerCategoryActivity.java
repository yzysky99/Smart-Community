package com.stev.smart_community.server;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
import com.stev.smart_community.ui.WebviewUI;
import com.stev.smart_community.net.DataRequest;
import com.stev.smart_community.widget.DataInfo;

import java.util.ArrayList;
import java.util.List;

public class ServerCategoryActivity extends Activity {
    private static final String TAG = "ServerCategoryActivity";
    private ImageView mBackBtn;
    private TextView mTitle;
    private TextView mNoData;
    private LinearLayout mContainer;

    private ListView mListView;
    private ServerAdapter mAdapter;
    private CommunityApplication mAppInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_category);

        mAppInstance = (CommunityApplication) getApplication();
        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mContainer = (LinearLayout) findViewById(R.id.com_container);
        mNoData = (TextView) findViewById(R.id.tv_data_empty);

        mTitle = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String category = intent.getStringExtra(Constants.ServerInfo.SERVER_CATEGORY);
        mTitle.setText(category);

        initData(category);
    }

    private void initData(final String category) {
        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new ServerAdapter(this);

//        for(int i = 0; i< 5; i++) {
//            DataInfo serverInfo = new DataInfo();
//            serverInfo.logo = BitmapFactory.decodeResource(getResources(), R.drawable.shop_logo);
//            Random random = new Random();
//            serverInfo.name = random.nextInt(1000) + 1000 + "";
////            serverInfo.price = "￥" + (random.nextInt(100) + 100);
//            serverInfo.ratingBar = random.nextInt(4) + 1;
//            serverInfo.id = i + "";
//            mServerInfoList.add(i, serverInfo);
//        }

//        mAdapter.updateData(mServerInfoList);
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

                Intent intent = new Intent(ServerCategoryActivity.this, WebviewUI.class);
                DataInfo dataInfoItem = (DataInfo) mAdapter.getItem(position);
                intent.putExtra(Constants.CommonInfo.DETAIL_URL, dataInfoItem.detailUrl);

//                Intent intent = new Intent(ServerCategoryActivity.this, ServerProfileActivity.class);
//                intent.putExtra(Constants.ServerInfo.SERVER_INFO_ID, dataInfoItem.id);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_LOGO, dataInfoItem.logo);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_NAME, dataInfoItem.name);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_RATING_BAR, dataInfoItem.serviceRating);
//                intent.putExtra(Constants.ServerInfo.SERVER_INFO_PRICE, dataInfoItem.price);

                startActivity(intent);
            }
        });


    }

}
