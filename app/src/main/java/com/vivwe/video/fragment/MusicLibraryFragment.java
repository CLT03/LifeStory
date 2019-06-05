package com.vivwe.video.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivwe.base.activity.BaseFragment;
import com.vivwe.base.constant.Globals;
import com.vivwe.main.R;
import com.vivwe.video.adapter.MusicLibraryAdapter;
import com.vivwe.video.entity.MusicEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MusicLibraryFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MusicLibraryAdapter adapter;

    /** 数据 */
//    private List<MusicEntity> datas;
    /** 页码 */
    private int pageNum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_music_library, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(adapter == null){
            adapter=new MusicLibraryAdapter(getActivity());
        }
        recyclerView.setAdapter(adapter);
    }

    public MusicLibraryAdapter getAdapter() {
        return adapter;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getNextPageNum(){
        return pageNum + 1;
    }

    public void setNextPageNum() {
        pageNum ++;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(Globals.isDebug){
            Log.v(">>>MusicLibraryFragment","destoryView");
        }
        unbinder.unbind();
    }
}
