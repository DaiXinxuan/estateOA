package estate.exception;

/**
 * Created by kangbiao on 15-10-20.
 * 图片上传异常
 */
public class PictureUploadException extends Exception
{
    public PictureUploadException (){}

    public PictureUploadException(String msg)
    {
        super(msg);
    }
}
