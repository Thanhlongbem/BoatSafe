package vnu.uet.boatsafe.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import vnu.uet.boatsafe.R;

public class WaveBar extends View {
    private static final int[] waveArrayFirst = new int[]{14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final int[] waveArrayTwo = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 10, 9, 8, 7, 6, 5, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final int[] waveArrayThree = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 15, 14, 13, 12, 11, 10, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 6, 7, 8, 9, 10, 11, 10, 9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 10, 9, 8, 7, 6};
    private static final int[] waveArrayFour = new int[]{20, 19, 18, 17, 16, 15, 14, 12, 11, 10, 9, 8, 9, 10, 11, 12, 13, 14, 15, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 11, 10, 9, 8, 7, 6, 5, 4, 5, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
    private Paint wavePain;
    private RectF[] waveRecF;
    private int barHeigh;
    private int waveWidth;
    private float waveMargin;
    private float waveSpeed;
    private int waveHeight;
    private float waveCorner;
    private int topBarPos;
    private int stepHeight;
    private float waveMarginRadio;
    private float waveHeightRadio;

    private boolean isRun;

    public WaveBar(Context context) {
        this(context, null, 0);
    }

    public WaveBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WaveBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        this.wavePain = new Paint();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.WaveBarStyleable);

        try {
            int colorBar = obtainStyledAttributes.getColor(R.styleable.WaveBarStyleable_colorBar, ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null));
            this.wavePain.setColor(colorBar);
            int radius = obtainStyledAttributes.getColor(R.styleable.WaveBarStyleable_radius,0);
            this.waveCorner = radius;
            waveMarginRadio = obtainStyledAttributes.getFloat(R.styleable.WaveBarStyleable_waveMarginRadio,9.0f);
            waveHeightRadio = obtainStyledAttributes.getFloat(R.styleable.WaveBarStyleable_waveMarginRadio,7.0f);
            this.wavePain.setStyle(Style.FILL);
            this.wavePain.setAntiAlias(true);
            this.waveRecF = new RectF[4];
            for (int i2 = 0; i2 < 4; i2++) {
                this.waveRecF[i2] = new RectF();
            }
            this.waveWidth = 0;
        }finally {
            obtainStyledAttributes.recycle();
        }

    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        super.onDraw(canvas);
        if (this.waveWidth <= 0) {
            this.waveWidth = getWidth();
            this.barHeigh = getHeight();
            this.waveMargin = ((float) this.waveWidth) / waveMarginRadio;
            float maxWaveHeight = this.waveMargin * waveHeightRadio;
            this.waveSpeed = maxWaveHeight / 20.0f;
            this.stepHeight = (int) ((((float) this.barHeigh) - maxWaveHeight) / 2.0f);
        }
        if (this.isRun) {
            this.waveHeight++;
            if (this.waveHeight > 69) {
                this.waveHeight = 0;
            }
            for (int i2 = 0; i2 < 4; i2++) {
                float stepSpeed = this.waveSpeed;
                i = this.waveHeight;
                switch (i2) {
                    case 0:
                        i = waveArrayFirst[i];
                        break;
                    case 1:
                        i = waveArrayTwo[i];
                        break;
                    case 2:
                        i = waveArrayThree[i];
                        break;
                    default:
                        i = waveArrayFour[i];
                        break;
                }
                this.topBarPos = (int) (((float) i) * stepSpeed);
                this.waveRecF[i2].set(this.waveMargin + (((float) (i2 * 2)) * this.waveMargin), (float) ((this.barHeigh - this.topBarPos) - this.stepHeight), (this.waveMargin * 2.0f) + (((float) (i2 * 2)) * this.waveMargin), (float) (this.barHeigh - this.stepHeight));
                canvas.drawRoundRect(this.waveRecF[i2], this.waveCorner, this.waveCorner, this.wavePain);
            }
            postInvalidateDelayed(10);
            return;
        }
        while (i < 4) {
            this.waveRecF[i].set(this.waveMargin + (((float) (i * 2)) * this.waveMargin), (((float) this.barHeigh) - (this.waveMargin * 2.0f)) - ((float) this.stepHeight), (this.waveMargin * 2.0f) + (((float) (i * 2)) * this.waveMargin), (float) (this.barHeigh - this.stepHeight));
            canvas.drawRoundRect(this.waveRecF[i], this.waveCorner, this.waveCorner, this.wavePain);
            i++;
        }
    }

    public void setPlaying(boolean z) {
        if (this.isRun != z) {
            this.isRun = z;
            invalidate();
        }
    }
}