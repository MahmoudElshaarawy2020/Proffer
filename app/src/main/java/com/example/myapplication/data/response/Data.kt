package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName


data class Data(

	@field:SerializedName("account_type")
	val accountType: Int? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
