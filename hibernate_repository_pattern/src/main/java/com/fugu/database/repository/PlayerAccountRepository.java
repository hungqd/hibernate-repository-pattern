package com.fugu.database.repository;

import com.fugu.database.entity.PlayerAccount;

public class PlayerAccountRepository extends AbstractRepository<PlayerAccount, Integer>{
	
	public Class<PlayerAccount> getDomainClass() {
		return PlayerAccount.class;
	}
	
}
