package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class ProjectTypesResponse(

	@field:SerializedName("data")
	val data: List<ProjectItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ProjectItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
