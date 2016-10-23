package com.stev.smart_community.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.stev.smart_community.R;

import java.util.ArrayList;
import java.util.List;

public class BillPaymentActivity extends Activity {

	private PaymentAdapter paymentAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill_payment_page);

		initDate();

	}

	private void initDate() {
		listView = (ListView) findViewById(R.id.listView);
		paymentAdapter = new PaymentAdapter(this);

		String [] strArr = { getString(R.string.water_charge),
				getString(R.string.electric_charge),
				getString(R.string.gas_charge),
				getString(R.string.broadband_charge),
				getString(R.string.tv_charge)};

		for (String str : strArr) {
			CategoryInfo categoryInfo = new CategoryInfo();
			categoryInfo.logo = BitmapFactory.decodeResource(getResources(), R.drawable.shop_logo);
			categoryInfo.category = str;
			paymentAdapter.addData(categoryInfo);
		}
		listView.setAdapter(paymentAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(BillPaymentActivity.this, PaymentActivity.class);
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



	public class PaymentAdapter extends BaseAdapter {

		private Context context;
		private List<CategoryInfo> mList = new ArrayList();

		public PaymentAdapter(Context context){
			this.context=context;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		public void addData(CategoryInfo item) {
			mList.add(item);
		}

		@Override
		public CategoryInfo getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holderView = null;
			if (convertView == null) {
				holderView = new HolderView();
				convertView = LayoutInflater.from(context).inflate(R.layout.payment_list_item, null);
				holderView.logo = (ImageView) convertView.findViewById(R.id.iv_logo);
				holderView.category = (TextView) convertView.findViewById(R.id.tv_name);
				holderView.notice = (TextView) convertView.findViewById(R.id.tv_notice);
				convertView.setTag(holderView);
			} else {
				holderView = (HolderView) convertView.getTag();
			}

			Bitmap logoBitmap = getItem(position).logo;
			holderView.logo.setImageBitmap(logoBitmap);
			holderView.category.setText(getItem(position).category);
			holderView.notice.setText(getItem(position).notice);

			return convertView;
		}

		public class HolderView {
			private ImageView logo;
			private TextView category;
			private TextView notice;
		}
	}

	public class CategoryInfo {
		Bitmap logo;
		String category;
		String notice;
	}

}
