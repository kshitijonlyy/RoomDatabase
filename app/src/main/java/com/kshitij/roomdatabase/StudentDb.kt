package com.kshitij.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentTable::class], version = 1)
abstract class StudentDb: RoomDatabase() {
    abstract fun studentDao(): studentDao

    companion object{
        var studentDb: StudentDb? = null

        @Synchronized
        fun getDatabase(context: Context): StudentDb {
            if (studentDb == null) {
                studentDb = Room.databaseBuilder(
                    context,
                    StudentDb::class.java, "student"
                ).build()
            }
            return studentDb!!
        }
    }
}