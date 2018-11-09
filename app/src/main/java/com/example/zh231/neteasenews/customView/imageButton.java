package com.example.zh231.neteasenews.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;


public class imageButton extends Button {//(*^_^*)

    private String text;
    private Bitmap bitmap;
    private Paint paint;

    public imageButton(Context context) {
        super(context);
        paint=new Paint();
    }

    public imageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = (this.getMeasuredWidth() - bitmap.getWidth())/2;
        int y = 0;
        if (bitmap!=null){
            canvas.drawBitmap(bitmap,x,y,null);
        }
        if (text!=null||!text.isEmpty()){
            canvas.drawText(text,x,y,paint);
        }

    }

    public void setImage(int resource){
        bitmap= BitmapFactory.decodeResource(getResources(),resource);
        invalidate();
    }

    public void setText(String text){
        this.text=text;
        invalidate();
    }
}
