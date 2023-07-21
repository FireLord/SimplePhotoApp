package com.firelord.growighassignment.domain.repository

import com.firelord.growighassignment.data.db.Comment
import com.firelord.growighassignment.data.db.CommentDao

class CommentRepository(private val dao:CommentDao) {
    val comments = dao.getAllComments()

    suspend fun insert(comment: Comment):Long{
        return dao.insertComment(comment)
    }

    suspend fun update(comment: Comment):Int{
        return dao.updateComment(comment)
    }
}