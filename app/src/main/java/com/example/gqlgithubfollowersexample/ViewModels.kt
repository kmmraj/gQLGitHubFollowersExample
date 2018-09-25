package com.example.gqlgithubfollowersexample

import android.os.Parcel
import android.os.Parcelable

/**
* Created by Mohanraj Karatadipalayam on 12/10/17.
*/


// Inline function to create Parcel Creator
inline fun <reified T : Parcelable> createParcel(crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }

// custom readParcelable to avoid reflection
fun <T : Parcelable> Parcel.readParcelable(creator: Parcelable.Creator<T>): T? {
    if (readString() != null) return creator.createFromParcel(this) else return null
}

data class RepoViewModel ( var repoName: String? = null,
                           var htmlUrl: String = "",
                           var description: String? ="") : Parcelable {


    constructor(parcelIn: Parcel) : this(repoName = parcelIn.readString(),
            htmlUrl =  parcelIn.readString(),
            description = parcelIn.readString()
            )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(repoName)
        dest.writeString(htmlUrl)
        dest.writeString(description)
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RepoViewModel(it) }
    }
}


data class RepoList(val repoList: List<RepoViewModel>)

data class BaseUserViewModel ( var loginName: String? = "",
                               var userName: String? = null,
                               var htmlUrl: String = "",
                               var repoList: List<RepoViewModel>? = null) : Parcelable {


    constructor(parcelIn: Parcel) : this(loginName = parcelIn.readString(),
            userName = parcelIn.readString(),
            htmlUrl =  parcelIn.readString(),
            repoList = mutableListOf<RepoViewModel>().apply {
                parcelIn.readTypedList(this, RepoViewModel.CREATOR)
            }
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(loginName)
        dest.writeString(userName)
        dest.writeString(htmlUrl)
        dest.writeTypedList(repoList)
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { BaseUserViewModel(it) }
    }
}


data class UserViewModel(val repoList: List<RepoViewModel>,
                    var followingUsers: List<BaseUserViewModel>) : Parcelable {


    constructor(parcelIn: Parcel) : this(
            repoList = mutableListOf<RepoViewModel>().apply {
                parcelIn.readTypedList(this, RepoViewModel.CREATOR)
            },
            followingUsers = mutableListOf<BaseUserViewModel>().apply {
                parcelIn.readTypedList(this, BaseUserViewModel.CREATOR)
            }

    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeTypedList(repoList)
        dest.writeTypedList(followingUsers)
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { UserViewModel(it) }
    }
}