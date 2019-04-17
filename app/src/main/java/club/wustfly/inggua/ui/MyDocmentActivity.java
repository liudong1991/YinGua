package club.wustfly.inggua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.model.bean.DocumentItem;
import club.wustfly.inggua.model.req.DeleteDocParam;
import club.wustfly.inggua.model.req.ObtainMyDocumentParam;
import club.wustfly.inggua.model.resp.DeleteDocRespDto;
import club.wustfly.inggua.model.resp.ObtainMyDocumentRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.adapter.MyDocumentAdapter;
import club.wustfly.inggua.ui.base.BaseActivity;

public class MyDocmentActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.footer)
    RelativeLayout footer;
    @BindView(R.id.print_btn)
    TextView print_btn;

    List<DocumentItem> list = new ArrayList<>();

    MyDocumentAdapter adapter;

    IOnSubtitle iOnSubtitle;

    int type;

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


        type = getIntent().getIntExtra("type", 0);

        adapter = new MyDocumentAdapter(this, list, print_btn, type);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);

        recycler_view.setAdapter(adapter);

        obtainMyDocument();

    }

    private void obtainMyDocument() {
        ObtainMyDocumentParam param = new ObtainMyDocumentParam();
        param.setUid(Session.getSession().getUser().getId() + "");
        param.setType(type);

        showProgressDialog();
        RequestWrapper.obtainMyDocument(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void obtainMyDocumentResult(ObtainMyDocumentRespDto respDto) {
        Log.i("wust-lz", respDto.getData().toString());
        adapter.addDocumentItem(respDto.getData());
    }


    void showFooter(boolean isVisible) {
        footer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.delete_doc_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.delete_doc_btn:
                String fid = adapter.assembleFid();
                if (TextUtils.isEmpty(fid)) {
                    showToast("请选择要删除的文档");
                    return;
                }

                DeleteDocParam param = new DeleteDocParam();
                param.setFid(fid);
                showProgressDialog();
                RequestWrapper.deleteDoc(param);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDeleteDocResult(DeleteDocRespDto respDto) {
        showToast("删除成功");
        adapter.delete();
    }


}
