package com.example.zh231.neteasenews.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class typeView extends View {

    final String tag="typeView";
    String title[];
    Paint paint;
    Rect rect;
    Context context;

    public typeView(Context context,final String title[]) {
        this(context,null,title);
    }

    public typeView(Context context, AttributeSet attrs,final String title[]) {
        super(context, attrs);
        this.title=title;
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(40);
        rect=new Rect();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minW=getSuggestedMinimumWidth();
        int minH=getSuggestedMinimumHeight();

        //Log.d(tag,"宽:"+widthMeasureSpec);
        //Log.d(tag,"高:"+heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set(0,0,100,70);



        canvas.drawRect(rect,paint);

        paint.setColor(Color.BLACK);

        int spac = 30 ;
        for (String temp:title){
            canvas.drawText(temp,spac,40,paint);
            spac=spac+90;
        }
        paint.setColor(Color.WHITE);
    }
}
