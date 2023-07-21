package com.firelord.growighassignment.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Comment::class], version = 1)
abstract class CommentDatabase : RoomDatabase() {
    abstract val commentDao : CommentDao
    companion object{
        @Volatile
        private  var INSTANCE: CommentDatabase? = null
        fun getInstance(context: Context):CommentDatabase{
            synchronized(this){
                var instance= INSTANCE
                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        CommentDatabase::class.java,
                        "comment_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}