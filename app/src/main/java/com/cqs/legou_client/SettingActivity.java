package com.cqs.legou_client;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;


public class SettingActivity extends AppCompatActivity {
    private MaterialDialog mMaterialDialog;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @OnClick({R.id.bt_know, R.id.bt_call})
    public void view(View v) {
        switch (v.getId()) {
            case R.id.bt_know:
                mMaterialDialog = new MaterialDialog(SettingActivity.this).setTitle("操作须知")
                        .setMessage("用户登陆后,点击头像直接修改用户信息.\n点击退出:表示退出用户信息.\n双击后退键退出应用.\n联系电话是作者电话.")
                        .setPositiveButton("了解", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("关闭", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });
                mMaterialDialog.show();
                break;
            case R.id.bt_call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "18094041575"));
                startActivity(callIntent);
                break;
//            case R.id.bt_check:
//                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }
}
