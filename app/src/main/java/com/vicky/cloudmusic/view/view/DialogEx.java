package com.vicky.cloudmusic.view.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.TextView;

import com.vicky.cloudmusic.R;

/**
 * Created by vicky on 2017/9/5.
 */
public class DialogEx {

    private OnDialogButtonCallback buttonCallback;
    private Context context;
    private Dialog dialog;
    private Window window;
    private Animation animation;
    private TextView tvText;
    private TextView tvLeft;
    private TextView tvRight;

    private boolean canceledOntouchOutside = true;

    public void setDialogButtonCallback(OnDialogButtonCallback callback) {
        this.buttonCallback = callback;
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCallback != null)
                    buttonCallback.leftClick();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCallback != null)
                    buttonCallback.rightClick();
            }
        });
    }

    public void setCanceledOntouchOutside(boolean canceledOntouchOutside){
        this.canceledOntouchOutside = canceledOntouchOutside;
    }

    public DialogEx(Context context){
        this.context = context;
        init();
    }

    private void init() {
        dialog = new Dialog(context, R.style.translucentDialogStyle);
        window = dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_dialog);
        dialog.setCanceledOnTouchOutside(canceledOntouchOutside);

        tvText = (TextView)dialog.findViewById(R.id.tv_text);
        tvLeft = (TextView)dialog.findViewById(R.id.tv_button_left);
        tvRight = (TextView)dialog.findViewById(R.id.tv_button_right);
    }

    public interface OnDialogButtonCallback {
        void leftClick();
        void rightClick();
    }

    public void setText(String text,String left,String right){
        tvText.setText(text);
        tvLeft.setText(left);
        tvRight.setText(right);
    }

    public void show() {
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dismiss() {
        dialog.dismiss();
    }

}
