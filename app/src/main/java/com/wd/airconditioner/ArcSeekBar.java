package com.wd.airconditioner;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/*
* 自定义继承自View的控件，弧形进度
*
* 考虑padding，考虑wrap_content
*
* */
public class ArcSeekBar extends View {

    private static final String TAG = "ArcSeekBar";

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    //*********************************** 以下为控件属性值

    /**
     * 圆弧进度的显示位置
     * */
    private int mProgressPosition = LEFT;
    /**
     * 圆弧开始的角度
     */
    private float mStartAngle = 135;
    /**
     * 起点角度和终点角度对应的夹角大小
     *
     * 正数表示顺时针进度递增，负数表示逆时针进度递增
     */
    private float mAngleSize = 270;
    /**
     * 圆的半径
     */
    private float mCircleRadius = dp2px(120);
    /**
     * 圆弧的宽度
     */
    private int mStrokeWidth = dp2px(8);
    /**
     * 进度浮球的半径
     */
    private float mBollRadius = dp2px(16);
    /**
     * 最大的进度，用于计算进度与夹角的比例
     */
    private float mMaxProgress = 500;
    /**
     * 当前进度
     */
    private float mCurrentProgress = 0;
    /**
     * 圆弧背景颜色
     */
    private int mBgColor = Color.YELLOW;
    /**
     * 进度圆弧的颜色
     */
    private int mProgressColor = Color.RED;
    /**
     * 进度浮球的颜色
     */
    private int mBollColor = Color.BLUE;


    //*********************************** 以下为计算方便而定义的属性值
    /**
     * 当前进度对应的起点角度到当前进度角度夹角的大小
     */
    private float mCurrentAngleSize = 0;
    /**
     * 动画的执行时长
     */
    private long mDuration = 3000;
    /**
     * 进度浮球X轴坐标
     */
    private float mBollX = mBollRadius / 2.0f;
    /**
     * 进度浮球Y轴坐标
     */
    private float mBollY = mBollRadius / 2.0f;

    public ArcSeekBar(Context context) {
        this(context, null);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        setFocusableInTouchMode(true);
    }

    /**
     * 设置初始化的参数
     *
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBar);
        mProgressPosition = array.getInt(R.styleable.ArcSeekBar_arc_progress_location, LEFT);
        mStartAngle = array.getFloat(R.styleable.ArcSeekBar_arc_start_angle, 135f);
        mAngleSize = array.getFloat(R.styleable.ArcSeekBar_arc_angle_size, 270f);
        mCircleRadius = array.getDimension(R.styleable.ArcSeekBar_arc_circle_radius, 120f);
        mStrokeWidth = dp2px(array.getDimension(R.styleable.ArcSeekBar_arc_stroke_width, 12f));
        mBollRadius = array.getDimension(R.styleable.ArcSeekBar_arc_boll_radius, 24f);
        mMaxProgress = array.getFloat(R.styleable.ArcSeekBar_arc_max_progress, 500f);
        mCurrentProgress = array.getFloat(R.styleable.ArcSeekBar_arc_progress, 300f);
        mBgColor = array.getColor(R.styleable.ArcSeekBar_arc_bg_color, Color.YELLOW);
        mProgressColor = array.getColor(R.styleable.ArcSeekBar_arc_progress_color, Color.RED);
        mBollColor = array.getColor(R.styleable.ArcSeekBar_arc_boll_color, Color.BLUE);
        setProgress(mCurrentProgress);
    }

    /*
    * 绘制时需要将 padding 考虑在内，padding 影响绘制内容在控件自身坐标系的位置。
    * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF();
        if(mProgressPosition == RIGHT) {
            rectF.right = getWidth() - getPaddingRight() - mStrokeWidth;
            rectF.left = rectF.right - mCircleRadius * 2;
        } else if(mProgressPosition == LEFT) {
            rectF.left = getPaddingLeft() + mStrokeWidth;
            rectF.right = rectF.left + mCircleRadius * 2;
        }
        rectF.top = getHeight() / 2.0f - mCircleRadius;
        rectF.bottom = getHeight() / 2.0f + mCircleRadius;

        //画最外层的圆弧
        drawArcBg(canvas, rectF);
        //画进度
        drawArcProgress(canvas, rectF);
        //画进度的浮球
        drawArcBoll(canvas);
    }

    /**
     * 画最开始的圆弧
     *
     * @param canvas
     * @param rectF
     */
    private void drawArcBg(Canvas canvas, RectF rectF) {
        Paint mPaint = new Paint();
        //画笔的填充样式，Paint.Style.FILL 填充内部;Paint.Style.FILL_AND_STROKE 填充内部和描边;Paint.Style.STROKE 描边
        mPaint.setStyle(Paint.Style.STROKE);
        //圆弧的宽度
        mPaint.setStrokeWidth(mStrokeWidth);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //画笔的颜色
        mPaint.setColor(mBgColor);
        //画笔的样式 Paint.Cap.Round 圆形,Cap.SQUARE 方形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //开始画圆弧
        canvas.drawArc(rectF, mStartAngle, mAngleSize, false, mPaint);
    }

