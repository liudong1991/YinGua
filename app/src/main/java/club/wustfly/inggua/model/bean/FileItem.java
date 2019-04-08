package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class FileItem extends BaseModel {


    /**
     * id : 3
     * file : /static/upload/file/52a18cf9032504bb558533f39629ca99.pdf
     * filename : 1.pdf
     * page : 1
     */

    private Integer id;
    private String file;
    private String filename;
    private Integer page;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
