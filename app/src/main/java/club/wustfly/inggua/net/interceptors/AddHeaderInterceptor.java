package club.wustfly.inggua.net.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
//                .addHeader("deviceId", Constants.DEVICE_ID)
//                .addHeader("clientId", Constants.CLIENT_ID)
//                .addHeader("androidModel", VersionUtil.getSysModel())
//                .addHeader("androidVersion", VersionUtil.getSysVersion())
//                .addHeader("clientVer", VersionUtil.getVersion(KKTApplication.getInstance()))
//                .addHeader("timestamp", System.currentTimeMillis() + "")
                .build();
        return chain.proceed(request);
    }
}
