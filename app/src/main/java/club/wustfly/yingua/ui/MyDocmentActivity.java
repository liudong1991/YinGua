package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.adapter.MyDocumentAdapter;
import club.wustfly.yingua.ui.base.BaseActivity;

public class MyDocmentActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.footer)
    RelativeLayout footer;
    @BindView(R.id.print_btn)
    TextView print_btn;


    List<String> list = Arrays.asList(new String[]{"", "", "", "", ""});

    MyDocumentAdapter adapter;

    IOnSubtitle iOnSubtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_docment_activity_layout);

        ButterKnife.bind(this);

        init();

    }

    private void init() {

        setTitle("我的文档");
        iOnSubtitle = new IOnSubtitle() {
            @Override
            public void handle() {
                adapter.printByBatch();
                setSubtitle("全选", new IOnSubtitle() {
                    @Override
                    public void handle() {
                        adapter.selectAll();
                    }
                });
                setLeft("取消", new IOnSubtitle() {
                    @Override
                    public void handle() {
                        setBack();
                        setSubtitle("批量打印", iOnSubtitle);
                        adapter.cancelPrintByBatch();
                        showFooter(false);
                    }
                });
                showFooter(true);
            }
        };
        setSubtitle("批量打印", iOnSubtitle);

        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();


        adapter = new MyDocumentAdapter(this, list, print_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);

        recycler_view.setAdapter(adapter);

    }


    void showFooter(boolean isVisible) {

        footer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


}
