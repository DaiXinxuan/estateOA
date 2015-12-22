package estate.common.util;

import org.junit.Test;

import java.util.Random;

/**
 * Created by kangbiao on 15-10-6.
 * 验证码生成类
 */
public class VerifyCodeGenerate
{
    public static String create()
    {
        Random random=new Random();
        return String.valueOf(random.nextInt(899999)+100000);
    }


    @Test
    public void test()
    {
        create();
    }
}
