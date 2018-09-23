package com.example.gqlgithubfollowersexample

/**
 * Created by Mohanraj Karatadipalayam on 22/09/18.
 */
interface GitHubApi{
    fun getRepoList( userName:String, token:String): List<RepoViewModel>
}