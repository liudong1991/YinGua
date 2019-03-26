package club.wustfly.yingua.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DotLineView extends View {

    Paint mPaint1;
    Paint mPaint2;

    public DotLineView(Context context) {
        super(context);
        init();
    }

    public DotLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerY = getHeight() / 2;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint1.setStrokeWidth(2);
        mPaint1.setColor(Color.parseColor("#333333"));
        mPaint1.setStyle(Paint.Style.STROKE);
        canvas.drawLine(0, centerY - 2, getWidth(), centerY - 2, mPaint1);
        mPaint1.setStrokeWidth(2);
        mPaint1.setColor(Color.parseColor("#333333"));
        mPaint2.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        canvas.drawLine(0, centerY + 2, getWidth(), centerY + 2, mPaint2);
    }
}
