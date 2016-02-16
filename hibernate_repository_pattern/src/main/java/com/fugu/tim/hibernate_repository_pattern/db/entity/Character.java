package com.fugu.tim.hibernate_repository_pattern.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fugu.tim.hibernate_repository_pattern.db.Persistable;

/**
 * Character Entity
 * 
 * 對 Player Account 為雙向多對一關係
 * 對 Skill 為雙向多對多關係
 * 
 * @see com.fugu.tim.hibernate_repository_pattern.db.entity.PlayerAccount#getCharacters()
 * @see com.fugu.tim.hibernate_repository_pattern.db.entity.Skill#getCharacter()
 */
@Entity
@Table(name="character")
public class Character implements Persistable {
	
	private static final long serialVersionUID = -7239975136579602269L;
	
	private int id;
	private String name;
	private PlayerAccount playerAccount;
	private List<Skill> skills;
	
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nin_id")
	public PlayerAccount getPlayerAccount() {
		return playerAccount;
	}
	
	public void setPlayerAccount(PlayerAccount playerAccount) {
		this.playerAccount = playerAccount;
	}
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="characters")
	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	@Override
	public String toString() {
		return String.format("Character [characterId=%d, name=%s]", getId(), getName());
//		return String.format("Character [characterId=%d, name=%s] %n%s %n%s", getId(), getName(), getSkills());
//		return String.format("Character [characterId=%d, name=%s] %n%s", getId(), getName(), getPlayerAccount());
//		return String.format("Character [characterId=%d, name=%s] %n%s %n%s", getId(), getName(), getPlayerAccount(), getSkills());
	}

}
