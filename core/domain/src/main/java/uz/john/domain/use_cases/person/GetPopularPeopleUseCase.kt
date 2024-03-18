package uz.john.domain.use_cases.person

import uz.john.data.repository.PersonRepository
import uz.john.domain.model.person.Person
import uz.john.domain.model.person.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(page: Int): ResultModel<List<Person>> {
        val response = personRepository.getPopularPeople(page = page)

        return when (response) {
            is ResultModel.Error -> {
                ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                val list = response.data.results.map { it.toDomain() }
                ResultModel.Success(list)
            }
        }
    }
}