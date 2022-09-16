package nl.project.newsreader2022.miscellaneous

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // check if recycler cannot scroll vertically
        if (isLoading().value == false && !recyclerView.canScrollVertically(1) && dy != 0) {
            recyclerView.post {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    protected abstract fun isLoading(): MutableLiveData<Boolean>
}