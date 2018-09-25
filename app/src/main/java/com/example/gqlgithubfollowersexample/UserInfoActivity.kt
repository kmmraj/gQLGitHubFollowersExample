package com.example.gqlgithubfollowersexample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView




class UserInfoActivity : AppCompatActivity() {


    private lateinit var repoListTaskListener: UserInfoTaskListener
    var mListOfRepos: List<RepoViewModel>? = null
    var mListOfFollowingUsers: List<BaseUserViewModel>? = null
    private var mRepoListViewAdapter: RepoListAdapter? = null
    private var mFollowingListAdapter: FollowingListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        repoListTaskListener = UserInfoTaskListener()
        val gitHubTask = RepoListGQLTask("octocat",Constants.TOKEN,repoListTaskListener)
        gitHubTask.execute()
        createRepoListView()
        createFollowingUserListView()
    }

    fun createRepoListView() {
        val listView: ListView =  findViewById(R.id.repo_cell) as ListView
        mRepoListViewAdapter = RepoListAdapter()
        listView.adapter = mRepoListViewAdapter
        listView.isClickable = true
       // listView.onItemClickListener = this
    }

    fun createFollowingUserListView() {
        val listView: ListView =  findViewById(R.id.follower_cell) as ListView
        mFollowingListAdapter = FollowingListAdapter()
        listView.adapter = mFollowingListAdapter
        listView.isClickable = true
        // listView.onItemClickListener = this
    }


    private inner class RepoListAdapter internal constructor() : BaseAdapter() {

        private val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return mListOfRepos?.size ?: 0
        }

        override fun getItem(position: Int): Any = mListOfRepos?.get(position) ?: 0

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var displayView = convertView

            if (displayView == null) {
                displayView = layoutInflater.inflate(R.layout.repo_cell, null)
                val viewHolder = RepoViewHolder()
                viewHolder.tvRepoName = displayView?.findViewById(R.id.tv_repo_name) as TextView
                viewHolder.tvRepoFullName = displayView.findViewById(R.id.tv_repo_fullname) as TextView
                displayView.tag = viewHolder
            }
            val viewHolder = displayView.tag as RepoViewHolder
            viewHolder.tvRepoName?.text = mListOfRepos?.get(position)?.repoName
            viewHolder.tvRepoFullName?.text = mListOfRepos?.get(position)?.description
            return displayView
        }
    }

    internal inner class RepoViewHolder {
        var tvRepoName: TextView? = null
        var tvRepoFullName: TextView? = null
    }

    private inner class UserInfoTaskListener: UserInfoTaskListenerInterface {
        override fun onFinished(result: UserViewModel) {
            mListOfRepos = result.repoList
            mListOfFollowingUsers = result.followingUsers
           this@UserInfoActivity.runOnUiThread {
               mRepoListViewAdapter?.notifyDataSetChanged()
               mFollowingListAdapter?.notifyDataSetChanged()
           }
        }
    }



    private inner class FollowingListAdapter internal constructor() : BaseAdapter() {

        private val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return mListOfFollowingUsers?.size ?: 0
        }

        override fun getItem(position: Int): Any = mListOfFollowingUsers?.get(position) ?: 0

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var displayView = convertView

            if (displayView == null) {
                displayView = layoutInflater.inflate(R.layout.follower_cell, null)
                val viewHolder = FollowingUserViewHolder()
                viewHolder.tvUserName = displayView?.findViewById<TextView>(R.id.tv_follower_name) as TextView
                viewHolder.tvUserFullName = displayView.findViewById<TextView>(R.id.tv_follwer_fullname) as TextView
                displayView.tag = viewHolder
            }
            val viewHolder = displayView.tag as FollowingUserViewHolder
            viewHolder.tvUserName?.text = mListOfFollowingUsers?.get(position)?.loginName
            viewHolder.tvUserFullName?.text = mListOfFollowingUsers?.get(position)?.userName
            return displayView
        }
    }

    internal inner class FollowingUserViewHolder {
        var tvUserName: TextView? = null
        var tvUserFullName: TextView? = null
    }
}


