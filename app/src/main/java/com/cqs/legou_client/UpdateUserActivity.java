package com.cqs.legou_client;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cqs.entity.ResponseObject;
import com.cqs.entity.User;
import com.cqs.util.Constant;
import com.cqs.util.MyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;
import com.marshalchen.common.commonUtils.urlUtils.UniversalImageLoader;
import com.marshalchen.common.ui.CircleImageView;
import com.marshalchen.common.ui.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chenqiusong on 15/9/28.
 */
public class UpdateUserActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.iv_update_avatar)
    CircleImageView mIvUpdateAvatar;
    @InjectView(R.id.ll_udpate_avatar)
    LinearLayout mLlUdpateAvatar;
    @InjectView(R.id.et_udpate_username)
    EditText mEtUdpateUsername;
    @InjectView(R.id.ll_update_username)
    LinearLayout mLlUpdateUsername;
    @InjectView(R.id.ll_udpate_loginpw)
    ImageView mLlUdpateLoginpw;
    @InjectView(R.id.et_update_loginpw)
    EditText mEtUpdateLoginpw;
    @InjectView(R.id.et_update_paypw)
    EditText mEtUpdatePaypw;
    @InjectView(R.id.ll_udpate_paypw)
    LinearLayout mLlUdpatePaypw;
    @InjectView(R.id.et_update_phone)
    EditText mEtUpdatePhone;
    @InjectView(R.id.ll_update_phone)
    LinearLayout mLlUpdatePhone;
    private String mAvatarPath;


    @OnClick(R.id.ll_udpate_avatar)
    public void udpateAvatar(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, MyUtils.GETIMAGE_REQUEST);

    }

    @OnClick(R.id.iv_update_commit)
    public void View(View v) {
        final String username = mEtUdpateUsername.getText().toString().trim();
        String loginpw = mEtUpdateLoginpw.getText().toString().trim();
        String paypw = mEtUpdatePaypw.getText().toString().trim();
        String phone = mEtUpdatePhone.getText().toString().trim();
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", loginpw);
        params.add("paypw", paypw);
        params.add("phone", phone);
        params.add("avatar", mAvatarPath);
        String userid = BasicUtils.getSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "userid");
        params.add("userid", userid);
        HttpUtilsAsync.post(Constant.USER_UPDATE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String datas = new String(bytes);
                Gson gson = new Gson();
                ResponseObject<Boolean> result = gson.fromJson(datas, new TypeToken<ResponseObject<Boolean>>() {
                }.getType());
                if (1 == result.getState()) {
                    if (result.getDatas()) {
                        ToastUtil.show(getApplicationContext(), result.getMsg());
                        BasicUtils.putSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "username", username);
                        BasicUtils.putSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "avatar", mAvatarPath);
                        User user = new User();
                        user.setName(username);
                        user.setAvatar(mAvatarPath);
                        Intent intent=new Intent(UpdateUserActivity.this,MainActivity.class);
                        intent.putExtra("user",user);
                        setResult(MyUtils.UPDATEUSER_RESULT, intent);
                        finish();
                    } else {
                        ToastUtil.show(getApplicationContext(), result.getMsg());
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), result.getMsg());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                MyUtils.showFalseToast(getApplicationContext());
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("更改信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (MyUtils.GETIMAGE_REQUEST == requestCode) {
            if (data != null) {
                Uri uri = data.getData();
                if (!TextUtils.isEmpty(uri.getAuthority())) {
                    Cursor cursor = getContentResolver().query(uri,
                            new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                    if (null == cursor) {
                        Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cursor.moveToFirst();
                    mAvatarPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();
                } else {
                    mAvatarPath = uri.getPath();
                }
                mAvatarPath = "file://" + mAvatarPath;
                ImageLoaderConfiguration config = UniversalImageLoader.getDefaultImageLoaderConfiguration(getApplicationContext());
                ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(mAvatarPath, mIvUpdateAvatar);
            } else {
                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
