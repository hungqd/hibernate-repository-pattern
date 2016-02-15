package com.fugu.tim.hibernate_repository_pattern.db.repository;

import com.fugu.tim.hibernate_repository_pattern.db.entity.PlayerAccount;

public class PlayerAccountRepository extends AbstractRepository<PlayerAccount, Integer>{
	
	public Class<PlayerAccount> getDomainClass() {
		return PlayerAccount.class;
	}
	
}
