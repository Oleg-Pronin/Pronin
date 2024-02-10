package pronin.oleg.lab_work.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import pronin.oleg.lab_work.app.di.annotations.IoDispatcher
import pronin.oleg.lab_work.data.network.api.KinopoiskUnofficialApi
import pronin.oleg.lab_work.data.network.models.FilmModel
import pronin.oleg.lab_work.data.utils.NetworkCallResult
import pronin.oleg.lab_work.data.utils.safeApiCall
import pronin.oleg.lab_work.domain.models.FilmDomainModel
import pronin.oleg.lab_work.domain.models.ItemsDomainModel
import pronin.oleg.lab_work.mappers.toDomain
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val apiService: KinopoiskUnofficialApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getTopFilms(
        page: Int
    ): NetworkCallResult<ItemsDomainModel<FilmDomainModel>> {
        return safeApiCall(
            dispatcher = dispatcher,
            onNullValue = { NetworkCallResult.Error(null) },
            apiCall = {
                apiService.getTop100Films(
                    page = page
                ).toDomain(FilmModel::toDomain)
            }
        )
    }
}
