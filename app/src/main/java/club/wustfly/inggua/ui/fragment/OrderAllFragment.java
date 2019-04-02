package club.wustfly.inggua.ui.fragment;

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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.model.bean.OrderItem;
import club.wustfly.inggua.model.event.RequestFinishEvent;
import club.wustfly.inggua.model.req.GetOrderListParam;
import club.wustfly.inggua.model.resp.GetOrderListRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.OrderDetailActivity;
import club.wustfly.inggua.ui.base.BaseFragment;
import club.wustfly.inggua.ui.handlers.RefreshLayoutHandler;

public class OrderAllFragment extends BaseFragment {

    private static final String TAG = OrderAllFragment.class.getSimpleName();

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    RefreshLayoutHandler handler;

    RecyclerViewAdapter adapter = new RecyclerViewAdapter();

    List<OrderItem> list = new ArrayList<>();

    String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};


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

        showProgressDialog();
        loadList(1);

    }

    private void loadList(int page) {

        if (!Session.getSession().isLogin()) return;

        GetOrderListParam param = new GetOrderListParam();
        param.setUid(Session.getSession().getUser().getId());
        param.setOption("");
        param.setPage(page);
        param.setTag(TAG);

        RequestWrapper.getOrderList(param);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveGetOrderListResult(GetOrderListRespDto orderListRespDto) {
        if (!TAG.equals(orderListRespDto.getTag())) return;
        handler.loadSuccess(list, orderListRespDto.getOrder());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void requestFinish(RequestFinishEvent ev) {
        super.requestFinish(ev);
        handler.loadFinish();
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

            OrderItem orderItem = list.get(i);


            String[] values = {orderItem.getPage() + "张", orderItem.getSize(), orderItem.getIssingle(), orderItem.getColor(), orderItem.getLayout(), orderItem.getNumber() + "份", "不装订"};

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
            return list.size();
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
