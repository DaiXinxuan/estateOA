package estate.service.impl;

import com.google.gson.Gson;
import estate.common.Config;
import estate.common.config.*;
import estate.common.enums.Entity;
import estate.common.excelDefine.*;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.common.util.Validator;
import estate.dao.*;
import estate.entity.database.*;
import estate.entity.json.ExcelImportReport;
import estate.service.ExcelImportService;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotatedElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by kangbiao on 15-10-24.
 *
 */
@Service("excelImportService")
public class ExcelImportServiceImpl implements ExcelImportService
{

    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private PropertyOwnerInfoDao propertyOwnerInfoDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private VillageDao villageDao;
    @Autowired
    private BrakeDao brakeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SsidSecretDao ssidSecretDao;


    @Override
    public ExcelImportReport importProperty(List<Map<String, String>> result)
    {
        ExcelImportReport excelImportReport=new ExcelImportReport();
        Integer errorNum=0;
        Integer succNum=0;
        Gson gson=new Gson();
        List<String> errorDescription=new ArrayList<>();
        for (Map<String, String> map : result)
        {
            PropertyEntity propertyEntity=new PropertyEntity();

            //设置状态
            String status=map.get(PropertyHead.STATUS);
            switch (status)
            {
                case "出租":
                    propertyEntity.setStatus(PropertyStatus.CHUZU);
                    break;
                case "自用":
                    propertyEntity.setStatus(PropertyStatus.SELF);
                    break;
                default:
                    errorNum += 1;
                    errorDescription.add("物业状态不合法: <br/>" + gson.toJson(map));
                    continue;
            }

            //设置编号
            PropertyEntity propertyEntity1=propertyDao.getByCode(map.get(PropertyHead.CODE));
            if (propertyEntity1!=null)
            {
                errorNum+=1;
                errorDescription.add("该物业编号已存在: <br/>"+gson.toJson(map));
                continue;
            }
            else
            {
                propertyEntity.setCode(map.get(PropertyHead.CODE));
            }

            //设置地址信息
            String location=map.get(PropertyHead.ADDRESS);
            if (!location.equals(""))
                propertyEntity.setLocation(location);
            else
            {
                errorNum+=1;
                errorDescription.add("地址信息不能为空: <br/>"+gson.toJson(map));
                continue;
            }

            //设置面积
            try
            {
                String squre=map.get(PropertyHead.SQURE);
                BigDecimal bigDecimal=new BigDecimal(squre).setScale(2,BigDecimal.ROUND_HALF_UP);
                propertyEntity.setPropertySquare(bigDecimal);
            }
            catch (Exception e)
            {
                errorNum+=1;
                errorDescription.add("房屋面积信息不合法: <br/>"+gson.toJson(map));
                continue;
            }

            // 设置楼栋和楼栋关联
            BuildingEntity buildingEntity = buildingDao.getByCode(map.get(PropertyHead.BUILDINGCODE));
            if (buildingEntity == null)
            {
                errorNum += 1;
                errorDescription.add("该楼栋编号不存在: <br/>" + gson.toJson(map));
                continue;
            }
            else
            {
                propertyEntity.setVillageId(buildingEntity.getVillageId());
                propertyEntity.setBuildingId(buildingEntity.getId());
            }

            //设置物业类型
            if (map.get(PropertyHead.TYPE).equals("商户"))
                propertyEntity.setType(PropertyType.SHOP);
            else if (map.get(PropertyHead.TYPE).equals("住宅"))
                propertyEntity.setType(PropertyType.APPARTEMENT);
            else
            {
                errorNum += 1;
                errorDescription.add("物业类型错误: <br/>" + gson.toJson(map));
                continue;
            }

            //通过校验后的保存操作
            try
            {
                propertyEntity.setOpenDoorStatus(Config.TRUE);
                propertyEntity.setModifyTime(System.currentTimeMillis());
                baseDao.save(propertyEntity);
                succNum+=1;
            }
            catch (Exception e)
            {
                errorNum+=1;
                errorDescription.add("插入数据库失败: <br/>"+gson.toJson(map));
            }
        }
        excelImportReport.setErrorNum(errorNum);
        excelImportReport.setErrorDescription(errorDescription);
        excelImportReport.setSuccNum(succNum);
        return excelImportReport;
    }

