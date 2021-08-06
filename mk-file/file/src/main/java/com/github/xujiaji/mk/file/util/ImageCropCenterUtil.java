package com.github.xujiaji.mk.file.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropCenterUtil {

    /**
     *
     * 压缩图片，以最小的边长作为压缩参照，以剪切中心正方形图片
     * @param srcImgPath
     *            :源图片路径 
     * @param outImgPath 
     *            :输出的压缩图片的路径 
     * @param
     *            :长宽为多少
     */
    public static void cropCenterImage(String srcImgPath, String outImgPath,
            int width, int height) throws IOException {
        // 得到图片  
        BufferedImage src = ImageIO.read(new File(srcImgPath));
        int old_w = src.getWidth();  
        // 得到源图宽  
        int old_h = src.getHeight();  
        // 得到源图长  
        int new_w = 0;  
        // 新图的宽  
        int new_h = 0;  
        // 新图的长  
        BufferedImage tempImg = new BufferedImage(old_w, old_h,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = tempImg.createGraphics();  
        g.setColor(Color.white);  
        // 从原图上取颜色绘制新图  
        g.fillRect(0, 0, old_w, old_h);  
        g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);  
        g.dispose();  
        // 根据图片尺寸压缩比得到新图的尺寸  
        if (old_w < old_h) {
            // 图片要缩放的比例  
            new_w = width;
            new_h = Math.round(old_h * ((float) width / old_w));
        } else {  
            new_w = Math.round(old_w * ((float) height / old_h));
            new_h = height;
        }  
        BufferedImage newImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        newImg.getGraphics().drawImage(  
                tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH),
                -(new_w - width) / 2,
                -(new_h - height) / 2, null);
        ImageIO.write(newImg, "jpg", new File(outImgPath));
    }

    public static void cropCenterImage(String srcImgPath, String outImgPath,
                                       int length) throws IOException {
        cropCenterImage(srcImgPath, outImgPath, length, length);
    }

    public static void crop(String srcImgPath, String outImgPath) {
        crop(srcImgPath, outImgPath, false);
    }

    public static void crop(String srcImgPath, String outImgPath,boolean isOne) {
        try {
            cropCenterImage(srcImgPath, outImgPath, isOne ? 600 : 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {  
        String srcImg = "tupian.jpg";
        String tarDir = "newImg/";
        cropCenterImage(srcImg, tarDir + System.currentTimeMillis() + ".jpg", 200);
    }
}  