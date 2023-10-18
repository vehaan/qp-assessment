package com.pg.grocery.dao;

import com.pg.grocery.constants.MySqlQueries;
import com.pg.grocery.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private DataSource dataSource;

    public List<Item> getAvailableItems() {
        List<Item> itemsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement fetchItemStmt = connection
                    .prepareStatement(MySqlQueries.FETCH_AVAILABLE_ITEMS)) {
                try (ResultSet rs = fetchItemStmt.executeQuery()) {
                    while (rs.next()) {
                        itemsList.add(new Item(rs.getInt("id"), rs.getString("product_id"),
                                rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while fetching available items");
        }
        return itemsList;
    }

    public boolean insertOrderItemDetails(String customerId, Item itemDetails) {
        int rowCount = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insertItemStmt = connection
                    .prepareStatement(MySqlQueries.INSERT_ORDER_ITEM_DETAILS)) {
                insertItemStmt.setString(1, customerId);
                insertItemStmt.setString(2, itemDetails.getProductId());
                insertItemStmt.setString(3, itemDetails.getName());
                insertItemStmt.setDouble(4, itemDetails.getPrice());
                insertItemStmt.setInt(5, itemDetails.getQuantity());
                rowCount = insertItemStmt.executeUpdate();
                System.out.println("Inserted Item: " + itemDetails);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while inserting item");
        }
        return rowCount != 0;
    }

    public void updateItemStock(Item availableItem) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insertItemStmt = connection
                    .prepareStatement(MySqlQueries.UPDATE_ITEM_STOCK)) {
                insertItemStmt.setString(1, availableItem.getProductId());
                insertItemStmt.executeUpdate();
                System.out.println("Updated Item Stock: " + availableItem);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while updating item stock");
        }
    }
}
