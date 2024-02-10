package pronin.oleg.lab_work.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ItemsDomainModel<T>(
    val items: @RawValue List<T>,
    val isLastBatch: Boolean,
    val isEmpty: Boolean,
    val totalCount: Int
) : Parcelable
