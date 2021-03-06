package kordoghli.firas.troc.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.adapters.ServiceUserInfoAdapter
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_profile_createur.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class ProfileCreateurActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_createur)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val idCreateur = intent.getStringExtra("idCreateur")
        getUserWithId(idCreateur.toInt())
        getServiceWithid(idCreateur.toInt())
    }

    private fun getUserWithId(id: Int) {
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_GET_User_WITH_ID,
            Response.Listener<String> { response ->
                try {
                    //converting response to json object
                    val jasonArray = JSONArray(response)
                    val obj = jasonArray.getJSONObject(0)
                    textView22.text = obj.getString("username")
                    textView26.text = obj.getString("email")
                    textView28.text = obj.getString("phone")
                    supportActionBar?.title = obj.getString("username")
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    imageView14.setOnClickListener {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:"+"54000673")
                        startActivity(intent)
                    }
                } catch (e: JSONException) {

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", id.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun getServiceWithid(id: Int) {
        val data: ArrayList<ResponseClasses.Service> = ArrayList()
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_GET_All_SERVICE_USER,
            Response.Listener<String> { response ->
                try {
                    //converting response to json object
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
                        val obj = jasonArray.getJSONObject(i)
                        val service = ResponseClasses.Service(
                            obj.getInt("id"),
                            obj.getString("titre"),
                            obj.getString("description"),
                            obj.getString("categorie"),
                            obj.getString("type"),
                            obj.getString("idUser"),
                            9.9F,
                            9.9F
                        )
                        data.add(service)
                        recycleViewUserInfo.layoutManager =
                                LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
                        recycleViewUserInfo.adapter = ServiceUserInfoAdapter(data,{ item : ResponseClasses.Service -> ItemClicked(item) })
                    }
                } catch (e: JSONException) {

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("idUser", id.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun ItemClicked(item : ResponseClasses.Service) {
        //Toast.makeText(this, "Clicked: ${item.id}", Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailsServiceActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }


}