package customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import interfaces.PathChangedFace;


/**
 * 实现画板功能的View
 */
public class HuaBanView extends View {
    private PathChangedFace pathChangedFace;


    /**
     * 缓冲位图
     */
    private Bitmap cacheBitmap;
    /**
     * 缓冲位图的画板
     */
    private Canvas cacheCanvas;
    /**
     * 缓冲画笔
     */
    private Paint paint;
    /**
     * 实际画笔
     */
    private Paint BitmapPaint;
    /**
     * 保存绘制曲线路径
     */
    private Path path;
    /**
     * 画布高
     */
    private int height;
    /**
     * 画布宽
     */
    private int width;
    /**
     * 保存上一次绘制的终点横坐标
     */
    private float pX;
    /**
     * 保存上一次绘制的终点纵坐标
     */
    private float pY;

    /**
     * 画笔初始颜色
     */
    private int paintColor = Color.RED;
    /**
     * 线状状态
     */
    private static Paint.Style paintStyle = Paint.Style.STROKE;
    /**
     * 画笔粗细
     */
    private static int paintWidth = 10;

    private Canvas canvas = null;


    /**
     * 获取View实际宽高的方法
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        height = h;
        width = w;
        init();
    }

    private void init() {
        cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        BitmapPaint = new Paint();
        updatePaint();
    }

    public void setPathChangedFace(PathChangedFace pathChangedFace) {
        this.pathChangedFace = pathChangedFace;
    }

    private void updatePaint() {
        paint.setColor(paintColor);
        paint.setStyle(paintStyle);
        paint.setStrokeWidth(paintWidth);
    }

    public HuaBanView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HuaBanView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            /*
            清空画布
			 */

                clearScreen();
                path.moveTo(event.getX(), event.getY());
                pX = event.getX();
                pY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(pX, pY, event.getX(), event.getY());
                pathChangedFace.paintChanged(event.getX() - pX, event.getY() - pY);

                Log.i("123", "px" + pX + "---py" + pY + "\n" + event.getX() + "----" + event.getY());
                pX = event.getX();
                pY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);

                path.reset();
                break;
        }
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        BitmapPaint = new Paint();
        canvas.drawBitmap(cacheBitmap, 0, 0, BitmapPaint);
        canvas.drawPath(path, paint);

    }

    /**
     * 更新画笔颜色
     */
    public void setColor(int color) {
        paintColor = color;
        updatePaint();
    }

    /**
     * 设置画笔粗细
     */
    public void setPaintWidth(int width) {
        paintWidth = width;
        updatePaint();
    }

    public static final int PEN = 1;
    public static final int PAIL = 2;

    /**
     * 设置画笔样式
     */
    public void setStyle(int style) {
        switch (style) {
            case PEN:
                paintStyle = Paint.Style.STROKE;
                break;
            case PAIL:
                paintStyle = Paint.Style.FILL;
                break;
        }
        updatePaint();
    }

    /**
     * 清空画布
     */
    public void clearScreen() {


            if (canvas != null) {
                Paint backPaint = new Paint();
                backPaint.setColor(Color.BLACK);

              canvas.drawRect(new Rect(0, 0, width, height), backPaint);
               cacheCanvas.drawRect(new Rect(0, 0, width, height), backPaint);
            }
            invalidate();


    }
}
