package com.stev.smart_community.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stev.smart_community.R;
import com.stev.smart_community.home.NoticeAcitvity;

public class MeAcitvity extends Fragment {

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
		mUserID.setText(getResources().getString(R.string.me_default_userid));
		mUserInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), UserInfoAcitvity.class);
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
