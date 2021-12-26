package com.example.firstapp.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment:Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("SecondFragment","onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SecondFragment","onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textview=TextView(context)
        textview.text="SecondFragment"
        textview.gravity=Gravity.CENTER

        Log.e("SecondFragment","onCreateView")
        return textview

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //在这里取传递的参数
        val intValue=arguments?.getInt("int_extra")
        val strValue=arguments?.getString("tab")

        val textview=view as TextView
        textview.text="${intValue} --- ${strValue}"
        if(strValue=="tab1"){
            textview.setOnClickListener {
                val intent= Intent(context,ServiceActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("SecondFragment","onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.e("SecondFragment","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("SecondeFragment","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("SecondFragment","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("SecondFragment","onStop")
    }
    //onCreateView返回的view对象被销毁的时候执行
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("SecondFragment","onDestroyView")
    }
    //fragment被销毁的时候执行
    override fun onDestroy() {
        super.onDestroy()
        Log.e("SecondFragment","onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("SecondFragment","onDetach")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //当且仅当activity存在多个fragment并且调用了show-hide
        //当fragment可见的时候 为false
        //不可见的时候 为true
        Log.e("SecondFragment","onHiddenCHanged: ${arguments?.getString("tab")}--${hidden}")
    }

}