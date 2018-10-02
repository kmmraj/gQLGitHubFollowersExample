package com.example.gqlgithubfollowersexample

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * Created by Mohanraj Karatadipalayam on 02/10/18.
 */

inline fun AppCompatActivity.transact(action: FragmentTransaction.() -> Unit) {
    supportFragmentManager.beginTransaction().apply {
        action()
    }.commit()
}