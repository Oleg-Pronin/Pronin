package pronin.oleg.lab_work.mappers

import pronin.oleg.lab_work.data.network.models.ItemsModel
import pronin.oleg.lab_work.domain.models.ItemsDomainModel

fun <T, R> ItemsModel<T>.toDomain(mapper: T.() -> R) = ItemsDomainModel(
    items = items?.mapNotNull { it.mapper() } ?: emptyList(),
    isLastBatch = totalPages == total,
    totalCount = total ?: 0,
    isEmpty = total == 0
)
