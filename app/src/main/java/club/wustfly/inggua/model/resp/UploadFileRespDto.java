package club.wustfly.inggua.model.resp;

import java.util.List;

import club.wustfly.inggua.model.RespDto;

public class UploadFileRespDto extends RespDto {

    private String tag;
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
