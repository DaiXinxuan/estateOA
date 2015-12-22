package estate.service.impl;

import estate.dao.VertifyInfoDao;
import estate.service.VertifyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dxx on 2015/12/21.
 */
@Service("vertifyInfoService")
public class VertifyInfoServiceImpl implements VertifyInfoService{

    @Autowired
    private VertifyInfoDao vertifyInfoDao;

    @Override
    public Object getByPhone(String phone) {
        return vertifyInfoDao.getByPhone(phone);
    }
}
