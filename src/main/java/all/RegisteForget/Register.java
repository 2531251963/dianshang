package all.RegisteForget;

import all.util.JdbcUtil;
import all.util.SnowFlakeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {

    public void userregister(String phonenumber, String password){


        //查看要添加的手机号是否存在
        String sql = "select * from  where phonenumber='"+ phonenumber +"'";


        try{
            Connection conn = JdbcUtil.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            //若手机号不存在
            if(!rs.next()){
                //添加用户信息
                sql = "insert into user(userid,phoneNumber,password) values('"+ SnowFlakeUtil.getId() +"','"+phonenumber+"','"+password+"')";
                stm.execute(sql);

            }
            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    public void userupdate(String phonenumber, String password){



        String sql = "select * from  where phonenumber='"+ phonenumber +"'";


        try{

            Connection conn = JdbcUtil.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if(rs.next()){

                sql = "update user set user.password= '"+password+"' WHERE dianshang.user.phonenumber='"+phonenumber+"'";

                stm.execute(sql);

            }

            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

   }

}
