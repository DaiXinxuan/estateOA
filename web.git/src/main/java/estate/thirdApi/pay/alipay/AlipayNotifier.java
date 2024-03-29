package estate.thirdApi.pay.alipay;

import estate.common.util.LogUtil;
import estate.entity.database.AlipayTradeEnity;
import estate.service.BaseService;
import estate.thirdApi.pay.alipay.util.AlipayNotify;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kangbiao on 15-8-26.
 *
 * 该类接收支付宝服务器异步的通知，并且根据相关支付状态进行相关的业务操作
 */
@WebServlet("/thirdApi/pay/aliPay/notify_url")
public class AlipayNotifier extends HttpServlet
{
    private Logger logger= LogUtil.getLogger(this.getClass());
    @Autowired
    private BaseService baseService;

    public void destory()
    {
        super.destroy();
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //总额
        Double amount = Double.parseDouble(request.getParameter("total_fee"));

        //买家支付宝账号
        String buyerID = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");

        //卖家支付宝账户
        String sellerID = new String(request.getParameter("seller_email").getBytes("ISO-8859-1"),"UTF-8");

        //付款时间
        Date date = null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"),"UTF-8");
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            logger.error("日期转换出错！");
            e.printStackTrace();
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            AlipayTradeEnity alipayTradeEnity = new AlipayTradeEnity();
            alipayTradeEnity.setOutTradeNo(out_trade_no);
            alipayTradeEnity.setTradeNo(trade_no);
            alipayTradeEnity.setTradeStatus(trade_status);
            alipayTradeEnity.setAmount(amount);
            alipayTradeEnity.setCustomerID(buyerID);
            alipayTradeEnity.setSellerID(sellerID);
            alipayTradeEnity.setTradeDate(date);
            try {
                baseService.save(alipayTradeEnity);
            }catch (Exception e){
                logger.error("保存支付宝订单信息失败！"+e.getMessage());
            }
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //该种交易状态只在两种情况下出现
                //1、开通了普通即时到账，买家付款成功后。
                //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            response.getWriter().print("success");	//请不要修改或删除

        }else{//验证失败
            response.getWriter().print("fail");
        }

    }
}
