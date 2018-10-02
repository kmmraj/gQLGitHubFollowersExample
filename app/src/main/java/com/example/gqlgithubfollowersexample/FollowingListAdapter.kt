package com.example.gqlgithubfollowersexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by Mohanraj Karatadipalayam on 02/10/18.
 */
class FollowingListAdapter (context: Context,var mListOfFollowingUsers: List<BaseUserViewModel>) : BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = mListOfFollowingUsers.size


    override fun getItem(position: Int): Any = mListOfFollowingUsers[position]

    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var displayView = convertView

        if (displayView == null) {
            displayView = layoutInflater.inflate(R.layout.follower_cell, null)
            val viewHolder = FollowingUserViewHolder()
            viewHolder.tvUserName = displayView?.findViewById(R.id.tv_follower_name) as TextView
            viewHolder.tvUserFullName = displayView.findViewById(R.id.tv_follwer_fullname) as TextView
            displayView.tag = viewHolder
        }
        val viewHolder = displayView.tag as FollowingUserViewHolder
        viewHolder.tvUserName?.text = mListOfFollowingUsers[position].loginName
        viewHolder.tvUserFullName?.text = mListOfFollowingUsers[position].userName
        return displayView
    }
}

class FollowingUserViewHolder {
    var tvUserName: TextView? = null
    var tvUserFullName: TextView? = null
}