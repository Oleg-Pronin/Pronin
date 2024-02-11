package pronin.oleg.lab_work.presentation.screens.list_films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pronin.oleg.lab_work.domain.interactors.FilmInteractor
import pronin.oleg.lab_work.domain.utils.RequestResult
import pronin.oleg.lab_work.presentation.screens.list_films.adapter.FilmListItem
import javax.inject.Inject

@HiltViewModel
class ListFilmViewModel @Inject constructor(
    private val filmInteractor: FilmInteractor
) : ViewModel() {

    private val _isInProgress = MutableStateFlow(false)
    val isInProgress = _isInProgress.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _viewModelItems = MutableStateFlow(emptyList<FilmListItem>())
    val items = _viewModelItems.asStateFlow()

    init {
        initializeItems()
    }

    private fun setProgress(inProgress: Boolean) {
        if (inProgress)
            _isError.value = false

        _isInProgress.value = inProgress
    }

    fun initializeItems() = viewModelScope.launch {
        if (!isRefreshing.value)
            setProgress(true)

        _viewModelItems.value = emptyList()

        getItems()

        if (!isRefreshing.value)
            setProgress(false)
        else
            _isRefreshing.value = false
    }

    private suspend fun getItems() {
        when (val result = filmInteractor.getTopFilms(1)) {
            is RequestResult.Success -> {
                _viewModelItems.value += result.body.items.map {
                    FilmListItem.Film(body = it)
                }
            }

            is RequestResult.Error -> {
                _isError.value = true
            }
        }
    }

    fun onSwipeRefresh() {
        _isError.value = false
        _isRefreshing.value = true

        initializeItems()
    }
}
