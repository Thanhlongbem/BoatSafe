

package vnu.uet.boatsafe.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int mCurrentPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }


    public void onBind(int position) {
        mCurrentPosition = position;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
