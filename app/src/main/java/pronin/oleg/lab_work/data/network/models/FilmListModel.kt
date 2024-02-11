package pronin.oleg.lab_work.data.network.models

data class FilmListModel(
    val kinopoiskId: Int,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<GenreModel>?,
    val countries: List<CountryModel>?,
    val year: String
)
