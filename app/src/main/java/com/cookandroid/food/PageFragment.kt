package com.cookandroid.food

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cookandroid.food.databinding.FragmentPageBinding
import com.cookandroid.food.model.ContentItem
import com.cookandroid.food.vm.MyViewModel
import com.squareup.picasso.Picasso
import org.json.JSONObject


class PageFragment : Fragment(R.layout.fragment_page) {

    private  val viewmodel by activityViewModels<MyViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPageBinding.bind(view)



        sendRequest(requireContext())

        fun bind2(data: List<ContentItem>, context: Context) {
            binding.name.text = data.get(0).username
            binding.text.text = data.get(0).text
            binding.rating.text =  "Rating:"+data.get(0).rating.toString()
            binding.date.text = data.get(0).date
            val str = StringBuilder(data.get(0).restaurant)
            Log.d("str", str.length.toString())
            val result = str.substring(16, str.length-2)
            binding.restaurant.text = result
            val uri = Uri.parse("http://18.117.244.158/img/"+data.get(0).img_path)
            Picasso.with(context).load(uri).into(binding.image);
            binding.textView2.text = "맛있다"

        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val result = bundle.getString("bundleKey")
            println("넘겨진값: $result")

            var profilenum = result!!.toInt()
            val resultData = viewmodel.dataList.filter{
               it.id == profilenum
            }
            println(resultData)

            bind2(resultData,requireContext())

            binding.deleteButton.setOnClickListener {
                sendcall(resultData, MainActivity.ApplicationContext())
            }
        }




    }




    fun sendRequest(context: Context){


        val queue =  Volley.newRequestQueue(context)
        val url =  "http://18.117.244.158/mainfeed"
        val stringRequest = JsonArrayRequest(
                Request.Method.GET, url,null,
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



                            viewmodel.dataList.add(tempData)
                            //mAdapter.notifyDataSetChanged()

                    }
                    viewmodel.dataListLive.value = viewmodel.dataList



                },
                { error ->
                    // Handle error

                    print("ERROR: %s".format(error.toString()))
                })
        queue.add(stringRequest)
    }


    fun sendcall(data: List<ContentItem>, context: Context) {
        val queue = Volley.newRequestQueue(context)
        val dataId = data.get(0).id
        val url = "http://18.117.244.158/mainfeed/$dataId"


        var mStringRequest =
            object :  StringRequest(Method.DELETE, url, Response.Listener { response ->



            }, Response.ErrorListener { error ->
                Log.i("This is the error", "Error :" + error.toString())

            }) {
                override fun getBodyContentType(): String? {
                    return "application/json"
                }

//                @Throws(AuthFailureError::class)
//                override fun getBody(): ByteArray? {

//                    val params = HashMap<String, Any>()
//
//                    params.put("username", data.get(0).username)
//                    params.put("text", data.get(0).text)
//                    params.put("rating", data.get(0).rating)
//                    params.put("calorie", 312.1)
//                    params.put("img_path", data.get(0).img_path)
//                    params.put("restaurant", 2)
//
//                    return JSONObject(params as Map<*, *>).toString().toByteArray()



//                }




            }
        queue.add(mStringRequest!!)
    }
}