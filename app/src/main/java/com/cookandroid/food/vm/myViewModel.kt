package com.cookandroid.food.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cookandroid.food.FileDataPart
import com.cookandroid.food.MainActivity
import com.cookandroid.food.SearchFragment
import com.cookandroid.food.VolleyFileUploadRequest
import com.cookandroid.food.model.*
import com.firebase.ui.auth.AuthUI.getApplicationContext

class MyViewModel: ViewModel() {
    var dataList: ArrayList<ContentItem> = ArrayList()
    val dataListLive = MutableLiveData<ArrayList<ContentItem>>()
    var dataList2: ArrayList<imageItem> = ArrayList()
    val dataList2Live = MutableLiveData<ArrayList<imageItem>>()
    var dataList3: ArrayList<searchimageItem> = ArrayList()
    val dataList3Live = MutableLiveData<ArrayList<searchimageItem>>()
    var search: String = String()
    var dataList4: ArrayList<dateItem> = ArrayList()
    val dataList4Live = MutableLiveData<ArrayList<dateItem>>()
    var dataList5: ArrayList<imageTag> = ArrayList()
    val dataList5Live = MutableLiveData<ArrayList<imageTag>>()
    var dataList6: ArrayList<calorieItem> = ArrayList()
    val dataList6Live = MutableLiveData<ArrayList<calorieItem>>()
    var cal: String = String()
    var date: String = String()
    var username: String = String()


    fun sendRequest(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val url = "http://18.117.244.158/mainfeed"
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val arrayItem = response.getJSONObject(i)
                    var tempData = ContentItem(
                        arrayItem.getInt("id"),
                        arrayItem.getString("username"),
                        arrayItem.getDouble("calorie"),
                        arrayItem.getDouble("rating"),
                        arrayItem.getString("date"),
                        arrayItem.getString("img_path"),
                        arrayItem.getString("restaurant"),
                        arrayItem.getString("text")
                    )

                    dataList.add(tempData)
                    //mAdapter.notifyDataSetChanged()


                }
                dataListLive.value = dataList

            },
            { error ->
                // Handle error

                print("ERROR: %s".format(error.toString()))
            })
        queue.add(stringRequest)
    }

    fun sendProfileRequest(context: Context) {
        dataList2.clear()

        val queue = Volley.newRequestQueue(context)

        val url = "http://18.117.244.158/mainfeed/$username"
        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->

                for (i in 0 until response.length()) {
                    val arrayItem = response.getJSONObject(i)
                    val tempData = imageItem(
                        arrayItem.getInt("id"),
                        arrayItem.getString("img_path")

                    )

                    dataList2.add(tempData)


                }
                dataList2Live.value = dataList2


            },
            { error ->
                // Handle error


            })

        queue.add(stringRequest)


    }

    fun sendSearchRequest(context: Context) {
        dataList3.clear()
        val queue = Volley.newRequestQueue(context)

        val url = "http://18.117.244.158/search/$search"


        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->

                for (i in 0 until response.length()) {
                    val arrayItem = response.getJSONObject(i)

                    val tempData = searchimageItem(
                        arrayItem.getInt("id"),

                        arrayItem.getString("img_path")

                    )


                    dataList3.add(tempData)


                }
                dataList3Live.value = dataList3


            },
            { error ->
                // Handle error


            })

        queue.add(stringRequest)


    }

    fun sendDateRequest(context: Context) {
        Log.d("kj",date)
        dataList4.clear()
        val queue =  Volley.newRequestQueue(context)


        val url = "http://18.117.244.158/calendar/$username/$date"
        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->

                for (i in 0 until response.length()) {
                    val arrayItem = response.getJSONObject(i)
                    val tempData= dateItem(
                        arrayItem.getInt("id"),
                        arrayItem.getString("img_path")

                    )

                    dataList4.add(tempData)


                }
                dataList4Live.value = dataList4



            },
            { error ->
                // Handle error


            })

        queue.add(stringRequest)



    }





    }



