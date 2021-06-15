package com.cookandroid.food

import android.content.Context
import android.database.DataSetObserver
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.food.model.searchimageItem
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val context: Context,
    private val dataList3: ArrayList<searchimageItem>,

    ) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>(), ListAdapter {

    interface ItemClickListener{
        fun onClick(view: View,position: Int)
    }
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }




    var mPosition = 0
    fun getPosition(): Int {
        return mPosition
    }

    private fun setPosition(position: Int) {
        mPosition = position
    }

    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

            val searchPhoto =  itemView.findViewById<ImageView>(R.id.searchimageView)

        fun bind(data: searchimageItem, context: Context) {


                val uri = Uri.parse("http://18.117.244.158/img/" + data.img_path)
                Picasso.with(context).load(uri).into(searchPhoto)







        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dataList3.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        context?.let { holder.bind(dataList3[position], it) }
        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
        }
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        dataList3.clear()
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(position: Int): Boolean {
        TODO("Not yet implemented")
    }


}
