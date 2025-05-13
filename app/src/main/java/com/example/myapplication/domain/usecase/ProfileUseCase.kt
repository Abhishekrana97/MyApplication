package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ProfileDetails
import com.example.myapplication.domain.repository.ProfileScreenRepository
import com.example.myapplication.utils.DataState

class ProfileUseCase(private val repository: ProfileScreenRepository) {

    suspend operator fun invoke(): DataState<ProfileDetails> {
        return repository.getProfileDetails()
    }
}


