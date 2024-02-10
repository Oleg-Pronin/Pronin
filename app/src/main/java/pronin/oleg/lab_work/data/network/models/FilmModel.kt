package pronin.oleg.lab_work.data.network.models

data class FilmModel(
    val kinopoiskId: Int,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<GenreModal>?,
    val year: String
)
