package com.github.xujiaji.mk.pay.front.pay.ios;

import sun.misc.BASE64Decoder;

import javax.net.ssl.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;


public class IOS_Verify {
    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static final String url_sandbox = "https://sandbox.itunes.apple.com/verifyReceipt";
    private static final String url_verify = "https://buy.itunes.apple.com/verifyReceipt";


    /**
     * 苹果服务器验证
     *
     * @param receipt 账单
     * @return null 或返回结果
     * 沙盒   https://sandbox.itunes.apple.com/verifyReceipt
     * @url 要验证的地址
     */
    public static String buyAppVerify(String receipt, boolean isSandbox) {
        String url = isSandbox ? url_sandbox : url_verify;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "text/json");
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedOutputStream hurlBufOus = new BufferedOutputStream(conn.getOutputStream());

            String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + receipt + "\"}");
            hurlBufOus.write(str.getBytes());
            hurlBufOus.flush();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 用BASE64加密
     *
     * @param str
     * @return
     */
    public static String getBASE64(String str) {
        byte[] b = str.getBytes();
        String s = null;
        if (b != null) {
            s = new sun.misc.BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * 解密BASE64字窜
     *
     * @param s
     * @return
     */
    public static String getFromBASE64(String s) {
        byte[] b = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                return new String(b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(b);
    }

    /**
     * md5加密方法
     *
     * @param plainText 加密字符串
     * @return String 返回32位md5加密字符串(16位加密取substring(8,24))
     * 每位工程师都有保持代码优雅的义务
     * each engineer has a duty to keep the code elegant
     * @author: zhengsunlei
     * Jul 30, 2010 4:38:28 PM
     */
    public final static String md5(String plainText) {
        // 返回字符串
        String md5Str = null;
        try {
            // 操作字符串
            StringBuffer buf = new StringBuffer();
            /**
             * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
             * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
             *
             * MessageDigest 对象开始被初始化。
             * 该对象通过使用 update()方法处理数据。
             * 任何时候都可以调用 reset()方法重置摘要。
             * 一旦所有需要更新的数据都已经被更新了，应该调用digest()方法之一完成哈希计算。
             *
             * 对于给定数量的更新数据，digest 方法只能被调用一次。
             * 在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
             */
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
            md.update(plainText.getBytes());
            // 计算出摘要,完成哈希计算。
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                // 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
                buf.append(Integer.toHexString(i));
            }
            // 32位的加密
            md5Str = buf.toString();
            // 16位的加密
            // md5Str = buf.toString().md5Strstring(8,24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

}