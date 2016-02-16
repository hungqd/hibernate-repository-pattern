package com.fugu.tim.hibernate_repository_pattern.db.repository;

import com.fugu.tim.hibernate_repository_pattern.db.entity.Skill;

public class SkillRepository extends AbstractRepository<Skill, Integer>{
	
	public Class<Skill> getDomainClass() {
		return Skill.class;
	}
	
}
