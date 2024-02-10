package pronin.oleg.lab_work.mappers

import pronin.oleg.lab_work.data.network.models.FilmModel
import pronin.oleg.lab_work.data.network.models.GenreModal
import pronin.oleg.lab_work.domain.models.FilmDomainModel
import pronin.oleg.lab_work.domain.models.GenreDomainModal

fun FilmModel.toDomain() = FilmDomainModel(
    id = kinopoiskId,
    nameRu = nameRu,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    genres = genres?.map { it.toDomain() } ?: emptyList(),
    year = year
)

fun GenreModal.toDomain() = GenreDomainModal(
    genre = genre
)
