package com.estiven.cryptodivisas.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object ShowMessage {

    fun show(view: View, text : String, duration: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(view, text, duration).show()
    }

}