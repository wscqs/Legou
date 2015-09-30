package com.cqs.legou_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.cqs.util.Constant;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;

public class WelcomeStartActivity extends Activity{

    private RelativeLayout mRlStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_start);
        mRlStart = (RelativeLayout)findViewById(R.id.rl_start);
        startAnim();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if("".equals(BasicUtils.getSharedPreferences(getBaseContext(), Constant.FILE_NAME, Constant.MODE_NAME))){
                    startActivity(new Intent(getApplicationContext(),WelcomeGuideActivity.class));
                    BasicUtils.putSharedPreferences(getBaseContext(), Constant.FILE_NAME, Constant.MODE_NAME, "1");
                }else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                finish();
                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);
    }

    private void startAnim() {
        AnimationSet set=new AnimationSet(false);

        RotateAnimation rotate=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);

        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        mRlStart.startAnimation(set);
    }


}
