package org.ninetripods.mq.study;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import org.ninetripods.mq.study.jetpack.JetpackAndKtFragment;
import org.ninetripods.mq.study.jetpack.KConsts;
import org.ninetripods.mq.study.jetpack_compose.JetpackComposeFragment;
import org.ninetripods.mq.study.util.WebViewPool;
import org.ninetripods.mq.study.util.fragment.HomeFragment;
import org.ninetripods.mq.study.util.fragment.MultiThreadFragment;
import org.ninetripods.mq.study.util.fragment.NestedScrollFragment;
import org.ninetripods.mq.study.util.fragment.PopFragment;
import org.ninetripods.mq.study.util.fragment.ProcessFragment;
import org.ninetripods.mq.study.recycle.RecycleFragment;

public class MainActivity extends BaseActivity {
    private long back_pressed;
    private DrawerLayout drawer_layout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private int currentPos = -1;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, getResources().getString(R.string.app_name), false);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0);
        drawerToggle.syncState();
        //NOTE: 切换初始化展示的Fragment
        selectItem(KConsts.FRAGMENT_VIEW);
    }

    @Override
    public void initEvents() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_navigation_0:
                        selectItem(KConsts.FRAGMENT_HOME);
                        break;
                    case R.id.my_navigation_6:
                        selectItem(KConsts.FRAGMENT_JETPACK);
                        break;
                    case R.id.my_navigation_7:
                        selectItem(KConsts.FRAGMENT_JETPACK_COMPOSE);
                        break;
                    case R.id.my_navigation_1:
                        selectItem(KConsts.FRAGMENT_POP);
                        break;
                    case R.id.my_navigation_2:
                        selectItem(KConsts.FRAGMENT_PROCESS);
                        break;
                    case R.id.my_navigation_3:
                        selectItem(KConsts.FRAGMENT_RECYCLERVIEW);
                        break;
                    case R.id.my_navigation_4:
                        selectItem(KConsts.FRAGMENT_MULTI_THREAD);
                        break;
                    case R.id.my_navigation_5:
                        selectItem(KConsts.FRAGMENT_NESTED_SCROLLER);
                        break;
                    case R.id.my_navigation_8:
                        selectItem(KConsts.FRAGMENT_VIEW);
                        break;
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void selectItem(int pos) {
        //点击的正是当前正在显示的，直接返回
        if (currentPos == pos) return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null) {
            //隐藏当前正在显示的fragment
            transaction.hide(currentFragment);
        }
        currentPos = pos;
        Fragment fragment = manager.findFragmentByTag(getTag(pos));
        //通过findFragmentByTag判断是否已存在目标fragment，若存在直接show，否则去add
        if (fragment != null) {
            currentFragment = fragment;
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_container, getFragment(pos), getTag(pos));
        }
        transaction.commitAllowingStateLoss();
    }

    private String getTag(int pos) {
        return "fg_tag_" + pos;
    }

    private Fragment getFragment(int pos) {
        switch (pos) {
            case KConsts.FRAGMENT_JETPACK:
                currentFragment = JetpackAndKtFragment.newInstance("");
                break;
            case KConsts.FRAGMENT_JETPACK_COMPOSE:
                currentFragment = new JetpackComposeFragment();
                break;
            case KConsts.FRAGMENT_POP:
                currentFragment = new PopFragment();
                break;
            case KConsts.FRAGMENT_PROCESS:
                currentFragment = new ProcessFragment();
                break;
            case KConsts.FRAGMENT_RECYCLERVIEW:
                currentFragment = new RecycleFragment();
                break;
            case KConsts.FRAGMENT_MULTI_THREAD:
                currentFragment = new MultiThreadFragment();
                break;
            case KConsts.FRAGMENT_NESTED_SCROLLER:
                currentFragment = new NestedScrollFragment();
                break;
            case KConsts.FRAGMENT_VIEW:
                currentFragment = new ViewFragment();
                break;
            case KConsts.FRAGMENT_HOME:
            default:
                currentFragment = new HomeFragment();
                break;
        }
        return currentFragment;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            toast("再点一次退出应用");
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        WebViewPool.INSTANCE.releaseAll();
        super.onDestroy();
    }
}
