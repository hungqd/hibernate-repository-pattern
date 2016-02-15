package com.fugu.tim.hibernate_repository_pattern.db.repository;

import com.fugu.tim.hibernate_repository_pattern.db.entity.PlayerInfo;

public class PlayerInfoRepository extends AbstractRepository<PlayerInfo, Integer>{
	
	public Class<PlayerInfo> getDomainClass() {
		return PlayerInfo.class;
	}
	
}
