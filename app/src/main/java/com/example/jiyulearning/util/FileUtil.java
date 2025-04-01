package com.example.jiyulearning.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    //把位图数据保存到指定路径的图片文件
    public static void saveImage(String path, Bitmap bitmap){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            //把位图数据压缩到文件输出流中
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //从指定路径的图片文件中读取位图数据
    public static Bitmap openImage(String path){
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
