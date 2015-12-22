package estate.common.util;

import java.security.MessageDigest;

/**
 * Created by kangbiao on 15-11-12.
 * 加密工具类
 */
public class DigestUtil
{

    /**
     * md5加密
     * @param inStr
     * @return 返回加密后的字符串
     * @throws Exception
     */
    public static String MD5(String inStr) throws Exception
    {
        MessageDigest md5;
        md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray;
        byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5 != null ? md5.digest(byteArray) : new byte[0];
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes)
        {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
