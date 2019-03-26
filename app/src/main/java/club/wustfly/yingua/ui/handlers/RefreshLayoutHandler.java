package club.wustfly.yingua.ui.handlers;

import android.support.annotation.NonNull;

import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class RefreshLayoutHandler {

    int curPage = 1;
    int prePage = 1;
    SmartRefreshLayout smartRefreshLayout;

    public static RefreshLayoutHandler getHandler(SmartRefreshLayout smartRefreshLayout, OnLoadListListener onLoadListListener) {
        return new RefreshLayoutHandler(smartRefreshLayout, onLoadListListener);
    }

    private RefreshLayoutHandler() {
    }

    private RefreshLayoutHandler(SmartRefreshLayout smartRefreshLayout, OnLoadListListener onLoadListListener) {
        this.smartRefreshLayout = smartRefreshLayout;
        this.onLoadListListener = onLoadListListener;
        init();
    }

    OnLoadListListener onLoadListListener;

    public void setOnLoadListListener(OnLoadListListener onLoadListListener) {
        this.onLoadListListener = onLoadListListener;
    }

    public interface OnLoadListListener {
        void loadListByPage(int page);
    }

    private void init() {
        this.smartRefreshLayout.setRefreshHeader(new StoreHouseHeader(smartRefreshLayout.getContext()).initWithString("YIN GUA"));
        this.smartRefreshLayout.setRefreshFooter(new ClassicsFooter(smartRefreshLayout.getContext()));

        this.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                prePage = 1;
                if (onLoadListListener != null) onLoadListListener.loadListByPage(prePage);
            }
        });

        this.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout smartRefreshLayout) {
                prePage = curPage;
                prePage++;
                if (onLoadListListener != null) onLoadListListener.loadListByPage(prePage);
            }
        });
    }

    public void loadFinish() {
        if (prePage == 1) {
            smartRefreshLayout.finishRefresh(1500);
        } else {
            smartRefreshLayout.finishLoadMore(1500);
        }
    }

    public <T> void loadSuccess(List<T> all, List<T> curPageList) {
        if (curPageList == null) {
            curPageList = new ArrayList<>();
        }
        if (!curPageList.isEmpty())
            curPage = prePage;
        if (prePage == 1) all.clear();
        all.addAll(curPageList);
    }

    public int prePageInit() {
        prePage = 1;
        return 1;
    }
}
