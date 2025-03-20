package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class MaterialsResponse(

	@field:SerializedName("data")
	val data: List<MaterialItem?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class MaterialItem(

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: Int? = null,

	@field:SerializedName("material_image")
	val materialImage: String? = null
)
