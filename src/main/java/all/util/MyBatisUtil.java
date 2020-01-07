package all.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName MyBatisUtil
 * @Description Mybatis工具类
 * @Author Liyihe
 * @Date 2020/01/06 下午10:24
 * @Version 1.0
 */
public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSessionFactory getsqlSessionFactory(){
        if (sqlSessionFactory==null){
            try {
                InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession getSqlSession(){
        return getsqlSessionFactory().openSession();
    }

    public static void closeSqlSession(SqlSession sqlSession){
        sqlSession.close();
    }
}
