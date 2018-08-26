package org.ninetripods.mq.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.ninetripods.mq.study.util.fragment.HomeFragment;
import org.ninetripods.mq.study.util.fragment.MultiThreadFragment;
import org.ninetripods.mq.study.util.fragment.NestedScrollFragment;
import org.ninetripods.mq.study.util.fragment.PopFragment;
import org.ninetripods.mq.study.util.fragment.ProcessFragment;
import org.ninetripods.mq.study.util.fragment.RecycleFragment;

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
        selectItem(1);
    }

    @Override
    public void initEvents() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_navigation_0:
                        selectItem(0);
                        break;
                    case R.id.my_navigation_1:
                        selectItem(1);
                        break;
                    case R.id.my_navigation_2:
                        selectItem(2);
                        break;
                    case R.id.my_navigation_3:
                        selectItem(3);
                        break;
                    case R.id.my_navigation_4:
                        selectItem(4);
                        break;
                    case R.id.my_navigation_5:
                        selectItem(5);
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
            case 0:
                currentFragment = new HomeFragment();
                break;
            case 1:
                currentFragment = new PopFragment();
                break;
            case 2:
                currentFragment = new ProcessFragment();
                break;
            case 3:
                currentFragment = new RecycleFragment();
                break;
            case 4:
                currentFragment = new MultiThreadFragment();
                break;
            case 5:
                currentFragment = new NestedScrollFragment();
                break;
            default:
                currentFragment = new HomeFragment();
                break;
        }
        return currentFragment;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
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
}
