package com.uzlov.moviefind.ui

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class ActorHorizontalItemDecorator : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.set(Rect(16, 0, 16, 0))
    }
}