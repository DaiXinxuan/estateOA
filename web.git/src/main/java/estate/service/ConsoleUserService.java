package estate.service;

import estate.entity.database.ConsoleUserEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kangbiao on 15-9-16.
 *
 */
public interface ConsoleUserService
{
    /**
     * 通过电话获取后台管理人员信息
     * @param username
     * @return
     */
    ConsoleUserEntity getConsoleUserByPhone(String username);
}
