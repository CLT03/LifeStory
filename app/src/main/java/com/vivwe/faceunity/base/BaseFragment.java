package com.vivwe.faceunity.base;

import android.support.v4.app.Fragment;
import com.vivwe.faceunity.listener.OnFragmentListener;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:46
 * remark: 该类用于执行fragment执行后的一些意图。
 */
public class BaseFragment extends Fragment {

    /**
     * Fragment监听
     */
    private OnFragmentListener onFragmentListener;


    public OnFragmentListener getOnFragmentListener() {
        return onFragmentListener;
    }

    public void setOnFragmentListener(OnFragmentListener onFragmentListener) {
        this.onFragmentListener = onFragmentListener;
    }
}
