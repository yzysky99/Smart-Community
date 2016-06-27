package com.stev.smart_community.server;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stev.smart_community.R;

public class ServerAcitvity extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.server_page, null);
		initView(view);
		return view;
	}
	

	private void initView(View view) {
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
