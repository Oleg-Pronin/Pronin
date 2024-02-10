package pronin.oleg.lab_work.presentation.screens.list_movie.adapter

import android.util.Log
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import pronin.oleg.lab_work.databinding.ItemListBinding
import pronin.oleg.lab_work.databinding.ItemProgressBarBinding
import pronin.oleg.lab_work.domain.models.FilmDomainModel


fun filmListAdapterDelegate(
    onClick: ((FilmDomainModel) -> Unit)? = null
) = adapterDelegateViewBinding<FilmListItem.Film, FilmListItem, ItemListBinding>(
    viewBinding = { layoutInflater, root ->
        ItemListBinding.inflate(layoutInflater, root, false)
    },
    on = { item, _, _ -> item is FilmListItem.Film }
) {
    binding.root.apply {
        setOnClickListener {
            Log.d("DD_click", "click")

            onClick?.invoke(item.body)
        }

        setOnLongClickListener {
            Log.d("DD_click", "long_click")

            return@setOnLongClickListener true
        }
    }

    bind {
        val film = item.body

        binding.apply {
            banner.apply {
                Glide.with(context)
                    .asBitmap()
                    .load(film.posterUrl)
                    .centerCrop()
                    .into(this)
            }

            type.text = film.genres.firstOrNull()?.let { item ->
                item.genre.replaceFirstChar { it.uppercase() } + " (${film.year})"
            } ?: film.year

            name.text = film.nameRu

            favorite.isVisible = item.isFavorite
        }
    }
}

fun progressBarAdapterDelegate() =
    adapterDelegateViewBinding<FilmListItem.ProgressBar, FilmListItem, ItemProgressBarBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemProgressBarBinding.inflate(layoutInflater, parent, false)
        },
        on = { item, _, _ -> item is FilmListItem.ProgressBar }
    ) {}

sealed interface FilmListItem {
    data class Film(
        val body: FilmDomainModel,
        val isFavorite: Boolean = false
    ) : FilmListItem

    data object ProgressBar : FilmListItem
}
