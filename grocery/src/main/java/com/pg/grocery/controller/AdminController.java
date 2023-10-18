package com.pg.grocery.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pg.grocery.dto.Item;
import com.pg.grocery.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private final JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;

    @PostMapping("/add_item")
    public ResponseEntity<JsonNode> addItem(@RequestHeader Map<String, String> headers, @RequestBody Item requestBody) {
        System.out.println("Request for add_item - headers: " + headers + " requestBody: "  + requestBody );
        ObjectNode response = jsonNodeFactory.objectNode();
        boolean isSuccess = adminService.addItem(requestBody);
        if (isSuccess) {
            response.put("is_success", true);
        }
        System.out.println("Response for add_item - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add_items")
    public ResponseEntity<JsonNode> addItems(@RequestHeader Map<String, String> headers, @RequestBody List<Item> requestBody) {
        System.out.println("Request for add_items - headers: " + headers + " requestBody: "  + requestBody );
        JsonNode response = adminService.addItems(requestBody);
        System.out.println("Response for add_items - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<JsonNode> getItems(@RequestHeader Map<String, String> headers, @PathVariable String id) {
        System.out.println("Request for get_items - headers: " + headers);
        ObjectNode response  = jsonNodeFactory.objectNode();
        Item item = adminService.getItem(id);
        if (item == null) {
            response.put("is_success", false);
        }
        System.out.println("Response for get_items - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems(@RequestHeader Map<String, String> headers) {
        System.out.println("Request for get_items - headers: " + headers);
        List<Item> response = adminService.getItems();
        System.out.println("Response for get_items - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<JsonNode> deleteItem(@RequestHeader Map<String, String> headers, @PathVariable String id) {
        System.out.println("Request for delete_item - headers: " + headers);
        ObjectNode response = jsonNodeFactory.objectNode();
        Boolean isSuccess = adminService.deleteItem(id);
        if (isSuccess) {
            response.put("is_success", true);
        }
        System.out.println("Response for delete_item - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<JsonNode> updateItem(@RequestHeader Map<String, String> headers, @PathVariable String id, @RequestBody Item requestBody) {
        System.out.println("Request for update_item - headers: " + headers + " id: " + id + " requestBody: " + requestBody);
        JsonNode response = adminService.updateItem(id, requestBody);
        System.out.println("Response for update_item - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
