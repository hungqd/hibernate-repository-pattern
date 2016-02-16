package com.fugu.database.entity;

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

import com.fugu.database.Persistable;

/**
 * Skill Entity
 * 
 * 對 Character 為多對多關係
 * 
 * @see com.fugu.database.entity.Character#getSkills()
 */
@Entity
@Table(name="skill")
public class Skill implements Persistable {

	private static final long serialVersionUID = 2366979573759613156L;
	
	private int id;
	private String name;
	private List<Character> characters;
	
	@Id
	@GeneratedValue
	@Column(name="skill_id")
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
            name="character_skill",
            joinColumns = @JoinColumn( name="skill_id"),
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
//		return String.format("Skill [skillId=%d, name=%s]", getId(), getName());
		return String.format("Skill [skillId=%d, name=%s] %n%s", getId(), getName(), getCharacters());
		
	}

}
