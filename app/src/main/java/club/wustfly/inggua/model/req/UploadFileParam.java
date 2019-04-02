package club.wustfly.inggua.model.req;

import java.util.List;

import club.wustfly.inggua.model.BaseModel;

public class UploadFileParam extends BaseModel {

    private String uid;
    private List<String> ufile;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getUfile() {
        return ufile;
    }

    public void setUfile(List<String> ufile) {
        this.ufile = ufile;
    }
}