    @Override
    public ExcelImportReport importBind(List<Map<String, String>> result)
    {
        ExcelImportReport excelImportReport=new ExcelImportReport();
        Integer errorNum=0;
        Integer succNum=0;
        Gson gson=new Gson();
        List<String> errorDescription=new ArrayList<>();
        for (Map<String,String> map:result)
        {

            OwnerEntity ownerEntity=new OwnerEntity();

            //基本的数据检验
            if (Validator.isMobile(map.get(BindHead.PHONE)))
            {
                ownerEntity.setPhone(map.get(BindHead.PHONE));
            }
            else
            {
                errorNum += 1;
                errorDescription.add("手机号码不合法: <br/>" + gson.toJson(map));
                continue;
            }

            if (map.get(BindHead.NAME).equals(""))
            {
                errorNum += 1;
                errorDescription.add("业主姓名不能为空: <br/>" + gson.toJson(map));
                continue;
            }
            ownerEntity.setName(map.get(BindHead.NAME));

            if (map.get(BindHead.SEX).equals("男"))
                ownerEntity.setSex(Sex.MAN);
            else if (map.get(BindHead.SEX).equals("女"))
                ownerEntity.setSex(Sex.WOMAN);
            else
            {
                errorNum += 1;
                errorDescription.add("性别不合法: <br/>" + gson.toJson(map));
                continue;
            }

            Long birthday=Convert.time2num(map.get(BindHead.BIRTHDAY));
            ownerEntity.setBirthday(birthday);
            if (birthday==null)
            {
                errorNum += 1;
                errorDescription.add("出生日期不合法: <br/>" + gson.toJson(map));
                continue;
            }

            ownerEntity.setUrgentName(map.get(BindHead.URGENTNAME));
            if (!map.get(BindHead.URGENTPHONE).equals(""))
            {
                if (Validator.isMobile(map.get(BindHead.URGENTPHONE)))
                {
                    ownerEntity.setUrgentPhone(map.get(BindHead.URGENTPHONE));
                }
                else
                {
                    errorNum += 1;
                    errorDescription.add("紧急联系人手机号码不合法: <br/>" + gson.toJson(map));
                    continue;
                }
            }

            String idTypeString=map.get(BindHead.IDTYPE);
            switch (idTypeString)
            {
                case "身份证":
                    ownerEntity.setIdentityType(CardType.IDCARD);
                    break;
                case "军官证":
                    ownerEntity.setIdentityType(CardType.SOLDIERCARD);
                    break;
                case "护照":
                    ownerEntity.setIdentityType(CardType.PASSPORT);
                    break;
                default:
                    errorNum += 1;
                    errorDescription.add("证件类型不合法: <br/>" + gson.toJson(map));
                    continue;
            }

            if (Validator.isIDCard(map.get(BindHead.IDCODE)))
            {
                ownerEntity.setIdentityCode(map.get(BindHead.IDCODE));
            }
            else
            {
                errorNum += 1;
                errorDescription.add("证件号码不合法: <br/>" + gson.toJson(map));
                continue;
            }

            Long authTime=Convert.time2num(map.get(BindHead.AUTHTIME));
            ownerEntity.setAuthenticationTime(authTime);

            //检查用户绑定的每一个物业编号以及该绑定关系是否存在
            try
            {
                String propertyCodeList = map.get(BindHead.PROPERTYCODELIST);
                boolean check=true;
                for (String propertyCode : Arrays.asList(propertyCodeList.split(";")))
                {
                    PropertyEntity propertyEntity = propertyDao.getByCode(propertyCode);
                    if (propertyEntity == null)
                    {
                        errorNum += 1;
                        errorDescription.add("该物业编号不存在:"+propertyCode+" <br/>" + gson.toJson(map));
                        check=false;
                        break;
                    }
                    else if (propertyOwnerInfoDao.
                            getByPhonePropertyID(ownerEntity.getPhone(), propertyEntity.getId())!=null)
                    {
                        errorNum += 1;
                        errorDescription.add("该用户已和该物业绑定:"+propertyCode+" <br/>" + gson.toJson(map));
                        check=false;
                        break;
                    }
                }
                if (!check)
                    continue;
            }
            catch (Exception e)
            {
                errorNum += 1;
                errorDescription.add("非法的物业编号: <br/>" + gson.toJson(map));
                continue;
            }

            //经过检查后将用户信息和绑定信息写入数据库
            try
            {
                OwnerEntity ownerEntity1 = (OwnerEntity) userDao.getUserInfoByPhoneRole(ownerEntity.getPhone(), UserType.OWNER);
                if (ownerEntity1 != null)
                {
                    ownerEntity1.setName(ownerEntity.getName());
                    ownerEntity1.setSex(ownerEntity.getSex());
                    ownerEntity1.setBirthday(ownerEntity.getBirthday());
                    ownerEntity1.setIdentityType(ownerEntity.getIdentityType());
                    ownerEntity1.setIdentityCode(ownerEntity.getIdentityCode());
                    ownerEntity1.setAuthenticationTime(ownerEntity.getAuthenticationTime());
                    ownerEntity1.setUrgentName(ownerEntity.getUrgentName());
                    ownerEntity1.setUrgentPhone(ownerEntity.getUrgentPhone());
                    baseDao.save(ownerEntity1);
                }
                else
                {
                    baseDao.save(ownerEntity);
                }
                String propertyCodeList = map.get(BindHead.PROPERTYCODELIST);
                for (String propertyCode : Arrays.asList(propertyCodeList.split(";")))
                {
                    PropertyEntity propertyEntity = propertyDao.getByCode(propertyCode);
                    PropertyOwnerInfoEntity propertyOwnerInfoEntity = new PropertyOwnerInfoEntity();
                    propertyOwnerInfoEntity.setStatus(BindStatus.CHECKED);
                    propertyOwnerInfoEntity.setUserRole(UserType.OWNER);
                    propertyOwnerInfoEntity.setPhone(ownerEntity.getPhone());
                    propertyOwnerInfoEntity.setPropertyId(propertyEntity.getId());
                    propertyOwnerInfoEntity.setBuildingId(propertyEntity.getBuildingId());
                    baseDao.save(propertyOwnerInfoEntity);
                }
                succNum+=1;
            }
            catch (Exception e)
            {
                errorNum += 1;
                errorDescription.add("写入失败: <br/>"+e.getMessage() + gson.toJson(map));
            }

        }

        excelImportReport.setErrorNum(errorNum);
        excelImportReport.setErrorDescription(errorDescription);
        excelImportReport.setSuccNum(succNum);
        return excelImportReport;
    }

