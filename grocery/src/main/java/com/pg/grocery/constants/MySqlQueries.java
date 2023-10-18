package com.pg.grocery.constants;

public class MySqlQueries {

    public static final String INSERT_ITEM = "INSERT INTO trendsutra3.grocery_items (product_id, name, price, quantity) VALUES (?, ?, ?, ?)";

    public static final String FETCH_ITEMS = "SELECT * FROM trendsutra3.grocery_items";

    public static final String DELETE_ITEM = "DELETE FROM trendsutra3.grocery_items where product_id = ?";

    public static final String FETCH_ITEM = "SELECT * FROM trendsutra3.grocery_items where product_id = ?";

    public static final String UPDATE_ITEM = "UPDATE trendsutra3.grocery_items SET name = ?, price = ?, quantity = ? where product_id = ?";

    public static final String FETCH_AVAILABLE_ITEMS = "SELECT * FROM trendsutra3.grocery_items where quantity > 0";
    public static final String INSERT_ORDER_ITEM_DETAILS = "INSERT INTO trendsutra3.order_item (customer_id, product_id, product_name, item_price, qty_ordered) VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_ITEM_STOCK = "UPDATE trendsutra3.grocery_items SET quantity = quantity - 1 where product_id = ?";
}
