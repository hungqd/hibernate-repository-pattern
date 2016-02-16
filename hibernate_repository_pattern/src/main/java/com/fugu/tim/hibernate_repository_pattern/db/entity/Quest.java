package com.fugu.tim.hibernate_repository_pattern.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fugu.tim.hibernate_repository_pattern.db.Persistable;

/**
 * Quest Entity
 * 
 * @see com.fugu.tim.hibernate_repository_pattern.db.entity.PlayerAccount#getQuests()
 */
@Entity
@Table(name="quest")
public class Quest implements Persistable {
	
	private static final long serialVersionUID = 8585803200953386093L;
	
	private int id;
	private String name;
	
	@Id
	@GeneratedValue
	@Column(name="quest_id")
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
		return String.format("Quest [QuestId=%d, name=%s]", getId(), getName());
		
	}

}
