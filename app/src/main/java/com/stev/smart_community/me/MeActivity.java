package com.stev.smart_community.me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;

public class MeActivity extends Fragment {

	ImageView mUserAvatar;
	TextView mUserID;
	RelativeLayout mUserInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.me_page, null);
		initView(view);
		initData();
		return view;
	}
	
	private void initView(View view) {
		mUserAvatar = (ImageView)view.findViewById(R.id.iv_user_avatar);
		mUserID = (TextView) view.findViewById(R.id.tv_user_id);
		mUserInfo = (RelativeLayout) view.findViewById(R.id.rl_user_info);
	}
	
	private void initData() {
//		mUserAvatar.setImageBitmap(bm);
		SharedPreferences shared = getActivity().getSharedPreferences(Constants.USER_INFO, 0);
		String name = shared.getString(Constants.USER_NAME, getString(R.string.me_default_userid));
		mUserID.setText(name);

		mUserInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), UserInfoActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
