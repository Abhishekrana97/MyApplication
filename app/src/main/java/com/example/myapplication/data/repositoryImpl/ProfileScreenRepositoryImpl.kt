package com.example.myapplication.data.repositoryImpl

import com.example.myapplication.data.model.ProfileDetails
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.domain.repository.ProfileScreenRepository
import com.example.myapplication.utils.DataState
import javax.inject.Inject

class ProfileScreenRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ProfileScreenRepository {

    override suspend fun getProfileDetails(): DataState<ProfileDetails>  {
       return try {
            val results = apiService.getProfileDetails()
            DataState.Success(results)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

}

