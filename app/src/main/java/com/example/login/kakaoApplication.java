package com.example.login;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class kakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Kakao SDK 초기화
        KakaoSdk.init(this, "{5cf27f9c755f242539bcd6db7e155ced}");
    }
}
