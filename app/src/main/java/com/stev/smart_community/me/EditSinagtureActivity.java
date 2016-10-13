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

public class EditSinagtureActivity extends Activity {
    private ImageView mBackBtn;
    private TextView mTitle;
    private EditText mSinagture;
    private Button mSaveBtn;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sinagture);

        mSinagture = (EditText) findViewById(R.id.edit_sinagture);
        mSaveBtn = (Button) findViewById(R.id.btn_save);

        mBackBtn = (ImageView) findViewById(R.id.back_action_bar);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.title_activity_edit_sinagture);

        mShared = getSharedPreferences(Constants.USER_INFO,  Context.MODE_WORLD_READABLE);
        final String sinagture = mShared.getString(Constants.UserInfo.USER_SIGNATURE, "");
        mEditor = mShared.edit();
        mSinagture.setText(sinagture);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String changSig = mSinagture.getText().toString();
                if (sinagture.equals(changSig)) {
                    ToastView toast = new ToastView(EditSinagtureActivity.this, getString(R.string.passowrd_not_change));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mSinagture.requestFocus();
                } else {
                    mEditor.putString(Constants.UserInfo.USER_SIGNATURE, changSig);
                    mEditor.commit();
                    finish();
                }
            }
        });
    }

}
