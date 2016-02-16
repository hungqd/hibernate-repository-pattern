package com.fugu.database.repository;

import com.fugu.database.entity.Character;

public class CharacterRepository extends AbstractRepository<Character, Integer>{
	
	public Class<Character> getDomainClass() {
		return Character.class;
	}
	
}
