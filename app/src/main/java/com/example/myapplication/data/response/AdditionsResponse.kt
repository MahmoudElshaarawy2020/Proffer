package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class AdditionsResponse(

	@field:SerializedName("data")
	val data: List<AdditionsItem?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class AdditionsItem(

	@field:SerializedName("price")
	val price: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: Int? = null
)
