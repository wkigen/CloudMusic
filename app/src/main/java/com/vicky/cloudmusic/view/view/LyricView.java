package com.vicky.cloudmusic.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.vicky.android.baselib.utils.DensityUtils;
import com.vicky.cloudmusic.lyric.Lyric;
import com.vicky.cloudmusic.lyric.LyricRow;

/**
 * Created by vicky on 2017/9/5.
 */
public class LyricView extends View {

    public static final String TAG = Lyric.class.getSimpleName();

    //参数
    private int g_LycFontSize = 45;
    private int g_TimeFontSize = 30;
    private int g_lycPadding = 80;
    private int g_LinePadding = 80;
    private int g_HighLightColor = 0xFFFFFFFF;
    private int g_NormaltColor = 0xFF888888;
    private int g_LineColor = 0xFFb2b2b2;
    private int g_TimeColor = 0xFF888888;
    private String g_NoLyric = "暂无歌词";


    private float lastX,lastY;
    private volatile Lyric lyric;
    private int playRow = 0;
    private boolean isManualSeek = false;  //是否手动滚动
    private ISeekCallback iSeekCallback;
    private ITouchOnceCallback iTouchOnceCallback;

    private Paint mPaint;

    public LyricView(Context context, AttributeSet attr) {
        super(context, attr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(g_LycFontSize);
    }

    public void setLyric(Lyric lyric){
        this.lyric = lyric;
        playRow = 0;
        invalidate();
    }

    public void setSeekCallback(ISeekCallback callback){
        this.iSeekCallback = callback;
    }

    public void setTouchOnceCallback(ITouchOnceCallback callback){
        this.iTouchOnceCallback = callback;
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
            mPaint.setColor(g_HighLightColor);
            mPaint.setTextSize(g_LycFontSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(g_NoLyric,centreX, centreY - g_LycFontSize, mPaint);
            return;
        }

        //画出正在播放的歌词
        String hightLightlyc = lyric.getLyricRowList().get(playRow).content;
        int highLightY  = centreY - g_LycFontSize;
        mPaint.setColor(g_HighLightColor);
        mPaint.setTextSize(g_LycFontSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(hightLightlyc, centreX, highLightY, mPaint);

        //拖动歌词的时候
        if (isManualSeek){
            mPaint.setColor(g_LineColor);
            canvas.drawLine(g_LinePadding, highLightY - g_LycFontSize / 2, width - g_LinePadding, highLightY - g_LycFontSize / 2, mPaint);

            mPaint.setColor(g_TimeColor);
            mPaint.setTextSize(g_TimeFontSize);
            mPaint.setTextAlign(Paint.Align.LEFT);
            String[] time = lyric.getLyricRowList().get(playRow).strTime.split(":");
            if (time.length >= 2)
                canvas.drawText(time[0] + ":" + time[1], width - g_LinePadding, highLightY- g_TimeFontSize / 2, mPaint);

            mPaint.setTextSize(g_LycFontSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }

        int offRow = g_lycPadding + g_LycFontSize; //歌词总间距

        //上面的歌词
        mPaint.setColor(g_NormaltColor);
        mPaint.setTextSize(g_LycFontSize);
        mPaint.setTextAlign(Paint.Align.CENTER);

        int drawRow = playRow - 1;  //要画的歌词行数
        int drawY = highLightY - offRow; //要画的Y轴
        while(drawRow >= 0 && drawY - g_LycFontSize> 0){
            String text = lyric.getLyricRowList().get(drawRow).content;
            canvas.drawText(text, centreX, drawY, mPaint);
            drawRow--;
            drawY -= offRow;
        }

        //下面的歌词
        drawRow = playRow + 1;  //要画的歌词行数
        drawY = highLightY + offRow; //要画的Y轴
        while (drawRow < lyric.getLyricRowList().size()&&
                drawY + g_LycFontSize< height){
            String text = lyric.getLyricRowList().get(drawRow).content;
            canvas.drawText(text, centreX, drawY, mPaint);
            drawRow ++;
            drawY += offRow;
        }
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
                if (isManualSeek && iSeekCallback != null){
                    if (lyric != null &&playRow >0 && playRow < lyric.getLyricRowList().size())
                        iSeekCallback.callback(lyric.getLyricRowList().get(playRow).time);
                }else {
                    if (iTouchOnceCallback != null)
                        iTouchOnceCallback.onTouchOnce();
                }
                isManualSeek = false;
                break;
        }
        return true;
    }

    private void manualSeek(float currX,float currY){
        float offY = currY - lastY;
        if (Math.abs(offY) < g_lycPadding)
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

    public interface ISeekCallback{
        void callback(long time);
    }

    public interface ITouchOnceCallback{
        void onTouchOnce();
    }
}
