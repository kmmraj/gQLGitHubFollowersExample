package com.example.gqlgithubfollowersexample


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


/**
 * A simple [Fragment] subclass.
 *
 */
class QuestionFragment : Fragment() {

    lateinit var etUserName: EditText
    lateinit var btnGetUserInfo : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_question, container, false)
        etUserName = view.findViewById(R.id.et_answer_user_name)
        btnGetUserInfo = view.findViewById(R.id.btn_get_user_info)
        btnGetUserInfo.setOnClickListener { getUserInfo(etUserName.text.toString()) }
        return view
    }

    private fun getUserInfo(userName: String?) {

    }


}
