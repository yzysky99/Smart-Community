package com.stev.smart_community.net;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.stev.smart_community.R;
import com.stev.smart_community.widget.ShopInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataRequest {
    protected static final String TAG = "DataRequest";

    Context mContext;
    public DataRequest(Context context) {
        mContext = context;
    }

    public JSONObject httpRequest(String tag, double latitude, double longitude) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;

        String ak = "MgBALVVeCd8THVBi6gPdvsvG";
        String requestUrl = "http://api.map.baidu.com/place/v2/search?" +
                "ak=" + ak +
                "&output=json" +
                "&query=" + tag +
                "&page_size=20" +
                "&page_num=1" +
                "&scope=2" +
                "&location=" + latitude + "," + longitude +
                "&radius=8000" +
                "&filter=sort_name:distance";

        try {
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setConnectTimeout(25 * 1000);
            httpUrlConn.setReadTimeout(25 * 1000);
            inputStream = httpUrlConn.getInputStream();
            if(httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }

                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                if (httpUrlConn != null) {
                    httpUrlConn.disconnect();
                }

                jsonObject = new JSONObject(buffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrlConn != null) {
                httpUrlConn.disconnect();
            }
        }
        Log.d(TAG,"jsonObject =  " + jsonObject.toString());
        return jsonObject;
    }

    public List<ShopInfo> parseData(JSONObject jsonObject) {
        List<ShopInfo> list = new ArrayList<>();

        try {

            int status = jsonObject.optInt("status");
            String message = jsonObject.optString("message");
            int total = jsonObject.optInt("total");
            Log.d(TAG, "status = " + status);
            Log.d(TAG, "message = " + message);
            Log.d(TAG, "total = " + total);

            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i< jsonArray.length(); i++) {
//                jsonArray.ge
                JSONObject jsonData = jsonArray.getJSONObject(i);
                ShopInfo shopInfo = new ShopInfo();
                shopInfo.name = jsonData.optString("name");
                shopInfo.address = jsonData.optString("address");
                shopInfo.streetId = jsonData.optString("street_id");
                shopInfo.telephone = jsonData.optString("telephone");
                shopInfo.detail = jsonData.optString("detail");
                shopInfo.uid = jsonData.optString("uid");

                JSONObject jsonLocation = jsonData.getJSONObject("location");
                shopInfo.lat = jsonLocation.optDouble("lat");
                shopInfo.lng = jsonLocation.optDouble("lng");

                JSONObject jsonDetailInfo = jsonData.getJSONObject("detail_info");
                //detail_info
                shopInfo.distance = jsonDetailInfo.optInt("distance");
                shopInfo.tag = jsonDetailInfo.optString("tag");
                shopInfo.type = jsonDetailInfo.optString("type");
                shopInfo.detailUrl = jsonDetailInfo.optString("detail_url");
                shopInfo.price = jsonDetailInfo.optString("price");
                shopInfo.overallRating = jsonDetailInfo.optString("overall_rating");
                shopInfo.serviceRating = jsonDetailInfo.optString("service_rating");
                shopInfo.environmentRating = jsonDetailInfo.optString("environment_rating");
                shopInfo.imageNum = jsonDetailInfo.optString("image_num");
                shopInfo.grouponNum = jsonDetailInfo.optInt("groupon_num");
                shopInfo.commentNum = jsonDetailInfo.optString("comment_num");

                shopInfo.shopLogo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.shop_logo);
                list.add(shopInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        for (ShopInfo shopInfo1 : list) {
            Log.d(TAG, "name = " + shopInfo1.name);
            Log.d(TAG, "address = " + shopInfo1.address);
            Log.d(TAG, "streetId = " + shopInfo1.streetId);
            Log.d(TAG, "telephone = " + shopInfo1.telephone);
            Log.d(TAG, "detail = " + shopInfo1.detail);
            Log.d(TAG, "uid = " + shopInfo1.uid);
            Log.d(TAG, "lat = " + shopInfo1.lat);
            Log.d(TAG, "lng = " + shopInfo1.lng);
            Log.d(TAG, "distance = " + shopInfo1.distance);
            Log.d(TAG, "tag = " + shopInfo1.tag);
            Log.d(TAG, "type = " + shopInfo1.type);

            Log.d(TAG, "detailUrl = " + shopInfo1.detailUrl);
            Log.d(TAG, "price = " + shopInfo1.price);
            Log.d(TAG, "overallRating = " + shopInfo1.overallRating);
            Log.d(TAG, "serviceRating = " + shopInfo1.serviceRating);
            Log.d(TAG, "environmentRating = " + shopInfo1.environmentRating);
            Log.d(TAG, "imageNum = " + shopInfo1.imageNum);
            Log.d(TAG, "grouponNum = " + shopInfo1.grouponNum);
            Log.d(TAG, "commentNum = " + shopInfo1.commentNum);
        }*/

        return list;
    }

}
