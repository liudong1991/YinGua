package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.cache.Session;
import club.wustfly.yingua.model.req.ModifyNicknameParam;
import club.wustfly.yingua.model.resp.ModifyNicknameRespDto;
import club.wustfly.yingua.net.RequestWrapper;
import club.wustfly.yingua.ui.base.BaseActivity;

public class ModifyNicknameActivity extends BaseActivity {

    @BindView(R.id.nickname_input)
    EditText nickname_input;


    String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_nickname_activity_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("修改昵称");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        nickname = Session.getSession().getUser().getUsername();

        nickname_input.setText(nickname);

        nickname_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nickname = s.toString();
            }
        });

    }


    @OnClick({R.id.save})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                modifyNickname();
                break;
        }
    }

    private void modifyNickname() {
        if (TextUtils.isEmpty(nickname)) {
            showToast("请输入昵称");
            return;
        }

        if (!Session.getSession().isLogin()) return;

        ModifyNicknameParam param = new ModifyNicknameParam();
        param.setId(Session.getSession().getUser().getId());
        param.setUsername(nickname);

        showProgressDialog();
        RequestWrapper.modifyNickname(param);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveModifyNicknameResult(ModifyNicknameRespDto modifyNicknameRespDto) {
        Session.getSession().getUser().setUsername(nickname);
        Session.getSession().saveLoginResp2Disk();
        showToast("修改成功");
    }


}
