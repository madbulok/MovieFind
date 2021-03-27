package com.uzlov.moviefind.ui

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class MyItemDecorator(private val orientation: Int = 1) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        when(orientation) {
            RecyclerView.HORIZONTAL -> outRect.set(Rect(16, 0, 16, 0))
            RecyclerView.VERTICAL -> outRect.set(Rect(0, 16, 0, 16))
            else -> outRect.set(Rect(16, 16, 16, 16))
        }
    }
}