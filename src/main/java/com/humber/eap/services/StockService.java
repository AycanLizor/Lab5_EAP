package com.humber.eap.services;

import com.humber.eap.model.Stock;
import com.humber.eap.producers.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    private List<Stock> stocks;
    private Producer producer;

    @Autowired
    public StockService(Producer producer) {
        this.stocks = producer.getStock();
        this.producer = producer;
    }

    public List<Stock> getStocks() {
        return this.stocks;
    }

    public void addStock(Stock stock) throws Exception {
        boolean exists = this.stocks.stream()
                .anyMatch(a -> a.getId() == stock.getId() || a.getName().equalsIgnoreCase(stock.getName()));
        if (exists) {
            throw new Exception("Record already exists");
        }

        this.stocks.add(stock);
    }

    public Stock getStockById(int id) {
        Optional<Stock> optionalStock = this.stocks.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (optionalStock.isPresent()) {
            return optionalStock.get();
        } else {
            return null;
        }
    }

    public boolean validName(Stock stock) {
        boolean error = this.stocks.stream().anyMatch(e -> e.getName().equalsIgnoreCase(stock.getName()));

        return error;
    }

    public Stock updateStock(int id, Stock stock) {

            Stock existedStock = getStockById(id);
            System.out.println(existedStock);
             System.out.println(validName(stock));
            if (stock.getPrice() <= 0) {
                return null;
            } else {
                existedStock.setName(stock.getName());
                existedStock.setPrice(stock.getPrice());
                existedStock.setTrends(stock.getTrends());
                return existedStock;
            }


    }

    public Stock deleteStock(int id) {
        try {
            System.out.println("Before deletion: " + this.stocks);

            Stock stock = getStockById(id);
            if (stock != null) {
                List<Stock> updatedList = this.stocks.stream().filter(e -> e.getId() != id).collect(Collectors.toList());
                this.stocks = updatedList;

                System.out.println("After deletion: " + this.stocks);

                return stock;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


}
