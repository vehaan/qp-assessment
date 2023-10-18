package com.pg.grocery.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pg.grocery.dao.AdminDao;
import com.pg.grocery.dao.UserDao;
import com.pg.grocery.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    public List<Item> getAvailableItems() {
        return userDao.getAvailableItems();
    }

    public JsonNode bookItems(String customerId, List<Item> itemsDetails) {
        List<Item> availableItems = new ArrayList<>();
        List<String> OutOfStockItems = new ArrayList<>();
        ObjectNode response = JsonNodeFactory.instance.objectNode();
        for (Item item: itemsDetails) {
            Item itemForOrder = adminDao.getItemDetails(item.getProductId());
            if (itemForOrder.getQuantity() < item.getQuantity()) {
                OutOfStockItems.add(item.getProductId());
            } else {
                itemForOrder.setQuantity(item.getQuantity());
                availableItems.add(itemForOrder);
            }
        }

        if (!OutOfStockItems.isEmpty()) {
            response.put("is_success", false);
            response.put("out_of_stock_items", OutOfStockItems.toString());
            response.put("in_stock_items", availableItems.toString());
            return response;
        }

        int rowCount = 0;
        for (Item availableItem: availableItems) {
            if (userDao.insertOrderItemDetails(customerId, availableItem)) {
                userDao.updateItemStock(availableItem);
                rowCount++;
            }
        }
        if (rowCount == availableItems.size()) {
            response.put("is_success", true);
            return response;
        }
        response.put("is_success", true);
        response.put("error_msg", "Something went wrong");
        return response;
    }
}
