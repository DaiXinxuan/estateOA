package estate.dao.impl;

import estate.dao.BillDao;
import estate.entity.database.PropertyBillEntity;
import estate.entity.database.PropertyOwnerInfoEntity;
import estate.entity.database.UserBillEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kangbiao on 15-10-6.
 *
 */
@Repository("billDao")
public class BillDaoImpl implements BillDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public TableData getList(TableFilter tableFilter)
    {
        Session session=getSession();
        TableData tableData = new TableData(true);
        Query query;
        StringBuilder hql=new StringBuilder("from UserBillEntity t where 1=1 ");
        if (tableFilter.getSearchValue()!=null)
        {
            hql.append(" and (t.phone like('%").append(tableFilter.getSearchValue()).append("%')");
            String hqlTemp="from PropertyOwnerInfoEntity t where t.propertyEntity.location like (:location) or t.propertyEntity.code like (:code)";
            ArrayList<PropertyOwnerInfoEntity> list= (ArrayList<PropertyOwnerInfoEntity>) session.createQuery(hqlTemp).setString("location","%"+tableFilter.getSearchValue()+"%").setString("code", "%"+tableFilter.getSearchValue()+"%").list();
            if (list.size()>0)
            {
                for (PropertyOwnerInfoEntity propertyOwnerInfoEntity:list)
                {
                    hql.append(" or t.phone like('%").append(propertyOwnerInfoEntity.getPhone()).append("%')");
                }
            }
            hql.append(")");
        }
        if (tableFilter.getStatus()!=null)
            hql.append(" and t.payStatus=").append(tableFilter.getStatus());
        if (tableFilter.getStartTime()!=null&&tableFilter.getEndTime()==null)
            hql.append(" and t.updateTime>=").append(tableFilter.getStartTime());
        if (tableFilter.getStartTime()==null&&tableFilter.getEndTime()!=null)
            hql.append(" and t.updateTime<=").append(tableFilter.getEndTime());
        if (tableFilter.getStartTime()!=null&&tableFilter.getEndTime()!=null)
            hql.append(" and t.updateTime>=").append(tableFilter.getStartTime()).append(" and t.updateTime<=").append(tableFilter.getEndTime());
        hql.append(" order by t.updateTime desc");
        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        List list=query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter.getLength()).list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(list);
        return tableData;
    }

    @Override
    public ArrayList<PropertyBillEntity> getPropertyBillByPropertyID(Integer propertyID, Byte status, Long startTime, Long endTime)
    {
        Session session=getSession();
        StringBuilder hql=new StringBuilder("from PropertyBillEntity t where t.propertyId=").append(propertyID);
        if (status!=null)
            hql.append(" and t.payStatus=").append(status);
        if (startTime!=null&&endTime==null)
            hql.append(" and t.billGenerationTime>=").append(startTime);
        if (startTime==null&&endTime!=null)
            hql.append(" and t.billGenerationTime<=").append(endTime);
        if (startTime!=null&&endTime!=null)
            hql.append(" and t.billGenerationTime>=").append(startTime).append(" and t.billGenerationTime<=").append(endTime);
        List list=session.createQuery(hql.toString()).list();
        if (list.size()>0)
            return (ArrayList<PropertyBillEntity>) list;
        return null;
    }

    @Override
    public ArrayList<UserBillEntity> getUserBillByPhone(String phone, Byte status, Long startTime, Long endTime)
    {
        Session session=getSession();
        StringBuilder hql=new StringBuilder("from UserBillEntity t where t.phone='").append(phone).append("'");
        if (status!=null)
            hql.append(" and t.payStatus=").append(status);
        if (startTime!=null&&endTime==null)
            hql.append(" and t.updateTime>=").append(startTime);
        if (startTime==null&&endTime!=null)
            hql.append(" and t.updateTime<=").append(endTime);
        if (startTime!=null&&endTime!=null)
            hql.append(" and t.updateTime>=").append(startTime).append(" and t.updateTime<=").append(endTime);
        List list=session.createQuery(hql.toString()).list();
        if (list.size()>0)
            return (ArrayList<UserBillEntity>) list;
        return null;
    }
}
