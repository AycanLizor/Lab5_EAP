package com.humber.eap.producers;

import com.humber.eap.model.Stock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Producer {

  @Bean
    public List<Stock> getStock() {
    List<Stock> stocks = new ArrayList<>();
      Stock stock1 = new Stock(1, "Tesla", 456, Arrays.asList("Bullish", "Hold"));
      Stock stock2 = new Stock(2, "PizzaPizza", 11, Arrays.asList("HighList"));
      Stock stock3 = new Stock(3, "SPY", 233, Arrays.asList("Volatile"));


    stocks.addAll(Arrays.asList(stock1, stock2, stock3));
    return stocks;
  }
}
