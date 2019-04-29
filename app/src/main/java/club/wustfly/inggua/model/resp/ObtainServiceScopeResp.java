package club.wustfly.inggua.model.resp;

import java.util.List;

import club.wustfly.inggua.model.RespDto;

public class ObtainServiceScopeResp extends RespDto {


    /**
     * data : {"fencing_event_list":[{"client_status":"in","client_action":"enter","enter_time":"2019-04-27 18:53:57","fence_info":{"fence_gid":"6062e00a-badc-4e80-9152-23ea40aafc8b","fence_center":"113.39292,23.055264","fence_name":"广州市大学城体育中心"}}],"status":0}
     * errcode : 0
     * errdetail : null
     * errmsg : OK
     * ext : null
     */

    private DataBean data;
    private int errcode;
    private Object errdetail;
    private String errmsg;
    private Object ext;

    @Override
    public String getCode() {
        return errmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public Object getErrdetail() {
        return errdetail;
    }

    public void setErrdetail(Object errdetail) {
        this.errdetail = errdetail;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    public static class DataBean {
        /**
         * fencing_event_list : [{"client_status":"in","client_action":"enter","enter_time":"2019-04-27 18:53:57","fence_info":{"fence_gid":"6062e00a-badc-4e80-9152-23ea40aafc8b","fence_center":"113.39292,23.055264","fence_name":"广州市大学城体育中心"}}]
         * status : 0
         */

        private int status;
        private List<FencingEventListBean> fencing_event_list;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<FencingEventListBean> getFencing_event_list() {
            return fencing_event_list;
        }

        public void setFencing_event_list(List<FencingEventListBean> fencing_event_list) {
            this.fencing_event_list = fencing_event_list;
        }

        public static class FencingEventListBean {
            /**
             * client_status : in
             * client_action : enter
             * enter_time : 2019-04-27 18:53:57
             * fence_info : {"fence_gid":"6062e00a-badc-4e80-9152-23ea40aafc8b","fence_center":"113.39292,23.055264","fence_name":"广州市大学城体育中心"}
             */

            private String client_status;
            private String client_action;
            private String enter_time;
            private FenceInfoBean fence_info;

            public String getClient_status() {
                return client_status;
            }

            public void setClient_status(String client_status) {
                this.client_status = client_status;
            }

            public String getClient_action() {
                return client_action;
            }

            public void setClient_action(String client_action) {
                this.client_action = client_action;
            }

            public String getEnter_time() {
                return enter_time;
            }

            public void setEnter_time(String enter_time) {
                this.enter_time = enter_time;
            }

            public FenceInfoBean getFence_info() {
                return fence_info;
            }

            public void setFence_info(FenceInfoBean fence_info) {
                this.fence_info = fence_info;
            }

            public static class FenceInfoBean {
                /**
                 * fence_gid : 6062e00a-badc-4e80-9152-23ea40aafc8b
                 * fence_center : 113.39292,23.055264
                 * fence_name : 广州市大学城体育中心
                 */

                private String fence_gid;
                private String fence_center;
                private String fence_name;

                public String getFence_gid() {
                    return fence_gid;
                }

                public void setFence_gid(String fence_gid) {
                    this.fence_gid = fence_gid;
                }

                public String getFence_center() {
                    return fence_center;
                }

                public void setFence_center(String fence_center) {
                    this.fence_center = fence_center;
                }

                public String getFence_name() {
                    return fence_name;
                }

                public void setFence_name(String fence_name) {
                    this.fence_name = fence_name;
                }
            }
        }
    }
}
