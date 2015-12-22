package estate.exception;

/**
 * Created by kangbiao on 15-10-9.
 * 系统通用的类型错误异常类
 */
public class TypeErrorException extends Exception
{
    public TypeErrorException(){}

    public TypeErrorException(String msg)
    {
        super(msg);
    }
}
