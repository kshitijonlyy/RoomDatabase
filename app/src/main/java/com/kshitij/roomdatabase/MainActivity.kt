package com.kshitij.roomdatabase

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kshitij.roomdatabase.databinding.ActivityMainBinding
import com.kshitij.roomdatabase.databinding.LayoutDeleteBinding
import com.kshitij.roomdatabase.databinding.LayoutDialogBinding
import com.kshitij.roomdatabase.databinding.LayoutEditBinding

class MainActivity : AppCompatActivity(), StudentList {
    lateinit var binding: ActivityMainBinding
    var name = ""
    lateinit var studentDb: StudentDb
    lateinit var listAdapter: ListAdapter
    var list: ArrayList<StudentTable> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        studentDb = StudentDb.getDatabase(this)
        listAdapter = ListAdapter(list, this)
        binding.list.adapter = listAdapter
        getFromDb()

        binding.btnAdd.setOnClickListener {
            var dialog = Dialog(this)
            var dialogBinding = LayoutDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.tvName.requestFocus()
            dialogBinding.btnAdd.setOnClickListener {
                name = dialogBinding.tvName.text.toString()
                Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
                saveInDb(name ?: "")
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    fun saveInDb(name: String) {
        class Save : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                studentDb.studentDao().enterStudent(StudentTable(name = name))
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                getFromDb()
            }
        }
        Save().execute()
    }

    fun getFromDb() {
        list.clear()
        class Get : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                list.addAll(studentDb.studentDao().getStudent())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                listAdapter.notifyDataSetChanged()
            }
        }
        Get().execute()
    }

    fun editDb(studentTable: StudentTable) {
        class Edit : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                studentDb.studentDao().editStudent(studentTable)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                getFromDb()
            }
        }
        Edit().execute()
    }

    fun deleteDb(studentTable: StudentTable) {
        class Delete : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                studentDb.studentDao().deleteStudent(studentTable)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                getFromDb()
            }
        }
        Delete().execute()
    }

    override fun onEdit(studentTable: StudentTable) {
        val edit = Dialog(this)
        val editBinding = LayoutEditBinding.inflate(layoutInflater)
        edit.setContentView(editBinding.root)
        editBinding.tvName.setText(studentTable.name)
        edit.show()
        editBinding.tvName.requestFocus()
        editBinding.btnEdit.setOnClickListener {

            studentTable.name = editBinding.tvName.text.toString()
            editDb(studentTable)
            edit.dismiss()
        }
    }

    override fun onDelete(studentTable: StudentTable) {
        val delete = Dialog(this)
        val deleteBinding = LayoutDeleteBinding.inflate(layoutInflater)
        delete.setContentView(deleteBinding.root)
        deleteBinding.tvName.setText("Delete '" + studentTable.name + "'?")
        delete.show()
        deleteBinding.btnEdit.setOnClickListener {
            deleteDb(studentTable)
            delete.dismiss()
        }
    }
}