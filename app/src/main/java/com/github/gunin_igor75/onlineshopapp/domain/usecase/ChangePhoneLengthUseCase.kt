package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import javax.inject.Inject

class ChangePhoneLengthUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(phone: String) = repository.checkPhoneLength(phone)
}