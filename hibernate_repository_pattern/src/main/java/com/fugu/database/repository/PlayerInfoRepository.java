package com.fugu.database.repository;

import com.fugu.database.entity.PlayerInfo;

public class PlayerInfoRepository extends AbstractRepository<PlayerInfo, Integer>{
	
	public Class<PlayerInfo> getDomainClass() {
		return PlayerInfo.class;
	}
	
}
