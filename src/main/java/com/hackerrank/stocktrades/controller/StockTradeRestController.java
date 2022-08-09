package com.hackerrank.stocktrades.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hackerrank.stocktrades.model.StockTrade;
import com.hackerrank.stocktrades.service.StockTradeService;
import com.hackerrank.stocktrades.validation.StockTypeValidator;


@RestController
@RequestMapping("/trades")
public class StockTradeRestController {
	
	  private final StockTradeService service;

	  @Autowired
	  public StockTradeRestController(StockTradeService service) {
	    this.service = service;
	  }
	  
	  @GetMapping
	  @ResponseStatus(HttpStatus.OK)
	  public List<StockTrade> getAllStockTrades(
			  @RequestParam(required=false) Map<String,String> params) {
		  
		  String type = params.get("type");
		  
		  String userId = params.get("userId");
		  
	    return service.getStockTradesByTypeBuyUserId(type, userId);
	  }

	  @GetMapping("/{id}")
	  @ResponseStatus(HttpStatus.OK)
	  public ResponseEntity<StockTrade> getStockTradeById(@PathVariable Integer id) {
		  try {
			  
			  StockTrade stockTradeById = service.getStockTradeById(id);
			  
			  return ResponseEntity.status(HttpStatus.OK).body(stockTradeById);
			  
		  } catch(NoSuchElementException e) {
			  
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);		  }
		  
	  }
	  
	  @PostMapping
	  @ResponseStatus(HttpStatus.CREATED)
	  public ResponseEntity<StockTrade> createStockTrade(@RequestBody StockTrade stockTrade) {
		  if(stockTrade.getShares() < 1 || stockTrade.getShares() > 100 ) {
			  
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		  }

		  if(!new StockTypeValidator().isValid(stockTrade.getType(), null)) {
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		  }
	
		  StockTrade createNewStockTrade = service.createNewStockTrade(stockTrade);
		  
		  return ResponseEntity.status(HttpStatus.CREATED).body(createNewStockTrade);
	  }
	  
	  @DeleteMapping("/{id}")
	  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	  public void deleteStockTradeById(@PathVariable Integer id) {throw new UnsupportedOperationException();}

	  @PutMapping("/{id}")
	  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	  public void putStockTradeById(@PathVariable Integer id) {throw new UnsupportedOperationException();}
	  
	  @PatchMapping("/{id}")
	  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	  public void patchStockTradeById(@PathVariable Integer id) {throw new UnsupportedOperationException();}
	  
	
}