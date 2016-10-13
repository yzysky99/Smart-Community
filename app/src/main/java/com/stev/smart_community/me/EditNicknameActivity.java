package com.stev.smart_community.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;
import com.stev.smart_community.widget.ToastView;

public class EditNicknameActivity extends Activity {
    private ImageView mBackBtn;
    private TextView mTitle;
    private EditText mUserName;
    private Button mSaveBtn;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nickname);

        mUserName = (EditText) findViewById(R.id.edit_nickname);
        mSaveBtn = (Button) findViewById(R.id.btn_save);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.title_activity_edit_nickname);

        mShared = getSharedPreferences(Constants.USER_INFO,  Context.MODE_WORLD_READABLE);
        final String userName = mShared.getString(Constants.UserInfo.USER_NAME, "");
        mEditor = mShared.edit();
        mUserName.setText(userName);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String changNickname = mUserName.getText().toString();
                if ("".equals(changNickname)) {
                    ToastView toast = new ToastView(EditNicknameActivity.this, getString(R.string.nickname_need_not_empty));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mUserName.setText("");
                    mUserName.requestFocus();
                } else if (mUserName.equals(changNickname)) {
                    ToastView toast = new ToastView(EditNicknameActivity.this, getString(R.string.nickname_not_change));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mUserName.requestFocus();
                } else {
                    mEditor.putString(Constants.UserInfo.USER_NAME, changNickname);
                    mEditor.commit();
                    finish();
                }
            }
        });
    }

}
