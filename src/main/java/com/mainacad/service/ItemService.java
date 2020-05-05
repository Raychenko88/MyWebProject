package com.mainacad.service;

import java.util.List;

import com.mainacad.dao.ItemDAO;
import com.mainacad.model.Item;

public class ItemService {
	public static Item getById(Integer id) {
		return ItemDAO.getById(id);
	}
	
	public static List<Item> getAllAvailable() {
		return ItemDAO.getAllAvailable();
	}

	public static void delete(Integer id){
		ItemDAO.delete(id);
	}

	public static Item save(Item item){
		return ItemDAO.save(item);
	}

	public static List<Item> findByItemCode(String code){
		return ItemDAO.findByItemCode(code);
	}
}
