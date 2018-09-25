package com.example.gqlgithubfollowersexample

import UserAndFollowersInfoQuery
import android.net.ParseException
import android.os.AsyncTask
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.example.gqlgithubfollowersexample.Constants.BASE_URL
import okhttp3.OkHttpClient
import type.CustomType
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mohanraj Karatadipalayam on 22/09/18.
 */



interface UserInfoTaskListenerInterface {
    fun onFinished(result: UserViewModel)
}

class RepoListGQLTask (val userName:String,
                       val token: String,
                       val userInfoTaskListener: UserInfoTaskListenerInterface): AsyncTask<Void,Void,Boolean>() {

    private lateinit var apolloClient: ApolloClient
    private val TAG = "RepoListGQLTask"

    override fun doInBackground(vararg params: Void?): Boolean {
        getUserInfo()
        return true
    }

    private fun getUserInfo() {
        apolloClient = setupApollo(token)
        apolloClient.query(UserAndFollowersInfoQuery
                .builder()
                .login(userName)
                .build())
                .enqueue(object : ApolloCall.Callback<UserAndFollowersInfoQuery.Data>() {

                    override fun onFailure(e: ApolloException) {
                        Log.d(TAG,e.message.toString())
                    }

                    override fun onResponse(response: Response<UserAndFollowersInfoQuery.Data>) {
                        val user: UserAndFollowersInfoQuery.User? = response.data()?.user()
                        val userRepoViewModelList = user?.repositories()
                        val followingViewModelList = user?.following()

                        val repoViewModelList = getRepoList(userRepoViewModelList)
                        val followingUserList = getFollowingUserInfo(followingViewModelList)

                        userInfoTaskListener.onFinished(UserViewModel(repoViewModelList,followingUserList))

                    }
                })
    }

    private fun getFollowingUserInfo(followingViewModelList: UserAndFollowersInfoQuery.Following? ): ArrayList<BaseUserViewModel>{

        val followingUserList = arrayListOf<BaseUserViewModel>()
        followingViewModelList?.let {
            it.nodes()?.let {
                for (following in it) {
                    val followerUserVM = BaseUserViewModel(following.login(),
                            following.name(),
                            following.userUrl(),
                            getRepoList(following.repositories() as? UserAndFollowersInfoQuery.Repositories))

                    followingUserList.add(followerUserVM)
                }
            }
        }
        return followingUserList
    }

    private fun getRepoList(userRepoViewModelList: UserAndFollowersInfoQuery.Repositories?):
            ArrayList<RepoViewModel> {
        val repoViewModelList = arrayListOf<RepoViewModel>()
        userRepoViewModelList?.let {
            it.nodes()?.let {
                for (repo in it) {
                   // val repoUrl = repo.repoUrl()
                    val repoVM = RepoViewModel(repoName = repo.repoName(),
                            htmlUrl = repo.repoUrl(),
                            description = repo.description())
                    repoViewModelList.add(repoVM)
                }
            }
        }
        return repoViewModelList
    }


    private fun setupApollo(authToken: String): ApolloClient {

        val dateTimeCustomTypeAdapter = object : CustomTypeAdapter<Date> {
            val ISO8601_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            override fun decode(value: CustomTypeValue<*>): Date? {
                try {
                    return ISO8601_DATE_FORMAT.parse(value.value.toString())
                } catch (ex: ParseException) {
                    throw RuntimeException(ex)
                }
            }

            override fun encode(value: Date): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(ISO8601_DATE_FORMAT.format(value))
            }
        }


        val okHttp = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(original.method(),
                    original.body())
            builder.addHeader("Authorization"
                    , "Bearer $authToken")
            chain.proceed(builder.build())
        }
                .build()

        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttp)
                .addCustomTypeAdapter(CustomType.DATETIME, dateTimeCustomTypeAdapter)
                .build()
    }
}


