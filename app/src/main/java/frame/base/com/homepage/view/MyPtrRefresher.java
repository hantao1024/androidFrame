package frame.base.com.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import frame.base.com.R;

/**
 * Created by hantao on 2018/2/22.
 */

public class MyPtrRefresher extends LinearLayout {
    public MyPtrRefresher(Context context) {
        super(context, null);
        LayoutInflater.from(context).inflate(R.layout.view_ptrrefresher, this);
    }

    public MyPtrRefresher(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.view_ptrrefresher, this);
    }

    public MyPtrRefresher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_ptrrefresher, this);
    }
}