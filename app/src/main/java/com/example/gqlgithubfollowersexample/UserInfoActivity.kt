package com.example.gqlgithubfollowersexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class UserInfoActivity : AppCompatActivity() {


    lateinit var repoListTaskListener: RepoListTaskListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        val token = "3e2381963012dfd0258d5c3478a1943554ef0130"
        repoListTaskListener = RepoListTaskListener()
        val gitHubTask = RepoListGQLTask("octocat",token,repoListTaskListener)
        gitHubTask.execute()
    }
}

class RepoListTaskListener: RepoListTaskListenerInterface {
    override fun onFinished(result: RepoList) {

    }

}
