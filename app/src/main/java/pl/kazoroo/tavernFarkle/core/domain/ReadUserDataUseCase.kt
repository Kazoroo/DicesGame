package pl.kazoroo.tavernFarkle.core.domain

import pl.kazoroo.tavernFarkle.core.data.local.UserDataKey
import pl.kazoroo.tavernFarkle.core.data.local.UserDataRepository

class ReadUserDataUseCase(private val userDataRepository: UserDataRepository) {
    suspend operator fun invoke(): String {
        return userDataRepository.readValue(UserDataKey.COINS) ?: "100"
    }
}