package com.cookandroid.food

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cookandroid.food.MainActivity.Companion.ApplicationContext
import com.cookandroid.food.model.ContentItem
import com.cookandroid.food.vm.MyViewModel
import com.squareup.picasso.Picasso

class CustomAdapter(private val context: Context, private val dataList: ArrayList<ContentItem> = ArrayList()) : RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

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
        private val userPhoto = itemView.findViewById<ImageView>(R.id.image)
        private val userName = itemView.findViewById<TextView>(R.id.name)
        private val text = itemView.findViewById<TextView>(R.id.text)
        private val rating = itemView.findViewById<TextView>(R.id.rating)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val restaurant = itemView.findViewById<TextView>(R.id.restaurant)




        var galaxies = arrayOf("햄버거","감자튀김","짜장면")

        fun bind(data: ContentItem, context: Context) {
            userName.text = data.username
            val rest = data.restaurant
            val str = StringBuilder(rest)
            Log.d("str", str.length.toString())
            val result = str.substring(16, str.length-2)
            text.text = data.text
            rating.text =  "Rating:"+data.rating.toString()
            date.text = data.date
            restaurant.text = result
            val uri = Uri.parse("http://18.117.244.158/img/"+data.img_path)
            Picasso.with(context).load(uri).into(userPhoto);


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        context?.let { holder.bind(dataList[position], it) }
        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


//    fun sendTagRequest(context: Context) {
//        var imageData: ByteArray? = null
//        val image = System.currentTimeMillis()
//        val imagename = image.toString()
//        val postURL = "http://18.117.244.158:5000/img"
//        val request = object : VolleyFileUploadRequest(
//            Method.GET,
//            postURL,
//            Response.Listener {
//                println("response is: $it")
//            },
//            Response.ErrorListener {
//                println("error is: $it")
//            }
//        ) {
//            override fun getByteData(): MutableMap<String, FileDataPart> {
//                var params = HashMap<String, FileDataPart>()
//
//
//                params["imageFile"] = FileDataPart("image$imagename", imageData!!, "jpeg")
//
//                return params
//            }
//        }
//        Volley.newRequestQueue(context).add(request)
//    }
}