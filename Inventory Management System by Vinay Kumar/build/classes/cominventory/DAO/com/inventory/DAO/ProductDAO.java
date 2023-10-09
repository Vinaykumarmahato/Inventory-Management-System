package com.inventory.DAO;

// Source code is decompiled from a .class file using FernFlower decompiler.
package com.inventory.DAO;

import com.inventory.DTO.ProductDTO;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductDAO {
    Connection conn = null;
    PreparedStatement prepStatement = null;
    PreparedStatement prepStatement2 = null;
    Statement statement = null;
    Statement statement2 = null;
    ResultSet resultSet = null;
    String suppCode;
    String prodCode;
    String custCode;
    boolean flag = false;

    public ProductDAO() {
        try {
            this.conn = (new ConnectionFactory()).getConn();
            this.statement = this.conn.createStatement();
            this.statement2 = this.conn.createStatement();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public ResultSet getSuppInfo() {
        try {
            String query = "SELECT * FROM suppliers";
            this.resultSet = this.statement.executeQuery(query);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getCustInfo() {
        try {
            String query = "SELECT * FROM customers";
            this.resultSet = this.statement.executeQuery(query);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getProdStock() {
        try {
            String query = "SELECT * FROM currentstock";
            this.resultSet = this.statement.executeQuery(query);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getProdInfo() {
        try {
            String query = "SELECT * FROM products";
            this.resultSet = this.statement.executeQuery(query);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public Double getProdCost(String prodCode) {
        Double costPrice = null;

        try {
            String query = "SELECT costprice FROM products WHERE productcode='" + prodCode + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                costPrice = this.resultSet.getDouble("costprice");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return costPrice;
    }

    public Double getProdSell(String prodCode) {
        Double sellPrice = null;

        try {
            String query = "SELECT sellprice FROM products WHERE productcode='" + prodCode + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                sellPrice = this.resultSet.getDouble("sellprice");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return sellPrice;
    }

    public String getSuppCode(String suppName) {
        try {
            String query = "SELECT suppliercode FROM suppliers WHERE fullname='" + suppName + "'";

            for (this.resultSet = this.statement.executeQuery(query); this.resultSet
                    .next(); this.suppCode = this.resultSet.getString("suppliercode")) {
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.suppCode;
    }

    public String getProdCode(String prodName) {
        try {
            String query = "SELECT productcode FROM products WHERE productname='" + prodName + "'";

            for (this.resultSet = this.statement.executeQuery(query); this.resultSet
                    .next(); this.suppCode = this.resultSet.getString("productcode")) {
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.prodCode;
    }

    public String getCustCode(String custName) {
        try {
            String query = "SELECT customercode FROM suppliers WHERE fullname='" + custName + "'";

            for (this.resultSet = this.statement.executeQuery(query); this.resultSet
                    .next(); this.suppCode = this.resultSet.getString("customercode")) {
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.custCode;
    }

    public boolean checkStock(String prodCode) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + prodCode + "'";

            for (this.resultSet = this.statement.executeQuery(query); this.resultSet.next(); this.flag = true) {
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.flag;
    }

    public void addProductDAO(ProductDTO productDTO) {
        try {
            String var10000 = productDTO.getProdName();
            String query = "SELECT * FROM products WHERE productname='" + var10000 + "' AND costprice='"
                    + productDTO.getCostPrice() + "' AND sellprice='" + productDTO.getSellPrice() + "' AND brand='"
                    + productDTO.getBrand() + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                JOptionPane.showMessageDialog((Component) null, "Product has already been added.");
            } else {
                this.addFunction(productDTO);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void addFunction(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO products VALUES(null,?,?,?,?,?)";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, productDTO.getProdCode());
            this.prepStatement.setString(2, productDTO.getProdName());
            this.prepStatement.setDouble(3, productDTO.getCostPrice());
            this.prepStatement.setDouble(4, productDTO.getSellPrice());
            this.prepStatement.setString(5, productDTO.getBrand());
            String query2 = "INSERT INTO currentstock VALUES(?,?)";
            this.prepStatement2 = this.conn.prepareStatement(query2);
            this.prepStatement2.setString(1, productDTO.getProdCode());
            this.prepStatement2.setInt(2, productDTO.getQuantity());
            this.prepStatement.executeUpdate();
            this.prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Product added and ready for sale.");
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public void addPurchaseDAO(ProductDTO productDTO) {
        String prodCode;
        try {
            prodCode = "INSERT INTO purchaseinfo VALUES(null,?,?,?,?,?)";
            this.prepStatement = this.conn.prepareStatement(prodCode);
            this.prepStatement.setString(1, productDTO.getSuppCode());
            this.prepStatement.setString(2, productDTO.getProdCode());
            this.prepStatement.setString(3, productDTO.getDate());
            this.prepStatement.setInt(4, productDTO.getQuantity());
            this.prepStatement.setDouble(5, productDTO.getTotalCost());
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Purchase log added.");
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        prodCode = productDTO.getProdCode();
        String query;
        if (this.checkStock(prodCode)) {
            try {
                query = "UPDATE currentstock SET quantity=quantity+? WHERE productcode=?";
                this.prepStatement = this.conn.prepareStatement(query);
                this.prepStatement.setInt(1, productDTO.getQuantity());
                this.prepStatement.setString(2, prodCode);
                this.prepStatement.executeUpdate();
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        } else if (!this.checkStock(prodCode)) {
            try {
                query = "INSERT INTO currentstock VALUES(?,?)";
                this.prepStatement = this.conn.prepareStatement(query);
                this.prepStatement.setString(1, productDTO.getProdCode());
                this.prepStatement.setInt(2, productDTO.getQuantity());
                this.prepStatement.executeUpdate();
            } catch (SQLException var4) {
                var4.printStackTrace();
            }
        }

        this.deleteStock();
    }

    public void editProdDAO(ProductDTO productDTO) {
        try {
            String query = "UPDATE products SET productname=?,costprice=?,sellprice=?,brand=? WHERE productcode=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, productDTO.getProdName());
            this.prepStatement.setDouble(2, productDTO.getCostPrice());
            this.prepStatement.setDouble(3, productDTO.getSellPrice());
            this.prepStatement.setString(4, productDTO.getBrand());
            this.prepStatement.setString(5, productDTO.getProdCode());
            String query2 = "UPDATE currentstock SET quantity=? WHERE productcode=?";
            this.prepStatement2 = this.conn.prepareStatement(query2);
            this.prepStatement2.setInt(1, productDTO.getQuantity());
            this.prepStatement2.setString(2, productDTO.getProdCode());
            this.prepStatement.executeUpdate();
            this.prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Product details updated.");
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public void editPurchaseStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + code + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                String query2 = "UPDATE currentstock SET quantity=quantity-? WHERE productcode=?";
                this.prepStatement = this.conn.prepareStatement(query2);
                this.prepStatement.setInt(1, quantity);
                this.prepStatement.setString(2, code);
                this.prepStatement.executeUpdate();
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public void editSoldStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + code + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                String query2 = "UPDATE currentstock SET quantity=quantity+? WHERE productcode=?";
                this.prepStatement = this.conn.prepareStatement(query2);
                this.prepStatement.setInt(1, quantity);
                this.prepStatement.setString(2, code);
                this.prepStatement.executeUpdate();
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public void deleteStock() {
        try {
            String query = "DELETE FROM currentstock WHERE productcode NOT IN(SELECT productcode FROM purchaseinfo)";
            String query2 = "DELETE FROM salesinfo WHERE productcode NOT IN(SELECT productcode FROM products)";
            this.statement.executeUpdate(query);
            this.statement.executeUpdate(query2);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void deleteProductDAO(String code) {
        try {
            String query = "DELETE FROM products WHERE productcode=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setString(1, code);
            String query2 = "DELETE FROM currentstock WHERE productcode=?";
            this.prepStatement2 = this.conn.prepareStatement(query2);
            this.prepStatement2.setString(1, code);
            this.prepStatement.executeUpdate();
            this.prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Product has been removed.");
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        this.deleteStock();
    }

    public void deletePurchaseDAO(int ID) {
        try {
            String query = "DELETE FROM purchaseinfo WHERE purchaseID=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setInt(1, ID);
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Transaction has been removed.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        this.deleteStock();
    }

    public void deleteSaleDAO(int ID) {
        try {
            String query = "DELETE FROM salesinfo WHERE salesID=?";
            this.prepStatement = this.conn.prepareStatement(query);
            this.prepStatement.setInt(1, ID);
            this.prepStatement.executeUpdate();
            JOptionPane.showMessageDialog((Component) null, "Transaction has been removed.");
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        this.deleteStock();
    }

    public void sellProductDAO(ProductDTO productDTO, String username) {
        int quantity = 0;
        String prodCode = null;

        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + productDTO.getProdCode() + "'";

            for (this.resultSet = this.statement.executeQuery(query); this.resultSet
                    .next(); quantity = this.resultSet.getInt("quantity")) {
                prodCode = this.resultSet.getString("productcode");
            }

            if (productDTO.getQuantity() > quantity) {
                JOptionPane.showMessageDialog((Component) null, "Insufficient stock for this product.");
            } else if (productDTO.getQuantity() <= 0) {
                JOptionPane.showMessageDialog((Component) null, "Please enter a valid quantity");
            } else {
                int var10000 = productDTO.getQuantity();
                String stockQuery = "UPDATE currentstock SET quantity=quantity-'" + var10000 + "' WHERE productcode='"
                        + productDTO.getProdCode() + "'";
                String var9 = productDTO.getDate();
                String salesQuery = "INSERT INTO salesinfo(date,productcode,customercode,quantity,revenue,soldby)VALUES('"
                        + var9 + "','" + productDTO.getProdCode() + "','" + productDTO.getCustCode() + "','"
                        + productDTO.getQuantity() + "','" + productDTO.getTotalRevenue() + "','" + username + "')";
                this.statement.executeUpdate(stockQuery);
                this.statement.executeUpdate(salesQuery);
                JOptionPane.showMessageDialog((Component) null, "Product sold.");
            }
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products ORDER BY pid";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getPurchaseInfo() {
        try {
            String query = "SELECT PurchaseID,purchaseinfo.ProductCode,ProductName,Quantity,Totalcost FROM purchaseinfo INNER JOIN products ON products.productcode=purchaseinfo.productcode ORDER BY purchaseid;";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getCurrentStockInfo() {
        try {
            String query = "SELECT currentstock.ProductCode,products.ProductName,\ncurrentstock.Quantity,products.CostPrice,products.SellPrice\nFROM currentstock INNER JOIN products\nON currentstock.productcode=products.productcode;\n";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getSalesInfo() {
        try {
            String query = "SELECT salesid,salesinfo.productcode,productname,\nsalesinfo.quantity,revenue,users.name AS Sold_by\nFROM salesinfo INNER JOIN products\nON salesinfo.productcode=products.productcode\nINNER JOIN users\nON salesinfo.soldby=users.username;\n";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getProductSearch(String text) {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products WHERE productcode LIKE '%"
                    + text + "%' OR productname LIKE '%" + text + "%' OR brand LIKE '%" + text + "%'";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getProdFromCode(String text) {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products WHERE productcode='"
                    + text + "' LIMIT 1";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getSalesSearch(String text) {
        try {
            String query = "SELECT salesid,salesinfo.productcode,productname,\n                    salesinfo.quantity,revenue,users.name AS Sold_by\n                    FROM salesinfo INNER JOIN products\n                    ON salesinfo.productcode=products.productcode\n                    INNER JOIN users\n                    ON salesinfo.soldby=users.username\n                    INNER JOIN customers\n                    ON customers.customercode=salesinfo.customercode\nWHERE salesinfo.productcode LIKE '%"
                    + text + "%' OR productname LIKE '%" + text + "%' OR users.name LIKE '%" + text
                    + "%' OR customers.fullname LIKE '%" + text + "%' ORDER BY salesid;";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getPurchaseSearch(String text) {
        try {
            String query = "SELECT PurchaseID,purchaseinfo.productcode,products.productname,quantity,totalcost FROM purchaseinfo INNER JOIN products ON purchaseinfo.productcode=products.productcode INNER JOIN suppliers ON purchaseinfo.suppliercode=suppliers.suppliercodeWHERE PurchaseID LIKE '%"
                    + text + "%' OR productcode LIKE '%" + text + "%' OR productname LIKE '%" + text
                    + "%' OR suppliers.fullname LIKE '%" + text + "%' OR purchaseinfo.suppliercode LIKE '%" + text
                    + "%' OR date LIKE '%" + text + "%' ORDER BY purchaseid";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public ResultSet getProdName(String code) {
        try {
            String query = "SELECT productname FROM products WHERE productcode='" + code + "'";
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return this.resultSet;
    }

    public String getSuppName(int ID) {
        String name = null;

        try {
            String query = "SELECT fullname FROM suppliers INNER JOIN purchaseinfo ON suppliers.suppliercode=purchaseinfo.suppliercode WHERE purchaseid='"
                    + ID + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                name = this.resultSet.getString("fullname");
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        return name;
    }

    public String getCustName(int ID) {
        String name = null;

        try {
            String query = "SELECT fullname FROM customers INNER JOIN salesinfo ON customers.customercode=salesinfo.customercode WHERE salesid='"
                    + ID + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                name = this.resultSet.getString("fullname");
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        return name;
    }

    public String getPurchaseDate(int ID) {
        String date = null;

        try {
            String query = "SELECT date FROM purchaseinfo WHERE purchaseid='" + ID + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                date = this.resultSet.getString("date");
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        return date;
    }

    public String getSaleDate(int ID) {
        String date = null;

        try {
            String query = "SELECT date FROM salesinfo WHERE salesid='" + ID + "'";
            this.resultSet = this.statement.executeQuery(query);
            if (this.resultSet.next()) {
                date = this.resultSet.getString("date");
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        return date;
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
