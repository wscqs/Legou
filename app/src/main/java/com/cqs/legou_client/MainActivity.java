package com.cqs.legou_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqs.entity.User;
import com.cqs.fragment.ContentFragment;
import com.cqs.util.Constant;
import com.cqs.util.MyUtils;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.ui.ToastUtil;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout mAppbar;
    @InjectView(R.id.frame_content)
    FrameLayout mFrameContent;
    @InjectView(R.id.main_content)
    CoordinatorLayout mMainContent;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.navigation_view)
    NavigationView mNavigationView;

    @InjectView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @InjectView(R.id.tv_username)
    TextView mTvUsername;

    @OnClick({R.id.iv_avatar, R.id.tv_username})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
            case R.id.tv_username:
                String userid = BasicUtils.getSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "userid");
                if(TextUtils.isEmpty(userid)){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, MyUtils.LOGIN_REQUESTCODE);
                }else {
                    Intent intent=new Intent(MainActivity.this,UpdateUserActivity.class);
                    startActivityForResult(intent, MyUtils.UPDATEUSER_REQUESTCODE);
                }

                break;

        }
    }

    private ActionBarDrawerToggle mDrawerToggle;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //设置toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigationView);

        String username = BasicUtils.getSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "username");
        if (!TextUtils.isEmpty(username)) {
            mTvUsername.setText(username);
            String avatar = BasicUtils.getSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "avatar");
            if (!TextUtils.isEmpty(avatar)) {
                Picasso.with(getApplicationContext()).load(avatar).placeholder(R.mipmap.icon).into(mIvAvatar);
            }
        }
        switchToContent();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_home:
//                        switchToContent();
                        break;
                    case R.id.navigation_item_about:
                        switchToAbout();
                        break;
                    case R.id.navigation_item_exit:
                        exitUser();
                        break;
                    case R.id.navigation_item_setting:
                        switchToSetting();
                        break;
                }
                menuItem.setCheckable(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }



    private void exitUser() {
        String username = BasicUtils.getSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "username");
        if(TextUtils.isEmpty(username)){
            ToastUtil.show(getApplicationContext(), "用户未登陆");
        }else {
            BasicUtils.putSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "username", "");
            BasicUtils.putSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "avatar", "");
            BasicUtils.putSharedPreferences(getApplicationContext(), Constant.FILE_NAME, "userid", "");

            setDefaltAvatarName();
        }
    }

    private void setDefaltAvatarName() {
        mTvUsername.setText("请登陆");
        mIvAvatar.setImageResource(R.mipmap.icon);
        ToastUtil.show(getApplicationContext(),"用户已成功退出");
    }
    private void switchToSetting() {
       /* getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new SettingFragment()).commit();
        setTitle("设置");*/
        Intent intent=new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
    }
    private void switchToContent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ContentFragment()).commit();
        setTitle("首页");
    }

    private void switchToAbout() {
       /* getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
        setTitle("关于");*/
       Intent intent=new Intent(MainActivity.this,AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    //获取mtoolbar
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MyUtils.SOCIAL_RESULTCODE:
                User user = data.getExtras().getParcelable("user");
                Picasso.with(getApplicationContext()).load(user.getAvatar()).into(mIvAvatar);
                mTvUsername.setText(user.getName());
            case MyUtils.LOGIN_RESULTCODE:
                User user1 = data.getExtras().getParcelable("user");
                mTvUsername.setText(user1.getName());
                Picasso.with(getApplicationContext()).load(user1.getAvatar()).placeholder(R.mipmap.icon).into(mIvAvatar);
            case MyUtils.REGISTER_RESULTCODE:
                User user2 = data.getExtras().getParcelable("user");
                mTvUsername.setText(user2.getName());
                Picasso.with(getApplicationContext()).load(user2.getAvatar()).placeholder(R.mipmap.icon).into(mIvAvatar);
            case MyUtils.UPDATEUSER_RESULT:
                User user3 = data.getExtras().getParcelable("user");
                mTvUsername.setText(user3.getName());
                Picasso.with(getApplicationContext()).load(user3.getAvatar()).placeholder(R.mipmap.icon).into(mIvAvatar);
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_share:
//                    msg += "Click share";
                    shareApplication();
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    // Intent { act=android.intent.action.SEND typ=text/plain flg=0x3000000 cmp=com.android.mms/.ui.ComposeMessageActivity (has extras) } from pid 256
    private void shareApplication() {
        Intent intent=new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"推荐您使用省钱利器软件，名称叫：乐购团购");
        startActivity(intent);
    }
}
