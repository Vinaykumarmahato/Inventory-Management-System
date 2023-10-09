package com.inventory.DAO;

// Source code is decompiled from a .class file using FernFlower decompiler.
package com.inventory.DAO;

import com.inventory.DTO.UserDTO;
import com.inventory.Database.ConnectionFactory;
import com.inventory.UI.UsersPage;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserDAO {
    Connection conn = null;
    PreparedStatement prepStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public UserDAO() {
        try {
            this.conn = (new ConnectionFactory()).getConn();
            this.statement = this.conn.createStatement();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public void addUserDAO(UserDTO userDTO, String userType) {
        try {
            String var10000 = userDTO.getFullName();
            String query = "SELECT * FROM users WHERE name='" + var10000 + "' AND location='" + userDTO.getLocation()
                    + "' AND phone='" + userDTO.getPhone() + "' AND usertype='" + userDTO.getUserType() + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                JOptionPane.showMessageDialog((Component) null, "User already exists");
            } else {
                this.addFunction(userDTO, userType);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void addFunction(UserDTO userDTO, String userType) {
        try {
            String username = null;
            String password = null;
            String oldUsername = null;
            String resQuery = "SELECT * FROM users";
            this.resultSet = this.statement.executeQuery(resQuery);
            if (!this.resultSet.next()) {
                username = "root";
                password = "root";
            }

            String query = "INSERT INTO users (name,location,phone,username,password,usertype) VALUES(?,?,?,?,?,?)";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, userDTO.getFullName());
            this.prepStatement.setString(2, userDTO.getLocation());
            this.prepStatement.setString(3, userDTO.getPhone());
            this.prepStatement.setString(4, userDTO.getUsername());
            this.prepStatement.setString(5, userDTO.getPassword());
            this.prepStatement.setString(6, userDTO.getUserType());
            this.prepStatement.executeUpdate();
            if ("ADMINISTRATOR".equals(userType)) {
                JOptionPane.showMessageDialog((Component) null, "New administrator added.");
            } else {
                JOptionPane.showMessageDialog((Component) null, "New employee added.");
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    public void editUserDAO(UserDTO userDTO) {
        try {
            String query = "UPDATE users SET name=?,location=?,phone=?,usertype=? WHERE username=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, userDTO.getFullName());
            this.prepStatement.setString(2, userDTO.getLocation());
            this.prepStatement.setString(3, userDTO.getPhone());
            this.prepStatement.setString(4, userDTO.getUserType());
            this.prepStatement.setString(5, userDTO.getUsername());
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Updated Successfully.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void deleteUserDAO(String username) {
        try {
            String query = "DELETE FROM users WHERE username=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, username);
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "User Deleted.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        (new UsersPage()).loadDataSet();
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT * FROM users";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getUserDAO(String username) {
        try {
            String query = "SELECT * FROM users WHERE username='" + username + "'";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public void getFullName(UserDTO userDTO, String username) {
        try {
            String query = "SELECT * FROM users WHERE username='" + username + "' LIMIT 1";
            this.resultSet = this.statement.executeQuery(query);
            String fullName = null;
            if (this.resultSet.next()) {
                fullName = this.resultSet.getString(2);
            }

            userDTO.setFullName(fullName);
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public ResultSet getUserLogsDAO() {
        try {
            String query = "SELECT users.name,userlogs.username,in_time,out_time,location FROM userlogs INNER JOIN users on userlogs.username=users.username";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public void addUserLogin(UserDTO userDTO) {
        try {
            String query = "INSERT INTO userlogs (username, in_time, out_time) values(?,?,?)";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, userDTO.getUsername());
            this.prepStatement.setString(2, userDTO.getInTime());
            this.prepStatement.setString(3, userDTO.getOutTime());
            this.prepStatement.executeUpdate();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public ResultSet getPassDAO(String username, String password) {
        try {
            String query = "SELECT password FROM users WHERE username='" + username + "' AND password='" + password
                    + "'";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        return this.resultSet;
    }

    public void changePass(String username, String password) {
        try {
            String query = "UPDATE users SET password=? WHERE username='" + username + "'";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, password);
            this.prepStatement.setString(2, username);
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Password has been changed.");
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; ++col) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector();

        while (resultSet.next()) {
            Vector<Object> vector = new Vector();

            for (int col = 1; col <= colCount; ++col) {
                vector.add(resultSet.getObject(col));
            }

            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
