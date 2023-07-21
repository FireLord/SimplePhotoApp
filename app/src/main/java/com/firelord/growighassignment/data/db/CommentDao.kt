package com.firelord.growighassignment.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CommentDao {
    @Insert
    suspend fun insertComment(comment: Comment):Long

    @Update
    suspend fun updateComment(comment: Comment): Int

    @Query("SELECT * FROM comment_table")
    fun getAllComments():LiveData<List<Comment>>

}