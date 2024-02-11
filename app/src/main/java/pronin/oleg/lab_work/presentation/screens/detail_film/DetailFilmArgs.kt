package pronin.oleg.lab_work.presentation.screens.detail_film

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailFilmArgs(
    val filmId: Int
) : Parcelable {
    companion object {
        const val ARGS_KEY = "detail_film_args_key"
    }
}
