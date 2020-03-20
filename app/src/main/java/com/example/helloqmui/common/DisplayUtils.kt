package com.example.helloqmui.common

import android.os.Build
import android.util.TypedValue
import androidx.annotation.DimenRes
import com.example.helloqmui.App

object DisplayUtils {

    @JvmStatic
    fun statusBarHeight(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return App.appInstance.resources.getDimensionPixelSize(
                App.appInstance.resources.getIdentifier(
                    "status_bar_height",
                    "dimen",
                    "android"
                )
            )
        }
        return 0
    }

    @JvmStatic
    fun dp2pxF(dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            App.appInstance.resources.displayMetrics
        )
    }

    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                App.appInstance.resources.displayMetrics
            )
        )
    }

    /**
     * 获取资源文件 dimen Dp
     *
     * @param dimenRes dimenResId
     * @return int
     */
    @JvmStatic
    fun getDimenDp(@DimenRes dimenRes: Int): Int {
        return App.appInstance.resources.getDimensionPixelOffset(dimenRes)
    }
}