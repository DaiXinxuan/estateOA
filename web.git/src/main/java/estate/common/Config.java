package estate.common;


/**
 * Created by kangbiao on 15-9-5.
 * 系统配置
 */
public class Config
{
    //路径配置
    public static final String FILEPATH=System.getProperty("WEB.ROOT")+"file/";
    public static final String PICPATH=System.getProperty("WEB.ROOT")+"file/picture/";
    public static final String DOCPATH=System.getProperty("WEB.ROOT")+"file/documentPath/";
    public static final String APKPATH=System.getProperty("WEB.ROOT")+"file/apk/";

    public static final String PICWEBPATH="/oa/file/picture/";

    //系统真假常量
    public static final Byte FALSE=0;
    public static final Byte TRUE=1;
}
