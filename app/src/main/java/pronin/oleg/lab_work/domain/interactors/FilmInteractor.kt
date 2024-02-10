package pronin.oleg.lab_work.domain.interactors

import pronin.oleg.lab_work.data.repository.FilmsRepository
import pronin.oleg.lab_work.domain.models.FilmDomainModel
import pronin.oleg.lab_work.domain.models.ItemsDomainModel
import pronin.oleg.lab_work.domain.utils.RequestResult
import pronin.oleg.lab_work.data.utils.handle
import javax.inject.Inject

class FilmInteractor @Inject constructor(
    private val repository: FilmsRepository
) {
    suspend fun getTopFilms(page: Int): RequestResult<ItemsDomainModel<FilmDomainModel>> {
        return repository.getTopFilms(page = page).handle(
            onSuccess = { RequestResult.Success(it) },
            onFailed = { RequestResult.Error(it.errorMessage) }
        )
    }
}
