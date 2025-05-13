package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.ProfileDetails
import com.example.myapplication.utils.DataState

interface ProfileScreenRepository {
    suspend fun getProfileDetails(): DataState<ProfileDetails>
}