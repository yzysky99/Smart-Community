package com.stev.smart_community.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stev.smart_community.Constants;
import com.stev.smart_community.MainActivity;
import com.stev.smart_community.R;

public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";
    private EditText mNameView;
    private EditText mPhoneView;
    private EditText mPasswordView;
    private Button mRegister;
    private TextView mLoginView;
    private View mProgressView;

    private UserRegisterTask mAuthTask = null;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
    }

    private void initData() {
        mNameView = (EditText) findViewById(R.id.name);
        mPhoneView = (EditText) findViewById(R.id.phone);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.register);
        mLoginView = (TextView) findViewById(R.id.login);
        mProgressView = findViewById(R.id.register_progress);

        mShared =getSharedPreferences(Constants.USER_INFO,  Context.MODE_WORLD_READABLE);
        mEditor = mShared.edit();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resigster();
            }
        });

        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void resigster() {
        if (mAuthTask != null) {
            return;
        }

        String name = mNameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            Toast toast = Toast.makeText(this, R.string.error_invalid_name, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Toast toast = Toast.makeText(this, R.string.error_invalid_password, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast toast = Toast.makeText(this, R.string.error_field_required, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            Toast toast = Toast.makeText(this, R.string.error_invalid_phone, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserRegisterTask(name, phone, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() >= 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mPhone;
        private final String mPassword;

        UserRegisterTask(String name, String phone, String password) {
            mName = name;
            mPhone = phone;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            Log.d(TAG,"onPostExecute success " + success);
            if (success) {
                mEditor.putBoolean(Constants.UserInfo.IS_LOGIN, true);
                mEditor.putString(Constants.UserInfo.USER_NAME, mName);
                mEditor.putString(Constants.UserInfo.USER_PHONE, mPhone);
                mEditor.putString(Constants.UserInfo.USER_PASSWORD, mPassword);
                mEditor.commit();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_register));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
