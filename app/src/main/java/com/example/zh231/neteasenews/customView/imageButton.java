package com.example.zh231.neteasenews.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zh231.neteasenews.R;


public class imageButton extends View {//(*^_^*)

    private String text;
    private Bitmap bitmap;
    private Paint paint;
    private Rect rect;

    public imageButton(Context context,int resource,String text) {
        super(context);
        paint=new Paint();
        if (resource!=0){
            bitmap= BitmapFactory.decodeResource(getResources(),resource);
        }
        if (text!=null){
            this.text=text;
        }
    }

    public imageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.imageButton);
        this.text = array.getString(R.styleable.imageButton_text);
        bitmap= BitmapFactory.decodeResource(getResources(),array.getResourceId(R.styleable.imageButton_imageId,R.mipmap.ic_launcher));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//空实现
        paint.setAntiAlias(true);

        rect=new Rect();
        rect.set(100,0,0,100);
        if (bitmap!=null){
            canvas.drawBitmap(bitmap,null,rect,paint);
        }
        if (text!=null){
            paint.setTextSize(24);
            paint.setColor(Color.RED);
            canvas.drawText(text,rect.centerX(),rect.centerY(),paint);
        }

    }

    public void setText(String text){
        paint.setTextSize(24);
        this.text=text;
        invalidate();
    }


}
