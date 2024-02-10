package pronin.oleg.lab_work.presentation.screens.list_movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pronin.oleg.lab_work.domain.interactors.FilmInteractor
import pronin.oleg.lab_work.domain.utils.RequestResult
import pronin.oleg.lab_work.presentation.screens.list_movie.adapter.FilmListItem
import javax.inject.Inject

@HiltViewModel
class ListFilmViewModel @Inject constructor(
    private val filmInteractor: FilmInteractor
) : ViewModel() {

    private val _viewModelItems = MutableStateFlow(emptyList<FilmListItem>())
    val items = _viewModelItems.asStateFlow()

    init {
        initializeItems()
    }

    private fun initializeItems() = viewModelScope.launch {
        when (val result = filmInteractor.getTopFilms(1)) {
            is RequestResult.Success -> {
                _viewModelItems.value += result.body.items.map {
                    FilmListItem.Film(body = it)
                }
            }

            is RequestResult.Error -> {
                Log.d("DD_error", result.errorMessage.toString())
            }
        }
    }
}
