package estate.dao.impl;

import estate.common.Config;
import estate.common.config.FeeType;
import estate.dao.FeeItemDao;
import estate.entity.database.FeeItemEntity;
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
 * Created by kangbiao on 15-9-15.
 *
 */
@Repository("feeItemDao")
public class FeeItemDaoImpl implements FeeItemDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    public TableData getList(TableFilter tableFilter,byte feeType)
    {
        Session session=getSession();
        TableData tableData=new TableData(true);
        Query query;

        StringBuilder hql=new StringBuilder("from FeeItemEntity t where t.feeType=").append(feeType);
        if (tableFilter.getSearchValue()!=null)
            hql.append(" and t.name like('%").append(tableFilter.getSearchValue()).append("%')");
        if (tableFilter.getVillageId()!=null)
            hql.append(" and t.villageId=").append(tableFilter.getVillageId());
        hql.append(" order by t.villageId,t.addTime desc");
        query=session.createQuery(hql.toString());

        Integer count=query.list().size();
        query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter.getLength());
        List list=query.list();

        tableData.setRecordsTotal(this.count(feeType));
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(list);
        return tableData;
    }

    @Override
    public void deleteByFeeTypeID(byte feeType, Integer id)
    {
        Session session=getSession();
        String hql="delete from FeeItemEntity t where t.feeType=:feeType and t.id=:id";
        session.createQuery(hql).setByte("feeType",feeType).setInteger("id",id).executeUpdate();

    }

    public Integer count(byte feeType)
    {
        Session session=getSession();
        String hql="select count(*) from FeeItemEntity t where t.feeType=:feeType";
        return ((Long)session.createQuery(hql).setByte("feeType", feeType).uniqueResult()).intValue();
    }

    @Override
    public Object getParkLotByVillageIdType(Integer villageID, String type)
    {
        Session session=getSession();
        String hql="from FeeItemEntity t where t.name=:parkLotType and t.villageId=:villageID and t.feeType=:feeType";
        List list=session.createQuery(hql)
                .setString("parkLotType", type)
                .setInteger("villageID", villageID)
                .setByte("feeType", FeeType.PARKING_LOT)
                .list();
        if (list.size()>0)
            return list.get(0);
        return null;
    }
}
