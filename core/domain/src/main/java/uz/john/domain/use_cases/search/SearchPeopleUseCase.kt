package uz.john.domain.use_cases.search

import uz.john.data.repository.PersonRepository
import uz.john.domain.model.person.Person
import uz.john.domain.model.person.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(query: String, page: Int): ResultModel<List<Person>> {
        val response = personRepository.searchPeople(query = query, page = page)
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