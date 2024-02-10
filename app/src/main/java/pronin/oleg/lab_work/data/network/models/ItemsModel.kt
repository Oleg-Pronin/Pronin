package pronin.oleg.lab_work.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemsModel<T>(
    @SerializedName("items")
    @Expose
    val items: List<T>? = null,

    @SerializedName("total")
    @Expose
    val total: Int?,

    @SerializedName("totalPages")
    @Expose
    val totalPages: Int?
)
