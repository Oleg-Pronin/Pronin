package pronin.oleg.lab_work.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setPaginationScrollListener(
    paginationFactor: Float = 1.6f,
    scrolledToLastItem: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val itemCount = (recyclerView.layoutManager as LinearLayoutManager).itemCount
            val lastVisiblePosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            if (lastVisiblePosition >= itemCount / paginationFactor) {
                scrolledToLastItem()
            }
        }
    })
}
