package all.login.Service;

import all.login.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {
    //根据电话号码 查询用户名帐号
    public User selectUserByP(Statement stmt,ResultSet rs,String phonenumber){

        User user=new User();
        String sql="select (userid,phonenumber,password) from user where phonenumber ='"+phonenumber+"'";
        try {
            rs=stmt.executeQuery(sql);
        } catch (SQLException e) {
            //System.out.println("selectUserByP");
            e.printStackTrace();
        }
        try {
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setPhoneNumber(rs.getString("phonenumber"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("没有搜索到user信息");
            e.printStackTrace();
        }
        return null;
    }
}
