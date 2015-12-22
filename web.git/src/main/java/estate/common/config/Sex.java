package estate.common.config;

import estate.exception.TypeErrorException;

/**
 * Created by kangbiao on 15-10-28.
 * 性别
 */
public class Sex
{
    public static byte MAN=0;
    public static byte WOMAN=1;

    public static boolean checkType(Byte type) throws TypeErrorException
    {
        if (!(type==MAN||type==WOMAN))
            throw new TypeErrorException("性别类型参数错误");
        return true;
    }
}
