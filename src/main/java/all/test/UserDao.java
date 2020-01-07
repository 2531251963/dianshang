package all.test;

import all.test.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author Liyihe
 * @Date 2020/01/06 下午10:04
 * @Version 1.0
 */
public interface UserDao {
    @Select("select * from user")
    List<User> findAll();

    //@Update()     @Insert()      @Delete()

}


/**
 * SqlSession session = MyBatisUtil.getSqlSession();
 *         UserDao userDao = session.getMapper(UserDao.class);
 *         List<User> users = userDao.findAll();
 *         for (User u :
 *                 users) {
 *             logger.info(u);
 *         }
 * */