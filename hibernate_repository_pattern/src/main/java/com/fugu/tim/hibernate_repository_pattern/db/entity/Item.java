package com.fugu.tim.hibernate_repository_pattern.db.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fugu.tim.hibernate_repository_pattern.db.Persistable;

/**
 * Item Entity
 * 
 * 對 PlayerAccount 為多對多關係 但是沒有單向關係
 * 
 * @see com.fugu.tim.hibernate_repository_pattern.db.entity.Character#getItems()
 */
@Entity
@Table(name="Item")
public class Item implements Persistable {

	private static final long serialVersionUID = 2366979573759613156L;
	
	private int id;
	private String name;
	private List<Character> characters;
	
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
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
            name="character_Item",
            joinColumns = @JoinColumn( name="Item_id"),
            inverseJoinColumns = @JoinColumn( name="character_id")
    )
	public List<Character> getCharacters() {
		return characters;
	}
	
	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}
	
	@Override
	public String toString() {
//		return String.format("Item [ItemId=%d, name=%s]", getId(), getName());
		return String.format("Item [ItemId=%d, name=%s] %n%s", getId(), getName(), getCharacters());
		
	}

}
