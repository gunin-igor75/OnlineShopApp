package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(signData: SignData) = repository.checkUser(signData)
}