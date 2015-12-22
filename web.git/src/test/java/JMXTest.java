import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.*;

/**
 * Created by kangbiao on 15-11-17.
 * JMX连接测试
 */
public class JMXTest
{
    private final static String URL = "service:jmx:rmi:///jndi/rmi://localhost:8888/jmxrmi";

    @Test
    public void test()
    {
        try
        {
            JMXServiceURL serviceURL = new JMXServiceURL(URL);

            Map<String,Object> map = new HashMap<>();
            String[] credentials = new String[]{"monitorRole", "QED"};
            map.put("jmx.remote.credentials", credentials);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
            MBeanServerConnection mbsc = connector.getMBeanServerConnection();


            //-------------------- Session ---------------
            ObjectName managerObjName = new ObjectName("Catalina:type=Manager,*");
            Set<ObjectName> s = mbsc.queryNames(managerObjName, null);
            for (ObjectName obj : s)
            {
                System.out.println("应用名:" + obj.getKeyProperty("path"));
                ObjectName objname = new ObjectName(obj.getCanonicalName());
                System.out.println("最大会话数:" + mbsc.getAttribute(objname, "maxActiveSessions"));
                System.out.println("会话数:" + mbsc.getAttribute(objname, "activeSessions"));
                System.out.println("活动会话数:" + mbsc.getAttribute(objname, "sessionCounter"));
            }

            //----------------- Thread Pool ----------------
            ObjectName threadpoolObjName = new ObjectName("Catalina:type=ThreadPool,*");
            Set<ObjectName> s2 = mbsc.queryNames(threadpoolObjName, null);
            for (ObjectName obj : s2)
            {
                System.out.println("端口名:" + obj.getKeyProperty("name"));
                ObjectName objname = new ObjectName(obj.getCanonicalName());
                System.out.println("最大线程数:" + mbsc.getAttribute(objname, "maxThreads"));
                System.out.println("当前线程数:" + mbsc.getAttribute(objname, "currentThreadCount"));
                System.out.println("繁忙线程数:" + mbsc.getAttribute(objname, "currentThreadsBusy"));
            }
//            Integer.MAX_VALUE
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
