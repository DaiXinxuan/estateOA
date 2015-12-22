package estate.dao.impl;

import estate.dao.SsidSecretDao;
import estate.entity.database.SsidSecretEntity;
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
 * Created by kangbiao on 15-9-21.
 *
 */
@Repository("ssidSecretDao")
public class SsidSecretDaoImpl implements SsidSecretDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public SsidSecretEntity getBySymbol(String symbol)
    {
        Session session=getSession();
        String hql="from SsidSecretEntity s where s.symbol=:symbol";
        List list=session.createQuery(hql).setString("symbol",symbol).list();
        if (list.size()>0)
            return (SsidSecretEntity)list.get(0);
        else
            return null;
    }

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        Session session = getSession();
        TableData tableData = new TableData(true);
        ArrayList<SsidSecretEntity> entities=new ArrayList<>();
        Query query;
        StringBuilder hql=new StringBuilder("from SsidSecretEntity t where 1=1 ");
        if (tableFilter.getSearchValue()!=null)
            hql.append(" and t.symbol like('%")
                    .append(tableFilter.getSearchValue())
                    .append("%')");
        if (tableFilter.getType()!=null)
            hql.append(" and t.type=").append(tableFilter.getType());
        if (tableFilter.getStatus()!=null)
        {
            if (tableFilter.getStatus()==4)
                hql.append(" and (t.controlType=null or t.controlType='') ");
            else
                hql.append(" and t.controlType=").append(tableFilter.getStatus());
        }
        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities= (ArrayList<SsidSecretEntity>) query
                .setFirstResult(tableFilter.getStart())
                .setMaxResults(tableFilter.getLength())
                .list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public ArrayList<SsidSecretEntity> getByControTypeControId(Integer contrlId, Byte type)
    {
        Session session=getSession();
        String hql="from SsidSecretEntity t where t.controlId=:id and t.controlType=:type";
        Query query=session.createQuery(hql).setInteger("id",contrlId).setByte("type",type);
        List list=query.list();
        if (list.size()>0)
            return (ArrayList<SsidSecretEntity>) list;
        return null;
    }

    @Override
    public ArrayList<SsidSecretEntity> getNotUsed()
    {
        Session session=getSession();
        String hql="from SsidSecretEntity t where t.controlId=null";
        List list=session.createQuery(hql).list();
        if (list.size()>0)
            return (ArrayList<SsidSecretEntity>) list;
        return null;
    }
}
