package com.stev.smart_community.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.stev.smart_community.Config;

import android.util.Log;

public class WeatherInfo {
	private static final String TAG = "getWeather";
	String mWeatherInfo;

	public void getWeatherInfo(String city, GetWeatherCallBack callBack){
		BufferedReader reader = null;
	    String weatherInfo = null;
	    StringBuffer sbf = new StringBuffer();
	    String newHttpUrl = Config.HTTP_URL + "?city=" + city + Config.WEATHER_KEY;
	    int status = 0;

	    try {
	        URL url = new URL(newHttpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        weatherInfo = sbf.toString();
	        status = 1;
	        if(weatherInfo != null){
//	        	 Log.d(TAG, "weatherInfo = " + weatherInfo);
	        	 mWeatherInfo = weatherInfo;
	 	        callBack.onSuccess(status, weatherInfo);
	        }else{
	        	status = -1;
	        	callBack.onError(status, "", "weatherInfo is null");
	        }
	       
	    } catch (Exception e) {
	    	Log.e(TAG, "e = " + e);
	        e.printStackTrace();
	        status = -1;
	        callBack.onError(status, weatherInfo, e.getMessage());
	    }
	    callBack.onComplete();
	}

}
