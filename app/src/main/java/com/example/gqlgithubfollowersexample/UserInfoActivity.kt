package com.example.gqlgithubfollowersexample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.gqlgithubfollowersexample.Constants.USER_NAME


class UserInfoActivity : AppCompatActivity(), UserInfoListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        showFragment(QuestionFragment())
    }



    private fun showFragment(fragment: Fragment) {
        transact {
            replace(R.id.container, fragment)
            setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }
    }

    override fun onGetUserInfo(userName: String) {
        // TODO: Pass the userName as attributes
        val args =  Bundle()
        args.putString(USER_NAME, userName)
        val userInfoListFragment = UserInfoListFragment()
        userInfoListFragment.arguments = args
        showFragment(userInfoListFragment)
    }






}


