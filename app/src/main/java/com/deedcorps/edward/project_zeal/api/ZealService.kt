package com.deedcorps.edward.project_zeal.api

import com.deedcorps.edward.project_zeal.api.model.Article
import com.deedcorps.edward.project_zeal.api.model.ZealResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ZealService {

    @Headers(
        "Accept: charset=utf-8",
        "Content-Type: application/json"
    )
    @POST("fakebox/check")
    fun postArticle(@Body article: Article): Call<ZealResponse>
}