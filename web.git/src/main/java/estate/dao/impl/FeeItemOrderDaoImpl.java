package estate.dao.impl;

import estate.common.util.LogUtil;
import estate.dao.FeeItemOrderDao;
import estate.entity.database.FeeItemOrderEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-9-24.
 *
 */
@Repository("feeItemOrderDao")
public class FeeItemOrderDaoImpl implements FeeItemOrderDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public ArrayList<FeeItemOrderEntity> getFeeItemOrdersByPropertyID(Integer id)
    {
        Session session=getSession();
        String hql="from FeeItemOrderEntity t where t.propertyId=:id";
        List list=session.createQuery(hql).setInteger("id",id).list();
        if (list.size()>0)
            return (ArrayList<FeeItemOrderEntity>) list;
        return null;
    }

    @Override
    public void deleteAllByFeeItemID(Integer id)
    {
        if (id==null)
            return;
        Session session=getSession();
        String hql="delete from FeeItemOrderEntity t where t.feeItemId=:feeItemId";
        session.createQuery(hql).setInteger("feeItemId",id).executeUpdate();
    }

    @Override
    public FeeItemOrderEntity getByPropertyIdFeeItemId(Integer propertyID, Integer feeItemID)
    {
        Session session=getSession();
        String hql="from FeeItemOrderEntity t where t.feeItemId=:feeItemId and t.propertyId=:propertyId";
        List list=session.createQuery(hql).setInteger("feeItemId",feeItemID).setInteger("propertyId",propertyID).list();
        if (list.size()>0)
            return (FeeItemOrderEntity) list.get(0);
        return null;
    }
}
