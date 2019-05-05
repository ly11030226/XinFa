package com.ads.xinfa.ui.displayVideo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ads.xinfa.R;
import com.ads.xinfa.base.MyFragment;
import com.ads.xinfa.bean.MyBean;
import com.ads.xinfa.ui.aboutVideoList.RecyclerItemNormalHolder;
import com.ads.xinfa.ui.aboutVideoList.RecyclerNormalAdapter;
import com.ads.xinfa.ui.aboutVideoList.SampleCoverVideo;
import com.ads.xinfa.ui.aboutVideoList.VideoModel;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisplayVideoFragment extends MyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private Unbinder unbinder;
    private View view;
    @BindView(R.id.rcv)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerNormalAdapter mRecyclerNormalAdapter;
    private List<VideoModel> mVideoList = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DisplayVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayVideoFragment newInstance(String param1) {
        DisplayVideoFragment fragment = new DisplayVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_video, container, false);
        unbinder = ButterKnife.bind(this,view);
        try {
            resolveData();
            initRecycleView();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initData() {
        mRecyclerNormalAdapter = new RecyclerNormalAdapter(getActivity(),mVideoList);
        mRecyclerView.setAdapter(mRecyclerNormalAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE: //滚动停止
                        autoPlayVideo(recyclerView);
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if(!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            mRecyclerNormalAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void autoPlayVideo(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleCount = mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition();
        for (int i = 0; i < visibleCount; i++) {
            if (layoutManager != null && layoutManager.getChildAt(i) != null && layoutManager.getChildAt(i).findViewById(R.id.video_item_player) != null) {
                SampleCoverVideo homeGSYVideoPlayer = layoutManager.getChildAt(i).findViewById(R.id.video_item_player);
                Rect rect = new Rect();
                homeGSYVideoPlayer.getLocalVisibleRect(rect);
                int videoheight = homeGSYVideoPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (homeGSYVideoPlayer.getCurrentState() == homeGSYVideoPlayer.CURRENT_STATE_NORMAL || homeGSYVideoPlayer.getCurrentState() == homeGSYVideoPlayer.CURRENT_STATE_ERROR) {
                        homeGSYVideoPlayer.getStartButton().performClick();
                    }
                    return;
                }

            }
        }
//        GSYVideoPlayer.releaseAllVideos();
        GSYVideoManager.releaseAllVideos();
    }


    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void doInit(MyBean.GroupsBean.GroupBean.AreasBean.AreaBean areaBean) {

    }
    @Override
    public void display() {
    }

    public boolean onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    private void resolveData() {
        for (int i = 0; i < 19; i++) {
            VideoModel videoModel = new VideoModel();
            mVideoList.add(videoModel);
        }
        if (mRecyclerNormalAdapter != null)
            mRecyclerNormalAdapter.notifyDataSetChanged();
    }
}
