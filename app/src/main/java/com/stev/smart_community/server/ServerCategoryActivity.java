package com.stev.smart_community.server;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerCategoryActivity extends Activity {
    private static final String TAG = "ServerCategoryActivity";
    private ImageView mBackBtn;
    private TextView mTitle;

    private ListView mListView;
    private ServerAdapter mAdapter;
    private List<ServerInfo> mServerInfoList = new ArrayList<ServerInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_category);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String category = intent.getStringExtra(Constants.ServerInfo.SERVER_CATEGORY);
        mTitle.setText(category);
        
        initData();
    }

    private void initData() {
        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new ServerAdapter(this);

        for(int i = 0; i< 5; i++) {
            ServerInfo serverInfo = new ServerInfo();
            serverInfo.logo = BitmapFactory.decodeResource(getResources(), R.drawable.shop_logo);
            Random random = new Random();
            serverInfo.name = random.nextInt(1000) + 1000 + "";
//            serverInfo.price = "￥" + (random.nextInt(100) + 100);
            serverInfo.ratingBar = random.nextInt(4) + 1;
            serverInfo.id = i + "";
            mServerInfoList.add(i, serverInfo);
        }
        mAdapter.updateData(mServerInfoList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ServerCategoryActivity.this, ServerProfileActivity.class);
                ServerInfo shopInfoItem = (ServerInfo) mAdapter.getItem(position);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_ID, shopInfoItem.id);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_LOGO, shopInfoItem.logo);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_NAME, shopInfoItem.name);
                intent.putExtra(Constants.ServerInfo.SERVER_INFO_RATING_BAR, shopInfoItem.ratingBar);
//                intent.putExtra(Constants.ServerInfo.SERVER_INFO_PRICE, shopInfoItem.price);
                startActivity(intent);
            }
        });


    }

}
