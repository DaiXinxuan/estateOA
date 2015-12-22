package estate.thirdApi.com.tenpay;

import estate.common.util.LogUtil;
import estate.thirdApi.com.tenpay.util.PayCommonUtil;
import estate.thirdApi.com.tenpay.util.XMLUtil;
import org.apache.logging.log4j.Logger;
import org.jdom.JDOMException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dxx on 2015/12/14.
 * 该类接收微信服务器异步的通知，并且根据相关支付状态进行相关的业务操作
 */
@WebServlet("/thirdApi/pay/weixin/notify_url")
public class WeiXinNotifier extends HttpServlet{
    private Logger logger= LogUtil.getLogger(this.getClass());
    //针对微信发来信息重复的暂时处理方法，生成一个列表，最大容量为1000，超过清零。不过建议将订单号存入数据库
    private ArrayList<String> out_trade_no = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        System.out.println("-----------------付款成功-----------------");
        byteArrayOutputStream.close();
        inputStream.close();
        //获取返回信息
        String result = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        Map<Object, Object> map = null;
        try {
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            logger.error("xml转换出错");
            e.printStackTrace();
        }
        for (Object key : map.keySet()) {
            System.out.println(key + "=" + map.get(key));
        }

        //微信发来的信息可能重复，在此处判断是不是已处理过的账单
        if(map.get("result_code").equals("SUCCESS")){
            //支付成功
            if(!out_trade_no.contains(map.get("out_trade_no").toString())) {
                if(out_trade_no.size()<1000) {
                    out_trade_no.add(map.get("out_trade_no").toString());
                }else {
                    out_trade_no.clear();
                    out_trade_no.add(map.get("out_trade_no").toString());
                }
                response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));
            }else {
                response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));
            }
        }else{
            response.getWriter().write(PayCommonUtil.setXML("FAIL",map.get("return_msg").toString()));
        }




    }
}
