package com.endless.study.baselibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endless.study.baselibrary.base.delegate.IFragment;
import com.endless.study.baselibrary.cache.local.DefaultCacheType;
import com.endless.study.baselibrary.cache.local.ICache;
import com.endless.study.baselibrary.mvvm.view.IView;
import com.endless.study.baselibrary.mvvm.viewmodel.BaseViewModel;
import com.endless.study.baselibrary.utils.UtilCommon;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author haosiyuan
 * @date 2019-07-01 10:17
 * info : fragment 基类
 */
public abstract class BaseFragment<M extends BaseViewModel> extends Fragment implements IFragment, IView {

    protected final String TAG = this.getClass().getSimpleName();

    private ICache<String, Object> mCache;

    @Nullable
    protected M viewModel;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    private View mRootView;

    /**
     * 等待显示
     */
    private boolean waitingShowToUser;
    /**
     * 是否view准备就绪
     */
    private boolean isViewPrepare;
    /**
     * 加载数据
     */
    private boolean hasLoadData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }

        return mRootView;
    }

    @Override
    public void onViewCreated() {

        //改Fragment已显示
        if (getUserVisibleHint()) {
            isParentFragmentVisible();
        }

        initView();
    }

    /**
     * view准备好之后加载数据
     */
    private void lazyLoadDataIfPrepared() {
        if (this.getUserVisibleHint() && this.isViewPrepare && !this.hasLoadData) {
            this.initData();
            this.hasLoadData = true;
        }
    }

    public void onPauseLazy() {}

    public void onResumeLazy() {}

    /**
     * 设置是否显示
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (isParentFragmentVisible()) {
                return;
            }
        }

        //加入Activity
        if (getActivity() != null) {

            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                for (Fragment childFragment : childFragmentList) {
                    if (childFragment instanceof BaseFragment) {
                        BaseFragment childBaseFragment = (BaseFragment) childFragment;
                        if (childBaseFragment.isWaitingShowToUser()) {
                            childBaseFragment.setWaitingShowToUser(false);
                            childFragment.setUserVisibleHint(true);
                        }
                    }
                }
            } else {
                // 将所有正在显示的子Fragment设置为隐藏状态，并设置一个等待显示标记
                for (Fragment childFragment : childFragmentList) {
                    if (childFragment instanceof BaseFragment) {
                        BaseFragment childBaseFragment = (BaseFragment) childFragment;
                        if (childFragment.getUserVisibleHint()) {
                            childBaseFragment.setWaitingShowToUser(true);
                            childFragment.setUserVisibleHint(false);
                        }
                    }
                }
            }
        }

        if (isVisibleToUser) {
            this.lazyLoadDataIfPrepared();
        }

        if (isVisibleToUser && this.hasLoadData) {
            this.onResumeLazy();
        } else if (this.hasLoadData) {
            this.onPauseLazy();
        }

    }

    /**
     * 如果有父容器的话 父容器是否渲染完成 true break
     * @return
     */
    private boolean isParentFragmentVisible() {
        //父Fragment未显示 则该Fragment也隐藏
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
            waitingShowToUser = true;
            setUserVisibleHint(false);
            return true;
        }

        return false;
    }

    @Override
    public void showToast(@NonNull String message) {
        UtilCommon.getAppComponent(getContext()).toast().toast(getContext(), message,false);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    /**
     * 获取是否等待显示
     * @return
     */
    public boolean isWaitingShowToUser() {
        return waitingShowToUser;
    }

    public void setWaitingShowToUser(boolean waitingShowToUser) {
        this.waitingShowToUser = waitingShowToUser;
    }

    /**
     * 提供一个缓冲容器
     * @return
     */
    @Override
    public ICache<String, Object> getCacheData() {

        if (mCache == null) {
            mCache = UtilCommon.getAppComponent(getContext()).cacheFactory().build(new DefaultCacheType());
        }

        return mCache;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {

            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            mRootView = null;
        }
    }
}
