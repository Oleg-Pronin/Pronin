package pronin.oleg.lab_work.data.network.api

import pronin.oleg.lab_work.data.network.models.FilmModel
import pronin.oleg.lab_work.data.network.models.ItemsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface KinopoiskUnofficialApi {
    @GET("/api/v2.2/films/collections")
    suspend fun getTop100Films(
        @Query("page") page: Int = 1,
        @Query("type") type: String = "TOP_POPULAR_MOVIES"
    ): ItemsModel<FilmModel>
}
