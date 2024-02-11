package pronin.oleg.lab_work.data.network.api

import pronin.oleg.lab_work.data.network.models.FilmListModel
import pronin.oleg.lab_work.data.network.models.FilmModel
import pronin.oleg.lab_work.data.network.models.ItemsModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskUnofficialApi {
    @GET("/api/v2.2/films/collections")
    suspend fun getTop100Films(
        @Query("page") page: Int = 1,
        @Query("type") type: String = "TOP_POPULAR_MOVIES"
    ): ItemsModel<FilmListModel>

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int
    ): FilmModel
}
