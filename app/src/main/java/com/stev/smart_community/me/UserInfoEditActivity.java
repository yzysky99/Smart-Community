package com.stev.smart_community.me;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;

public class UserInfoEditActivity extends Activity implements View.OnClickListener{
    private TextView mTitle;
    private ImageView mBackBtn;
    private RelativeLayout mEditNickname;
    private RelativeLayout mEditAvartar;
    private RelativeLayout mEditSinagture;
    private RelativeLayout mEditPassword;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.user_info_edit);

        mEditNickname = (RelativeLayout) findViewById(R.id.user_nickname);
        mEditAvartar = (RelativeLayout) findViewById(R.id.user_avartar);
        mEditSinagture = (RelativeLayout) findViewById(R.id.user_sinagture);
        mEditPassword = (RelativeLayout) findViewById(R.id.user_password);

        mBackBtn.setOnClickListener(this);
        mEditNickname.setOnClickListener(this);
        mEditAvartar.setOnClickListener(this);
        mEditSinagture.setOnClickListener(this);
        mEditPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_action_bar:
                finish();
                break;
            case R.id.user_nickname:
                intent = new Intent(this, EditNicknameActivity.class);
                startActivity(intent);
                break;
            case R.id.user_avartar:
//                intent = new Intent(this, EditNicknameActivity.class);
//                startActivity(intent);
                break;
            case R.id.user_sinagture:
                intent = new Intent(this, EditSinagtureActivity.class);
                startActivity(intent);
                break;
            case R.id.user_password:
                intent = new Intent(this, EditPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
