package estate.thirdApi.com.tenpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import estate.common.util.LogUtil;
import estate.entity.json.BasicJson;
import estate.thirdApi.com.tenpay.util.*;
import org.apache.logging.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by dxx on 2015/12/10.
 */
@RestController
@RequestMapping(value = "/api/tenpay")
public class WeiXinPayController {

    private Logger logger = LogUtil.getLogger(this.getClass());


    @RequestMapping(value = "/pay")
    protected BasicJson doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Map<Object,Object> resInfo = new HashMap<Object, Object>();
        BasicJson basicJson = new BasicJson(false);
        //最新版还需要body（客户端传来）和mch_id（问公司要）
        String body = (String)request.getSession().getAttribute("body");
        String total_fee = (String)request.getSession().getAttribute("total_fee");//单位为分，不能带小数点
        //终端IP
        //String spbill_create_ip = (String)request.getSession().getAttribute("spbill_create_ip");
        //接收财付通通知的URL
        String notify_url = "http://127.0.0.1:8180/oa/thirdApi/pay/weixin/notify_url";

        //---------------生成订单号 开始------------------------
        //当前时间 yyyyMMddHHmmss
        String currTime = TenpayUtil.getCurrTime();
        //8位日期
        String strTime = currTime.substring(8, currTime.length());
        //四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        //10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        //订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
        String out_trade_no = strReq;
        //---------------生成订单号 结束------------------------

        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid",WeixinPayConfig.APP_ID);
        parameters.put("mch_id",WeixinPayConfig.MCH_ID);
        parameters.put("nonce_str",WXUtil.getNonceStr());
        parameters.put("body",body);
        parameters.put("out_trade_no",out_trade_no);
        parameters.put("total_fee",total_fee);
        parameters.put("spbill_create_ip",request.getRemoteAddr());
        parameters.put("notify_url", notify_url);
        parameters.put("trade_type","APP");
        String sign1 = PayCommonUtil.createSign("UTF-8", parameters);
        parameters.put("sign", sign1);
        String requestXML = PayCommonUtil.getRequestXml(parameters);

        String result="";
        try {
            result = CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL,"POST",requestXML);
        }catch (Exception e){
            logger.error("与微信服务器交互出错！");
            basicJson.getErrorMsg().setDescription("与微信交互出错！");
            return basicJson;
        }

        Map<String,String> map = null;
        try {
            //解析微信返回的信息，以Map方式存储
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            logger.error("解析微信返回的信息出错！");
            e.printStackTrace();
            basicJson.getErrorMsg().setDescription("解析微信返回的信息出错！");
            return basicJson;
        }
        if (map!=null) {
            JSONObject json = new JSONObject();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                json.put((String) entry.getKey(), entry.getValue());
            }
            basicJson.setStatus(true);
            basicJson.setJsonString(json);
            return basicJson;
        }else{
            logger.info("map解析出错！");
            basicJson.setStatus(false);
            basicJson.getErrorMsg().setDescription("map解析出错");
            return basicJson;
        }

//        PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);//生成package的请求类
//        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
//        ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//返回客户端支付参数的请求类
//        packageReqHandler.setKey(WeixinPayConfig.PARTNER_KEY);
//
//        int retcode ;
//        String retmsg = "";
//        String xml_body = "";
//        //获取token值
//
//        String token = AccessTokenRequestHandler.getAccessToken();
//
//        logger.info("获取token------值 " + token);
//
//        if (!"".equals(token)) {
//            //设置package订单参数
//            packageReqHandler.setParameter("bank_type", "WX");//银行渠道
//            packageReqHandler.setParameter("body", "测试"); //商品描述
//            packageReqHandler.setParameter("notify_url", notify_url); //接收财付通通知的URL
//            packageReqHandler.setParameter("partner", WeixinPayConfig.PARTNER); //商户号
//            packageReqHandler.setParameter("out_trade_no", out_trade_no); //商家订单号
//            packageReqHandler.setParameter("total_fee", "1"); //商品金额,以分为单位
//            packageReqHandler.setParameter("spbill_create_ip",request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP
//            packageReqHandler.setParameter("fee_type", "1"); //币种，1人民币   66
//            packageReqHandler.setParameter("input_charset", "GBK"); //字符编码
//
//            //获取package包
//            String packageValue = packageReqHandler.getRequestURL();
//            resInfo.put("package", packageValue);
//
//            logger.info("获取package------值 " + packageValue);
//
//            String noncestr = WXUtil.getNonceStr();
//            String timestamp = WXUtil.getTimeStamp();
//            String traceid = "";
//            ////设置获取prepayid支付参数
//            prepayReqHandler.setParameter("appid", WeixinPayConfig.APP_ID);
////            prepayReqHandler.setParameter("appkey", WeixinPayConfig.APP_KEY);
//            //新加的
////            prepayReqHandler.setParameter("body",body);
//            prepayReqHandler.setParameter("mch_id","");
//            prepayReqHandler.setParameter("trade_type","APP");
////            prepayReqHandler.setParameter("out_trade_no",out_trade_no);
////            prepayReqHandler.setParameter("total_fee",total_fee);
////            prepayReqHandler.setParameter("spbill_create_ip",spbill_create_ip);
//
//            prepayReqHandler.setParameter("noncestr", noncestr);
//            prepayReqHandler.setParameter("package", packageValue);
//            prepayReqHandler.setParameter("timestamp", timestamp);
//            prepayReqHandler.setParameter("traceid", traceid);
//
//            //生成获取预支付签名
//            String sign = prepayReqHandler.createSHA1Sign();
//            //增加非参与签名的额外参数
////            prepayReqHandler.setParameter("app_signature", sign);
//            prepayReqHandler.setParameter("sign",sign);
//            prepayReqHandler.setParameter("sign_method",
//                    WeixinPayConfig.SIGN_METHOD);
//            String gateUrl = WeixinPayConfig.GATEURL + token;
//            prepayReqHandler.setGateUrl(gateUrl);
//
//            //获取prepayId
//            String prepayid = prepayReqHandler.sendPrepay();
//
//            logger.info("获取prepayid------值 " + prepayid);
//
//            //吐回给客户端的参数
//            if (null != prepayid && !"".equals(prepayid)) {
//                //输出参数列表
//                clientHandler.setParameter("appid", WeixinPayConfig.APP_ID);
//                clientHandler.setParameter("appkey", WeixinPayConfig.APP_KEY);
//                clientHandler.setParameter("noncestr", noncestr);
//                //clientHandler.setParameter("package", "Sign=" + packageValue);
//                clientHandler.setParameter("package", "Sign=WXPay");
//                clientHandler.setParameter("partnerid", WeixinPayConfig.PARTNER);
//                clientHandler.setParameter("prepayid", prepayid);
//                clientHandler.setParameter("timestamp", timestamp);
//                //生成签名
//                sign = clientHandler.createSHA1Sign();
//                clientHandler.setParameter("sign", sign);
//
//                xml_body = clientHandler.getXmlBody();
//                resInfo.put("entity", xml_body);
//                retcode = 0;
//                retmsg = "OK";
//            } else {
//                retcode = -2;
//                retmsg = "错误：获取prepayId失败";
//            }
//        } else {
//            retcode = -1;
//            retmsg = "错误：获取不到Token";
//        }
//
//        resInfo.put("retcode", retcode);
//        resInfo.put("retmsg", retmsg);
//        String strJson = JSON.toJSONString(resInfo);
//        return responseAjax(request, strJson);
    }

}
