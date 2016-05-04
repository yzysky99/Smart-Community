package com.stev.smart_community.weather;


public interface GetWeatherCallBack {
      public void onSuccess(int status, String weatherInfo);
      public void onComplete();
      public void onError(int status, String weatherInfo, String error);
}
