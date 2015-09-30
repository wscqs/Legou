package com.cqs.legou_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
import com.marshalchen.common.ui.ToastUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity {


    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout mAppbar;
    @InjectView(R.id.et_username)
    EditText mEtUsername;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.ti_username)
    TextInputLayout mTiUsername;
    private Intent mIntent;
    private User mUser;
    private int mState;
    private String mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mToolbar.setTitle("用户登录");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        mUser = new User();
    }

    private void toast(String s) {
        ToastUtil.show(LoginActivity.this, s);
    }

    @OnClick(R.id.btn_qq)
    public void loginqq(View v) {
        LoginQQ();

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onclick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                mTiUsername.setError("");
                final String username = mEtUsername.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    toast("用户名不能为空");
                    mEtPassword.setText("");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    toast("密码不能为空");
                    return;
                }
                RequestParams params = new RequestParams();
                params.add("username", username);
                params.add("password", password);

                mUser.setName(username);
                HttpUtilsAsync.post(Constant.USER_LOGIN, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String data = new String(bytes);
                        Gson gson = new Gson();
                        ResponseObject<User> result = gson.fromJson(data, new TypeToken<ResponseObject<User>>() {
                        }.getType());
                        mMsg=result.getMsg();
                        if (1 == result.getState()) {
                            mIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mIntent.putExtra("user", mUser);
                            setResult(MyUtils.SOCIAL_RESULTCODE, mIntent);
                            User datas = result.getDatas();
                            mUser.setId(datas.getId());
                            putSharePreferences(mUser);
                            toast(mMsg);
                            finish();
                        } else {
                            mEtPassword.setText("");
                            mEtUsername.setText("");
                            mEtUsername.requestFocus();
                            toast(mMsg);
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        MyUtils.showFalseToast(getApplicationContext());
                    }
                });
                break;
            case R.id.btn_register:
                SMSSDK.initSDK(this, "abeb20b52f98", "fecab15491e63c30540f852effb013a6");
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            final String phone = (String) phoneMap.get("phone");

                            //                    // 提交用户信息
                            //                    registerUser(country, phone);
                            RequestParams params = new RequestParams();
                            params.add("username", phone);
                            params.add("password", phone);


                            HttpUtilsAsync.post(Constant.USER_REGISTER, params, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    String data = new String(bytes);
                                    Gson gson = new Gson();
                                    ResponseObject<User> result = gson.fromJson(data, new TypeToken<ResponseObject<User>>() {
                                    }.getType());
                                    mMsg=result.getMsg();
                                    mUser.setName(phone);
                                    if (1 == result.getState()) {
                                        toast(mMsg);
                                        User datas = result.getDatas();
                                        mUser.setId(datas.getId());
                                    } else {
                                        toast(mMsg);
                                    }
                                    mUser.setName(phone);
                                    mUser.setTel(phone);
                                    mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mIntent.putExtra("user", mUser);
                                    setResult(MyUtils.REGISTER_RESULTCODE, mIntent);
                                    putSharePreferences(mUser);
                                    finish();
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                    MyUtils.showFalseToast(getApplicationContext());
                                }
                            });
                        }
                    }
                });
                registerPage.show(this);
                break;
            default:

        }

    }

    public static Tencent mTencent;

    //这里是调用QQ登录的关键代码
    public void LoginQQ() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
        }
        mTencent.logout(this);
        mTencent.login(this, "all", new IUiListener() {

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null) {
                    final JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        final String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        final String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        /**到此已经获得OpneID以及其他你想获得的内容了
                         QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
                         sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
                         如何得到这个UserInfo类呢？  */
                        QQToken qqToken = mTencent.getQQToken();
                        UserInfo info = new UserInfo(getApplicationContext(), qqToken);
                        //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON
                        info.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(Object o) {
                                JSONObject jsonObject1 = (JSONObject) o;
                                try {
                                    final String nickname = (String) jsonObject1.get("nickname");
                                    String avatar = (String) jsonObject1.get("figureurl_2");
                                    RequestParams params = new RequestParams();
                                    params.add("username", nickname);
                                    params.add("password", openId);
                                    params.add("avatar", avatar);
                                    mUser.setAvatar(avatar);
                                    mUser.setName(nickname);
                                    HttpUtilsAsync.post(Constant.USER_SOCIAL, params, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                            String data = new String(bytes);
                                            Gson gson = new Gson();
                                            ResponseObject<User> result = gson.fromJson(data, new TypeToken<ResponseObject<User>>() {
                                            }.getType());
                                            mMsg=result.getMsg();
                                            if (1 == result.getState()) {
                                                User datas = result.getDatas();
                                                mUser.setId(datas.getId());
                                                mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                                mIntent.putExtra("user", mUser);
                                                setResult(MyUtils.SOCIAL_RESULTCODE, mIntent);
                                                putSharePreferences(mUser);
                                                toast(mMsg);
                                            } else {
                                                toast(mMsg);
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                            MyUtils.showFalseToast(getApplicationContext());
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(UiError uiError) {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    } catch (JSONException e) {
                    }
                }
            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                toast("QQ授权出错：" + arg0.errorCode + "--" + arg0.errorDetail);
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                toast("取消qq授权");
            }
        });
    }

    private void putSharePreferences(User user) {
        HashMap<String, String> iuser = new HashMap<String, String>();
        iuser.put("username", user.getName());
        iuser.put("userid",user.getId());
        if (null != user.getAvatar()) {
            iuser.put("avatar", user.getAvatar());
        }
        BasicUtils.putSharedPreferences(getApplicationContext(), Constant.FILE_NAME, iuser);
    }

    @Override
    protected void onDestroy() {
        mUser = null;
        super.onDestroy();
    }
}
