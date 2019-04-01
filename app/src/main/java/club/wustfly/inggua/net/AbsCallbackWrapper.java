package club.wustfly.inggua.net;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

import club.wustfly.inggua.YinGuaApplication;
import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.event.RequestFinishEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbsCallbackWrapper<T extends RespDto> implements Callback<T> {

    Callback<? extends RespDto> callback = new Callback<T>() {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            T body = response.body();
            if (body == null) return;

            if ("1".equals(body.getCode()) || "OK".equalsIgnoreCase(body.getCode())) {
                onSuccess(body);
            } else {
                onFail(body.getMsg());
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Toast.makeText(YinGuaApplication.getInstance(), "请求失败", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    public void onResponse(Call call, Response response) {
        EventBus.getDefault().post(new RequestFinishEvent());
        callback.onResponse(call, response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        EventBus.getDefault().post(new RequestFinishEvent());
        callback.onFailure(call, t);
    }

    protected void onSuccess(T t) {
        ParameterizedType ptype = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) ptype.getActualTypeArguments()[0];
        try {
            EventBus.getDefault().post(t == null ? clazz.newInstance() : t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    protected void onFail(CharSequence errorMsg) {
        Toast.makeText(YinGuaApplication.getInstance(), errorMsg == null ? "请求失败" : errorMsg, Toast.LENGTH_SHORT).show();
    }
}
