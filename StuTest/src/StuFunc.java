import java.sql.*;
import java.util.regex.*;

public class StuFunc {
    public Connection ConnectSql(){
        Connection con = null;
        String user = "root";
        String password = "MZL_Alu819";
        String driver = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://localhost:3306/students"+"?serverTimezone=GMT%2B8&characterEncoding=utf8";
        String url = "jdbc:mysql://localhost:3306/students?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";

        try {
            Class.forName(driver);
            System.out.println("JDBC 驱动程序已装载");
            con = DriverManager.getConnection(url, user, password);
            return con;
        }catch (Exception ex){
            System.out.println("JDBC 装载失败");
            ex.printStackTrace();
            return null;
        }
    }

    public void Add(Long id, String name, String classNo, String course1, String course2, String course3){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        String sql = "insert tb_student values("
                +id + ", '" + name + "', '" +  classNo + "' , '" + course1 + "', '" + course2 + "', '" + course3+ "');";

        try{
            con = ConnectSql();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                con.close();   //在这里关闭 connection
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public void DeleteById(String id){
        Connection con = null;
        Statement statement = null;
        String sql = "delete from tb_student where id = " + id + ";";
        try{
            con = ConnectSql();
            statement = con.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                statement.close();
                con.close();
                System.out.println("删除成功");
            }catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("删除失败");
            }
        }
    }

    public void Update(Long id, String name, String classNo, String course1, String course2, String course3){
        Connection con =null;
        Statement statement = null;
        String sql = "update tb_student set " +
                "name = '" + name + "', class = '" + classNo +
                "', course1 = '" + course1 + "', course2 = '" + course2 + "', course3 = '" + course3 + "'" +
                "where Id = " + id + " ;";
        try {
            con = ConnectSql();
            statement = con.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                statement.close();
                con.close();
                System.out.println("修改成功");
            }catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("修改失败");
            }
        }
    }

    public String[][] Select(String temp){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql;

        Table table = new Table();
        int columns = table.getColumnCount();  //获取列数
        int rowIndex = 0;   //行标记

        String sql_some = "select* from tb_student where Id = " + temp + " or name = '" + temp + "' " +
                "or class = '" + temp + "' or course1 = '" + temp + "' " +
                "or course2 = '" + temp + "' or course3 = '" + temp + "';" ;
        String sql_all = "select * from tb_student;";

        try {
            con = ConnectSql();
            if(temp.equals(""))
                sql = sql_all;
            else
                sql = sql_some;

            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            resultSet.last();
            int rowsCount = resultSet.getRow();  //获取行数

            resultSet.beforeFirst();
            String[][] rowData = new String[rowsCount][columns]; //定义动态二维数组

            while (resultSet.next()) {
                for (int i = 0; i < table.getColumnCount(); i++) {
                    rowData[rowIndex][i] = resultSet.getString(i + 1);
                }
                rowIndex++;
            }

            return rowData;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
                System.out.println("查询成功");
            }catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("查询失败");
            }catch (Exception ex){
                System.out.println("查询失败" + ex.toString());
            }
        }
    }


    public boolean idRegex(String id, String name, String classno){
        String pattern =  "2[0-9]{12}$";
        boolean isMatch = Pattern.matches(pattern, id);
        if(isMatch && name != "" && classno != "")
            return true;
        else
            return false;
    }
}
