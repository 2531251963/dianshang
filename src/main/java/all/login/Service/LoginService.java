package all.login.Service;

import all.login.entity.User;
import all.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginService {
    Connection conn= null;
    Statement stmt=null;
    ResultSet rs=null;

    LoginDao loginDao=new LoginDao();

    public LoginService(){
        conn= JdbcUtil.getConnection();
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User selectUserByPhoneNumber(String phonenumber){
        User user = loginDao.selectUserByP(stmt,rs,phonenumber);
        //数据库没有搜索到用户信息 ==》提示注册
        if(user!=null){
            return user;
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        JdbcUtil.close_2(conn,stmt,rs);
    }


}
