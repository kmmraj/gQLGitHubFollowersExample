package com.example.gqlgithubfollowersexample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.gqlgithubfollowersexample.Constants.TOKEN


class UserInfoListFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var repoListTaskListener: UserInfoTaskListener
    var mListOfRepos: List<RepoViewModel>? = arrayListOf<RepoViewModel>()
    var mListOfFollowingUsers: List<BaseUserViewModel>? = arrayListOf()
    private var mRepoListViewAdapter: RepoListAdapter? = null
    private var mFollowingListAdapter: FollowingListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_info_list, container, false)
        val tvUserReposLabel = view.findViewById<TextView>(R.id.tv_user_repos)
        val tvUserFollowing = view.findViewById<TextView>(R.id.tv_userFollowing)
        repoListTaskListener = UserInfoTaskListener()

        val userName = this.arguments?.getString("USER_NAME")
        userName?.let {
        getUserInfo(userName)
        createRepoListView(view)
        createFollowingUserListView(view)
        }
        tvUserReposLabel.text = "$userName Repositories"
        tvUserFollowing.text = "$userName Following"
        return view
    }

    private fun getUserInfo(userName: String) {
        val gitHubTask = RepoListGQLTask(userName, TOKEN, repoListTaskListener)
        gitHubTask.execute()
    }

    fun createRepoListView(view: View) {
        this.activity?.let {
            val context = it
        mListOfRepos?.let {
            val listView: ListView =  view.findViewById(R.id.repo_cell)
            mRepoListViewAdapter = RepoListAdapter(context,it)
            listView.adapter = mRepoListViewAdapter
            listView.isClickable = true
            // listView.onItemClickListener = this
        }
        }
    }

    fun createFollowingUserListView(view: View) {
        this.activity?.let{
            val context = it
        mListOfFollowingUsers?.let {
            val listView: ListView = view.findViewById(R.id.follower_cell)

            mFollowingListAdapter = FollowingListAdapter(context,it)
            listView.adapter = mFollowingListAdapter
            listView.isClickable = true
            // listView.onItemClickListener = this
        }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    private inner class UserInfoTaskListener: UserInfoTaskListenerInterface {
        override fun onFinished(result: UserViewModel) {
            mListOfRepos = result.repoList
            mListOfFollowingUsers = result.followingUsers
            this@UserInfoListFragment.activity?.runOnUiThread {
                mRepoListViewAdapter?.mListOfRepos = result.repoList
                mRepoListViewAdapter?.notifyDataSetChanged()
                mFollowingListAdapter?.mListOfFollowingUsers = result.followingUsers
                mFollowingListAdapter?.notifyDataSetChanged()
            }
        }
    }
}
