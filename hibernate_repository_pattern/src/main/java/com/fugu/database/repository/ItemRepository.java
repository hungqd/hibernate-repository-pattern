package com.fugu.database.repository;

import com.fugu.database.entity.Item;

public class ItemRepository extends AbstractRepository<Item, Integer>{
	
	public Class<Item> getDomainClass() {
		return Item.class;
	}
	
}
