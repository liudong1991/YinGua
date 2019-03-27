package club.wustfly.yingua.net;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import club.wustfly.yingua.YinGuaApplication;
import club.wustfly.yingua.model.event.RequestFinishEvent;
import club.wustfly.yingua.model.resp.RespDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackWrapper<T extends RespDto> implements Callback<T> {

    Callback<? extends RespDto> callback = new Callback<T>() {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            T body = response.body();
            if (body == null) return;

            if (body.getCode() == 1) {
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

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String errorMsg);
}
