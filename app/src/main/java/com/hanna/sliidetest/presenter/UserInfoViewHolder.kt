package com.hanna.sliidetest.presenter

import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import com.hanna.sliidetest.R
import com.hanna.sliidetest.models.User

class UserInfoViewHolder(itemView: View) : BindableViewHolder<User>(itemView) {

    private val resources = itemView.resources
    private val nameTv: TextView = itemView.findViewById(R.id.name_tv)
    private val emailTv: TextView = itemView.findViewById(R.id.email_tv)
    private val createdAtTv: TextView = itemView.findViewById(R.id.created_tv)

    override fun bind(data: User) {
        nameTv.text = resources.getString(R.string.user_name, data.name)
        emailTv.text = resources.getString(R.string.user_email, data.email)
        val relativeTime = DateUtils.getRelativeTimeSpanString(data.creationTime.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
            .toString()
        createdAtTv.text = resources.getString(R.string.user_created_at, relativeTime)
    }
}