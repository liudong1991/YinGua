package club.wustfly.inggua.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LzViewPager extends ViewPager {
    public LzViewPager(@NonNull Context context) {
        super(context);
    }

    public LzViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            // 按住Banner的时候，停止自动轮播
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_DOWN:
                if (listener != null)
                    listener.pause();
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null)
                    listener.start();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private onLzEventListener listener;

    public void setListener(onLzEventListener listener) {
        this.listener = listener;
    }

    public interface onLzEventListener {
        void pause();

        void start();
    }
}
