package com.firelord.growighassignment.presentation.ui.feed

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.firelord.growighassignment.R
import com.firelord.growighassignment.presentation.adapter.PhotosAdapter
import com.kevincodes.recyclerview.ItemDecorator


class SwipeToShareCallback(
    private val adapter: PhotosAdapter,
    context: Context
    ) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    private val backgroundColor = ContextCompat.getColor(context, R.color.blue)
    private val defaultWhiteColor = ContextCompat.getColor(context, R.color.white)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false // We don't need dragging functionality, so return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val itemToShare = adapter.getItem(position)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, itemToShare.urls.regular) // Share the URL
            type = "text/plain"
        }

        // Start the activity for sharing
        val shareIntent = Intent.createChooser(sendIntent, null)
        viewHolder.itemView.context.startActivity(shareIntent)

        adapter.notifyItemChanged(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        ItemDecorator.Builder(c, recyclerView, viewHolder, dX, actionState).set(
            backgroundColorFromStartToEnd = backgroundColor,
            textFromStartToEnd = "",
            textFromEndToStart = "",
            iconTintColorFromStartToEnd = defaultWhiteColor,
            iconResIdFromStartToEnd = R.drawable.ic_share_swipe
        )
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}