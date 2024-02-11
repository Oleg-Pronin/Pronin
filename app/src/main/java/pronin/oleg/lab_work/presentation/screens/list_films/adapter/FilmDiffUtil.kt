package pronin.oleg.lab_work.presentation.screens.list_films.adapter

import androidx.recyclerview.widget.DiffUtil

class FilmDiffUtil : DiffUtil.ItemCallback<FilmListItem>() {
    override fun areItemsTheSame(oldItem: FilmListItem, newItem: FilmListItem): Boolean {
        if (oldItem !is FilmListItem.Film || newItem !is FilmListItem.Film)
            return false

        return oldItem.body.id == newItem.body.id
    }

    override fun areContentsTheSame(oldItem: FilmListItem, newItem: FilmListItem): Boolean {
        if (oldItem !is FilmListItem.Film || newItem !is FilmListItem.Film)
            return false

        return oldItem.body == newItem.body
    }
}
