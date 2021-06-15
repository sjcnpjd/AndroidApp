package com.cookandroid.food

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cookandroid.food.databinding.FragCalendarBinding
import com.cookandroid.food.databinding.FragmentDateBinding
import com.cookandroid.food.vm.MyViewModel
import org.json.JSONObject
import java.util.*


class DateFragment : Fragment(R.layout.fragment_date) {
    private val viewmodel by activityViewModels<MyViewModel>()

    lateinit var request: RequestQueue
    lateinit var stringRequest: StringRequest

    private lateinit var name: String


    override fun onAttach(context: Context) {
        super.onAttach(context)
        name = (context as MainActivity).getName()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDateBinding.bind(view)

        setFragmentResultListener("dateKey1") { dateKey, bundle ->
            val result1 = bundle.getString("bundleKey1")

            setFragmentResultListener("dateKey2") { dateKey, bundle ->
                val result2 = bundle.getString("bundleKey2")
                var result22: Int = result2!!.toInt()
                result22++


                setFragmentResultListener("dateKey3") { dateKey, bundle ->
                    val result3 = bundle.getString("bundleKey3")


                    binding.datetv.text = "$result1. ${result22.toString()}. $result3"
                    var cal: String = "$result1-$result22-$result3"
                }

            }
        }




        val mAdapter = CalendarAdapter(this.requireActivity(), viewmodel.dataList4)
        binding.dateRecyclerView.adapter = mAdapter
        val layout =
            GridLayoutManager(this.requireActivity(), 4, LinearLayoutManager.VERTICAL, false)
        binding.dateRecyclerView.layoutManager = layout
        binding.dateRecyclerView.setHasFixedSize(true)


        viewmodel.dataList4Live.observe(viewLifecycleOwner) {
            mAdapter.notifyDataSetChanged()
        }

        mAdapter.setItemClickListener( object : CalendarAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val clicked = viewmodel.dataList4[position]
                val result = clicked.id
                Log.d("SSS", "${result}")
                setFragmentResult("requestKey", bundleOf("bundleKey" to result.toString()))



//                requireActivity().supportFragmentManager.beginTransaction()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, PageFragment())
                    .commit()

            }
        })


        setFragmentResultListener("dateKey1") { dateKey, bundle ->
            val result1 = bundle.getString("bundleKey1")

            setFragmentResultListener("dateKey2") { dateKey, bundle ->
                val result2 = bundle.getString("bundleKey2")
                var result22: Int = result2!!.toInt()
                result22++


                setFragmentResultListener("dateKey3") { dateKey, bundle ->
                    val result3 = bundle.getString("bundleKey3")


                    binding.datetv.text = "$result1-$result22-$result3"
                    var day = "$result1-$result22-$result3"
                    Log.d("qqq",day)

                    // Setup RequestQueue
                    request = Volley.newRequestQueue(requireContext())
                    val url = "http://18.117.244.158/calendar/sum/$name/$day"

                    // Setup StringRequest
                    stringRequest = StringRequest(Request.Method.GET, url, {
                            response ->
                        val temp = Math.floor(response.toDouble())
                        binding.calsum.text = "의 총 칼로리: $temp" // Print http source using textview
                        println("$response")
                    }, {
                            error ->   Log.i("This is the error", "Error :" + error.toString())
                    })

                    // Set tag for cancel

                    // Request
                    request.add(stringRequest)


                }

            }
        }




    }

}