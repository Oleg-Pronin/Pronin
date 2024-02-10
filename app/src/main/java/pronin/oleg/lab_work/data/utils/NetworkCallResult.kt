package pronin.oleg.lab_work.data.utils

sealed class NetworkCallResult<out T> {
    data class Success<out T>(val value: T) : NetworkCallResult<T>()

    data class Error(val errorMessage: String?) : NetworkCallResult<Nothing>()
}
