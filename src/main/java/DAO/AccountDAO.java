package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    public Account getAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                account.setAccount_id(rs.getInt("account_id"));
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            /** First statement to check if username exists in DB **/
            String sql = "SELECT username FROM account WHERE username = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next() == false) {
                /** Once we know the username doesn't exist, we can freely insert the new account **/
                String sql1 = "INSERT INTO account (username, password) VALUES (?, ?);" ;
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
    
                preparedStatement1.setString(1, account.getUsername());
                preparedStatement1.setString(2, account.getPassword());
                
                preparedStatement1.executeUpdate();
                return getAccount(account);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
