package pl.kazoroo.dices.core.domain

import pl.kazoroo.dices.core.data.local.UserDataKey
import pl.kazoroo.dices.core.data.local.UserDataRepository

class SaveUserDataUseCase(private val userDataRepository: UserDataRepository) {
    suspend operator fun invoke(value: String) {
        userDataRepository.saveNewValue(
            key = UserDataKey.COINS,
            value = value
        )
    }
}