package com.example.gqlgithubfollowersexample


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.app.Activity
import android.content.Context


/**
 * A simple [Fragment] subclass.
 *
 */
class QuestionFragment : Fragment() {

    lateinit var etUserName: EditText
    lateinit var btnGetUserInfo : Button
    lateinit var userInfoListener: UserInfoListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_question, container, false)
        etUserName = view.findViewById(R.id.et_answer_user_name)
        btnGetUserInfo = view.findViewById(R.id.btn_get_user_info)
        btnGetUserInfo.setOnClickListener {
            etUserName.text?.toString().let {
            getUserInfo(etUserName.text.toString())
            }
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            userInfoListener = activity as UserInfoListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity!!.toString() + " must implement UserInfoListener")
        }

    }


    private fun getUserInfo(userName: String) {

        userInfoListener.onGetUserInfo(userName)
    }


}

interface UserInfoListener{
    fun onGetUserInfo(userName: String)
}
