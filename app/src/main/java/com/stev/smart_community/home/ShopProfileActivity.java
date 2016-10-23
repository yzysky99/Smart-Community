package com.stev.smart_community.home;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;

public class ShopProfileActivity extends Activity {
    private static final String TAG = "ShopProfileActivity";
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
        setContentView(R.layout.activity_shop_profile);

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
        String shopId = intent.getStringExtra(Constants.ShopInfo.SHOP_INFO_ID);
        String shopLogo = intent.getStringExtra(Constants.ShopInfo.SHOP_INFO_LOGO);
        String shopName = intent.getStringExtra(Constants.ShopInfo.SHOP_INFO_NAME);
        float shopRatingBar = intent.getFloatExtra(Constants.ShopInfo.SHOP_INFO_RATING_BAR, 2);
        String shopPrice = intent.getStringExtra(Constants.ShopInfo.SHOP_INFO_PRICE);

        Log.d(TAG,"stevyang shopName " + shopName);
        Log.d(TAG,"stevyang shopRatingBar " + shopRatingBar);

        mTitle.setText(shopName);

        mLogo = (ImageView) findViewById(R.id.logo);
        mName = (TextView) findViewById(R.id.name);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);
        mAddress = (TextView) findViewById(R.id.address);
        mProduct = (TextView) findViewById(R.id.product);
        mProductDetail = (TextView) findViewById(R.id.product_detail);
        mShop = (TextView) findViewById(R.id.shop);
        mShopDetail = (TextView) findViewById(R.id.shop_detail);

        mName.setText(shopName);
        mRatingBar.setRating(shopRatingBar);
    }

}
