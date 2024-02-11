package pronin.oleg.lab_work.mappers

import pronin.oleg.lab_work.data.network.models.CountryModel
import pronin.oleg.lab_work.data.network.models.FilmListModel
import pronin.oleg.lab_work.data.network.models.FilmModel
import pronin.oleg.lab_work.data.network.models.GenreModel
import pronin.oleg.lab_work.domain.models.CountryDomainModal
import pronin.oleg.lab_work.domain.models.FilmDomainModel
import pronin.oleg.lab_work.domain.models.FilmListDomainModel
import pronin.oleg.lab_work.domain.models.GenreDomainModal

fun FilmListModel.toDomain() = FilmListDomainModel(
    id = kinopoiskId,
    nameRu = nameRu,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    genres = genres?.map { it.toDomain() } ?: emptyList(),
    year = year
)

fun FilmModel.toDomain() = FilmDomainModel(
    id = kinopoiskId,
    nameRu = nameRu,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    year = year,
    description = description,
    genres = genres?.map { it.toDomain() } ?: emptyList(),
    countries = countries?.map { it.toDomain() } ?: emptyList()
)

fun GenreModel.toDomain() = GenreDomainModal(
    genre = genre
)

fun CountryModel.toDomain() = CountryDomainModal(
    country = country
)