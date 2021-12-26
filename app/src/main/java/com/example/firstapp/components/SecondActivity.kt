package com.example.firstapp.components

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.firstapp.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_second.*
import java.lang.IllegalStateException

class SecondActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val strHaha=intent.getStringExtra("haha")
//        val data=intent.getIntExtra("h2",0)
//
//        val textview=TextView(this)
//        textview.text="SecondActivity :${strHaha} - ${data}"
//        textview.gravity=Gravity.CENTER
//        textview.setTextColor(Color.parseColor("#FF8890"))
//        setContentView(textview)
//
//        textview.setOnClickListener {
//            val resultIntent=Intent()
//            resultIntent.putExtra("result_text","extra_string")
//            resultIntent.putExtra("result_int",1)
//            setResult(Activity.RESULT_OK,resultIntent)
//            finish()
//        }

        setContentView(R.layout.activity_second)

        //写底部导航栏
        toggle_group.addOnButtonCheckedListener{group, checkedId, isChecked ->
            val childCount=group.childCount
            var selectedIndex=0
            Log.e("ttm","你点击了一下 ${checkedId} $isChecked")
            if(isChecked){
                for (index in 0 until childCount){
                    val button=group.getChildAt(index) as MaterialButton
                    if(button.id==checkedId){
                        //选中的按钮
                        selectedIndex=index
                        button.setTextColor(Color.RED)
                        button.iconTint= ColorStateList.valueOf(Color.RED)
                    }else{
                        button.setTextColor(Color.BLACK)
                        button.iconTint= ColorStateList.valueOf(Color.BLACK)
                    }
                }

                Log.e("ttm","here $selectedIndex ")
                switchFragment(selectedIndex)
            }

        }
        toggle_group.check(R.id.tab1)//check方法默认选中某个

//        val fragment=SecondFragment()
//        val bundle=Bundle()
//        bundle.putInt("int_extra",100)
//        bundle.putString("str_extra","hello")
//        fragment.arguments=bundle
//
//        val ft=supportFragmentManager.beginTransaction()
//        if(!fragment.isAdded){
//            ft.add(R.id.container,fragment)//把fragment添加到事务中，当且仅当该fragment未被添加过
//        }
////        ft.show(fragment)//显示出fragment的视图
////        ft.hide(fragment)//隐藏fragment，使得它的视图不可见
////        ft.remove(fragment)//移除fragment
////        ft.replace(R.id.container,fragment)//替换fragment，之前添加过的fragment会被暂时移除
//
//        ft.commitAllowingStateLoss()//提交事务，执行对fragment的add/replace/show/hide

    }

    private var tab1Fragment: SecondFragment?=null
    private var tab2Fragment: SecondFragment?=null
    private var tab3Fragment: SecondFragment?=null
    private  var shownFragment: Fragment?=null
    private fun switchFragment(selectedIndex:Int) {
        val fragment=when(selectedIndex){
            0->{
                if(tab1Fragment==null){
                    tab1Fragment= SecondFragment()
                    val bundle=Bundle()
                    bundle.putString("tab","tab1")
                    tab1Fragment!!.arguments=bundle
                }
                tab1Fragment
            }
            1->{
                if(tab2Fragment==null){
                    tab2Fragment= SecondFragment()
                    val bundle=Bundle()
                    bundle.putString("tab","tab2")
                    tab2Fragment!!.arguments=bundle
                }
                tab2Fragment
            }
            2->{
                if(tab3Fragment==null){
                    tab3Fragment= SecondFragment()
                    val bundle=Bundle()
                    bundle.putString("tab","tab3")
                    tab3Fragment!!.arguments=bundle
                }
                tab3Fragment
            }
            else->{
                throw IllegalStateException("下标不符合预期")
            }
        }?:return
        val ft=supportFragmentManager.beginTransaction()
        if(!fragment.isAdded){
            ft.add(R.id.container,fragment)
        }
        ft.show(fragment)
        if(shownFragment!=null&&shownFragment!=fragment){
            ft.hide(shownFragment!!)
        }
        shownFragment=fragment
        ft.commitAllowingStateLoss()
    }
}