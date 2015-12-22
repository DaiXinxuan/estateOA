package estate.app;

import estate.common.config.BillPayStatus;
import estate.common.config.BillPayType;
import estate.common.util.Convert;
import estate.common.util.LogUtil;
import estate.dao.BillDao;
import estate.entity.app.Bill;
import estate.entity.database.PropertyBillEntity;

import estate.entity.json.BasicJson;


import estate.service.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by dxx on 2015/12/14.
 * 该类用来存储支付宝和微信支付成功后由APP传来的账单信息
 */
@RestController
@RequestMapping(value = "api/pay")
public class PayHandler {
    private Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private BillDao billDao;
    @Autowired
    private BaseService baseService;

    @RequestMapping(value="/save")
    public BasicJson saveBill(HttpServletRequest request){
        BasicJson basicJson = new BasicJson(false);
        //买方手机号
        String phone= (String) request.getSession().getAttribute("phone");
        //物业号
        int propertyID = Integer.parseInt((String) request.getSession().getAttribute("propertyID"));
        //支付时间
        String time = (String)request.getSession().getAttribute("time");
        //支付方式
        String type = (String)request.getSession().getAttribute("type");

        PropertyBillEntity propertyEntity;
        try{
            propertyEntity = billDao.
                    getPropertyBillByPropertyID(propertyID, BillPayStatus.UNPAY,null,null).get(0);
        }catch(Exception e){
            logger.error("物业号码有误，查询数据出错");
            basicJson.getErrorMsg().setDescription("物业号码有误！");
            return basicJson;
        }
        propertyEntity.setPayStatus(BillPayStatus.PAYED);
        propertyEntity.setPayer(phone);
        if(type.equals("1")){
            propertyEntity.setPayType(BillPayType.ALIPAY);
        }else if(type.equals("0")){
            propertyEntity.setPayType(BillPayType.WEIXIN);
        }else{
            logger.info("支付方式有误！");
            basicJson.getErrorMsg().setDescription("支付方式有误！");
            return basicJson;
        }

        propertyEntity.setPayTime(Convert.time2num(time));
        try {
            baseService.save(propertyEntity);
        }catch(Exception e){
            logger.error("保存物业账单信息出错!");
            basicJson.getErrorMsg().setDescription("保存账单信息出错！");
            return basicJson;
        }


        basicJson.setStatus(true);
        return basicJson;
    }

}
