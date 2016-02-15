package com.fugu.tim.hibernate_repository_pattern.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fugu.tim.hibernate_repository_pattern.db.Persistable;

@Entity
@Table(name="character")
public class Character implements Persistable {
	
	private static final long serialVersionUID = -7239975136579602269L;
	
	private int id;
	private String name;
	private PlayerAccount playerAccount;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="character_id")
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
	
	@ManyToOne
	@JoinColumn(name="nin_id")
	public PlayerAccount getPlayerAccount() {
		return playerAccount;
	}
	
	public void setPlayerAccount(PlayerAccount playerAccount) {
		this.playerAccount = playerAccount;
	}
	
	@Override
	public String toString() {
		return String.format("Character [characterId=%d, name=%s]", getId(), getName());
//		return String.format("Character [characterId=%d, name=%s] %s", getId(), getName(), getPlayerAccount());
	}
}
