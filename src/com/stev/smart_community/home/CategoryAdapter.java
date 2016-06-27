package com.stev.smart_community.home;

import com.stev.smart_community.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private static final String TAG = "CategoryAdapter";
	private Context context;
	private int[][] data;
		
	public CategoryAdapter(Context context,int[][] data){
		this.context=context;
		this.data=data;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView=null;
		if (convertView==null) {
			holderView=new HolderView();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_category_home, null);
			holderView.categoryImage=(ImageView) convertView.findViewById(R.id.category_iv);
			holderView.categoryText=(TextView) convertView.findViewById(R.id.category_tv);
			convertView.setTag(holderView);
		}else {
			holderView=(HolderView) convertView.getTag();
		}
		
		Log.d(TAG, "position = " + position);
		holderView.categoryImage.setImageResource(data[position][0]);
		holderView.categoryText.setText(data[position][1]);
		
		return convertView;
	}
	
	public class HolderView {
		private ImageView categoryImage;
		private TextView categoryText;
	}

}
