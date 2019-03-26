package club.wustfly.yingua.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.OrderDetailActivity;
import club.wustfly.yingua.ui.base.BaseFragment;
import club.wustfly.yingua.ui.handlers.RefreshLayoutHandler;

public class OrderAllFragment extends BaseFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    RefreshLayoutHandler handler;

    RecyclerViewAdapter adapter = new RecyclerViewAdapter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_all_fragment_layout, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {

        handler = RefreshLayoutHandler.getHandler(refreshLayout, new RefreshLayoutHandler.OnLoadListListener() {
            @Override
            public void loadListByPage(int page) {
                loadList(page);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(adapter);

        loadList(1);

    }

    private void loadList(int page) {

        recycler_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.loadFinish();
            }
        }, 3000);

    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.center_of_order_item_layout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

            String[] values = {"30张", "A4", "单面", "黑白", "每版1页", "1份", "不装订"};

            viewHolder.order_detail.removeAllViews();
            for (int j = 0; j < labels.length; j++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.pay_order_detail_item_layout, null);
                TextView labelTxt = view.findViewById(R.id.label);
                TextView valueTxt = view.findViewById(R.id.value);
                labelTxt.setText(labels[j]);
                valueTxt.setText(values[j]);
                viewHolder.order_detail.addView(view);
            }

            viewHolder.order_num_bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), OrderDetailActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout order_detail;
            LinearLayout order_num_bar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                order_detail = itemView.findViewById(R.id.order_detail);
                order_num_bar = itemView.findViewById(R.id.order_num_bar);
            }
        }
    }
}
