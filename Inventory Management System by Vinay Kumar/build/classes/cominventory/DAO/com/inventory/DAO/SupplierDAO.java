package com.inventory.DAO;

// Source code is decompiled from a .class file using FernFlower decompiler.
package com.inventory.DAO;

import com.inventory.DTO.SupplierDTO;
import com.inventory.Database.ConnectionFactory;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SupplierDAO {
    Connection conn = null;
    Statement statement = null;
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;

    public SupplierDAO() {
        try {
            this.conn = (new ConnectionFactory()).getConn();
            this.statement = this.conn.createStatement();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public void addSupplierDAO(SupplierDTO supplierDTO) {
        try {
            String var10000 = supplierDTO.getFullName();
            String query = "SELECT * FROM suppliers WHERE fullname='" + var10000 + "' AND location='"
                    + supplierDTO.getLocation() + "' AND mobile='" + supplierDTO.getPhone() + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                JOptionPane.showMessageDialog((Component) null, "This supplier already exists.");
            } else {
                this.addFunction(supplierDTO);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void addFunction(SupplierDTO supplierDTO) {
        try {
            String query = "INSERT INTO suppliers VALUES(null,?,?,?,?)";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, supplierDTO.getSuppCode());
            this.prepStatement.setString(2, supplierDTO.getFullName());
            this.prepStatement.setString(3, supplierDTO.getLocation());
            this.prepStatement.setString(4, supplierDTO.getPhone());
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "New supplier has been added successfully.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void editSupplierDAO(SupplierDTO supplierDTO) {
        try {
            String query = "UPDATE suppliers SET fullname=?,location=?,mobile=? WHERE suppliercode=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, supplierDTO.getFullName());
            this.prepStatement.setString(2, supplierDTO.getLocation());
            this.prepStatement.setString(3, supplierDTO.getPhone());
            this.prepStatement.setString(4, supplierDTO.getSuppCode());
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Supplier details have been updated.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void deleteSupplierDAO(String suppCode) {
        try {
            String query = "DELETE FROM suppliers WHERE suppliercode='" + suppCode + "'";
            this.statement.executeUpdate(query);
            JOptionPane.showMessageDialog((Component) null, "Supplier has been removed.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT suppliercode, fullname, location, mobile FROM suppliers";
            this.resultSet = this.statement.executeQuery(query);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getSearchResult(String searchText) {
        try {
            String query = "SELECT suppliercode, fullname, location, mobile FROM suppliers WHERE suppliercode LIKE '%"
                    + searchText + "%' OR location LIKE '%" + searchText + "%' OR fullname LIKE '%" + searchText
                    + "%' OR mobile LIKE '%" + searchText + "%'";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> suppNames = new Vector();

        while (resultSet.next()) {
            suppNames.add(resultSet.getString("fullname"));
        }

        return new DefaultComboBoxModel(suppNames);
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
