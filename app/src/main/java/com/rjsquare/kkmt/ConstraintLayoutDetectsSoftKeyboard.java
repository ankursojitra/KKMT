package com.rjsquare.kkmt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ConstraintLayoutDetectsSoftKeyboard extends ConstraintLayout {

    public ConstraintLayoutDetectsSoftKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface Listener {
        public void onSoftKeyboardShown(boolean isShowing);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Activity activity = (Activity) getContext();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        int diff = (screenHeight - statusBarHeight) - height;
        //finding keyboard height
        int keypadHeight = screenHeight - rect.bottom;
        if (keypadHeight > screenHeight * 0.15) {

        } else {

        }
        if (listener != null) {
            listener.onSoftKeyboardShown(keypadHeight > screenHeight * 0.15); // assume all soft keyboards are at least 128 pixels high
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}