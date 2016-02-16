package com.fugu.tim.hibernate_repository_pattern.db.repository;

import com.fugu.tim.hibernate_repository_pattern.db.entity.Item;

public class ItemRepository extends AbstractRepository<Item, Integer>{
	
	public Class<Item> getDomainClass() {
		return Item.class;
	}
	
}
