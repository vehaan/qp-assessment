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
public class AdminDao {

    @Autowired
    private DataSource dataSource;

    public boolean addItem(Item item) {
        int rowCount = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insertItemStmt = connection
                    .prepareStatement(MySqlQueries.INSERT_ITEM)) {
                insertItemStmt.setString(1, item.getProductId());
                insertItemStmt.setString(2, item.getName());
                insertItemStmt.setDouble(3, item.getPrice());
                insertItemStmt.setInt(4, item.getQuantity());
                rowCount = insertItemStmt.executeUpdate();
                System.out.println("Inserted Item: " + item);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while inserting item");
        }
        return rowCount != 0;
    }

    public List<Item> getItems() {
        List<Item> itemsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement fetchItemStmt = connection
                    .prepareStatement(MySqlQueries.FETCH_ITEMS)) {
                try (ResultSet rs = fetchItemStmt.executeQuery()) {
                    while (rs.next()) {
                        itemsList.add(new Item(rs.getInt("id"), rs.getString("product_id"),
                                rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while fetching items");
        }
        return itemsList;
    }

    public boolean deleteItem(String productId) {
        int rowCount = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insertItemStmt = connection
                    .prepareStatement(MySqlQueries.DELETE_ITEM)) {
                insertItemStmt.setString(1, productId);
                rowCount = insertItemStmt.executeUpdate();
                System.out.println("Deleted Item id: " + productId);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while deleting item");
        }
        return rowCount != 0;
    }

    public Item getItemDetails(String productId) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement fetchItemStmt = connection
                    .prepareStatement(MySqlQueries.FETCH_ITEM)) {
                fetchItemStmt.setString(1, productId);
                try (ResultSet rs = fetchItemStmt.executeQuery()) {
                    if (rs.next()) {
                        return new Item(rs.getInt("id"), rs.getString("product_id"),
                                rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while fetching item details");
        }
        return null;
    }

    public boolean updateItemData(String productId, Item item) {
        int rowCount = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insertItemStmt = connection
                    .prepareStatement(MySqlQueries.UPDATE_ITEM)) {
                insertItemStmt.setString(1, item.getName());
                insertItemStmt.setDouble(2, item.getPrice());
                insertItemStmt.setInt(3, item.getQuantity());
                insertItemStmt.setString(4, productId);
                rowCount = insertItemStmt.executeUpdate();
                System.out.println("updated Item: " + item);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while updating item");
        }
        return rowCount != 0;
    }
}
