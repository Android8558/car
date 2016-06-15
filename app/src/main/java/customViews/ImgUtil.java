package customViews;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;


public class ImgUtil {
    private static ImgUtil mImgUtil;

    public static ImgUtil getInstance() {
        if (null == mImgUtil) {
            mImgUtil = new ImgUtil();
        }
        return mImgUtil;
    }


    /* 设置圆角的图片*/
    public Bitmap toRoundCornerBitmap(Bitmap bitmap, int pixels) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            int color = 0XFFFFFFFF;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /* 将图片转换为圆形的*/
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        try {
            bitmap = cutSquareBitmap(bitmap);
            return toRoundCornerBitmap(bitmap, bitmap.getWidth() / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public Bitmap cutSquareBitmap(Bitmap bitmap) {
        try {
            Bitmap result;
            int w = bitmap.getWidth();// 输入长方形宽
            int h = bitmap.getHeight();// 输入长方形高
            int nw;// 输出正方形宽
            if (w > h) { // 宽大于高
                nw = h;
                result = Bitmap.createBitmap(bitmap, (w - nw) / 2, 0, nw, nw);
            } else {// 高大于宽
                nw = w;
                result = Bitmap.createBitmap(bitmap, 0, (h - nw) / 2, nw, nw);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}