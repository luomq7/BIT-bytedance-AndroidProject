package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;

    private int degreesColor;

    private int hoursValuesColor;

    private int numbersColor;

    private boolean mShowAnalog = true;
    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;

        numbersColor = Color.WHITE;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getHeight() > getWidth() ? getWidth() : getHeight();

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawDegrees(canvas);
            drawHoursValues(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }

    }


    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    /**
     * @param canvas
     */
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", hour),
                String.format(Locale.getDefault(), "%02d", minute),
                String.format(Locale.getDefault(), "%02d", second),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     * 改动
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        // Default Color:
        // - hoursValuesColor

        Paint textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(hoursValuesColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top =fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int r = mCenterX - (int) (mWidth * 0.1f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {
            int baseX = (int)(mCenterX + r * Math.sin(Math.toRadians(i)));
            int baseY = (int) (mCenterY - r * Math.cos(Math.toRadians(i))-top/2-bottom/2);

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0){
                continue;
            }
            else {
                textPaint.setAlpha(FULL_ALPHA);
                String text;
                int m = i/30;
                if(m==0){
                    text = "12";
                }
                else if(m<10){
                    text = "0" + m;
                }
                else{
                    text = ""+ (i/30);
                }
                canvas.drawText(text,baseX,baseY,textPaint);
            }

        }


    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     * 改动
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor

        Paint hourPaint = new Paint();
        hourPaint.setColor(hoursNeedleColor);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeWidth(12);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);

        Paint minutePaint = new Paint();
        minutePaint.setColor(minutesNeedleColor);
        minutePaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeWidth(8);
        minutePaint.setStrokeCap(Paint.Cap.ROUND);

        Paint secondPaint = new Paint();
        secondPaint.setColor(secondsNeedleColor);
        secondPaint.setStyle(Paint.Style.FILL);
        secondPaint.setStrokeWidth(6);
        secondPaint.setStrokeCap(Paint.Cap.ROUND);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int rHour = mCenterX - (int) (mWidth * 0.3f);
        int rMinute = mCenterX - (int)(mWidth * 0.25f);
        int rsecond = mCenterX - (int)(mWidth * 0.18f);


        float hourX = (int)(mCenterX + rHour*Math.sin(Math.toRadians((hour + (float)minute / 60) * 360 / 12)));
        float hourY = (int)(mCenterY - rHour*Math.cos(Math.toRadians((hour + (float)minute / 60) * 360 / 12)));
        float minuteX = (int)(mCenterX + rMinute*Math.sin(Math.toRadians((minute + (float)second / 60) * 360 / 60)));
        float minuteY = (int)(mCenterY - rMinute*Math.cos(Math.toRadians((minute + (float)second / 60) * 360 / 60)));
        float secondX = (int)(mCenterX + rsecond*Math.sin(Math.toRadians(second * 360 / 60)));
        float secondY = (int)(mCenterY - rsecond*Math.cos(Math.toRadians(second * 360 / 60)));

        canvas.drawLine(mCenterX,mCenterY,hourX,hourY,hourPaint);
        canvas.drawLine(mCenterX,mCenterY,minuteX,minuteY,minutePaint);
        canvas.drawLine(mCenterX,mCenterY,secondX,secondY,secondPaint);
    }

    /**
     * Draw Center Dot
     * 改动
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        // Default Color:
        // - centerInnerColor
        // - centerOuterColor

        Paint ciclePaint = new Paint();
        ciclePaint.setColor(centerInnerColor);
        ciclePaint.setStyle(Paint.Style.FILL);
        ciclePaint.setStrokeWidth(10f);
        canvas.drawCircle(mCenterX,mCenterY,10,ciclePaint);

        ciclePaint.setColor(centerOuterColor);
        ciclePaint.setStyle(Paint.Style.STROKE);
        ciclePaint.setStrokeWidth(10f);
        canvas.drawCircle(mCenterX,mCenterY,10,ciclePaint);

    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
    }

    public boolean isShowAnalog() {
        return mShowAnalog;
    }

}