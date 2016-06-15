package customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import carduino.lzh.car.R;


public class CircleBtnView extends RelativeLayout {
    ScaleAnimation animation1;
    ScaleAnimation animation2;

    public CircleBtnView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
//        initView();
    }

    public CircleBtnView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
//        initView();
    }

    public CircleBtnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
//        initView();
    }

    public void initView(String text) {
        if ("".equals(text) || null == text)
            return;
        try {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(0xDDDD2C00);
            paint.setStrokeWidth(1.0f);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            Bitmap bitmap = Bitmap.createBitmap(getResources().getDimensionPixelSize(R.dimen.dim350),
                    getResources().getDimensionPixelSize(R.dimen.dim350), Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            int radius = (Math.min(bitmap.getWidth(), bitmap.getHeight())) / 2;
            if (radius <= 0)
                radius = 300;
           setShader(bitmap, paint, radius, 0xffDD2C00, 0xDDDD2C00);
            canvas.drawCircle(radius, radius, radius, paint);
            canvas.save();
            canvas.restore();
            Paint paintText = new Paint();
            paintText.setAntiAlias(true);
            paintText.setFilterBitmap(true);
            paintText.setFakeBoldText(true);
            paintText.setColor(0xFFFFEB3B);
            paintText.setTextSize(getResources().getDimensionPixelSize(
                    R.dimen.dim50));
            paintText.setTextAlign(Paint.Align.CENTER);

            canvas.drawText(text + "", radius, radius + getResources().getDimensionPixelSize(
                    R.dimen.dim25),
                    paintText);
            canvas.save();
            ImageView iv = new ImageView(getContext());
            iv.setId(R.id.threeview);
            iv.setScaleType(ScaleType.FIT_CENTER);
            bitmap = ImgUtil.getInstance().toRoundBitmap(bitmap);
            iv.setImageBitmap(bitmap);
            LayoutParams params = new LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dim350),
                    getResources().getDimensionPixelSize(R.dimen.dim350));
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            iv.setLayoutParams(params);

            Paint paint1 = new Paint();
            paint1.setAntiAlias(true);
            paint1.setColor(0x26DD2C00);
            paint1.setStrokeWidth(10f);
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            Bitmap bitmap1 = Bitmap.createBitmap(getResources().getDimensionPixelSize(R.dimen.dim350),
                    getResources().getDimensionPixelSize(R.dimen.dim350), Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmap1);
            int radius1 = (Math.min(bitmap1.getWidth(), bitmap1.getHeight())) / 2;
            if (radius1 <= 0)
                radius1 = 300;
//            setShader(bitmap1, paint1, radius1, 0xddFFCCBC, 0x33FFCCBC);
            canvas1.drawCircle(radius1, radius1, radius1, paint1);
            canvas1.save();
            ImageView iv1 = new ImageView(getContext());
            iv1.setScaleType(ScaleType.FIT_CENTER);
            bitmap1 = ImgUtil.getInstance().toRoundBitmap(bitmap1);
            iv1.setImageBitmap(bitmap1);
            LayoutParams params1 = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            iv1.setLayoutParams(params1);

            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setColor(0x1ADD2C00);
            paint2.setStrokeWidth(5f);
            paint2.setStyle(Paint.Style.FILL_AND_STROKE);
            Bitmap bitmap2 = Bitmap.createBitmap(getResources().getDimensionPixelSize(R.dimen.dim350),
                    getResources().getDimensionPixelSize(R.dimen.dim350), Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmap2);
            int radius2 = (Math.min(bitmap2.getWidth(), bitmap2.getHeight())) / 2;
            if (radius2 <= 0)
                radius2 = 300;
//        Shader mShader2 = new RadialGradient(radius2, radius2, radius2,
//                new int[]{0x33FFCCBC, 0x66FFCCBC, 0xddFFCCBC, 0x66FFCCBC,
//                        0x33FFCCBC}, null, Shader.TileMode.MIRROR);
//        paint2.setShader(mShader2);
//            setShader(bitmap2, paint2, radius2, 0xddFFCCBC, 0x33FFCCBC);
            canvas2.drawCircle(radius2, radius2, radius2, paint2);
            canvas2.save();
            ImageView iv2 = new ImageView(getContext());
            iv2.setScaleType(ScaleType.FIT_CENTER);
            bitmap2 = ImgUtil.getInstance().toRoundBitmap(bitmap2);
            iv2.setImageBitmap(bitmap2);
            LayoutParams params2 = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            iv2.setLayoutParams(params2);
            addView(iv2, 0);
            addView(iv1, 1);
            addView(iv, 2);

            animation1 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            animation1.setDuration(3000);
            animation1.setRepeatCount(-1);
            animation1.setRepeatMode(ScaleAnimation.RESTART);
            iv1.setAnimation(animation1);
            animation2 = new ScaleAnimation(1.0f, 2.2f, 1.0f, 2.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            animation2.setDuration(4000);
            animation2.setRepeatCount(-1);
            animation2.setRepeatMode(ScaleAnimation.RESTART);
            iv2.setAnimation(animation2);
            animation1.setStartOffset(1000);
            animation1.startNow();
            animation2.startNow();
        } catch (Exception e) {
        }
    }

    private void setShader(Bitmap bitmap, Paint paint, int radius, int centerColor, int endColor) {
        // 创建位图渲染
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        // 创建环形渐变
        RadialGradient radialGradient = new RadialGradient(radius, radius, radius,
                centerColor, endColor, Shader.TileMode.MIRROR);
        // 创建组合渐变，由于直接按原样绘制就好，所以选择Mode.SRC_OVER
        ComposeShader composeShader = new ComposeShader(bitmapShader, radialGradient,
                PorterDuff.Mode.SRC_OVER);
        paint.setShader(composeShader);
    }



}