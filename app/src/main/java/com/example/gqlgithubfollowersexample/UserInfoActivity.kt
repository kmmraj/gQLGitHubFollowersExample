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
    var mListOfRepos: List<RepoViewModel>? = arrayListOf()
    var mListOfFollowingUsers: List<BaseUserViewModel>? = arrayListOf()
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
        mListOfRepos?.let {
        val listView: ListView =  findViewById(R.id.repo_cell)
        mRepoListViewAdapter = RepoListAdapter(this,it)
        listView.adapter = mRepoListViewAdapter
        listView.isClickable = true
       // listView.onItemClickListener = this
        }
    }

    fun createFollowingUserListView() {
        mListOfFollowingUsers?.let {
        val listView: ListView = findViewById(R.id.follower_cell)

        mFollowingListAdapter = FollowingListAdapter(this,it)
        listView.adapter = mFollowingListAdapter
        listView.isClickable = true
        // listView.onItemClickListener = this
        }
    }




    private inner class UserInfoTaskListener: UserInfoTaskListenerInterface {
        override fun onFinished(result: UserViewModel) {
            mListOfRepos = result.repoList
            mListOfFollowingUsers = result.followingUsers
           this@UserInfoActivity.runOnUiThread {
               mRepoListViewAdapter?.mListOfRepos = result.repoList
               mRepoListViewAdapter?.notifyDataSetChanged()
               mFollowingListAdapter?.mListOfFollowingUsers = result.followingUsers
               mFollowingListAdapter?.notifyDataSetChanged()
           }
        }
    }

}


