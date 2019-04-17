package club.wustfly.inggua.net;

import com.google.gson.Gson;

import club.wustfly.inggua.common.Constants;
import club.wustfly.inggua.net.interceptors.AddHeaderInterceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private static kktService kktService;

    static {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AddHeaderInterceptor())
                .build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        kktService = retrofit.create(kktService.class);
    }

    public static kktService getService() {
        return kktService;
    }


    public static <T> RequestBody convert(T t) {
        String json = new Gson().toJson(t);
        return RequestBody.create(MediaType.parse("application/json"), json);
    }
}
