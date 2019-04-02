package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class DocumentItem extends BaseModel {


    /**
     * id : 5
     * uid : 1
     * file : /static/upload/file/7e25dd81553b8490ca82375d1d402b50.pdf
     * type : 2
     * filename : 1.pdf
     * addtime : 2019-04-01 14:32
     * page : 1
     */

    private Integer id;
    private Integer uid;
    private String file;
    private Integer type;
    private String filename;
    private String addtime;
    private Integer page;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
