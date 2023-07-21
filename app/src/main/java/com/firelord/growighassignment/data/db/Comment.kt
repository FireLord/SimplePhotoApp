package com.firelord.growighassignment.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    val id: Int,
    @ColumnInfo(name = "comment_string")
    val comment: String
)