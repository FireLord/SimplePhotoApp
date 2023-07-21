package com.firelord.growighassignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.data.db.Comment
import com.firelord.growighassignment.databinding.ListCommentBinding

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    private val commentList = ArrayList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListCommentBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun setList(comment: List<Comment>){
        commentList.clear()
        commentList.addAll(comment)
    }

    inner class MyViewHolder(val binding: ListCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.tvComment.text = comment.comment
        }
    }
}