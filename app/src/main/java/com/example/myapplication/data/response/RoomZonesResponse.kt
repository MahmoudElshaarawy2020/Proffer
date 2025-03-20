package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class RoomZonesResponse(

	@field:SerializedName("data")
	val data: List<RoomItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class RoomItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null
)
