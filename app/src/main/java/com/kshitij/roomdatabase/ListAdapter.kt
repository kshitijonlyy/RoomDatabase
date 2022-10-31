package com.kshitij.roomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(var list: List<StudentTable>, var studentList: StudentList) : BaseAdapter() {
    override fun getCount(): Int {
        System.out.println("in list size ${list.size}")
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return list.size.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        System.out.println("in list $p0")
        val initView = LayoutInflater.from(p2?.context).inflate(R.layout.layout_item, p2, false)
        val id = initView.findViewById<TextView>(R.id.tvId)
        val name = initView.findViewById<TextView>(R.id.tvName)
        val edit = initView.findViewById<ImageView>(R.id.imgEdit)
        val delete = initView.findViewById<ImageView>(R.id.imgDelete)
        id.text = (p0+1).toString()
        name.text = list[p0].name

        edit.setOnClickListener {
            studentList.onEdit(list[p0])
        }

        delete.setOnClickListener {
            studentList.onDelete(list[p0])
        }
//        initView.setOnClickListener {
//            studentList.onEdit(list[p0])
//        }
        return initView
    }

}