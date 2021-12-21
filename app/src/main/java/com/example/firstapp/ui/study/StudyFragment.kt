package com.example.firstapp.ui.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.firstapp.R
import com.example.firstapp.http.ApiService
import com.example.firstapp.http.Course
import com.example.firstapp.http.HiOkHttp
import com.example.firstapp.http.HiRetrofit
import com.google.android.material.shape.RoundedCornerTreatment
import kotlinx.android.synthetic.main.fragment_study.*
import kotlinx.android.synthetic.main.item_fragment_study.view.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class StudyFragment:Fragment(R.layout.fragment_study){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var studyAdapter=StudyAdapter()
        recycler_view.layoutManager=LinearLayoutManager(context)
        recycler_view.adapter=studyAdapter

        course_add.setOnClickListener{
            val course=Course("android 学习基础","https://www.songyubao.com/static/book/assets/icon-android.jpeg","100%","Android RecyclerView基础学习")
            studyAdapter.addCourse(course)
            //当且仅当recycler_view第0个位置insert一个item时，需要手动调用一下recycler_view.scrollToPosition(0)
            recycler_view.scrollToPosition(0)
        }

        course_delete.setOnClickListener {
            val course=Course("android 学习基础","https://www.songyubao.com/static/book/assets/icon-android.jpeg","100%","Android RecyclerView基础学习")
            studyAdapter.deleteCourse(0)
        }

        course_update.setOnClickListener {
            val course=Course("android 学习基础","https://www.songyubao.com/static/book/assets/icon-android.jpeg","100%","Android RecyclerView基础学习")
            studyAdapter.updateCourse(0,"20%")
        }

        HiRetrofit.create(ApiService::class.java)
            .getStudy().enqueue(object : Callback, retrofit2.Callback<List<Course>> {
                override fun onResponse(
                    call: Call<List<Course>>,
                    response: Response<List<Course>>
                ) {
                    Log.e("onResponse",response.body()?.toString()?:"unknown error")
                    // response.body不为空，才设置数据
                    response.body()?.let {
                        studyAdapter.setDatas(it)
                    }
                }

                override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                    Log.e("onFailure: ",t.message?:"unknown error")
                }

            })
    }

    inner class StudyAdapter : RecyclerView.Adapter<StudyAdapter.StudyViewHolder>() {
        private val courses= mutableListOf<Course>()
        fun setDatas(datas:List<Course>){
            if (datas.isNotEmpty()){
                courses.addAll(datas)
                notifyDataSetChanged()
            }
        }

        fun addCourse(course:Course){
            //插到第0个
            courses.add(0,course)
            notifyItemInserted(0)

            //插到最后一条
//            courses.add(course)
//            notifyItemInserted(courses.size-1)
        }

        fun deleteCourse(position: Int){
            courses.removeAt(position)
            notifyItemRemoved(position)
        }

        fun updateCourse(position: Int,progress:String){
            val course=courses[position]
            course.progress=progress
            notifyItemChanged(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):StudyViewHolder {
            val view=LayoutInflater.from(context).inflate(R.layout.item_fragment_study,parent,false)
            return StudyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return courses.size
        }

        inner class StudyViewHolder(view:View):RecyclerView.ViewHolder(view){

        }

        override fun onBindViewHolder(holder: StudyViewHolder, position: Int) {
            val course=courses[position]
            //加载圆角图片
            val options= RequestOptions().transform(RoundedCorners(20))

            Glide.with(context!!).load(course.poster).apply(options).into(holder.itemView.item_course_poster)
            //holder.itemView.item_course_poster.setImageResource(R.drawable.ic_png_5)
            holder.itemView.item_course_title.text=course.title
            holder.itemView.item_course_lable.text=course.label
            holder.itemView.item_course_progress.text=course.progress
        }
    }

}

