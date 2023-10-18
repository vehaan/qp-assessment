package com.pg.grocery.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pg.grocery.dao.AdminDao;
import com.pg.grocery.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    private final JsonNodeFactory jsonNodeFactoryInstance = JsonNodeFactory.instance;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean addItem(Item item) {
        return adminDao.addItem(item);
    }

    public JsonNode addItems(List<Item> items) {
        ObjectNode response = jsonNodeFactoryInstance.objectNode();
        int successCount = 0;
        for (Item item: items) {
            boolean isSuccess = addItem(item);
            if (isSuccess) {
                successCount++;
            }
        }
        response.put("successfully_added", successCount);
        return response;
    }

    public Item getItem(String productId) {
        return adminDao.getItemDetails(productId);
    }

    public List<Item> getItems() {
        return adminDao.getItems();
    }

    public Boolean deleteItem(String productId) {
        return adminDao.deleteItem(productId);
    }

    public JsonNode updateItem(String productId, Item newItemDetails) {
        ObjectNode response = jsonNodeFactoryInstance.objectNode();
        Item itemDetails = adminDao.getItemDetails(productId);

        if (itemDetails == null) {
            response.put("is_success", false);
            response.put("error_msg", "Wrong product id to updated");
            return response;
        }

        if (newItemDetails.getName() != null) {
            itemDetails.setName(newItemDetails.getName());
        }
        if (newItemDetails.getPrice() != 0.0) {
            itemDetails.setPrice(newItemDetails.getPrice());
        }
        if (newItemDetails.getQuantity() != 0) {
            itemDetails.setQuantity(newItemDetails.getQuantity());
        }
        if (adminDao.updateItemData(productId, itemDetails)) {
            response.put("is_success", true);
            response.set("product_details", objectMapper.valueToTree(itemDetails));
        } else {
            response.put("is_success", false);
        }
        return response;
    }
}
