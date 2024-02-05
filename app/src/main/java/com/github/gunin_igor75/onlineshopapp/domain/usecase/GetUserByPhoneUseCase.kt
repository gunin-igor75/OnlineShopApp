package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByPhoneUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(phone: String) = repository.getUserByPhone(phone)
}