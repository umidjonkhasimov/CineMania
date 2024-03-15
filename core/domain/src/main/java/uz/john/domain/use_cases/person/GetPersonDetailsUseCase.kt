package uz.john.domain.use_cases.person

import uz.john.data.repository.PersonRepository
import uz.john.domain.model.person.Person
import uz.john.domain.model.person.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(personId: Int): ResultModel<Person> {
        val response = personRepository.getPersonDetails(personId)

        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                val person = response.data.toDomain()
                ResultModel.Success(person)
            }
        }
    }
}