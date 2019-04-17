package club.wustfly.inggua.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.model.req.UploadFileParam;
import club.wustfly.inggua.model.resp.UploadFileRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;

public class PrintDocumentActivity extends BaseActivity {
    private static final String TAG = PrintDocumentActivity.class.getSimpleName();

    private static final int SELECT_FILE_REQUESTCODE = 1001;
    private static final int APPLY_PERMISSION_REQUESTCODE = 1002;

    String path = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_document_activity_layout);

        ButterKnife.bind(this);

        init();
    }

    private void init() {

        setTitle("文档打印");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

    }


    @OnClick({R.id.word_btn, R.id.pdf_btn, R.id.ppt_btn, R.id.wx_import_btn, R.id.qq_import_btn, R.id.wps_import_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.word_btn:
                Intent intent = new Intent(this, MyDocmentActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.pdf_btn:
                Intent intent2 = new Intent(this, MyDocmentActivity.class);
                intent2.putExtra("type", 2);
                startActivity(intent2);
                break;
            case R.id.ppt_btn:
                Intent intent3 = new Intent(this, MyDocmentActivity.class);
                intent3.putExtra("type", 3);
                startActivity(intent3);
                break;
            case R.id.wx_import_btn:
                path = "tencent/MicroMsg/Download";
                checkPermission();
                break;
            case R.id.qq_import_btn:
                path = "tencent/QQfile_recv";
                checkPermission();
                break;
            case R.id.wps_import_btn:
                path = "documents";
                checkPermission();
                break;
        }
    }

    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, APPLY_PERMISSION_REQUESTCODE);
        } else {
            selectFiles();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case APPLY_PERMISSION_REQUESTCODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectFiles();
                } else {
                    Toast.makeText(getApplicationContext(), "获取读写手机存储权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    private void selectFiles() {
        new LFilePicker()
                .withActivity(this)
                .withRequestCode(SELECT_FILE_REQUESTCODE)
                .withStartPath(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), path).getAbsolutePath())
                .withIsGreater(true)
                .withFileSize(0)
                .withFileFilter(new String[]{".docx", ".pptx", ".pdf"})
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE_REQUESTCODE && resultCode == RESULT_OK) {
            List<String> list = data.getStringArrayListExtra("paths");
            Log.i("wust-lz", list.toString());

            UploadFileParam param = new UploadFileParam();
            param.setTag(TAG);
            param.setUfile(list);
            param.setUid(Session.getSession().getUser().getId() + "");

            showProgressDialog();
            RequestWrapper.uploadFile(param);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveUploadFileResult(UploadFileRespDto respDto) {
        if (!TAG.equals(respDto.getTag())) return;
        showToast("上传成功");
    }
}
