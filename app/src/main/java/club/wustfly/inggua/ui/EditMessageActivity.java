package club.wustfly.inggua.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.ui.base.BaseActivity;

public class EditMessageActivity extends BaseActivity {

    @BindView(R.id.edit_txt)
    EditText edit_txt;


    String message = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_message_layout);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("留言");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();


        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        edit_txt.setText(message);
        edit_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                message = s.toString();
            }
        });

    }


    @OnClick({R.id.confirm_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn:
                Intent intent = new Intent();
                intent.putExtra("message", message);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

}
