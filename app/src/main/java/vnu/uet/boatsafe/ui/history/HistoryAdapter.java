package vnu.uet.boatsafe.ui.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.models.History;
import vnu.uet.boatsafe.models.Location;
import vnu.uet.boatsafe.ui.base.BaseViewHolder;

public class HistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<History> m_history;

    public HistoryAdapter(List<History> m_history) {
        this.m_history = m_history;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.item_history_trip,
                viewGroup,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return m_history.size();
    }

    class HistoryViewHolder extends BaseViewHolder {
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtTimeCount)
        TextView txtTimeCount;
        @BindView(R.id.txtDistance)
        TextView txtDistance;
        @BindView(R.id.txtPositionStart)
        TextView txtPositionStart;
        @BindView(R.id.txtPositionEnd)
        TextView txtPositionEnd;
        @BindView(R.id.txtTimeStart)
        TextView txtTimeStart;
        @BindView(R.id.txtTimeEnd)
        TextView txtTimeEnd;
        public  HistoryViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            History history = m_history.get(position);
            txtDate.setText(history.getDate());
            txtTimeCount.setText(history.getTime());
            txtDistance.setText(String.valueOf(history.getDistance()) + " km");
            if (history.m_locationData != null) {
                Location startLocation = history.m_locationData.get(0);
                txtPositionStart.setText(String.valueOf(startLocation.getLat()) + ", " + String.valueOf(startLocation.getLng()));
                txtTimeStart.setText(String.valueOf(startLocation.getTime()));
                Location endLocation = history.m_locationData.get(history.m_locationData.size() - 1);
                txtPositionEnd.setText(String.valueOf(endLocation.getLat()) + ", " + String.valueOf(endLocation.getLng()));
                txtTimeEnd.setText(String.valueOf(endLocation.getTime()));
            }
        }
    }
}