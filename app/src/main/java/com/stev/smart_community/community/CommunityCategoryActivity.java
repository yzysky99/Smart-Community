package com.stev.smart_community.community;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class CommunityCategoryActivity extends Activity {
    private ImageView mBackBtn;
    private TextView mTitle;

    private ListView mListView;
    private CommunityAdapter mAdapter;
    private List<CommunityInfo> mCommunityInfoList = new ArrayList<CommunityInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunity_category);

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
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new CommunityAdapter(this);
        for (int i = 0; i < 5; i++) {
            CommunityInfo info = new CommunityInfo();
            info.logo = BitmapFactory.decodeResource(getResources(), R.drawable.shop_logo);
            Random random = new Random();
            info.name = random.nextInt(1000) + 1000 + "";
//            info.price = "￥" + (random.nextInt(100) + 100);
            info.ratingBar = random.nextInt(9) + 1;
            info.id = i + "";
            mCommunityInfoList.add(i, info);
        }
        mAdapter.updateData(mCommunityInfoList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommunityCategoryActivity.this, CommunityProfileActivity.class);
                CommunityInfo infoItem = (CommunityInfo) mAdapter.getItem(position);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_ID, infoItem.id);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_LOGO, infoItem.logo);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_NAME, infoItem.name);
                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_RATING_BAR, infoItem.ratingBar);
//                intent.putExtra(Constants.CommunityInfo.COMMUNITY_INFO_PRICE, infoItem.price);
                startActivity(intent);
            }
        });
    }
}