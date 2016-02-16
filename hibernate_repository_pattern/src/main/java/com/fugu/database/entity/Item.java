package com.fugu.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fugu.database.Persistable;

/**
 * Item Entity
 * 
 * @see com.fugu.database.entity.Character#getItems()
 */
@Entity
@Table(name="item")
public class Item implements Persistable {
	
	private static final long serialVersionUID = 3660674673121716460L;
	
	private int id;
	private String name;
	
	@Id
	@GeneratedValue
	@Column(name="Item_id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("Item [ItemId=%d, name=%s]", getId(), getName());
		
	}

}
