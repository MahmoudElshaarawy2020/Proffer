package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class CreateProjectResponse(

	@field:SerializedName("data")
	val data: CreatedProjectData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class CreatedProjectData(

	@field:SerializedName("area")
	val area: Int? = null,

	@field:SerializedName("project_type_id")
	val projectTypeId: String? = null,

	@field:SerializedName("to_budget")
	val toBudget: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("long")
	val jsonMemberLong: String? = null,

	@field:SerializedName("from_budget")
	val fromBudget: Int? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("is_open_budget")
	val isOpenBudget: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lat")
	val lat: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)
