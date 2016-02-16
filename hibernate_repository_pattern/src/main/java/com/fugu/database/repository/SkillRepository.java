package com.fugu.database.repository;

import com.fugu.database.entity.Skill;

public class SkillRepository extends AbstractRepository<Skill, Integer>{
	
	public Class<Skill> getDomainClass() {
		return Skill.class;
	}
	
}