    @Override
    public ExcelImportReport importSecret(List<Map<String, String>> result)
    {
        ExcelImportReport excelImportReport=new ExcelImportReport();
        Integer errorNum=0;
        Integer succNum=0;
        Gson gson=new Gson();
        List<String> errorDescription=new ArrayList<>();
        for (Map<String,String> map:result)
        {
            SsidSecretEntity ssidSecretEntity=new SsidSecretEntity();
            LogUtil.E(GsonUtil.getGson().toJson(map));
            //设置锁的编码
            String code=map.get(SecretHead.CODE);
            if (code.equals(""))
            {
                errorNum+=1;
                errorDescription.add("锁的编号不能为空: "+gson.toJson(map));
                continue;
            }
            else
            {
                if (ssidSecretDao.getBySymbol(code)==null)
                {
                    ssidSecretEntity.setSymbol(code);
                }
                else
                {
                    errorNum+=1;
                    errorDescription.add("已导入该锁: <br/>"+gson.toJson(map));
                    continue;
                }
            }

            if (map.get(SecretHead.TYPE).equals("蓝牙"))
            {
                ssidSecretEntity.setType(SecretType.LANYA);
                if (map.get(SecretHead.SECRET).equals(""))
                {
                    errorNum += 1;
                    errorDescription.add("蓝牙密钥不能为空: <br/>" + gson.toJson(map));
                    continue;
                }
            }
            else if (map.get(SecretHead.TYPE).equals("wifi"))
            {
                ssidSecretEntity.setType(SecretType.WIFI);
                if (map.get(SecretHead.SECRET).equals("")||map.get(SecretHead.PASSWORD).equals(""))
                {
                    errorNum += 1;
                    errorDescription.add("wifi密钥或者密码不能为空: <br/>" + gson.toJson(map));
                    continue;
                }
            }
            else
            {
                errorNum += 1;
                errorDescription.add("无线类型错误: <br/>" + gson.toJson(map));
                continue;
            }

            //可选导入项
            if (map.get(SecretHead.CONTROLTYPE)!=null&&!map.get(SecretHead.CONTROLTYPE).equals(""))
            {
                try
                {
                    switch (map.get(SecretHead.CONTROLTYPE))
                    {
                        case "园区":
                            VillageEntity villageEntity = (VillageEntity) baseDao.getByCode(map.get(SecretHead.CONTROLCODE), Entity.VILLAGE);
                            if (villageEntity == null)
                            {
                                errorNum += 1;
                                errorDescription.add("该园区不存在: <br/>" + gson.toJson(map));
                                continue;
                            }
                            ssidSecretEntity.setControlId(villageEntity.getId());
                            ssidSecretEntity.setControlType(SsidControlType.VILLAGE);
                            break;
                        case "道闸":
                            BrakeEntity brakeEntity = (BrakeEntity) baseDao.getByCode(map.get(SecretHead.CONTROLCODE), Entity.BRAKE);
                            if (brakeEntity == null)
                            {
                                errorNum += 1;
                                errorDescription.add("该道闸不存在: <br/>" + gson.toJson(map));
                                continue;
                            }
                            ssidSecretEntity.setControlId(brakeEntity.getId());
                            ssidSecretEntity.setControlType(SsidControlType.BRAKE);
                            break;
                        case "楼栋":
                            BuildingEntity buildingEntity = (BuildingEntity) baseDao.getByCode(map.get(SecretHead.CONTROLCODE), Entity.BUILDING);
                            if (buildingEntity == null)
                            {
                                errorNum += 1;
                                errorDescription.add("该楼栋不存在: <br/>" + gson.toJson(map));
                                continue;
                            }
                            ssidSecretEntity.setControlType(SsidControlType.BUILDING);
                            ssidSecretEntity.setControlId(buildingEntity.getId());
                            break;
                        default:
                            errorNum += 1;
                            errorDescription.add("控制对象类型错误: <br/>" + gson.toJson(map));
                            continue;
                    }
                }
                catch (Exception e)
                {
                    errorNum += 1;
                    errorDescription.add("该数据异常: <br/>" + gson.toJson(map));
                    continue;
                }
            }

            ssidSecretEntity.setSecret(map.get(SecretHead.SECRET));
            if (ssidSecretEntity.getType()==SecretType.WIFI)
            {
                ssidSecretEntity.setPassword(map.get(SecretHead.PASSWORD));
            }

            try
            {
                baseDao.save(ssidSecretEntity);
                succNum+=1;
            }
            catch (Exception e)
            {
                errorNum += 1;
                errorDescription.add("写入失败: <br/>" + gson.toJson(map));
            }
        }
        excelImportReport.setErrorNum(errorNum);
        excelImportReport.setSuccNum(succNum);
        excelImportReport.setErrorDescription(errorDescription);
        return excelImportReport;
    }

