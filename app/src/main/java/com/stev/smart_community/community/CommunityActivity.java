package com.stev.smart_community.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.stev.smart_community.R;
import com.stev.smart_community.customview.CategoryAdapter;
import com.stev.smart_community.home.NoticeActivity;

public class CommunityActivity extends Fragment {

	private GridView mGridViewCategory;
	private CategoryAdapter mCategoryAdapter;
	
	private int[][] mCategoryPic = { 
			{R.drawable.category_pic_1, R.string.community_category_1},
			{R.drawable.category_pic_2, R.string.community_category_2},  
			{R.drawable.category_pic_3, R.string.community_category_3},
			{R.drawable.category_pic_4, R.string.community_category_4}, 
			{R.drawable.category_pic_5, R.string.community_category_5}};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.community_page, null);
		initView(view);
		return view;
	}
	

	private void initView(View view) {
		mGridViewCategory = (GridView)view.findViewById(R.id.comunity_gv_category); 
		mCategoryAdapter = new CategoryAdapter(getActivity(), mCategoryPic);
		mGridViewCategory.setAdapter(mCategoryAdapter);
		
		mGridViewCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), NoticeActivity.class);
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
