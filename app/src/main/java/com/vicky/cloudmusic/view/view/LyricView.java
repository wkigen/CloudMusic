package com.vicky.cloudmusic.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.callback.ITouchCallback;
import com.vicky.cloudmusic.lyric.Lyric;
import com.vicky.cloudmusic.lyric.LyricRow;

/**
 * Created by vicky on 2017/9/5.
 */
public class LyricView extends View {

    private static final String TAG = Lyric.class.getSimpleName();
    private static final long Disappear_Time = 2000;
    //参数
    private int g_LycFontSize = 45;
    private int g_TimeFontSize = 30;
    private int g_LycPadding = 80;
    private int g_HighLightColor = 0xFFFFFFFF;
    private int g_NormaltColor = 0xFF888888;
    private int g_LineColor = 0xFFb2b2b2;
    private int g_TimeColor = 0xFF888888;
    private String g_HintLyric = "暂无歌词";


    private float lastX,lastY;
    private volatile Lyric lyric;
    private int playRow = 0;
    private boolean isManualSeek = false;  //是否手动滚动
    private ISeekCallback iSeekCallback;
    private ITouchCallback iTouchCallback;

    private Handler handler = new Handler();

    private Rect triangleRect;
    private Path trianglePath;

    private Paint highlightPaint;
    private Paint normalPaint;
    private Paint linePaint;
    private Paint timePaint;

