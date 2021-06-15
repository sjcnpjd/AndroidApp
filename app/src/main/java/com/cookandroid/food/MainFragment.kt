package com.cookandroid.food


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookandroid.food.databinding.FragMainBinding
import com.cookandroid.food.vm.MyViewModel





class MainFragment() : Fragment(R.layout.frag_main) {





    private  val viewmodel by activityViewModels<MyViewModel>()
//    private var dataList: ArrayList<ContentItem> = ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragMainBinding.bind(view)



        val mAdapter = CustomAdapter(this.requireActivity(), viewmodel.dataList)
        binding.mainRecyclerview.adapter = mAdapter
        val layout = LinearLayoutManager(this.requireActivity())
        binding.mainRecyclerview.layoutManager =layout
        binding.mainRecyclerview.setHasFixedSize(true)


        viewmodel.dataListLive.observe(viewLifecycleOwner) {
            mAdapter.notifyDataSetChanged()
        }
        mAdapter.setItemClickListener( object : CustomAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val clicked = viewmodel.dataList[position]
                val result = clicked.id
                Log.d("SSS", "${result}")
                setFragmentResult("requestKey", bundleOf("bundleKey" to result.toString()))



//                requireActivity().supportFragmentManager.beginTransaction()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, PageFragment())
                    .commit()

            }
        })


    }


}












