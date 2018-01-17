package www.test720.com.naneducation.utils;

import android.telephony.TelephonyManager;
import android.util.Log;

import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


import www.test720.com.naneducation.application.MyApplication;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by LuoPan on 2017/8/17 11:49.
 */

public class MD5Util {
    /**
     * MD5加密
     *
     * @param str
     * @param isUp true是否大写
     * @return
     */
    public static String MD5(String str, boolean isUp) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        if (isUp) {
            return (hexValue.toString()).toUpperCase();
        } else {
            return hexValue.toString();
        }
    }


    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static final String DESKEY = "QpOiUnYbVp3bB73Fsn7O12CX";


    public static String getIMEI() {
        /*use AdvertisingIdClient$Info#getId and for analytics, use InstanceId#getId.*/
        TelephonyManager TelephonyMgr = (TelephonyManager) MyApplication.getContext().getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        if (szImei == null) {
            return DESKEY;
        }
        Log.e("TAG++++getIMEI", szImei);
        return szImei;

    }

    /**
     * 默认加密方式
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        String str = encrypt(getIMEI(), data);
        Log.e("TAG+++encrypt", str);
        return str;
    }

    /**
     * 默认解密方式
     *
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        String str = "";
        try {
            str = decrypt(getIMEI(), data);
        } catch (Exception e) {

        }
        Log.e("TAG+++decrypt", str);
        return str;
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws
     * @throws Exception
     */
    public static String encrypt(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2hex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decrypt(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