    @Override
    public ExcelImportReport importVillage(List<Map<String, String>> result)
    {
        ExcelImportReport excelImportReport=new ExcelImportReport();
        Integer errorNum=0;
        Integer succNum=0;
        Gson gson=new Gson();
        List<String> errorDescription=new ArrayList<>();
        for (Map<String,String> map:result)
        {
            VillageEntity villageEntity=new VillageEntity();
            String code=map.get(VillageHead.CODE);
            if (code.equals(""))
            {
                errorNum+=1;
                errorDescription.add("园区编号不能为空: "+gson.toJson(map));
                continue;
            }
            else
            {
                if (baseDao.getByCode(code, Entity.VILLAGE)==null)
                {
                    villageEntity.setCode(code);
                }
                else
                {
                    errorNum+=1;
                    errorDescription.add("已导入该园区: <br/>"+gson.toJson(map));
                    continue;
                }
            }

            String name=map.get(VillageHead.NAME);
            if (name.equals(""))
            {
                errorNum+=1;
                errorDescription.add("园区名称不能为空: <br/>"+gson.toJson(map));
                continue;
            }
            villageEntity.setName(name);

            try
            {
                baseDao.save(villageEntity);
                succNum+=1;
            }
            catch (Exception e)
            {
                errorNum += 1;
                errorDescription.add("写入失败: <br/>" + gson.toJson(map));
            }
        }
        excelImportReport.setErrorNum(errorNum);
        excelImportReport.setSuccNum(succNum);
        excelImportReport.setErrorDescription(errorDescription);
        return excelImportReport;
    }

