package com.stev.smart_community.me;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	TextView mUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		mUserName = (TextView)findViewById(R.id.tv_user_name);

		SharedPreferences shared = getSharedPreferences(Constants.USER_INFO, 0);
		String name = shared.getString(Constants.USER_NAME, getString(R.string.me_default_userid));
		mUserName.setText(name);
	}

	@Override
	protected void onResume() {
		super.onResume();
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
