package com.humber.eap.controller;

import com.humber.eap.model.Stock;
import com.humber.eap.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {

StockService stockService;

    @Autowired
    public HomeController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/")
    public String hello() {
        return "hello from branch aycan \n hello from branch long";
    }

    @GetMapping(value = "/stock", produces = MediaType.APPLICATION_XML_VALUE)
    public Stock getStock() {
        return new Stock(1, "Tesla", 456, Arrays.asList("Bullish", "Hold"));
    }

    @GetMapping(value = "/stocks", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Stock> getStockList() {
        return this.stockService.getStocks(); // Just return the injected list directly
    }

    @GetMapping(value = "/stocks/trend/{search}", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Stock> getStockListByTrends(@PathVariable String search) {
        List<Stock> filteredStocks = this.stockService.getStocks().stream()
                .filter(stock -> stock.getTrends().stream()
                        .anyMatch(trend -> trend.toLowerCase().contains(search.toLowerCase())))
                .collect(Collectors.toList());

        return filteredStocks;
    }

    @PostMapping("/stocks")
    //@PostMapping(value="/stocks", consumes=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity addStock(@RequestBody Stock stock) {
        ResponseEntity responseEntity;

        try{
            this.stockService.addStock(stock);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
       return responseEntity;
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity updateStock(@PathVariable int id, @RequestBody Stock stock) {
        ResponseEntity responseEntity;

        try {
            Stock existedStock = stockService.getStockById(id);
            Stock updatedStock = stockService.updateStock(id, stock);
            if (updatedStock != null) {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter");
            }
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Id");
        }

        return responseEntity;
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity deleteStock(@PathVariable("id") int id) {
        ResponseEntity responseEntity;
        Stock existedStock = stockService.getStockById(id);
        if (existedStock != null) {
            stockService.deleteStock(id);
            responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Id");
        }

        return responseEntity;
    }









}






