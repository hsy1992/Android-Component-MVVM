package com.endless.study.myproject.ui;

import android.view.MenuItem;

import com.endless.study.baselibrary.base.BaseActivity;
import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.myproject.R;
import com.endless.study.myproject.UserContract;
import com.endless.study.myproject.dagger.component.DaggerUserComponent;
import com.endless.study.myproject.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @author haosiyuan
 */
public class MainActivity extends BaseActivity<UserViewModel> implements UserContract.View {


    @BindView(R.id.navigationBar)
    BottomNavigationView navigationBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        navigationBar.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void initData() {
        assert viewModel != null;
    }

    /**
     * 导航切换
     * @param menuItem
     * @return
     */
    private boolean onNavigationItemSelected(MenuItem menuItem) {

        return false;
    }

    @Override
    public void setUpAppComponent(@NonNull AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showUser() {

    }

    @Override
    public void setViewPagerPosition(int position) {
        viewPager.setCurrentItem(position);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
