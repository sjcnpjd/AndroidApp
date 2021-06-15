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
import com.cookandroid.food.model.dateItem
import com.cookandroid.food.model.imageItem
import com.squareup.picasso.Picasso

class CalendarAdapter (
    private val context: Context,
    private val dataList4: ArrayList<dateItem>,

    ) : RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>(), ListAdapter {

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
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
        private val userPhoto = itemView.findViewById<ImageView>(R.id.wholeimageView)


        fun bind(data: dateItem, context: Context){

            val uri = Uri.parse("http://18.117.244.158/img/"+data.img_path)
            Picasso.with(context).load(uri).into(userPhoto);

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dataList4.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        context?.let { holder.bind(dataList4[position], it) }


        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
        }
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        dataList4.clear()

    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        dataList4.clear()
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
