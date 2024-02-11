package pronin.oleg.lab_work.util

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import pronin.oleg.lab_work.R

fun NavController.actionNavOptions(@IdRes resId: Int) =
    currentDestination?.getAction(resId)?.navOptions

fun NavOptions.edit(
    shouldLaunchSingleTop: Boolean = this.shouldLaunchSingleTop(),
    shouldRestoreState: Boolean = this.shouldRestoreState(),
    @IdRes popUpToId: Int = this.popUpToId,
    popUpToRoute: String? = this.popUpToRoute,
    isPopUpToInclusive: Boolean = this.isPopUpToInclusive(),
    shouldPopUpToSaveState: Boolean = this.shouldPopUpToSaveState(),
    @AnimRes @AnimatorRes enterAnim: Int = this.enterAnim,
    @AnimRes @AnimatorRes exitAnim: Int = this.exitAnim,
    @AnimRes @AnimatorRes popEnterAnim: Int = this.popEnterAnim,
    @AnimRes @AnimatorRes popExitAnim: Int = this.popExitAnim
) = NavOptions.Builder().apply {
    setLaunchSingleTop(shouldLaunchSingleTop)
    setRestoreState(shouldRestoreState)

    if (popUpToId != -1) {
        setPopUpTo(
            destinationId = popUpToId,
            inclusive = isPopUpToInclusive,
            saveState = shouldPopUpToSaveState
        )
    }

    if (popUpToRoute != null) {
        setPopUpTo(
            route = popUpToRoute,
            inclusive = isPopUpToInclusive,
            saveState = shouldPopUpToSaveState
        )
    }

    setEnterAnim(enterAnim)
    setExitAnim(exitAnim)
    setPopEnterAnim(popEnterAnim)
    setPopExitAnim(popExitAnim)
}.build()

fun NavController.animNavigate(
    @IdRes resId: Int,
    args: Bundle? = null
) {
    navigate(
        resId = resId,
        args = args,
        navOptions = actionNavOptions(resId)?.edit(
            enterAnim = R.anim.screen_slide_in_left,
            exitAnim = R.anim.screen_slide_out_left,
            popEnterAnim = R.anim.screen_slide_out_right,
            popExitAnim = R.anim.screen_slide_in_right
        )
    )
}
