package com.stev.smart_community.community;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stev.smart_community.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends BaseAdapter {
	private static final String TAG = "CommunityAdapter";
	private Context context;
	private List<CommunityInfo> mCommunityInfoList = new ArrayList<CommunityInfo>();

	public CommunityAdapter(Context context){
		this.context=context;
	}

	@Override
	public int getCount() {
		return mCommunityInfoList.size();
	}

	public void updateData(List<CommunityInfo> data) {
		mCommunityInfoList.clear();
		if (data != null && data.size() != 0) {
			mCommunityInfoList.addAll(data);
		}
		Log.d(TAG, "updateData mCommunityInfoList.size() = %d" + mCommunityInfoList.size());
	}
	
	@Override
	public Object getItem(int position) {
		return mCommunityInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView=null;
		if (convertView==null) {
			holderView=new HolderView();
			convertView=LayoutInflater.from(context).inflate(R.layout.community_list_item, null);
			holderView.logo = (ImageView) convertView.findViewById(R.id.logo);
			holderView.name = (TextView) convertView.findViewById(R.id.name);
			holderView.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
//			holderView.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(holderView);
		}else {
			holderView=(HolderView) convertView.getTag();
		}
		
		Log.d(TAG, "position = " + position);
		Bitmap logoBitmap = mCommunityInfoList.get(position).logo;
		holderView.logo.setImageBitmap(logoBitmap);
		holderView.name.setText(mCommunityInfoList.get(position).name);
		holderView.ratingBar.setRating((mCommunityInfoList.get(position).ratingBar));
//		holderView.price.setText((mCommunityInfoList.get(position).price));
		
		return convertView;
	}
	
	public class HolderView {
		private ImageView logo;
		private TextView name;
		private RatingBar ratingBar;
		private TextView price;
	}
	
}

