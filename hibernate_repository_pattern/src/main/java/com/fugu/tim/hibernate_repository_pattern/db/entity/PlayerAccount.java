package com.fugu.tim.hibernate_repository_pattern.db.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fugu.tim.hibernate_repository_pattern.db.Persistable;

/**
 * PlayerAccount Entity
 * 
 * 對 PlayerInfo 為雙向一對一關聯
 * 對 Character 為雙向一對多關聯
 * 
 */
@Entity
@Table(name = "player_account")
public class PlayerAccount implements Persistable {

	private static final long serialVersionUID = -4228711708177477841L;
	
	private int ninId;
	private String username;
	private int serverId;
	private Date createDate;
	
	private PlayerInfo playerInfo;
	private List<Character> characters;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nin_id")
	public int getNinId() {
		return ninId;
	}

	public void setNinId(int ninId) {
		this.ninId = ninId;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "server_id")
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 實現與 PlayerInfo 的 One to One 關聯
	 * 
	 * @return
	 */
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="playerAccount")
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
	
	// Entity 有一個以上 FetchType.EAGER 因此使用 @LazyCollection
	@OneToMany(cascade=CascadeType.ALL, mappedBy="playerAccount")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<Character> getCharacters() {
		return characters;
	}
	
	public void setCharacters(List<Character> chars) {
		this.characters = chars;
	}
	

	public static PlayerAccount newInstance(final String userName, final String nickName, final int serverId) {
		final PlayerAccount playerAccount = new PlayerAccount();
		playerAccount.setUsername(userName);
		playerAccount.setServerId(serverId);
		playerAccount.setCreateDate(new Date());
		return playerAccount;
	}
	
	@Override
	public String toString() {
//		return String.format("Player Account [Ninid=%d, username=%s, ServerId=%d, Createdate=%s]", getNinId(), getUsername(), getServerId(), getCreateDate());
		return String.format("Player Account [Ninid=%d, username=%s, ServerId=%d, Createdate=%s, %n%s]", getNinId(), getUsername(), getServerId(), getCreateDate(), getPlayerInfo());		
//		return String.format("Player Account [Ninid=%d, username=%s, ServerId=%d, Createdate=%s, %n%s, %n%s]", getNinId(), getUsername(), getServerId(), getCreateDate(), getPlayerInfo(), getCharacters());
	}
	
}
