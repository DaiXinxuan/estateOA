package estate.dao;

import estate.entity.database.VertifyInfoEnity;

/**
 * Created by dxx on 2015/12/21.
 */
public interface VertifyInfoDao {
    public VertifyInfoEnity getByPhone(String phone);
}
