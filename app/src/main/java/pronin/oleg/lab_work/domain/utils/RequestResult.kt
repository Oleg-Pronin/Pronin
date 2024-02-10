package pronin.oleg.lab_work.domain.utils

sealed class RequestResult<out T> {
    class Success<T>(val body: T) : RequestResult<T>()

    class Error(val errorMessage: String?) : RequestResult<Nothing>()
}
