package com.stev.smart_community.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;

public class CommunityProfileActivity extends Activity {
    private ImageView mBackBtn;
    private TextView mTitle;

    private ImageView mLogo;
    private TextView mName;
    private RatingBar mRatingBar;
    private TextView mAddress;
    private TextView mProduct;
    private TextView mProductDetail;
    private TextView mShop;
    private TextView mShopDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_profile);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);

        initData();
    }


    private void initData() {


        Intent intent = getIntent();
        String id = intent.getStringExtra(Constants.CommunityInfo.COMMUNITY_INFO_ID);
        String logo = intent.getStringExtra(Constants.CommunityInfo.COMMUNITY_INFO_LOGO);
        String name = intent.getStringExtra(Constants.CommunityInfo.COMMUNITY_INFO_NAME);
        float ratingBar = intent.getFloatExtra(Constants.CommunityInfo.COMMUNITY_INFO_RATING_BAR, 2);
        String price = intent.getStringExtra(Constants.CommunityInfo.COMMUNITY_INFO_PRICE);

        mTitle.setText(name);

        mLogo = (ImageView) findViewById(R.id.logo);
        mName = (TextView) findViewById(R.id.name);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);
        mAddress = (TextView) findViewById(R.id.address);
        mProduct = (TextView) findViewById(R.id.product);
        mProductDetail = (TextView) findViewById(R.id.product_detail);
        mShop = (TextView) findViewById(R.id.shop);
        mShopDetail = (TextView) findViewById(R.id.shop_detail);

        mName.setText(name);
        mRatingBar.setRating(ratingBar);
    }

}
