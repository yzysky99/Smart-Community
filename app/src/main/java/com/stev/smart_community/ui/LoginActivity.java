package com.stev.smart_community.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stev.smart_community.Constants;
import com.stev.smart_community.MainActivity;
import com.stev.smart_community.R;
import com.stev.smart_community.widget.ToastView;

import java.util.List;

public class LoginActivity extends Activity{
    private static final String TAG = "LoginActivity";

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        TextView registerTv = (TextView) findViewById(R.id.register);
        registerTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        registerTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mProgressView = findViewById(R.id.login_progress);

        mShared = getSharedPreferences(Constants.USER_INFO, Context.MODE_WORLD_READABLE);
        mEditor = mShared.edit();

        if (mShared.getBoolean(Constants.UserInfo.IS_LOGIN, false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            mEditor.putBoolean(Constants.UserInfo.IS_LOGIN, false);
            mEditor.commit();
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
            mAuthTask = new UserLoginTask(phone, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() >= 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPhoneView.getWindowToken(), 0);
    }

    private void showProgress(final boolean show) {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPhone;
        private final String mPassword;

        UserLoginTask(String phone, String password) {
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

            if (mPhone == mShared.getString(Constants.UserInfo.USER_PHONE, "")
                    && mPassword == mShared.getString(Constants.UserInfo.USER_PASSWORD, "")) {
                return true;
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
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastView toast = new ToastView(LoginActivity.this, getString(R.string.error_incorrect_password));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

