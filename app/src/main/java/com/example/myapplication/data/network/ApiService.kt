package com.example.myapplication.data.network

import com.example.myapplication.data.model.ExchangeCurrency
import com.example.myapplication.data.model.ExpenseDetails
import com.example.myapplication.data.model.GraphDetails
import com.example.myapplication.data.model.ProfileDetails
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("6e5c5226-91e7-4d5d-835d-d9fd33a1d8f1")
    suspend fun getProfileDetails(): ProfileDetails

    @GET("88f95883-e014-473e-9e49-260654d821b7")
    suspend fun getGraphDetails(): GraphDetails

    @GET("201726cf-2575-49e2-8614-a91f415ba23b")
    suspend fun getExpenseDetails(): ExpenseDetails

    @GET("https://open.er-api.com/v6/latest/{path}")
    suspend fun getExchangeCurrencyDetails(@Path("path") path: String): ExchangeCurrency


}
