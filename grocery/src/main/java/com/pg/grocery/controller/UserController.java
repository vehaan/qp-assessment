package com.pg.grocery.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pg.grocery.dto.Item;
import com.pg.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/available_items")
    public ResponseEntity<List<Item>> getAvailableItems(@RequestHeader Map<String, String> headers) {
        System.out.println("Request for available_items - headers: " + headers);
        List<Item> response = userService.getAvailableItems();
        System.out.println("Response for available_items - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/book_items")
    public ResponseEntity<JsonNode> bookItems(@RequestHeader Map<String, String> headers, @RequestBody List<Item> requestBody) {
        System.out.println("Request for book_items - headers: " + headers + " RequestBody: " + requestBody);
        JsonNode response = userService.bookItems(headers.get("customer_id"), requestBody);
        System.out.println("Response for book_items - " + "responseBody: "  + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
