package pronin.oleg.lab_work.data.utils

inline fun <Body, Result> NetworkCallResult<Body>.handle(
    onSuccess: (Body) -> Result,
    onFailed: (NetworkCallResult.Error) -> Result
) = when (this) {
    is NetworkCallResult.Error -> onFailed(this)
    is NetworkCallResult.Success -> onSuccess(value)
}
