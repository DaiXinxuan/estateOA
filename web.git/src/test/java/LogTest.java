import estate.common.util.LogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


/**
 * Created by kangbiao on 15-11-7.
 * 日志测试
 */
public class LogTest
{
    private Logger logger= LogUtil.getLogger(this.getClass());


    @Test
    public void test()
    {
        logger.info("Hello");
        logger.error("sssssssss");
    }


}
