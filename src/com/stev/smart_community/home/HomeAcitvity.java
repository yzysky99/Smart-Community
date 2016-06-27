package com.stev.smart_community.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.location.service.LocationService;
import com.stev.smart_community.CommunityApplication;
import com.stev.smart_community.customview.CategoryAdapter;
import com.stev.smart_community.weather.*;
import com.stev.smart_community.R;

public class HomeAcitvity extends Fragment {
	protected static final String TAG = "HomePage";
	private LocationService locationService;
	private TextView mCityName;
	private TextView mUpdateTime;
	private TextView mDateInfo;
	private TextView mWeatherInfo;
	private TextView mTmpRange;
	private TextView mHomeTitle;
	private ImageView mWeatherIcon;
	
	private String mCity = "shenzhen";
	private Boolean mLocationState = false;
	private WeatherInfo mWeatherData;
	
	private GridView mGridViewCategory;
	private CategoryAdapter mCategoryAdapter;
	
	private ListView mShopInfoLV;
	private ShopAdapter shopAdapter;
	private List<ShopInfo> mShopInfoList = new ArrayList<ShopInfo>();
	
	private int[][] mCategoryPic = { 
			{R.drawable.category_pic_1, R.string.category_1},
			{R.drawable.category_pic_2, R.string.category_2},  
			{R.drawable.category_pic_3, R.string.category_3},
			{R.drawable.category_pic_4, R.string.category_4}, 
			{R.drawable.category_pic_5, R.string.category_5}, 
			{R.drawable.category_pic_6, R.string.category_6}, 
			{R.drawable.category_pic_7, R.string.category_7}, 
			{R.drawable.category_pic_8, R.string.category_8}};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_page, null);
		mLocationState = false;
		initView(view);
		initData();
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		// -----------location config ------------
		locationService = ((CommunityApplication)getActivity().getApplication()).locationService; 
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		//注册监听
		int type = 0;//getIntent().getIntExtra("from", 0);
		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
		locationService.start();
	}


	@Override
	public void onStop() {
		locationService.unregisterListener(mListener); //注销掉监听
		locationService.stop(); //停止定位服务
		super.onStop();
	}

	private void initView(View view) {
		mCityName = (TextView)view.findViewById(R.id.city_name);
		mUpdateTime = (TextView)view.findViewById(R.id.update_time);
		mDateInfo = (TextView)view.findViewById(R.id.date_info);
		mWeatherInfo = (TextView)view.findViewById(R.id.weather_info);
		mTmpRange = (TextView)view.findViewById(R.id.tmp_range);
		mHomeTitle = (TextView)view.findViewById(R.id.home_title);
		mWeatherIcon = (ImageView)view.findViewById(R.id.weather_icon); 
		mGridViewCategory = (GridView)view.findViewById(R.id.gv_category); 
		mCategoryAdapter = new CategoryAdapter(getActivity(), mCategoryPic);
		mGridViewCategory.setAdapter(mCategoryAdapter);
		
		mGridViewCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), NoticeAcitvity.class);
				startActivity(intent);
			}
		});
		
		mShopInfoLV = (ListView)view.findViewById(R.id.lv_shop_info);
		shopAdapter = new ShopAdapter(getActivity());
		
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.shopLogo = BitmapFactory.decodeResource(getResources(), R.drawable.kfc_logo);
		shopInfo.shopName = getResources().getString(R.string.shop_name);
