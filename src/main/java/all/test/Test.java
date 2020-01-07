package all.test;

import all.util.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName Test
 * @Description 描述
 * @Author Liyihe
 * @Date 2020/01/06 下午10:02
 * @Version 1.0
 */
public class Test {
    /**
     * 接口方法(函数) !!!入和出!!   用日志打印 普通级别info 异常级别 error
     *
     */
    private static Logger logger = Logger.getLogger(Test.class);
    public String test(String data){
        logger.info("入");

        /**
        * 业务
        */
        try {

        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常打印");
        }

        logger.info("出");

        String returnres="ok";
        return returnres;
    }


    public static void main(String[] args) throws IOException, SQLException {
        //ES
        TransportClient client=EsUtil.getClient();
        //jdbc
        Connection connection=JdbcUtil.getConnection();
        //mybatis      用Mybatis的 别用xml 用注解方式; 示例 UserDao接口
        SqlSession session = MyBatisUtil.getSqlSession();
        //redis
        Jedis jedis=RedisUtil.getJedis();
        //cdn
        CdnUtil.upload("objectname",new File(""));
        CdnUtil.deleteFile("objectname");
        //发送验证码
        SendCodeUtil.sendCode("15510828597");
        //获取唯一ID
        String id=String.valueOf(SnowFlakeUtil.getId());
        //获取token
        String token=TokenUtil.getToken("userid");

    }
}











       /* TransportClient transportClient=EsUtil.getClient();
        List<DiscoveryNode> n=transportClient.listedNodes();
        for (DiscoveryNode no :
                n) {
            System.out.println(no);
            System.out.println(no.getId());
        }*/
      /*  String code=SendCodeUtil.sendCode("17822002550");
        System.out.println(code);*/
       /* Jedis jedis=RedisUtil.getJedis();
        jedis.close();
   *//*     SqlSession session = MyBatisUtil.getSqlSession();
        UserDao userDao = session.getMapper(UserDao.class);
        List<User> users = userDao.findAll();
        for (User u :
                users) {
            logger.info(u);
        }*//*
        Connection connection=JdbcUtil.getConnection();
        PreparedStatement pre=connection.prepareStatement("select * from user");
        ResultSet res=pre.executeQuery();
        while (res.next()){
            logger.info(res.getString("account"));
            logger.info(res.getString("password"));

        }*/