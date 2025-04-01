package com.example.jiyulearning.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void show(Context ctx, String desc){//用于提示的工具类
        Toast.makeText(ctx,desc,Toast.LENGTH_SHORT).show();
    }
}