//		shopInfo.shopRatingBar
		shopInfo.shopPrice = getResources().getString(R.string.shop_price);
		for(int i = 0; i< 10; i++) {
			mShopInfoList.add(i, shopInfo);
		}
		shopAdapter.updateData(mShopInfoList);
		mShopInfoLV.setAdapter(shopAdapter);
	}
	
	
	private void initData() {
		String city;
		String updateTime;
		String date;
		String weatherInfo;
		String temp;
		String community;
		int weatherIcon;
		
		mWeatherData = new WeatherInfo();
		SharedPreferences spWeatherInfo = getActivity().getSharedPreferences("weatherInfo", Activity.MODE_PRIVATE);
		
		city = spWeatherInfo.getString("city", getString(R.string.default_city));
		updateTime = spWeatherInfo.getString("updateTime", getResources().getString(R.string.update_time, "8:00"));
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String spDate = sDateFormat.format(new java.util.Date());
		Log.d(TAG, "spDate =" + spDate);
		date = spWeatherInfo.getString("date", spDate);
		weatherInfo = spWeatherInfo.getString("weatherInfo", getString(R.string.default_weather_cond));
		temp = spWeatherInfo.getString("temp", getString(R.string.default_temp_cond));
		community = spWeatherInfo.getString("community", "");
		int resID = getResources().getIdentifier("cond_101", "drawable", "com.stev.smart_community");
		weatherIcon = spWeatherInfo.getInt("weatherIcon", resID);
		
		mCityName.setText(city);
		mUpdateTime.setText(updateTime);
		mDateInfo.setText(date);
		mWeatherInfo.setText(weatherInfo);
		mTmpRange.setText(temp);
		if(!TextUtils.isEmpty(community)) {
			mHomeTitle.setText(community);
		}
		mWeatherIcon.setImageResource(weatherIcon);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/*****
	 * @see copy funtion to you project
	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());
				sb.append("\nCountry : ");
				sb.append(location.getCountry());
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());
				sb.append("\ncity : ");
				sb.append(location.getCity());
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");
				sb.append(location.getStreet());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\nDescribe: ");
				sb.append(location.getLocationDescribe());
				sb.append("\nDirection(not all devices have value): ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");
				if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
					for (int i = 0; i < location.getPoiList().size(); i++) {
						Poi poi = (Poi) location.getPoiList().get(i);
						sb.append(poi.getName() + ";");
					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
					mLocationState = true;
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
					mLocationState = true;
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					mLocationState = true;
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					mLocationState = false;
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					mLocationState = false;
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					mLocationState = false;
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}
				
				mCity = location.getCity();
//				Log.d(TAG, "mCity =" + mCity);
				if(mCity != null && mCity.length() > 0){
					mCity = mCity.substring(0, mCity.length() -1);
				}else {
					mCity = "深圳";
				}
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						getWeatherData(mCity);
					}
				}).start();
				
				if(mLocationState){
					mLocationState = false;
					locationService.stop();
				}
			}
		}
	};
	
	
	private void initWeatherData(String weatherInfo) {
		String heWeatherVersion = "HeWeather data service 3.0";
		String aqi = "aqi";
		String basic = "basic";
		String dailyForecast = "daily_forecast";
		String hourlyForecast = "hourly_forecast";
		String now = "now";
		String suggestion = "suggestion";
		
		JSONObject aqiJson;
		JSONObject basicJson;
		JSONArray dailyForecastJsonArray;
		JSONArray hourlyForecastJsonArray;
		JSONObject nowJson;
		JSONObject suggestionJson;
		JSONArray weatherJsonArray;
		JSONObject weahterJson;
		try {
			JSONObject weatherJsonObject = new JSONObject(weatherInfo);
			weatherJsonArray = weatherJsonObject.getJSONArray(heWeatherVersion);
			weahterJson = weatherJsonArray.getJSONObject(0);
			aqiJson = weahterJson.getJSONObject(aqi);
			basicJson = weahterJson.getJSONObject(basic);
			
			// basic解析
			String updateTime;
			JSONObject updateTimeJson;
			String cityName = basicJson.getString("city");
			updateTimeJson = basicJson.getJSONObject("update");
			updateTime = updateTimeJson.getString("loc");
			mCityName.setText(cityName);
			String mTime = updateTime.substring(updateTime.length() - 5);
			String updateName = getResources().getString(R.string.update_time, mTime);
			mUpdateTime.setText(updateName);
			
			JSONObject astroJson;
			JSONObject tmpJson;
			String tmpMax, tmpMin;
			dailyForecastJsonArray = weahterJson.getJSONArray(dailyForecast);
			astroJson = dailyForecastJsonArray.getJSONObject(0);
			tmpJson = astroJson.getJSONObject("tmp");
			tmpMax = tmpJson.getString("max");
			tmpMin = tmpJson.getString("min");
			String date = astroJson.getString("date");
//			Log.d(TAG, "tmpMax =" + tmpMax);
//			Log.d(TAG, "tmpMin =" + tmpMin);
//			Log.d(TAG, "date =" + date);
			String tmp = tmpMax + "℃ -" + tmpMin + "℃";//°
			mDateInfo.setText(date);
			mTmpRange.setText(tmp);
			
			hourlyForecastJsonArray = weahterJson.getJSONArray(hourlyForecast);
			
			// now解析
			nowJson = weahterJson.getJSONObject(now);
			JSONObject condJson;
			condJson = nowJson.getJSONObject("cond");
			String code = condJson.getString("code");
			String txt = condJson.getString("txt");
			String tmpNow = nowJson.getString("tmp");
//			Log.d(TAG, "code =" + code);
//			Log.d(TAG, "txt =" + txt);
//			Log.d(TAG, "tmpNow =" + tmpNow);
			
			mWeatherInfo.setText(txt);
			int resID = getResources().getIdentifier("cond_" + code, "drawable", "com.stev.smart_community");
			mWeatherIcon.setImageResource(resID);
			suggestionJson = weahterJson.getJSONObject(suggestion);
			
//			Log.d(TAG, "aqiJson =" + aqiJson);
//			Log.d(TAG, "basicJson =" + basicJson);
//			Log.d(TAG, "dailyForecastJsonArray =" + dailyForecastJsonArray);
//			Log.d(TAG, "hourlyForecastJsonArray =" + hourlyForecastJsonArray);
//			Log.d(TAG, "nowJson =" + nowJson);
//			Log.d(TAG, "suggestionJson =" + suggestionJson);
			
			SharedPreferences spWeatherInfo = getActivity().getSharedPreferences("weatherInfo", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = spWeatherInfo.edit();
			editor.putString("city", cityName);
			editor.putString("updateTime", updateName);
			editor.putString("weatherInfo", txt);
			editor.putString("temp", tmp);
			editor.putInt("weatherIcon", resID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void getWeatherData(String city) {
		mWeatherData.getWeatherInfo(city, new GetWeatherCallBack() {
			@Override
			public void onSuccess(int status, final String weatherInfo) {
				Log.i(TAG, "onSuccess");
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						initWeatherData(weatherInfo);
					}
				});
			}
			
			@Override
			public void onError(int status, String weatherInfo, final String error) {
				Log.e(TAG, "onError, status: " + status);
            	getActivity().runOnUiThread(new Runnable() {
					public void run() {
		            	Log.e(TAG, "errMsg: " + (error));
					}
				});
			}
			
			@Override
			public void onComplete() {
				Log.i(TAG, "onComplete");
			}
		});
	}
	
}
