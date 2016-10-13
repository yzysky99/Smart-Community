package com.stev.smart_community.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;
import com.stev.smart_community.widget.ToastView;

public class EditPasswordActivity extends Activity {
    private ImageView mBackBtn;
    private TextView mTitle;
    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mConfirmPassword;

    private Button mSaveBtn;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        mOldPassword = (EditText) findViewById(R.id.edit_old_password);
        mNewPassword = (EditText) findViewById(R.id.edit_new1_password);
        mConfirmPassword = (EditText) findViewById(R.id.edit_new2_password);

        mSaveBtn = (Button) findViewById(R.id.btn_save);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.title_activity_edit_password);

        mShared = getSharedPreferences(Constants.USER_INFO,  Context.MODE_WORLD_READABLE);
        final String password = mShared.getString(Constants.UserInfo.USER_PASSWORD, "");
        mEditor = mShared.edit();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputOldPwd = mOldPassword.getText().toString();
                if (!inputOldPwd.equals(password)) {
                    ToastView toast = new ToastView(EditPasswordActivity.this, getString(R.string.input_old_passowrd_error));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                String inputNewPwd = mNewPassword.getText().toString();
                if ("".equals(inputNewPwd)) {
                    ToastView toast = new ToastView(EditPasswordActivity.this, getString(R.string.new_passowrd_need_not_empty));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mNewPassword.setText("");
                    mNewPassword.requestFocus();
                    return;
                }

                String inputComfirmPwd = mConfirmPassword.getText().toString();
                if (!inputNewPwd.equals(inputComfirmPwd)) {
                    ToastView toast = new ToastView(EditPasswordActivity.this, getString(R.string.input_passowrd_not_same));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (inputOldPwd.equals(inputNewPwd)) {
                    ToastView toast = new ToastView(EditPasswordActivity.this, getString(R.string.passowrd_not_change));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mNewPassword.setText("");
                    mNewPassword.requestFocus();
                } else {
                    mEditor.putString(Constants.UserInfo.USER_PASSWORD, inputComfirmPwd);
                    mEditor.commit();
                    finish();
                }
            }
        });
    }
}
