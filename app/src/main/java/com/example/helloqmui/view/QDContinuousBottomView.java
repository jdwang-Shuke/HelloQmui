package com.example.helloqmui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloqmui.BottomListAdapter;
import com.example.helloqmui.common.DisplayUtils;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.nestedScroll.IQMUIContinuousNestedBottomView;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout;
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import org.jetbrains.annotations.NotNull;

public class QDContinuousBottomView extends QMUIContinuousNestedBottomDelegateLayout {

    private MyViewPager mViewPager;
    private QMUIContinuousNestedBottomRecyclerView mCurrentItemView;
    private int mCurrentPosition = -1;
    private IQMUIContinuousNestedBottomView.OnScrollNotifier mOnScrollNotifier;

    public QDContinuousBottomView(Context context) {
        super(context);
    }

    public QDContinuousBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QDContinuousBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //吸顶布局高度
    private static final int stickyBarSize = DisplayUtils.dp2px(50f);

    @NonNull
    @Override
    protected View onCreateHeaderView() {
        LinearLayout vpBelow = new LinearLayout(getContext());
        vpBelow.setGravity(Gravity.BOTTOM);
        TabLayout tabLayout = new TabLayout(getContext());
        tabLayout.setBackgroundColor(Color.parseColor("#ff8a00"));
        ViewGroup.LayoutParams tabParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, stickyBarSize
        );
        tabLayout.setLayoutParams(tabParams);
        tabLayout.setTabTextColors(Color.BLACK,Color.BLUE);
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager,true);
        vpBelow.addView(tabLayout);
        return vpBelow;
    }

    @Override
    protected int getHeaderHeightLayoutParam() {
        return getHeaderStickyHeight();
    }

    @Override
    protected int getHeaderStickyHeight() {
        return stickyBarSize;//吸顶标签
    }


    @NonNull
    @Override
    protected View onCreateContentView() {
        mViewPager = new MyViewPager(getContext());
        mViewPager.setAdapter(new QMUIPagerAdapter() {

            @NotNull
            @Override
            public CharSequence getPageTitle(int position) {
                return "ITEM::"+(position+1);
            }

            @NotNull
            @Override
            protected Object hydrate(@NotNull ViewGroup container, int position) {
                QMUIContinuousNestedBottomRecyclerView recyclerView = new QMUIContinuousNestedBottomRecyclerView(getContext());

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
                    @Override
                    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
                recyclerView.setAdapter(new BottomListAdapter(getContext()));
                return recyclerView;
            }

            @Override
            protected void populate(@NotNull ViewGroup container, @NotNull Object item, int position) {
                container.addView((View) item);
            }

            @Override
            protected void destroy(@NotNull ViewGroup container, int position, @NotNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.setPrimaryItem(container, position, object);
                mCurrentItemView = (QMUIContinuousNestedBottomRecyclerView) object;
                mCurrentPosition = position;
                if (mOnScrollNotifier != null) {
                    mCurrentItemView.injectScrollNotifier(mOnScrollNotifier);
                }
            }
        });
        return mViewPager;
    }

    class MyViewPager extends QMUIViewPager implements IQMUIContinuousNestedBottomView {
        static final String KEY_CURRENT_POSITION = "demo_bottom_current_position";

        public MyViewPager(Context context) {
            super(context);
        }

        @Override
        public void consumeScroll(int dyUnconsumed) {
            if (mCurrentItemView != null) {
                mCurrentItemView.consumeScroll(dyUnconsumed);
            }

        }

        @Override
        public void smoothScrollYBy(int dy, int duration) {
            if (mCurrentItemView != null) {
                mCurrentItemView.smoothScrollYBy(dy, duration);
            }
        }

        @Override
        public void stopScroll() {
            if (mCurrentItemView != null) {
                mCurrentItemView.stopScroll();
            }
        }

        @Override
        public int getContentHeight() {
            if (mCurrentItemView != null) {
                return mCurrentItemView.getContentHeight();
            }
            return 0;
        }

        @Override
        public int getCurrentScroll() {
            if (mCurrentItemView != null) {
                return mCurrentItemView.getCurrentScroll();
            }
            return 0;
        }

        @Override
        public int getScrollOffsetRange() {
            if (mCurrentItemView != null) {
                return mCurrentItemView.getScrollOffsetRange();
            }
            return getHeight();
        }

        @Override
        public void injectScrollNotifier(OnScrollNotifier notifier) {
            mOnScrollNotifier = notifier;
            if (mCurrentItemView != null) {
                mCurrentItemView.injectScrollNotifier(notifier);
            }
        }

        @Override
        public void saveScrollInfo(@NonNull Bundle bundle) {
            bundle.putInt(KEY_CURRENT_POSITION, mCurrentPosition);
            if (mCurrentItemView != null) {
                mCurrentItemView.saveScrollInfo(bundle);
            }
        }

        @Override
        public void restoreScrollInfo(@NonNull Bundle bundle) {
            if (mCurrentItemView != null) {
                int currentPos = bundle.getInt(KEY_CURRENT_POSITION, -1);
                if (currentPos == mCurrentPosition) {
                    mCurrentItemView.restoreScrollInfo(bundle);
                }
            }
        }

    }
}