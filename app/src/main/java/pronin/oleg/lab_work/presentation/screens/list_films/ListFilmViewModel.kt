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

    private var pageNumber = 1
    private var isPaginating = false
    private var areAllItemsLoaded = false
    private var isGettingItemsInProgress = false

    init {
        initializeItems()
    }

    private fun setProgress(inProgress: Boolean) {
        if (inProgress)
            _isError.value = false

        _isInProgress.value = inProgress
    }

    private fun initializeItems() {
        _viewModelItems.value = emptyList()

        getItems()
    }

    fun repeatButtonClicked() {
        getItems()
    }

    fun onScrolledToLastItem() {
        if (!isGettingItemsInProgress && !areAllItemsLoaded) {
            isPaginating = true

            _viewModelItems.value += FilmListItem.ProgressBar

            getItems()
        }
    }

    fun onSwipeRefresh() {
        _isError.value = false
        _isRefreshing.value = true

        initializeItems()
    }

    private fun getItems() = viewModelScope.launch {
        if (!isPaginating && !isRefreshing.value)
            setProgress(true)

        isGettingItemsInProgress = true

        when (val result = filmInteractor.getTopFilms(pageNumber)) {
            is RequestResult.Success -> {
                processPaginationResultSuccess(
                    isLastBatch = result.body.isLastBatch,
                    items = result.body.items.map {
                        FilmListItem.Film(body = it)
                    }
                )
            }

            is RequestResult.Error -> {
                _isError.value = true
            }
        }

        if (!isPaginating)
            if (!isRefreshing.value)
                setProgress(false)
            else
                _isRefreshing.value = false
        else
            isPaginating = false

        isGettingItemsInProgress = false
    }

    private fun processPaginationResultSuccess(
        isLastBatch: Boolean,
        items: List<FilmListItem>
    ) {
        areAllItemsLoaded = isLastBatch

        if (!isLastBatch)
            pageNumber++

        if (isPaginating)
            _viewModelItems.value -= FilmListItem.ProgressBar

        _viewModelItems.value += items
    }
}
