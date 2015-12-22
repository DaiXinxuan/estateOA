package estate.service.impl;

import estate.common.FeeRuleUnit;
import estate.common.config.BillPayStatus;
import estate.common.config.ParkLotOwnerRole;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.dao.*;
import estate.entity.database.*;
import estate.entity.json.ParkLotExtra;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by kangbiao on 15-10-6.
 *
 */
@Service("billService")
public class BillServiceImpl implements BillService
{
    @Autowired
    private FeeItemOrderDao feeItemOrderDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private ParkLotOwnerInfoDao parkLotOwnerInfoDao;
    @Autowired
    private PropertyOwnerInfoDao propertyOwnerInfoDao;
    @Autowired
    private BillDao billDao;
    @Autowired
    private FeeItemDao feeItemDao;

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=billDao.getList(tableFilter);
        ArrayList<UserBillEntity> userBillEntities= (ArrayList<UserBillEntity>) tableData.getJsonString();
        if (userBillEntities!=null&&userBillEntities.size()>0)
        {
            for (UserBillEntity userBillEntity:userBillEntities)
            {
                if (userBillEntity.getPayStatus()==BillPayStatus.UNPAY)
                    userBillEntity.setPropertyBill(
                            this.getPropertyBillString(
                                    userBillEntity.getPhone(),
                                    tableFilter.getStatus(),
                                    tableFilter.getStartTime(),
                                    tableFilter.getEndTime()));
            }
        }
        tableData.setRecordsTotal(baseDao.count(UserBillEntity.class));
        return tableData;
    }

    @Override
    public ArrayList<UserBillEntity> getUserBill(String phone, Byte status, Long startTime, Long endTime)
    {
        ArrayList<UserBillEntity> userBillEntities= billDao.getUserBillByPhone(phone, status, startTime, endTime);
        if (userBillEntities==null)
            return null;
        for (UserBillEntity userBillEntity:userBillEntities)
        {
            if (userBillEntity.getPayStatus()==BillPayStatus.UNPAY)
                userBillEntity.setPropertyBill(this.getPropertyBillString(phone,status,startTime,endTime));
        }
        return userBillEntities;
    }

    @Override
    public String getPropertyBillString(String phone, Byte status, Long startTime, Long endTime)
    {
        StringBuilder propertyBill=new StringBuilder();
        ArrayList<PropertyOwnerInfoEntity> propertyOwnerInfoEntities= propertyOwnerInfoDao.getByPhone(phone);
        if (propertyOwnerInfoEntities!=null)
        {
            int temp=0;
            //获取所有的物业
            for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
            {
                Integer propertyId = propertyOwnerInfoEntity.getPropertyEntity().getId();
                //获取每个物业下的账单
                ArrayList<PropertyBillEntity> propertyBillEntities = billDao
                        .getPropertyBillByPropertyID(propertyId, status, startTime, endTime);
                if (propertyBillEntities!=null)
                {
                    int temp2 = 0;
                    for (PropertyBillEntity propertyBillEntity : propertyBillEntities)
                    {
                        if (temp!=0&&temp2==0)
                        {
                            propertyBill.append(";");
                        }
                        if (temp2==0)
                        {
                            propertyBill.append(propertyBillEntity.getFeeItemFee());
                        }
                        else
                        {
                            propertyBill.append(";").append(propertyBillEntity.getFeeItemFee());
                        }
                        temp2++;
                    }
                    temp++;
                }
            }
        }
        return propertyBill.toString();
    }

    @Override
    public void generateBillByPropertyID(Integer id)
    {
        ArrayList<FeeItemOrderEntity> feeItemOrderEntities =feeItemOrderDao.getFeeItemOrdersByPropertyID(id);
        PropertyEntity propertyEntity= (PropertyEntity) baseDao.get(id,PropertyEntity.class);

        if (feeItemOrderEntities==null)
            return;

        StringBuilder billInfo=new StringBuilder("");
        PropertyBillEntity propertyBillEntity = new PropertyBillEntity();

        Long thisMonth = Convert.time2num(Convert.num2time(System.currentTimeMillis(), "yyyy-MM"), "yyyy-MM");

        ArrayList<PropertyBillEntity> propertyBillEntities=billDao
                .getPropertyBillByPropertyID(id, BillPayStatus.UNPAY, thisMonth, null);
        if (propertyBillEntities!=null)
        {
            for (PropertyBillEntity propertyBillEntityTemp:propertyBillEntities)
            {
                propertyBillEntity=propertyBillEntityTemp;
            }
        }

        int forCount=0;
        for (FeeItemOrderEntity feeItemOrderEntity:feeItemOrderEntities)
        {
            String kv;
            switch (feeItemOrderEntity.getFeeItemEntity().getUnit())
            {
                case FeeRuleUnit.SQURE:
                    double unitPrice = feeItemOrderEntity.getFeeItemEntity().getUnitPrice().doubleValue();
                    double price = unitPrice * propertyEntity.getPropertySquare().doubleValue();
                    BigDecimal sum = new BigDecimal(price);
                    sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
                    kv = feeItemOrderEntity.getFeeItemEntity().getName() + ":" + sum;
                    break;
                case FeeRuleUnit.PERPROPERTY:
                    kv = feeItemOrderEntity.getFeeItemEntity().getName() + ":" + feeItemOrderEntity
                            .getFeeItemEntity().getUnitPrice();
                    break;
                default:
                    kv = "";
                    break;
            }
            if (forCount==0)
            {
                billInfo.append(kv);
            }
            else
            {
                billInfo.append(";").append(kv);
            }
            forCount++;
        }

        propertyBillEntity.setBillGenerationTime(System.currentTimeMillis());
        propertyBillEntity.setFeeItemFee(billInfo.toString());
        propertyBillEntity.setPayStatus(BillPayStatus.UNPAY);
        propertyBillEntity.setPropertyId(id);
        baseDao.save(propertyBillEntity);
    }

    @Override
    public void generateUserBill(String phone)
    {
        if (phone==null)
            return;
        StringBuilder parkLotBill=new StringBuilder();
        UserBillEntity userBillEntity=new UserBillEntity();
        userBillEntity.setPhone(phone);

        Long thisMonth = Convert.time2num(Convert.num2time(System.currentTimeMillis(), "yyyy-MM"), "yyyy-MM");
        ArrayList<UserBillEntity> userBillEntities=billDao
                .getUserBillByPhone(phone, BillPayStatus.UNPAY, thisMonth, null);
        //已经生成则更新账单,只会对未付款的账单进行更新
        if (userBillEntities!=null)
        {
            //防止数据异常
            for (UserBillEntity userBillEntityTemp:userBillEntities)
            {
                userBillEntity=userBillEntityTemp;
            }
        }
        //开始计算用户的车位费账单
        ArrayList<ParklotOwnerInfoEntity> parklotOwnerInfoEntities=parkLotOwnerInfoDao.getByPhone(phone);
        if (parklotOwnerInfoEntities!=null)
        {
            int temp=0;
            for (ParklotOwnerInfoEntity parklotOwnerInfoEntity : parklotOwnerInfoEntities)
            {
                FeeItemEntity feeItemEntity = (FeeItemEntity) feeItemDao.getParkLotByVillageIdType
                        (parklotOwnerInfoEntity.getParkingLotEntity().getBrakeEntity().getVillageId(),
                                String.valueOf(parklotOwnerInfoEntity.getParkingLotEntity().getType()));
                if (feeItemEntity != null)
                {
                    ParkLotExtra parkLotExtra = (GsonUtil.getGson().fromJson(feeItemEntity.getExtendInfo(), ParkLotExtra.class));
                    switch (parklotOwnerInfoEntity.getOwnerType())
                    {
                        case ParkLotOwnerRole.OWNER:
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            }
                            break;
                        case ParkLotOwnerRole.TEMP:
                            parkLotExtra.getPerTimePrice();
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getPerTimePrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getPerTimePrice());
                            }
                            break;
                        case ParkLotOwnerRole.TENANT:
                            parkLotExtra.getMonthPrice();
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位月租费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getMonthPrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位月租费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getMonthPrice());
                            }
                            break;
                        case ParkLotOwnerRole.USER:
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            }
                            break;
                        default:
                            break;
                    }
                    temp++;
                }
            }
        }
        if (userBillEntity.getBillSeris()==null)
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            userBillEntity.setBillSeris(df.format(System.currentTimeMillis())+(new Random().nextInt(899999)+100000));
        }
        userBillEntity.setParklotBill(parkLotBill.toString());
        userBillEntity.setUpdateTime(System.currentTimeMillis());
        userBillEntity.setPayStatus(BillPayStatus.UNPAY);
        baseDao.save(userBillEntity);
    }

}
