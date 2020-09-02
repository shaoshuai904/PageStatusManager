package com.maple.pagestatusmanager.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlin.math.max

/**
 * 页面状态管理者
 *
 * @author : shaoshuai27
 * @date ：2020/8/17
 */
class PageStatusManager {
    private lateinit var mPageStatusLayout: PageStatusLayout
    private lateinit var mContext: Context

    companion object {
        const val NO_LAYOUT_ID = 0

        @JvmField
        @LayoutRes
        var mBaseLoadingLayoutId: Int = NO_LAYOUT_ID

        @JvmField
        @LayoutRes
        var mBaseRetryLayoutId: Int = NO_LAYOUT_ID

        @JvmField
        @LayoutRes
        var mBaseEmptyLayoutId: Int = NO_LAYOUT_ID

        @JvmField
        var mPageChangeAction: PageChangeAction = PageChangeAction()
    }

    constructor(activity: Activity) {
        this.mContext = activity
        val contentParent = activity.findViewById(android.R.id.content) as ViewGroup
        initView(contentParent)
    }

    constructor(fragment: Fragment) {
        this.mContext = fragment.requireContext()
        val contentParent = fragment.view?.parent as ViewGroup
        initView(contentParent)
    }

    constructor(view: View) {
        this.mContext = view.context
        val contentParent = view.parent
        if (contentParent is ViewGroup) {
            if (contentParent.javaClass.name.contains("SmartRefreshLayout")) {
                throw IllegalStateException("请避免view的parentView是SmartRefreshLayout，可对目标view包裹一层FrameLayout。")
            }
            val index = max(contentParent.indexOfChild(view), 0)// 可能为-1，修正为0
            initView(contentParent, index)
        }
    }

    private fun initView(contentParent: ViewGroup, index: Int = 0) {
        val oldContent = contentParent.getChildAt(index)
        contentParent.removeView(oldContent)
        mPageStatusLayout = PageStatusLayout(mContext).apply {
            setPageStatusChangeAction(mPageChangeAction)
            setContentView(oldContent)
        }
        contentParent.addView(mPageStatusLayout, index, oldContent.layoutParams)

        // 初始化默认配置页面
        setLoadingView(mBaseLoadingLayoutId)
        setRetryView(mBaseRetryLayoutId)
        setEmptyView(mBaseEmptyLayoutId)
    }

    // 各状态图的显示、隐藏
    fun showRetry() = mPageStatusLayout.showRetry()
    fun showContent() = mPageStatusLayout.showContent()

    fun dismissLoading() = showLoadingView(false)
    fun showLoading() = showLoadingView(true)
    fun showLoadingView(isShow: Boolean) {
        if (isShow) {
            mPageStatusLayout.showLoading()
        } else {
            mPageStatusLayout.showContent()
        }
    }

    fun showEmpty() = showEmptyView(true)
    fun showEmptyView(isShow: Boolean) {
        if (isShow) {
            mPageStatusLayout.showEmpty()
        } else {
            mPageStatusLayout.showContent()
        }
    }

    // 设置状态图 by layoutId
    fun setLoadingView(@LayoutRes layoutId: Int) = mPageStatusLayout.setLoadingView(layoutId)
    fun setRetryView(@LayoutRes layoutId: Int) = mPageStatusLayout.setRetryView(layoutId)
    fun setEmptyView(@LayoutRes layoutId: Int) = mPageStatusLayout.setEmptyView(layoutId)
    fun setContentView(@LayoutRes layoutId: Int) = mPageStatusLayout.setContentView(layoutId)

    // 设置状态图 by view
    fun setLoadingView(view: View?) = mPageStatusLayout.setLoadingView(view)
    fun setRetryView(view: View?) = mPageStatusLayout.setRetryView(view)
    fun setEmptyView(view: View?) = mPageStatusLayout.setEmptyView(view)
    fun setContentView(view: View?) = mPageStatusLayout.setContentView(view)

    // 获取状态图
    fun getLoadingView() = mPageStatusLayout.getLoadingView()
    fun getRetryView() = mPageStatusLayout.getRetryView()
    fun getEmptyView() = mPageStatusLayout.getEmptyView()
    fun getContentView() = mPageStatusLayout.getContentView()

}

/**
 * 页面状态改变监听
 */
open class PageChangeAction {
    open fun onShowLoading(loadingView: View?) {}
    open fun onShowRetry(retryView: View?) {}
    open fun onShowEmpty(emptyView: View?) {}
    open fun onShowContent(contentView: View?) {}
}