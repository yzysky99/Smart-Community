package com.stev.smart_community.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;
import com.stev.smart_community.ui.LoginActivity;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SettingsActivity";
    private TextView mTitle;
    private ImageView mBackBtn;

    private RelativeLayout mAbout;
    private RelativeLayout mSugget;
    private RelativeLayout mVersionInfo;
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.me_settings);

        mAbout = (RelativeLayout) findViewById(R.id.about);
        mSugget = (RelativeLayout) findViewById(R.id.suggest);
        mVersionInfo = (RelativeLayout) findViewById(R.id.version_info);
        mLogout = (Button) findViewById(R.id.btn_logout);

        mBackBtn.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mSugget.setOnClickListener(this);
        mVersionInfo.setOnClickListener(this);
        mLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_action_bar:
                finish();
                break;
            case R.id.about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.suggest:
                intent = new Intent(this, SuggestActivity.class);
                startActivity(intent);
                break;
            case R.id.version_info:
                intent = new Intent(this, VersionInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("是否确认退出？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences shared = getSharedPreferences(Constants.USER_INFO, Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = shared.edit();
                        Log.d(TAG,"stevyang value = " + shared.getBoolean(Constants.UserInfo.IS_LOGIN, false));
                        editor.clear();
                        editor.commit();
                        Log.d(TAG,"stevyang 22 value = " + shared.getBoolean(Constants.UserInfo.IS_LOGIN, false));
                        dialog.dismiss();
                        Intent loginIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                 });
                builder.show();
                break;
        }
    }
}
