package com.hackerrank.stocktrades.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackerrank.stocktrades.exceptions.BadRequestException;
import com.hackerrank.stocktrades.model.StockTrade;
import com.hackerrank.stocktrades.repository.StockTradeRepository;
import com.hackerrank.stocktrades.service.StockTradeService;

@Service
class StockTradeServiceImpl implements StockTradeService {
  private final StockTradeRepository repository;

  @Autowired
  public StockTradeServiceImpl(StockTradeRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<StockTrade> getAllStockTrades() {
	  return repository.findAll();
  }
  
  @Override
  public List<StockTrade> getStockTradesByTypeBuyUserId(String type, String userId) {
	  List<StockTrade> all = repository.findAll();
	  
	  return filterByTypeByUserId(type, userId, all);
	  
  }

  private List<StockTrade> filterByTypeByUserId(String type, String userId, List<StockTrade> all) {

	  if((type == null || "".equals(type)) 
			  && userId != null && !"".equals(userId.trim())) {

		  return all.parallelStream().filter(
				  stock -> 
				  stock.getUserId().equals(Integer.valueOf(userId))
				  ).collect(Collectors.toList());
	  } 
	  
	  if(type != null && !"".equals(type) 
			  && (userId == null || "".equals(userId.trim()))) {

		  return  all.parallelStream().filter(
				  stock -> 
				  	stock.getType().equals(type)
				  ).collect(Collectors.toList());
	  } 

	  if(type != null && !"".equals(type) 
			  && userId != null && !"".equals(userId.trim())) {

		  return  all.stream().filter(
				  stock -> 
				  stock.getType().equals(type) 
				  && stock.getUserId().equals(Integer.valueOf(userId))
				  ).collect(Collectors.toList());
	  }
	  
	return getAllStockTrades();
  }

  @Override
  public StockTrade createNewStockTrade(StockTrade stockTrade) {
	  if (stockTrade.getId() != null) {
		  throw new BadRequestException("The ID must not be provided when creating a new stock");
	  }

	  return repository.save(stockTrade);
  }

  @Override
  public StockTrade getStockTradeById(Integer id) {
	  return repository.findById(id).get();
  }

}