    public LyricView(Context context, AttributeSet attr) {
        super(context, attr);

        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.LyricView);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = typedArray.getIndex(i);
            switch (itemId) {
                case R.styleable.LyricView_LycFontSize:
                    g_LycFontSize = (int)typedArray.getDimension(itemId,g_LycFontSize);
                    break;
                case R.styleable.LyricView_TimeFontSize:
                    g_TimeFontSize = (int)typedArray.getDimension(itemId,g_TimeFontSize);
                    break;
                case R.styleable.LyricView_LycPadding:
                    g_LycPadding = (int)typedArray.getDimension(itemId,g_LycPadding);
                    break;
                case R.styleable.LyricView_HighLightColor:
                    g_HighLightColor = typedArray.getColor(itemId,g_HighLightColor);
                    break;
                case R.styleable.LyricView_NormaltColor:
                    g_NormaltColor = typedArray.getColor(itemId,g_NormaltColor);
                    break;
                case R.styleable.LyricView_LineColor:
                    g_LineColor = typedArray.getColor(itemId,g_LineColor);
                    break;
                case R.styleable.LyricView_TimeColor:
                    g_TimeColor = typedArray.getColor(itemId,g_TimeColor);
                    break;
                case R.styleable.LyricView_HintLyric:
                    String hint = typedArray.getString(itemId);
                    if (!TextUtils.isEmpty(hint))
                        g_HintLyric = hint;
                    break;
            }
        }

        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setTextSize(g_LycFontSize);
        highlightPaint.setColor(g_HighLightColor);
        highlightPaint.setTextAlign(Paint.Align.CENTER);

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPaint.setTextSize(g_LycFontSize);
        normalPaint.setColor(g_NormaltColor);
        normalPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint();
        linePaint.setColor(g_LineColor);

        timePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timePaint.setColor(g_TimeColor);
        timePaint.setTextSize(g_TimeFontSize);
        timePaint.setTextAlign(Paint.Align.LEFT);

        trianglePath = new Path();
        triangleRect = new Rect();
    }

    public void setLyric(Lyric lyric){
        this.lyric = lyric;
        playRow = 0;
        invalidate();
    }

    public void setSeekCallback(ISeekCallback callback){
        this.iSeekCallback = callback;
    }

    public void setTouchCallback(ITouchCallback callback){
        this.iTouchCallback = callback;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int highLightY  = getHeight() / 2 - g_LycFontSize;
        trianglePath.moveTo(g_LycFontSize, highLightY - g_LycFontSize);
        trianglePath.lineTo(g_LycFontSize * 2, highLightY - g_LycFontSize / 2);
        trianglePath.lineTo(g_LycFontSize, highLightY);
        trianglePath.close();

        triangleRect.set(g_LycFontSize,highLightY - g_LycFontSize,highLightY - g_LycFontSize / 2,highLightY);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();

        int centreX = width / 2;
        int centreY = height / 2;

        //没有歌词
        if (lyric == null || lyric.getLyricRowList() == null ||
                lyric.getLyricRowList().size()==0){
            canvas.drawText(g_HintLyric,centreX, centreY - g_LycFontSize, highlightPaint);
            return;
        }

        //画出正在播放的歌词
        String hightLightlyc = lyric.getLyricRowList().get(playRow).content;
        int highLightY  = centreY - g_LycFontSize;
        //canvas.drawText(hightLightlyc, centreX, highLightY, highlightPaint);
        int highLightRows = drawText(canvas,highlightPaint,hightLightlyc,centreX,highLightY,g_LycFontSize,true);

        //拖动歌词的时候
        if (isManualSeek){
            //画三角
            canvas.drawPath(trianglePath, linePaint);
            //画线
            canvas.drawLine(g_LycFontSize * 3, highLightY - g_LycFontSize / 2, width - g_LycFontSize * 3, highLightY - g_LycFontSize / 2, highlightPaint);
            //画时间
            String[] time = lyric.getLyricRowList().get(playRow).strTime.split(":");
            if (time.length >= 2)
                canvas.drawText(time[0] + ":" + time[1], width - g_LycFontSize * 2.5f, highLightY- g_TimeFontSize / 2, timePaint);
        }

        int offRow = g_LycPadding + g_LycFontSize; //歌词总间距

        //上面的歌词
        int drawRow = playRow - 1;  //要画的歌词行数
        int drawY = highLightY - offRow; //要画的Y轴
        while(drawRow >= 0 && drawY - g_LycFontSize> 0){
            String text = lyric.getLyricRowList().get(drawRow).content;
            //canvas.drawText(text, centreX, drawY, normalPaint);
            int tempRows = drawText(canvas,normalPaint,text,centreX,drawY,g_LycFontSize,false);
            drawRow--;
            drawY = drawY - offRow - tempRows * g_LycFontSize;
        }

        //下面的歌词
        drawRow = playRow + 1;  //要画的歌词行数
        drawY = highLightY + offRow + highLightRows * g_LycFontSize; //要画的Y轴 中心+行距+高亮歌词的换行占用的距离
        while (drawRow < lyric.getLyricRowList().size()&&
                drawY + g_LycFontSize< height){
            String text = lyric.getLyricRowList().get(drawRow).content;
            //canvas.drawText(text, centreX, drawY, normalPaint);
            int tempRows = drawText(canvas,normalPaint,text,centreX,drawY,g_LycFontSize,true);
            drawRow ++;
            drawY = drawY + offRow + tempRows * g_LycFontSize;
        }
    }

    //返回值 换行数
    private int drawText(Canvas canvas,Paint paint,String text,int x,int y,int fontSize,boolean downward){
        float maxlen = paint.measureText(text);
        float maxWidth = getWidth() - g_TimeFontSize * 7;
        int count = (int)(maxlen / maxWidth);  //行数
        if (count > 0){
            float modCount = maxlen % maxWidth;//多余的 ？不足一行？
            //全字符串的比例
            float allRate = count;
            if (modCount >0)
                allRate = count  + modCount / maxWidth;
            //算出一行多少个字
            int rowCount = (int)(text.length() / allRate);
            //高亮上面的歌词还是下面的歌词？
            //算出第一行的Y轴
            int firstY = y;
            if (!downward)
                firstY = y - count * fontSize;
            for (int ii = 0 ; ii < count +1; ii++){
                int realY = firstY + ii * fontSize;
                String realText ="";
                if (ii == count){
                    realText = text.substring(ii * rowCount,text.length());
                }else {
                    realText = text.substring(ii * rowCount,ii * rowCount + rowCount);
                }
                canvas.drawText(realText,x,realY,paint);
            }
        }else {
            canvas.drawText(text,x,y,paint);
            return 0;
        }
        return count;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (lyric == null || lyric.getLyricRowList().size() == 0)
            return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                manualSeek(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (isManualSeek && triangleRect.contains((int)event.getX(),(int)event.getY()) &&iSeekCallback != null){
                    if (lyric != null &&playRow >0 && playRow < lyric.getLyricRowList().size())
                        iSeekCallback.callback(lyric.getLyricRowList().get(playRow).time);

                    isManualSeek = false;
                }else {
                    if (!isManualSeek && iTouchCallback != null)
                        iTouchCallback.onTouchOnce();

                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,Disappear_Time);
                }
                break;
        }
        return true;
    }

    private void manualSeek(float currX,float currY){
        float offY = currY - lastY;
        if (Math.abs(offY) < g_LycPadding)
            return;
        isManualSeek = true;
        int offRow = Math.abs((int)offY / g_LycFontSize);
        if (offY < 0){      //反向
            playRow += offRow;
        }else {
            playRow -= offRow;
        }
        playRow = Math.max(0,playRow);
        playRow = Math.min(playRow, lyric.getLyricRowList().size() - 1);
        if (Math.abs(offRow) > 0){
            lastY = currY;
            invalidate();
        }
    }

    public void seekRow(int position){
        if (lyric == null || isManualSeek ||
                position < 0 || position >= lyric.getLyricRowList().size())
            return;

        playRow = position;
        invalidate();
    }

    public void seekTime(long time){
        if (lyric == null || isManualSeek || time < 0)
            return;

        for (int ii = 0 ; ii < lyric.getLyricRowList().size();ii++){

            LyricRow current = lyric.getLyricRowList().get(ii);
            LyricRow next = ii + 1 == lyric.getLyricRowList().size() ? null : lyric.getLyricRowList().get(ii + 1);

            if ((time >= current.time && next != null && time < next.time)
                    || (time > current.time && next == null)){
                seekRow(ii);
                return;
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isManualSeek = false;
            invalidate();
        }
    };

    public interface ISeekCallback{
        void callback(long time);
    }
}
