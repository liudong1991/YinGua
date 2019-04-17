package club.wustfly.inggua.model.req;

import java.util.List;

import club.wustfly.inggua.model.BaseModel;

public class UploadFileParam extends BaseModel {

    private String uid;
    private List<String> ufile;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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
