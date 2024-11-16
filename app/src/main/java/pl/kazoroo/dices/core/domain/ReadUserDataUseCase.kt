package pl.kazoroo.dices.core.domain

import pl.kazoroo.dices.core.data.local.UserDataKey
import pl.kazoroo.dices.core.data.local.UserDataRepository

class ReadUserDataUseCase(private val userDataRepository: UserDataRepository) {
    suspend operator fun invoke(): String {
        return userDataRepository.readValue(UserDataKey.COINS) ?: "100"
    }
}