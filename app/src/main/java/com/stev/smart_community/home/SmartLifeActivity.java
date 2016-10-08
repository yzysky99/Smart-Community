package com.stev.smart_community.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.stev.smart_community.R;
import com.stev.smart_community.customview.CategoryAdapter;
import com.stev.smart_community.smart.AirConditionActivity;
import com.stev.smart_community.smart.LampActivity;
import com.stev.smart_community.smart.RefrigeratorActivity;
import com.stev.smart_community.smart.WashingMachineActivity;

public class SmartLifeActivity extends Activity {
	private GridView mGridViewCategory;
	private CategoryAdapter mCategoryAdapter;

	private int[][] mCategoryPic = {
			{R.drawable.category_pic_1, R.string.lamp},
			{R.drawable.category_pic_2, R.string.air_condition},
			{R.drawable.category_pic_3, R.string.refrigerator},
			{R.drawable.category_pic_4, R.string.washing_machine},
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smart_life_page);
		initView();
	}

	private void initView() {
		mGridViewCategory = (GridView)findViewById(R.id.smart_gv_category);
		mCategoryAdapter = new CategoryAdapter(this, mCategoryPic);
		mGridViewCategory.setAdapter(mCategoryAdapter);

		mGridViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				Intent intent;
				switch (position){
					case 0:
						intent = new Intent(SmartLifeActivity.this, LampActivity.class);
						break;
					case 1:
						intent = new Intent(SmartLifeActivity.this, AirConditionActivity.class);
						break;
					case 2:
						intent = new Intent(SmartLifeActivity.this, RefrigeratorActivity.class);
						break;
					case 3:
						intent = new Intent(SmartLifeActivity.this, WashingMachineActivity.class);
						break;
					default:
						intent = new Intent(SmartLifeActivity.this, LampActivity.class);
						break;
				}
				startActivity(intent);
			}
		});
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
