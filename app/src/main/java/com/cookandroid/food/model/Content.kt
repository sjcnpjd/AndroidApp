package com.cookandroid.food.model



import com.google.gson.annotations.SerializedName







data class ContentItem(
    @SerializedName("id")
    var id: Int,

    @SerializedName("username")

    val username: String = "",
    @SerializedName("calorie")
    val calorie: Double,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("img_path")
    var img_path: String = "",
    @SerializedName("restaurant")
    val restaurant: String = "",
    @SerializedName("text")
    val text: String = ""


)
data class imageItem(
    @SerializedName("id")
    var id: Int,

    @SerializedName("img_path")
    var img_path: String = ""


)
data class  imageTag(


@SerializedName("img_path")
var img_tag: String = ""


)



data class searchimageItem(
    @SerializedName("id")
    var id: Int,

    @SerializedName("img_path")
    var img_path: String = ""

)
data class calorieItem(


    var calorie: String = ""

)


data class dateItem(
    @SerializedName("id")
    var id: Int,

    @SerializedName("img_path")
    var img_path: String = ""


)


