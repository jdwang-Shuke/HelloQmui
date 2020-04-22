package com.example.helloqmui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var mBinding: VB

    protected abstract fun doBusiness()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        val vbClass =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(null, layoutInflater) as VB
        setContentView(mBinding.root)
        doBusiness()
    }

}