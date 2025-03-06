package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class ContractorProfileResponse(

	@field:SerializedName("data")
	val data: ContractorProfileData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ReviewsItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("client_name")
	val clientName: String? = null,

	@field:SerializedName("client_image")
	val clientImage: String? = null
)

data class ContractorProfileData(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("rate_avg")
	val rateAvg: Int? = null,

	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("work_images")
	val workImages: List<Any?>? = null
)
