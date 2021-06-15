package com.appat.squircleavatar.utilities

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class LinearLayoutManagerWrapper(context: Context,
                                 orientation: Int,
                                 reverseLayout: Boolean): LinearLayoutManager(context, orientation, reverseLayout) {

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}