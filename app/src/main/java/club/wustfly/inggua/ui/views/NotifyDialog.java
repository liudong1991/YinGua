package club.wustfly.inggua.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.MainActivity;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;

public class NotifyDialog extends Dialog {

    Context mContext;

    public NotifyDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_dialog_layout);

        ButterKnife.bind(this);

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = defaultDisplay.getWidth() * 3 / 5;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);

    }

    @OnClick({R.id.cancel_btn, R.id.confirm_btn})
    public void handleClick(View view) {

        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.confirm_btn:
                Session.getSession().logout();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("from", "logout");
                mContext.startActivity(intent);
                dismiss();
                break;
        }
    }
}
