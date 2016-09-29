package com.stev.smart_community.me;

import com.stev.smart_community.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	TextView mUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		mUserName = (TextView)findViewById(R.id.tv_user_name);
		mUserName.setText(R.string.me_default_userid);
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
