package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("data")
	val data: ContractorData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ContractorData(

	@field:SerializedName("contractors")
	val contractors: List<ContractorsItem?>? = null,

	@field:SerializedName("projects")
	val projects: List<Any?>? = null,

	@field:SerializedName("projects_count")
	val projectsCount: Int? = null
)

data class ContractorsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("rate_avg")
	val rateAvg: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
