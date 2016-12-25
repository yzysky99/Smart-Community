package com.stev.smart_community.server;

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
import com.stev.smart_community.widget.DataInfo;

import java.util.ArrayList;
import java.util.List;

public class ServerAdapter extends BaseAdapter {
	private static final String TAG = "ServerAdapter";
	private Context context;
	private List<DataInfo> mServerInfoList = new ArrayList<DataInfo>();

	public ServerAdapter(Context context){
		this.context=context;
	}

	@Override
	public int getCount() {
		return mServerInfoList.size();
	}

	public void updateData(List<DataInfo> data) {
		mServerInfoList.clear();
		if (data != null && data.size() != 0) {
			mServerInfoList.addAll(data);
		}
		Log.d(TAG, "updateData mServerInfoList.size() = " + mServerInfoList.size());
	}
	
	@Override
	public Object getItem(int position) {
		return mServerInfoList.get(position);
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
			convertView=LayoutInflater.from(context).inflate(R.layout.server_list_item, null);
			holderView.logo = (ImageView) convertView.findViewById(R.id.logo);
			holderView.name = (TextView) convertView.findViewById(R.id.name);
			holderView.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
//			holderView.price = (TextView) convertView.findViewById(R.id.price);

			holderView.distance = (TextView) convertView.findViewById(R.id.tv_distance);
			holderView.address = (TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(holderView);
		}else {
			holderView=(HolderView) convertView.getTag();
		}

		Log.d(TAG, "position = " + position);
		Bitmap logoBitmap = mServerInfoList.get(position).logo;
		holderView.logo.setImageBitmap(logoBitmap);
		holderView.name.setText(mServerInfoList.get(position).name);

		if (mServerInfoList.get(position).serviceRating != null && mServerInfoList.get(position).serviceRating.length() > 0) {
			holderView.ratingBar.setRating(Float.parseFloat(mServerInfoList.get(position).serviceRating));
		}

//		holderView.price.setText((mServerInfoList.get(position).price));
		holderView.distance.setText(context.getString(R.string.distance, mServerInfoList.get(position).distance));
		holderView.address.setText(context.getString(R.string.addr, mServerInfoList.get(position).address));
		return convertView;
	}
	
	public class HolderView {
		private ImageView logo;
		private TextView name;
		private RatingBar ratingBar;
		private TextView price;
		private TextView distance;
		private TextView telephone;
		private TextView address;
	}
	
}

