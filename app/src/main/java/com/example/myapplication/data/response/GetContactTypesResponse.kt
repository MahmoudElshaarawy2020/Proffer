package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class GetContactTypesResponse(

	@field:SerializedName("data")
	val data: List<ContactData?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ContactData(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
