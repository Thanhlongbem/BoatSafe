package vnu.uet.boatsafe.ui.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerEndlessScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentTotalItems = 5;
    private boolean loading = true;

    private OnLoadMoreListener loadMoreListener;


    public RecyclerEndlessScrollListener(
            OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public RecyclerEndlessScrollListener(int visibleThreshold, OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        this.visibleThreshold = visibleThreshold;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        int adapterItemCount = recyclerView.getAdapter().getItemCount();
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (adapterItemCount < currentTotalItems) {
            this.currentTotalItems = adapterItemCount;
            if (adapterItemCount == 0) { this.loading = true; }
        }

        if (loading && (adapterItemCount > currentTotalItems)) {
            loading = false;
            currentTotalItems = adapterItemCount;
        }

        if (!loading && (adapterItemCount - visibleItemCount)<=(firstVisibleItemPosition + visibleThreshold)) {
            if (loadMoreListener != null) {
                loadMoreListener.onLoadMore();
                loading = true;
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setLoaded(boolean loading) {
        this.loading = loading;
    }
}
