package com.devforfun.sliidetask.utils;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.devforfun.sliidetask.R
import com.squareup.picasso.Picasso

/** Extension  for inflating the layout resource. Can be used in Adapters*/
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


