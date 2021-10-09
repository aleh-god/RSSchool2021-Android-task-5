package by.godevelopment.rsschool2021_android_task_5.network

import by.godevelopment.rsschool2021_android_task_5.model.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY = "8d4ccab1-e437-479e-857e-4e002abdb22d"

interface WebApi {
    // https://api.thecatapi.com/v1/images/search?limit=5&page=10&order=Desc
    // @Headers("x-api-key:$API_KEY")
    @GET("search?order=Desc")
    suspend fun getPagingImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<List<Cat>>
}
