package com.fugu.tim.hibernate_repository_pattern.db.repository;

import com.fugu.tim.hibernate_repository_pattern.db.entity.Character;

public class CharacterRepository extends AbstractRepository<Character, Integer>{
	
	public Class<Character> getDomainClass() {
		return Character.class;
	}
	
}
