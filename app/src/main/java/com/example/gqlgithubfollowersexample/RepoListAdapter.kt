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
class RepoListAdapter (private val context: Context, var mListOfRepos: List<RepoViewModel> ) : BaseAdapter() {



    override fun getCount(): Int = mListOfRepos.size

    override fun getItem(position: Int): Any = mListOfRepos[position]

    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var displayView = convertView

        if (displayView == null) {
            displayView =  LayoutInflater.from(context).inflate(R.layout.repo_cell, null)
            val viewHolder = RepoViewHolder()
            viewHolder.tvRepoName = displayView?.findViewById(R.id.tv_repo_name) as TextView
            viewHolder.tvRepoFullName = displayView.findViewById(R.id.tv_repo_fullname) as TextView
            displayView.tag = viewHolder
        }
        val viewHolder = displayView.tag as RepoViewHolder
        viewHolder.tvRepoName?.text = mListOfRepos[position].repoName
        viewHolder.tvRepoFullName?.text = mListOfRepos[position].description
        return displayView
    }
}

 class RepoViewHolder {
    var tvRepoName: TextView? = null
    var tvRepoFullName: TextView? = null
}