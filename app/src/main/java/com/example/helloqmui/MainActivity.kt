package com.example.helloqmui

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.helloqmui.base.BaseActivity
import com.example.helloqmui.common.DisplayUtils.getDimenDp
import com.example.helloqmui.common.DisplayUtils.statusBarHeight
import com.example.helloqmui.databinding.ActivityMainBinding
import com.example.helloqmui.databinding.ItemBannerBinding
import com.example.helloqmui.view.QDContinuousBottomView
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun doBusiness() {
        ImmersionBar.with(this)
            .titleBar(mBinding.flTitle).init()
        //top
        val mTopDelegateLayout = QMUIContinuousNestedTopDelegateLayout(this).apply {
            //背景色
            setBackgroundColor(Color.LTGRAY)
            //header
            headerView = ItemBannerBinding.inflate(layoutInflater).root
            //top list view
//            delegateView = QMUIContinuousNestedTopRecyclerView(context).apply {
//                layoutManager = object : GridLayoutManager(context, 6) {
//                }
//                adapter = TopListAdapter(context)
//            }
        }
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val topLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(this)
        mBinding.coordinator.setTopAreaView(mTopDelegateLayout, topLp)
        //bottom
        val mBottomView = QDContinuousBottomView(this)
        val bottomLp = CoordinatorLayout.LayoutParams(
            matchParent, matchParent
        )
        bottomLp.behavior = QMUIContinuousNestedBottomAreaBehavior().apply {
            setTopInset(statusBarHeight()/*状态栏*/ + getDimenDp(R.dimen.title_bar_size) /*title*/)
        }
        mBinding.coordinator.setBottomAreaView(mBottomView, bottomLp)
        //滑动监听
        setListener()
    }

    private fun setListener() {
        //刷新;下拉刷新圆圈对刘海的偏移？？？
        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveRefreshView(offset: Int) {
            }

            override fun onRefresh() {
                Handler(Looper.getMainLooper()).postDelayed({
                    mBinding.pullToRefresh.finishRefresh()
                }, 2000)
            }

            override fun onMoveTarget(offset: Int) {
            }
        })
        mBinding.coordinator.addOnScrollListener(object :
            QMUIContinuousNestedScrollLayout.OnScrollListener {
            override fun onScrollStateChange(
                scrollLayout: QMUIContinuousNestedScrollLayout?,
                newScrollState: Int,
                fromTopBehavior: Boolean
            ) {
            }

            override fun onScroll(
                scrollLayout: QMUIContinuousNestedScrollLayout?,
                topCurrent: Int,
                topRange: Int,
                offsetCurrent: Int,
                offsetRange: Int,
                bottomCurrent: Int,
                bottomRange: Int
            ) {
                Log.e(
                    "TAG", String.format(
                        "topCurrent = %d; topRange = %d; " +
                                "offsetCurrent = %d; offsetRange = %d; " +
                                "bottomCurrent = %d, bottomRange = %d",
                        topCurrent,
                        topRange,
                        offsetCurrent,
                        offsetRange,
                        bottomCurrent,
                        bottomRange
                    )
                )
                mBinding.flTitle.alpha = (offsetCurrent * 1f / SCROLL_THRESHOLD).coerceAtMost(1f)
            }
        })
    }

    companion object{
        const val SCROLL_THRESHOLD = 240
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            mBinding.coordinator.removeAllScrollListener()
        }
    }

}