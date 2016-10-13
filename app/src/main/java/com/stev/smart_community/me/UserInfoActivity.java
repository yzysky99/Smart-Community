package com.stev.smart_community.me;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	TextView mUserName;
	TextView mSignature;
	ImageView mAvatar;
	Button mUserInfoBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		mUserName = (TextView)findViewById(R.id.tv_user_name);
		mAvatar = (ImageView) findViewById(R.id.iv_avatar);
		mSignature = (TextView) findViewById(R.id.tv_signature);
		mUserInfoBtn = (Button) findViewById(R.id.btn_user_info_edit);

		mAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this, AvatarActivity.class);
				intent.putExtra(AvatarActivity.AVATAR_URL, "");// TODO: 2016/10/13
				startActivity(intent);
			}
		});

		mUserInfoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initData() {
		SharedPreferences shared = getSharedPreferences(Constants.USER_INFO,  Context.MODE_WORLD_READABLE);
		String name = shared.getString(Constants.UserInfo.USER_NAME, getString(R.string.me_default_userid));
		String signature = shared.getString(Constants.UserInfo.USER_SIGNATURE, getString(R.string.me_default_sinagture));
		mUserName.setText(name);
		mSignature.setText(signature);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
