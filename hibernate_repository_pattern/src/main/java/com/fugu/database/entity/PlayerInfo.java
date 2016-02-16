package com.fugu.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fugu.database.Persistable;

/**
 * PlayerInfo Entity
 * 
 * 對 Player Account 為多對一關係
 */
@Entity
@Table(name = "player_info")
public class PlayerInfo implements Persistable {

	private static final long serialVersionUID = -6069233823189796005L;
	
	private int ninId;
	private int exp;
	private int level;
	private int gold;
	private Date lastLoginDate;
	
	private PlayerAccount playerAccount;
	
	@Id
	@Column(name = "nin_id")
	public int getNinId() {
		return ninId;
	}

	public void setNinId(int ninId) {
		this.ninId = ninId;
	}

	@Column(name = "exp")
	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "gold")
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Column(name = "last_login_date")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public int addGold(final int g) {
		this.gold += g;
		return this.gold;
	}
	
	public int addExp(final int e) {
		this.exp += e;
		return this.exp;
	}
	
	/**
	 * 使用 PlayerAccount 的 Primary Key 作為 Primary Key 實現與 PlayerAccount 的 One to One 關聯
	 * 
	 * @return PlayerAccount
	 */
    @JoinColumn(name="ninId")
    @OneToOne(fetch=FetchType.LAZY)
    @MapsId
    public PlayerAccount getPlayerAccount() {
    	return this.playerAccount;
    }
    
    public void setPlayerAccount(PlayerAccount playerAccount) {
    	this.playerAccount = playerAccount;
    }
	
	public static PlayerInfo newInstance() {
		final PlayerInfo playerInfo = new PlayerInfo();
		playerInfo.setGold(0);
		playerInfo.setExp(0);
		playerInfo.setLevel(1);
		final Date currentDate = new Date();
		playerInfo.setLastLoginDate(currentDate);
		return playerInfo;
	}
	
	@Override
	public String toString() {
//		return String.format("Player Info [Ninid=%d, exp=%d, gold=%d, level= %d, LastLoginDate=%s, \n%s]", getNinId(), getExp(), getGold(), getLevel(), Util.dateToString(getLastLoginDate()), getPlayerAccount());
		return String.format("Player Info [Ninid=%d, exp=%d, gold=%d, level= %d, LastLoginDate=%s]", getNinId(), getExp(), getGold(), getLevel(), getLastLoginDate());
	}
}
