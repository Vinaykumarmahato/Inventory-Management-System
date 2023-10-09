// Source code is decompiled from a .class file using FernFlower decompiler.
package com.inventory.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {
   static final String driver = "com.mysql.cj.jdbc.Driver";
   static final String url = "jdbc:mysql://localhost:3306/inventory";
   static String username;
   static String password;
   Properties prop;
   Connection conn = null;
   Statement statement = null;
   ResultSet resultSet = null;

   public ConnectionFactory() {
      try {
         this.prop = new Properties();
         this.prop.loadFromXML(new FileInputStream("lib/DBCredentials.xml"));
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      username = this.prop.getProperty("username");
      password = this.prop.getProperty("password");

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", username, password);
         this.statement = this.conn.createStatement();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public Connection getConn() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", username, password);
         System.out.println("Connected successfully.");
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return this.conn;
   }

   public boolean checkLogin(String username, String password, String userType) {
      String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password
            + "' AND usertype='" + userType + "' LIMIT 1";

      try {
         this.resultSet = this.statement.executeQuery(query);
         if (this.resultSet.next()) {
            return true;
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return false;
   }
}