    @Override
    public ExcelImportReport importBuilding(List<Map<String, String>> result)
    {
        ExcelImportReport excelImportReport=new ExcelImportReport();
        Integer errorNum=0;
        Integer succNum=0;
        Gson gson=new Gson();
        List<String> errorDescription=new ArrayList<>();
        for (Map<String,String> map:result)
        {
            BuildingEntity buildingEntity=new BuildingEntity();
            String code=map.get(BuildingHead.CODE);
            if (code.equals(""))
            {
                errorNum+=1;
                errorDescription.add("楼栋编号不能为空: "+gson.toJson(map));
                continue;
            }
            else
            {
                if (baseDao.getByCode(code, Entity.BUILDING)==null)
                {
                    buildingEntity.setBuildingCode(code);
                }
                else
                {
                    errorNum+=1;
                    errorDescription.add("已导入该楼栋: <br/>"+gson.toJson(map));
                    continue;
                }
            }

            String name=map.get(BuildingHead.NAME);
            if (name.equals(""))
            {
                errorNum+=1;
                errorDescription.add("楼栋名称不能为空: <br/>"+gson.toJson(map));
                continue;
            }
            buildingEntity.setBuildingName(name);

            try
            {
                VillageEntity villageEntity= (VillageEntity) baseDao.getByCode(map.get(BuildingHead.VILLAGECODE), Entity.VILLAGE);
                if (villageEntity==null)
                {
                    errorNum+=1;
                    errorDescription.add("该园区不存在: <br/>"+gson.toJson(map));
                    continue;
                }
                else
                {
                    buildingEntity.setVillageId(villageEntity.getId());
                }
            }
            catch (Exception e)
            {
                errorNum+=1;
                errorDescription.add("数据异常: <br/>"+gson.toJson(map));
                continue;
            }

            try
            {
                baseDao.save(buildingEntity);
                succNum+=1;
            }
            catch (Exception e)
            {
                errorNum += 1;
                errorDescription.add("写入失败: <br/>" + gson.toJson(map));
            }
        }
        excelImportReport.setErrorNum(errorNum);
        excelImportReport.setSuccNum(succNum);
        excelImportReport.setErrorDescription(errorDescription);
        return excelImportReport;
    }
}
