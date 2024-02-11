package pronin.oleg.lab_work.domain.models

data class FilmDomainModel(
    val id: Int,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: String,
    val description: String,
    val countries: List<CountryDomainModal>?,
    val genres: List<GenreDomainModal>?
)
