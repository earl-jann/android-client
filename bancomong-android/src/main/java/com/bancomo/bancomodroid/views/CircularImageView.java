/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bancomo.bancomodroid.R;

/**
 * @author fomenkoo
 */
public class CircularImageView extends ImageView {
    private int borderWidth;
    private int canvasSize;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;

    public CircularImageView(final Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);

        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);

        // load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable
                .CircularImageView);

        if (attributes.getBoolean(R.styleable.CircularImageView_border, true)) {
            int defaultBorderSize = (int) (4 * getContext().getResources().getDisplayMetrics()
                    .density + 0.5f);
            setBorderWidth(attributes.getDimensionPixelOffset(R.styleable
                    .CircularImageView_border_width, defaultBorderSize));
            setBorderColor(attributes.getColor(R.styleable.CircularImageView_border_color, Color
                    .WHITE));
        }

        if (attributes.getBoolean(R.styleable.CircularImageView_shadow, false))
            addShadow();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        this.requestLayout();
        this.invalidate();
    }

    public void setBorderColor(int borderColor) {
        if (paintBorder != null)
            paintBorder.setColor(borderColor);
        this.invalidate();
    }

    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // load the bitmap
        image = drawableToBitmap(getDrawable());

        // init shader
        if (image != null) {

            canvasSize = canvas.getWidth();
            if (canvas.getHeight() < canvasSize)
                canvasSize = canvas.getHeight();

            BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize,
                    canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);

            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            int circleCenter = (canvasSize - (borderWidth * 2)) / 2;
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, (
                    (canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f, paintBorder);
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, (
                    (canvasSize - (borderWidth * 2)) / 2) - 4.0f, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // The parent has not imposed any constraint on the child.
            result = canvasSize;
        }
        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = canvasSize;
        }

        return (result + 2);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable.getIntrinsicWidth() > 0 && drawable
                .getIntrinsicHeight() > 0) {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
        }
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, bitmap.getHeight() / 5, bitmap
                .getHeight() / 5, true);
        Canvas canvas = new Canvas(bitmapScaled);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapScaled;
    }
}