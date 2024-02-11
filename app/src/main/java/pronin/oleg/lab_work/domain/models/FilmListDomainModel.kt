package pronin.oleg.lab_work.domain.models

data class FilmListDomainModel(
    val id: Int,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<GenreDomainModal>,
    val year: String
)
