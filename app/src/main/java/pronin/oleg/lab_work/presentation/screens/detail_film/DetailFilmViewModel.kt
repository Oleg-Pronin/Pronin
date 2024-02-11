package pronin.oleg.lab_work.presentation.screens.detail_film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pronin.oleg.lab_work.domain.interactors.FilmInteractor
import pronin.oleg.lab_work.domain.models.FilmDomainModel
import pronin.oleg.lab_work.domain.utils.RequestResult


class DetailFilmViewModel @AssistedInject constructor(
    @Assisted("args") args: DetailFilmArgs?,
    private val filmInteractor: FilmInteractor
) : ViewModel() {

    private val _isInProgress = MutableStateFlow(false)
    val isInProgress = _isInProgress.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _viewModelItem = MutableStateFlow<FilmDomainModel?>(null)
    val item = _viewModelItem.asStateFlow()

    private val filmId = args?.filmId

    init {
        initializeItem()
    }

    private fun setProgress(inProgress: Boolean) {
        if (inProgress)
            _isError.value = false

        _isInProgress.value = inProgress
    }

    fun initializeItem() = viewModelScope.launch {
        setProgress(true)

        if (filmId == null)
            _isError.value = true
        else
            when (val result = filmInteractor.getFilmById(filmId)) {
                is RequestResult.Success -> {
                    _viewModelItem.value = result.body
                }

                is RequestResult.Error -> {
                    _isError.value = true
                }
            }

        setProgress(false)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("args") args: DetailFilmArgs?
        ): DetailFilmViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            args: DetailFilmArgs?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(args) as T
            }
        }
    }
}