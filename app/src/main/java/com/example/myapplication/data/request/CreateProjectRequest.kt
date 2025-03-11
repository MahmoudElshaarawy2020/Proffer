package com.example.myapplication.data.request

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

data class CreateProjectRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("project_type_id")
    val project_type_id: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("from_budget")
    val from_budget: String,
    @SerializedName("to_budget")
    val to_budget: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String,
    @SerializedName("start_date")
    val start_date: String,
    @SerializedName("is_open_budget")
    val is_open_budget: String,
    @SerializedName("project_image")
    val project_image: String,
){
    fun CreateProjectRequest.toPartMap(): Map<String, RequestBody> {
        return mapOf(
            "name" to name.toRequestBody(),
            "project_type_id" to project_type_id.toRequestBody(),
            "area" to area.toRequestBody(),
            "from_budget" to from_budget.toRequestBody(),
            "to_budget" to to_budget.toRequestBody(),
            "location" to location.toRequestBody(),
            "lat" to lat.toRequestBody(),
            "long" to long.toRequestBody(),
            "start_date" to start_date.toRequestBody(),
            "is_open_budget" to is_open_budget.toRequestBody(),
            "project_image" to project_image.toRequestBody()
        )
    }

    // Helper extension function to convert String to RequestBody
    fun String.toRequestBody(): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
    }

}

