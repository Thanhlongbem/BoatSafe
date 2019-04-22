package vnu.uet.boatsafe.ui.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.ui.base.BaseViewHolder;
import vnu.uet.boatsafe.ui.base.OnItemClickListener;

public class FilterAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<String> filters;
    private OnItemClickListener onItemClickListener;

    public FilterAdapter(List<String> filters) {
        if (filters == null) {
            this.filters = new ArrayList<>();
        } else {
            this.filters = filters;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    class FilterViewHolder extends BaseViewHolder {

        @BindView(R.id.tvDictionary)
        TextView tvDictionary;

        public FilterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            String filter = filters.get(position);
            if (!TextUtils.isEmpty(filter)) {
                tvDictionary.setText(filter);
            }
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) onItemClickListener.onClick(position);
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

