package com.example.myapp;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

public class MainActivity extends AppCompatActivity {
    private static String OAUTH_ID = String.valueOf(R.string.naver_client_id);
    private static String OAUTH_SECRET = String.valueOf(R.string.naver_client_secret);
    private static String OAUTH_NAME = String.valueOf(R.string.naver_client_name);

    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;

    private OAuthLoginButton mOAuthLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initData();
    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_ID, OAUTH_SECRET, OAUTH_NAME);

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.btn_naverlogin);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

        // 색상변경 to white
        mOAuthLoginButton.setBgResourceId(R.drawable.btn_naver_white_full);
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                redirectSignupActivity();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode: " + errorCode + ", errorDesc :" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    // after success
    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, AfterActivity.class);
        startActivity(intent);
        finish();
    }
}
