package estate.common.config;

import estate.exception.TypeErrorException;

/**
 * Created by kangbiao on 15-10-28.
 * 密钥类型类
 */
public class SecretType
{
    public static byte LANYA=1;
    public static byte WIFI=2;

    public static boolean checkType (byte value) throws TypeErrorException
    {
        if (!(value==LANYA||value==WIFI))
            throw new TypeErrorException("密钥类型错误");
        return true;
    }
}