    /**
     * 画进度的圆弧
     *
     * @param canvas
     * @param rectF
     */
    private void drawArcProgress(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        paint.setColor(mProgressColor);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, mStartAngle, mCurrentAngleSize, false, paint);
    }

    /**
     * 画进度的浮球
     *
     * @param canvas
     */
    private void drawArcBoll(Canvas canvas) {
        float currentAngle = mCurrentAngleSize + mStartAngle;
//        Log.d(TAG, "drawArcBoll: mCurrentAngleSize =" + mCurrentAngleSize + ", mStartAngle = " + mStartAngle +"， currentAngle = " + currentAngle);
        float xCoord =  getXCoord(currentAngle, mCircleRadius);
        float yCoord = getYCoord(currentAngle, mCircleRadius);
        mBollX = getXPos(xCoord);
        mBollY = getYPos(yCoord);

        Paint paint = new Paint();
        paint.setColor(mBollColor);
        paint.setAntiAlias(true);

        canvas.drawCircle(mBollX, mBollY, mBollRadius, paint);
    }

    /**
     * 设置最大的进度
     *
     * @param progress
     */
    public void setMaxProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("Progress value can not be less than 0 ");
        }
        mMaxProgress = progress;
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("Progress value can not be less than 0");
        }
        if (progress > mMaxProgress) {
            progress = mMaxProgress;
        }
        mCurrentProgress = progress;
        float size = mCurrentProgress / mMaxProgress;
        float oldAngleSize = mCurrentAngleSize;
        mCurrentAngleSize = (int) (mAngleSize * size);

        setAnimator(oldAngleSize, mCurrentAngleSize);
    }

    /**
     * 设置进度圆弧的颜色
     *
     * @param color
     */
    public void setProgressColor(int color) {
        if (color == 0) {
            throw new IllegalArgumentException("Color can no be 0");
        }
        mProgressColor = color;
    }

    /**
     * 设置圆弧的颜色
     *
     * @param color
     */
    public void setBgColor(int color) {
        if (color == 0) {
            throw new IllegalArgumentException("Color can no be 0");
        }
        mBgColor = color;
    }

    /**
     * 设置圆弧的宽度
     *
     * @param strokeWidth
     */
    public void setStrokeWidth(int strokeWidth) {
        if (strokeWidth < 0) {
            throw new IllegalArgumentException("strokeWidth value can not be less than 0");
        }
        mStrokeWidth = dp2px(strokeWidth);
    }

    /**
     * 设置动画的执行时长
     *
     * @param duration
     */
    public void setAnimatorDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration value can not be less than 0");
        }
        mDuration = duration;
    }

    /**
     * 设置圆弧开始的角度
     *
     * @param startAngle
     */
    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
    }

    /**
     * 设置圆弧的起始角度到终点角度的大小
     *
     * @param angleSize
     */
    public void setAngleSize(int angleSize) {
        mAngleSize = angleSize;
    }

    /**
     * dp转成px
     *
     * @param dp
     * @return
     */
    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * 设置动画
     *
     * @param start  开始位置
     * @param target 结束位置
     */
    private void setAnimator(float start, float target) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, target);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setTarget(mCurrentAngleSize);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentAngleSize = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: event.action = " + event.getAction() + ", event.y = " + event.getY());

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(x > (mBollX - mBollRadius) && x < (mBollX + mBollRadius) && y > (mBollY - mBollRadius) && y < (mBollY + mBollRadius)) {
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                setBollPosition(y);
                break;
            case MotionEvent.ACTION_UP:
                setBollPosition(y);
                int progress = getProgress(mCurrentAngleSize + mStartAngle);
                setProgress(progress);
                if(mOnProgressChangedListener != null) {
                    mOnProgressChangedListener.onProgressChanged(this, progress, true);
                }
                Log.d(TAG, "onTouchEvent_ACTION_UP: progress = " + progress);
                break;
        }

        return true;
    }

    public void setBollPosition(float y) {
        y = Math.max(getPaddingTop(), Math.min((getHeight() - getPaddingBottom()), y));
        float yCoord = getYCoord(y);
        float angle = getAngle(yCoord, mCircleRadius);
        mCurrentAngleSize = angle - mStartAngle;
        invalidate();
    }

    /**
     * 将触控点在控件坐标系的位置，转换为在圆坐标系中的位置
     *
     * @param yPos, 表示在控件坐标系中的Y轴坐标：圆点为0，向下为正，向上为负。
     *
     * @return 返回在所绘制的圆坐标系中的位置，圆点坐标为(0,0)
     * */
    private float getYCoord(float yPos) {
        return yPos - getHeight()/2;
    }

    /**
     * 根据Y轴坐标和圆的半径计算角度
     *
     * @param yCoord, 表示在所绘制的圆弧坐标系中的Y轴坐标：圆点为0，向下为正，向上为负。
     * @param radius, 圆的半径
     *
     * @return 角度：顺X轴方向(向右)为0，顺时针角度增加
     * */
    private float getAngle(float yCoord, float radius) {
        double radian = Math.asin(yCoord/radius);
        float angle = (float)Math.toDegrees(radian);
        if (mProgressPosition == LEFT){
            angle = 180 - angle;
        }
        return angle;
    }

    /**
     * 根据进度计算角度
     *
     * @param progress, 表示进度
     *
     * @return 角度：顺X轴方向(向右)为0，顺时针角度增加
     * */
    private float getAngle(float progress) {
        return (progress / mMaxProgress) * mAngleSize + mStartAngle;
    }

    /**
     * 根据角度计算进度值，进度值按四舍五入取整
     *
     * @param angle, 角度
     *
     * @return 返回角度对应的进度值，进度值按四舍五入取整
     * */
    private int getProgress(float angle) {
        float progressRatio = (angle - mStartAngle) / mAngleSize;
        return (int)(progressRatio * mMaxProgress + 0.5);
    }

    /**
     * 根据角度和圆的半径获取X轴坐标
     * 圆是顺时针画的。0度角对应于0度的几何角(手表上的3点)。
     *
     * @param angle，角度：顺X轴方向(向右)为0，顺时针角度增加
     * @param radius, 圆的半径
     *
     * @retrun 返回X轴坐标：圆点为0，向右为正，向左为负。
     */
    public float getXCoord(float angle, float radius) {
        return (float) (Math.cos(Math.toRadians(angle)) * radius);
    }

    /**
     * 根据角度和圆的半径获取Y轴坐标
     * 圆是顺时针画的。0度角对应于0度的几何角(手表上的3点)。
     *
     * @param angle，角度：顺X轴方向(向右)为0，顺时针角度增加
     * @param radius, 圆的半径
     *
     * @retrun 返回Y轴坐标：圆点为0，向下为正，向上为负。
     */
    public float getYCoord(float angle, float radius) {
        return (float) (Math.sin(Math.toRadians(angle)) * radius);
    }

    /**
     * 将触控点在圆弧坐标系中的位置，转换为在控件坐标系的位置
     *
     * @param xCoord, 表示在所绘制的圆弧坐标系中的位置，圆点坐标为(0,0)
     *
     * @return 返回在控件坐标系中的位置，控件左上角坐标为(0,0)
     * */
    private float getXPos(float xCoord) {
        if(xCoord < 0) {  //在圆的左半边
            return getPaddingLeft() + mStrokeWidth + mCircleRadius + xCoord;
        } else {  //在圆的右半边
            return getWidth() - mStrokeWidth - getPaddingRight() - mCircleRadius + xCoord;
        }
    }

    /**
     * 将触控点在圆弧坐标系中的位置，转换为在控件坐标系的位置
     *
     * @param yCoord, 表示在所绘制的圆弧坐标系中的Y轴坐标：圆点为0，向下为正，向上为负。
     *
     * @return 返回在控件坐标系中的位置，控件左上角坐标为(0,0)
     * */
    private float getYPos(float yCoord) {
        return getHeight() / 2 + yCoord;
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(ArcSeekBar arcSeekBar, int progress, boolean isFinalProgress);
    }

    private ArcSeekBar.OnProgressChangedListener mOnProgressChangedListener;

    public void setOnProgressChangedListener(ArcSeekBar.OnProgressChangedListener l) {
        mOnProgressChangedListener = l;
    }
}
