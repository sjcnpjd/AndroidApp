package com.cookandroid.food

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cookandroid.food.MainActivity.Companion.ApplicationContext
import com.cookandroid.food.databinding.FragProfileBinding
import com.cookandroid.food.model.calorieItem
import com.cookandroid.food.model.imageItem
import com.cookandroid.food.model.searchimageItem
import com.cookandroid.food.vm.MyViewModel
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment(R.layout.frag_profile) {
    private val viewmodel by activityViewModels<MyViewModel>()



    private lateinit var name: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        name = (context as MainActivity).getName()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragProfileBinding.bind(view)
        val postingAdd = binding.addButton


        postingAdd.setOnClickListener {

            val intent = Intent(getActivity(), PostActivity::class.java)
            intent.putExtra("name",name)
            startActivity(intent)


        }


        val mAdapter = GridAdapter(this.requireActivity(), viewmodel.dataList2)
        binding.profileRecyclerView.adapter = mAdapter
        val layout =
            GridLayoutManager(this.requireActivity(), 3, LinearLayoutManager.VERTICAL, false)
        binding.profileRecyclerView.layoutManager = layout
        binding.profileRecyclerView.setHasFixedSize(true)




        viewmodel.dataList2Live.observe(viewLifecycleOwner) {
            mAdapter.notifyDataSetChanged()
        }

        mAdapter.setItemClickListener( object : GridAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val clicked = viewmodel.dataList2[position]
                val result = clicked.id
                Log.d("SSS", "${result}")
                setFragmentResult("requestKey", bundleOf("bundleKey" to result.toString()))



//                requireActivity().supportFragmentManager.beginTransaction()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, PageFragment())
                    .commit()

            }
        })


        val profileName = binding.profileName
        profileName.text = name
        val profileImage = binding.imageView

        profileImage.setImageResource(R.drawable.ic_baseline_person_24)

        val queue = Volley.newRequestQueue(context)



        val url = "http://18.117.244.158/sum/calorie/$name"

        val stringRequest = StringRequest(Request.Method.GET, url, {
                response ->
            val temp = Math.floor(response.toDouble())
            binding.textView.text = "총 칼로리: $temp" // Print http source using textview
            println("$response")
        }, {
                error ->   Log.i("This is the error", "Error :" + error.toString())
        })




        queue .add(stringRequest)



    }




}


































