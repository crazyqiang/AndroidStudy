package org.ninetripods.mq.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by MQ on 2017/1/16.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isShowRight;
    private int rightType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        initEvents();
    }

    public void setContentView() {
    }

    public void initViews() {
    }

    public void initEvents() {
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp) {
        initToolBar(toolbar, name, showHomeAsUp, false);
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp, boolean isShowRight) {
        initToolBar(toolbar, name, showHomeAsUp, isShowRight, 0);
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp, boolean isShowRight, int rightType) {
        this.isShowRight = isShowRight;
        this.rightType = rightType;
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;
        if (isShowRight) {
            getMenuInflater().inflate(R.menu.toolbar_right, menu);
            menuItem = menu.findItem(R.id.action_icon);
            switch (rightType) {
                case 1:
                    //清除缓存
                    menuItem.setTitle("清除缓存");
                    break;
                case 2:
                    //清除缓存
                    menuItem.setTitle("BLOG");
                    break;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_icon:
                switch (rightType) {
                    case 0:
                        //通讯录添加Item动画
                        add();
                        break;
                    case 1:
                        //清除缓存
                        clearCache();
                        break;
                    case 2:
                        //打开webview
                        openWebview();
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openWebview() {

    }

    @Override
    public void onClick(View v) {

    }

    public void add() {
    }

    public void clearCache() {

    }

    /**
     * @param str 弹出的文字
     */
    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
