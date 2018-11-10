package com.deedcorps.edward.project_zeal.api

import com.deedcorps.edward.project_zeal.api.model.Article
import com.deedcorps.edward.project_zeal.api.model.ZealResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    private fun getZealService(): ZealService {
        return getRetrofitInstance().create(ZealService::class.java)
    }

    fun getZealResponse(article: Article): ZealResponse {
        return getZealService().postArticle(article).execute().body()
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private const val BASE_URL = "http://10.10.190.59:8080/"
}