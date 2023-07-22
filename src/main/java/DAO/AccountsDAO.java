package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountsDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
 //          Write SQL logic here. You should only be inserting with the name column, so that the database may
 //          automatically generate a primary key.
            String sql = "INSERT INTO account (account_id, username, password) values (default, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 
            //write preparedStatement's setString method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()){
                return new Account(keys.getInt(1), account.getUsername(), account.getPassword());
            }
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

        public Account getAccountByUsernameAndPassword(String username, String password){
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet rs = preparedStatement.executeQuery();
       
           
          
            while (rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
            
                
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        
 
}
}