package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class SliderResponse(

	@field:SerializedName("data")
	val data: List<SliderItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class SliderItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("order")
	val order: Int? = null
)
