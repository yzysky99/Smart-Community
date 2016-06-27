package com.stev.smart_community.home;

import com.stev.smart_community.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NoticeAcitvity extends Activity {
	TextView mNoticeTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_page);
		mNoticeTv = (TextView)findViewById(R.id.tv_notice);
		mNoticeTv.setText(R.string.notice);
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
