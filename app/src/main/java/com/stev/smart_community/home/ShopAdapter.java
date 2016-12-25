package com.stev.smart_community.home;

import java.util.ArrayList;
import java.util.List;

import com.stev.smart_community.R;
import com.stev.smart_community.widget.DataInfo;

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

public class ShopAdapter extends BaseAdapter {
	private static final String TAG = "ShopAdapter";
	private Context context;
	private List<DataInfo> mDataInfoList = new ArrayList<DataInfo>();
		
	public ShopAdapter(Context context){
		this.context=context;
	}

	@Override
	public int getCount() {
		return mDataInfoList.size();
	}

	public void updateData(List<DataInfo> data) {
		mDataInfoList.clear();
		if (data != null && data.size() != 0) {
			mDataInfoList.addAll(data);
		}
		Log.d(TAG, "updateData mDataInfoList.size() = %d" + mDataInfoList.size());
	}
	
	@Override
	public Object getItem(int position) {
		return mDataInfoList.get(position);
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
			convertView=LayoutInflater.from(context).inflate(R.layout.shop_list_item, null);
			holderView.shopLogo = (ImageView) convertView.findViewById(R.id.iv_shop_logo);
			holderView.shopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
			holderView.shopRatingBar = (RatingBar) convertView.findViewById(R.id.rb_shop_ratingbar);
			holderView.shopPrice = (TextView) convertView.findViewById(R.id.tv_shop_price);
			holderView.shopDistance =  (TextView) convertView.findViewById(R.id.tv_shop_distance);
//			holderView.shopTel = (TextView) convertView.findViewById(R.id.tv_shop_tel);
			holderView.shopAddr =  (TextView) convertView.findViewById(R.id.tv_shop_address);
			convertView.setTag(holderView);
		}else {
			holderView=(HolderView) convertView.getTag();
		}
		
		Log.d(TAG, "position = " + position);
		Bitmap logoBitmap = mDataInfoList.get(position).logo;
		holderView.shopLogo.setImageBitmap(logoBitmap);

		holderView.shopName.setText(mDataInfoList.get(position).name);
		holderView.shopRatingBar.setRating(Float.parseFloat(mDataInfoList.get(position).serviceRating));
		holderView.shopPrice.setText(context.getString(R.string.price, mDataInfoList.get(position).price));

		holderView.shopDistance.setText(context.getString(R.string.distance, mDataInfoList.get(position).distance));
//		holderView.shopTel.setText(context.getString(R.string.tel, mDataInfoList.get(position).telephone));
		holderView.shopAddr.setText(context.getString(R.string.addr, mDataInfoList.get(position).address));
		return convertView;
	}
	
	public class HolderView {
		private ImageView shopLogo;
		private TextView shopName;
		private RatingBar shopRatingBar;
		private TextView shopPrice;
		private TextView shopDistance;
		private TextView shopTel;
		private TextView shopAddr;
	}
	
}

