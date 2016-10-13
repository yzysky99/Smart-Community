package com.stev.smart_community.me;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stev.smart_community.R;

public class AvatarActivity extends Activity {

    public static final String AVATAR_URL="avatar_url";

    private ImageView mBackBtn;
    private TextView mTitle;
    private ImageView  mAvartar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        String url=getIntent().getStringExtra(AVATAR_URL);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarActivity.this.finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.title_activity_avatar);

        mAvartar = (ImageView) findViewById(R.id.avatar_image);
//        mAvartar.setImageBitmap();


    }
}
