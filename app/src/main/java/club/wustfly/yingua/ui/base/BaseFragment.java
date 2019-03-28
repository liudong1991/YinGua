package club.wustfly.yingua.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import club.wustfly.yingua.R;
import club.wustfly.yingua.model.event.RequestFinishEvent;
import club.wustfly.yingua.ui.views.CustomProgressDialog;

public class BaseFragment extends Fragment {

    private CustomProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setTitle(String title) {
        TextView title_txt = getView().findViewById(R.id.title);
        title_txt.setText(title);
    }

    protected void setHeaderBackgroundColor(String colorStr) {
        getView().findViewById(R.id.header).setBackgroundColor(Color.parseColor(colorStr));
    }

    protected void setHeaderTopMargin() {
        View header = getView().findViewById(R.id.header);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) header.getLayoutParams();
        lp.topMargin = BaseActivity.getStatusBarHeight(getActivity());
        header.setLayoutParams(lp);
    }

    protected void setHeaderTopPadding() {
        View header = getView().findViewById(R.id.header);
        header.setPadding(0, BaseActivity.getStatusBarHeight(getActivity()), 0, 0);
    }

    public void dismissProgressDialog() {
        if (this.dialog != null) {
            if (this.dialog.isShowing())
                this.dialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new CustomProgressDialog(getContext());
        }
        this.dialog.setTips("加载中...");
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    protected void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestFinish(RequestFinishEvent ev) {
        dismissProgressDialog();
    }

    protected void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
