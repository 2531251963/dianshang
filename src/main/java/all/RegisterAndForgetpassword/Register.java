package all.RegisterAndForgetpassword;

import all.util.JdbcUtil;
import all.util.SnowFlakeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {

    public boolean userregister(String phonenumber, String password){

        boolean b = false;

        String sql = "select * from  where phonenumber='"+ phonenumber +"'";


        try{

            Connection conn = JdbcUtil.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if(!rs.next()){
                sql = "insert into User(userid,phoneNumber,password) values('"+ SnowFlakeUtil.getId() +"','"+phonenumber+"','"+password+"')";
                stm.execute(sql);
                b = true;
            }

            rs.close();
            stm.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        if(b)
        {
            return true;
        }
        else {
            return false;}
    }

}
