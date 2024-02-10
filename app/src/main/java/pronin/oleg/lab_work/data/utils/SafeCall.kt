package pronin.oleg.lab_work.data.utils

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Suppress("TooGenericExceptionCaught")
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    onNullValue: () -> NetworkCallResult<T>,
    apiCall: suspend () -> T?
): NetworkCallResult<T> {
    return withContext(context = dispatcher) {
        try {
            val result = apiCall()

            if (result != null)
                NetworkCallResult.Success(result)
            else
                onNullValue()
        } catch (e: Exception) {
            Log.d("DD_safeApiCall", e.toString())
            NetworkCallResult.Error(errorMessage = e.message)
        }
    }
}
