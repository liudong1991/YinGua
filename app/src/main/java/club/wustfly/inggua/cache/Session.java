package club.wustfly.inggua.cache;

import android.text.TextUtils;

import com.google.gson.Gson;

import club.wustfly.inggua.model.bean.User;
import club.wustfly.inggua.utils.SPUtil;

public class Session {

    private static final String PREFIX = "USER_";

    private static final String USER_KEY = "USER_KEY";

    private static Session session;

    private User user;

    private Session() {

    }

    public static Session getSession() {
        if (session == null) {
            synchronized (Session.class) {
                if (session == null) {
                    session = new Session();
                }
            }
        }
        return session;
    }

    public User getUser() {
        if (user == null) {
            String userId = SPUtil.get(USER_KEY);
            if (!TextUtils.isEmpty(userId)) {
                user = getLoginRespFromDisk(userId);
            }
        }
        return user == null ? new User() : user;
    }

    public void setLoginResp(User user) {
        this.user = user;
        saveLoginResp2Disk(user);
    }

    private User getLoginRespFromDisk(String userId) {
        if (!TextUtils.isEmpty(userId)) {
            String json = SPUtil.get(userId);
            if (!TextUtils.isEmpty(json)) {
                return new Gson().fromJson(json, User.class);
            }
        }
        return null;
    }

    private void saveLoginResp2Disk(User user) {
        if (user != null && user.getId() != null) {
            String json = new Gson().toJson(user);
            SPUtil.put(PREFIX + user.getId(), json);
        }
    }

    public void saveLoginResp2Disk() {
        saveLoginResp2Disk(user);
    }

    public void login(User user) {
        if (user != null && user.getId() != null) {
            SPUtil.put(USER_KEY, PREFIX + user.getId());
            setLoginResp(user);
        }
    }

    public void logout() {
        SPUtil.put(USER_KEY, "");
        user = null;
    }

    public boolean isLogin() {
        return getUser().getId() != null;
    }
}
