package estate.thirdApi.message;

import estate.common.util.LogUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by kangbiao on 15-9-15.
 * 发送短信通知
 */
public class Message
{
    /**
     * 消息发送
     * @param mobile
     * @param content
     * @return
     * @throws IOException
     */
    public static String send(String mobile, String content) throws IOException
    {
        String sign="阳辰";
        StringBuilder sb = new StringBuilder("http://sms.1xinxi.cn/asmx/smsservice.aspx?");
        sb.append("name=VerPass@163.com");
        sb.append("&pwd=0C9E4B9FCDD8A770110444E6A7B8");
        sb.append("&mobile=").append(mobile);
        sb.append("&content=").append(URLEncoder.encode(content, "UTF-8"));
        sb.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
        sb.append("&type=pt&extno=");
        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputline = in.readLine();
        System.out.println(inputline);
        String[] strings=inputline.split(",");
        switch (strings[0])
        {
            case "0":
                return "succ";
            case "1":
                return "含有敏感词汇";
            case "2":
                return "余额不足";
            case "3":
                return "请输入正确的电话号码";
            case "4":
                return "包含sql语句";
            case "10":
                return "账号不存在";
            default:
                return "未知错误";
        }
    }

    /**
     * 发送注册验证码
     * @param phone
     * @param verifyCode
     * @return
     */
    public static String sendRegisterVerifyCode(String phone,String verifyCode)
    {
        String content="多能通用户注册验证码"+verifyCode+"(10分钟有效),消息来自:多能通安全中心";
        try
        {
            return send(phone,content);
        } catch (Exception e)
        {
            return "验证码发送失败";
        }
    }

    /**
     * 发送找回密码的验证码
     * @param phone
     * @param verifyCode
     * @return
     */
    public static String sendFindPasswordVerifyCode(String phone,String verifyCode)
    {
        String content="您的校验码是"+verifyCode+"(10分钟有效),请妥善保管,消息来自:多能通安全中心";
        try
        {
            return send(phone,content);
        } catch (Exception e)
        {
            return "验证码发送失败";
        }
    }

    @Test
    public void test()
    {
        LogUtil.E(sendRegisterVerifyCode("18144240528", "101012"));
    }
}
