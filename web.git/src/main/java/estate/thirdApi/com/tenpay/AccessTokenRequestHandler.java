package estate.thirdApi.com.tenpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import estate.thirdApi.com.tenpay.client.TenpayHttpClient;
import estate.thirdApi.com.tenpay.util.ConstantUtil;
import estate.thirdApi.com.tenpay.util.JsonUtil;
import estate.thirdApi.com.tenpay.util.WXUtil;

public class AccessTokenRequestHandler extends RequestHandler {

    public AccessTokenRequestHandler(HttpServletRequest request,
                                     HttpServletResponse response) {
        super(request, response);
    }

    private static String access_token = "";

    /**
     * 获取凭证access_token
     * @return
     */
    public static String getAccessToken() {
        if ("".equals(access_token)) {// 如果为空直接获取
            return getTokenReal();
        }

        if (tokenIsExpire(access_token)) {// 如果过期重新获取
            return getTokenReal();
        }
        return access_token;
    }

    /**
     * 实际获取access_token的方法
     * @return
     */
    protected static String getTokenReal() {
        String requestUrl = WeixinPayConfig.TOKENURL + "?grant_type=" + WeixinPayConfig.GRANT_TYPE + "&appid="
                + WeixinPayConfig.APP_ID + "&secret=" + WeixinPayConfig.APP_SECRET;
        String resContent = "";
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setMethod("GET");
        httpClient.setReqContent(requestUrl);
        if (httpClient.call()) {
            resContent = httpClient.getResContent();
            if (resContent.indexOf(WeixinPayConfig.ACCESS_TOKEN) > 0) {
                access_token = JsonUtil.getJsonValue(resContent, WeixinPayConfig.ACCESS_TOKEN);
            } else {
                System.out.println("获取access_token值返回错误！！！");
            }
        } else {
            System.out.println("后台调用通信失败");
            System.out.println(httpClient.getResponseCode());
            System.out.println(httpClient.getErrInfo());
            // 有可能因为网络原因，请求已经处理，但未收到应答。
        }

        return access_token;
    }

    /**
     * 判断传递过来的参数access_token是否过期
     * @param access_token
     * @return
     */
    private static boolean tokenIsExpire(String access_token) {
        boolean flag = false;
        PrepayIdRequestHandler wxReqHandler = new PrepayIdRequestHandler(null, null);
        wxReqHandler.setParameter("appid", WeixinPayConfig.APP_ID);
        wxReqHandler.setParameter("appkey",WeixinPayConfig.APP_KEY);
        wxReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
        wxReqHandler.setParameter("package", WeixinPayConfig.packageValue);
        wxReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
        wxReqHandler.setParameter("traceid", WeixinPayConfig.traceid);

        // 生成支付签名
        String sign = wxReqHandler.createSHA1Sign();
        wxReqHandler.setParameter("app_signature", sign);
        wxReqHandler.setParameter("sign_method", WeixinPayConfig.SIGN_METHOD);
        String gateUrl = WeixinPayConfig.GATEURL + access_token;
        wxReqHandler.setGateUrl(gateUrl);

        // 发送请求
        String accesstoken = wxReqHandler.sendAccessToken();
        if (WeixinPayConfig.EXPIRE_ERRCODE.equals(accesstoken) || WeixinPayConfig.FAIL_ERRCODE.equals(accesstoken))
            flag = true;
        return flag;
    }

}
