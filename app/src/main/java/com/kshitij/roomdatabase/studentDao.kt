package com.kshitij.roomdatabase

import androidx.room.*

@Dao
interface studentDao {
    @Insert
    fun enterStudent(student: StudentTable)

    @Query("SELECT * FROM StudentTable")
    fun getStudent(): List<StudentTable>

    @Update
    fun editStudent(student: StudentTable)

    @Delete
    fun deleteStudent(student: StudentTable)
}