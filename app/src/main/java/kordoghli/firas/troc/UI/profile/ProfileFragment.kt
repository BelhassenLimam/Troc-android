package kordoghli.firas.troc.UI.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_profile, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preference = SharedPrefManager(context!!)
        var user = preference.getUser()
        textViewUserName.text = user.username
        view.cardDeconnect.setOnClickListener { view ->
            preference.logout()
        }
        view.cardProfil.setOnClickListener { view ->
            startActivity(Intent(context, ProfileInfoAcrivity::class.java))
        }
        view.cardMesOffre.setOnClickListener { View ->
            startActivity(Intent(context, MesOffresActivity::class.java))
        }
    }

    companion object {
        fun newInstance(): ProfileFragment =
            ProfileFragment()
    }

}