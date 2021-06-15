package com.cookandroid.food


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookandroid.food.MainActivity.Companion.ApplicationContext
import com.cookandroid.food.databinding.FragSearchBinding
import com.cookandroid.food.vm.MyViewModel

class SearchFragment: Fragment(R.layout.frag_search) {
    private val viewmodel by activityViewModels<MyViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val binding = FragSearchBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)



        val mAdapter = SearchAdapter(this.requireActivity(), viewmodel.dataList3)
        binding.searchRecyclerView.adapter = mAdapter
        val layout = GridLayoutManager(this.requireActivity(), 4, LinearLayoutManager.VERTICAL, false)
        binding.searchRecyclerView.layoutManager = layout
        binding.searchRecyclerView.setHasFixedSize(true)
        viewmodel.dataList3Live.observe(viewLifecycleOwner) {
            mAdapter.notifyDataSetChanged()
        }
        binding.searchicon.setOnClickListener {
            val search = binding.editTextTextSearch.text.trim().toString()
            viewmodel.search = search
            viewmodel.sendSearchRequest(ApplicationContext())
        }
        mAdapter.setItemClickListener( object : SearchAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val clicked = viewmodel.dataList3[position]
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
