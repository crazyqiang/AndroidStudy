package org.ninetripods.mq.study.recycle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.water_fall.ImgUtils.ImageCache;
import org.ninetripods.mq.study.recycle.water_fall.ImgUtils.ImageFetcher;
import org.ninetripods.mq.study.recycle.water_fall.ImgUtils.Utils;
import org.ninetripods.mq.study.recycle.water_fall.MyDividerDecoration;
import org.ninetripods.mq.study.util.DpUtil;
import org.ninetripods.mq.study.util.adapter.WaterFallAdapter;

public class WaterFallActivity extends BaseActivity {

    private RecyclerView recycler_view;
    private WaterFallAdapter mAdapter;
    private ImageFetcher mImageFetcher;

    private static final String IMAGE_CACHE_DIR = "thumbs";//图片缓存目录

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_water_fall);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "图片瀑布流", true, true, 1);

        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);// Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(this, (int) DpUtil.dp2px(this, 100));
        mImageFetcher.setLoadingImage(R.mipmap.img_default_bg);
        mImageFetcher.addImageCache(cacheParams);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recycler_view.addItemDecoration(new MyDividerDecoration());
        mAdapter = new WaterFallAdapter(this, mImageFetcher);
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    public void initEvents() {
        mAdapter.setOnItemClickListener(new WaterFallAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                Toast.makeText(WaterFallActivity.this, "position is " + position, Toast.LENGTH_SHORT).show();
            }
        });
//        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                // Pause fetcher to ensure smoother scrolling when flinging
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                    // Before Honeycomb pause image loading on scroll to help with performance
//                    if (!Utils.hasHoneycomb()) {
//                        mImageFetcher.setPauseWork(true);
//                    }
//                } else {
//                    mImageFetcher.setPauseWork(false);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageFetcher.closeCache();
    }

    @Override
    public void clearCache() {
        mImageFetcher.clearCache();
        toast("缓存已清除");
    }
}
