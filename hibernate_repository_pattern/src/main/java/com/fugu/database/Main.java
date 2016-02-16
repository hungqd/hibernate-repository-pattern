package com.fugu.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fugu.database.entity.Character;
import com.fugu.database.entity.Item;
import com.fugu.database.entity.PlayerAccount;
import com.fugu.database.entity.PlayerInfo;
import com.fugu.database.entity.Quest;
import com.fugu.database.entity.Skill;
import com.fugu.database.page.Page;
import com.fugu.database.page.PageRequest;
import com.fugu.database.repository.PlayerAccountRepository;
import com.fugu.database.sort.Sort;
import com.fugu.database.sort.Sort.Direction;
import com.fugu.database.specification.PlayerSpecification;
import com.fugu.database.specification.Specifiable;
import com.fugu.database.specification.TimeSpecification;
import com.fugu.database.utility.Util;

/**
 * 使用 Repository 操作資料庫範例
 */
public class Main {
	
	/**
	 * 測試 Repository 主要功能 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Util.logInfo("建立 Session Factory 並使用 SELECT 1 測試連線是否成功");
		HibernateUtil.initSessionFactory();
		
		
		PlayerAccountRepository repo = new PlayerAccountRepository();
		
		// 建立帳號
		PlayerAccount ac = new PlayerAccount();
		ac.setUsername("Tim");
		ac.setServerId(1);
		ac.setCreateDate(new Date());
		
		PlayerInfo info = PlayerInfo.newInstance();
		
		// 建立角色
		Character c1 = new Character();
		c1.setName("c1");
		Character c2 = new Character();
		c2.setName("c2");
		List<Character> chars = new ArrayList<>();
		chars.add(c1);
		chars.add(c2);
		
		// 建立技能
		Skill fireExplosion = new Skill();
		fireExplosion.setName("magic");
		Skill sonicSlash = new Skill();
		sonicSlash.setName("Sonic Slash");		
		List<Skill> skills = new ArrayList<>();
		skills.add(fireExplosion);
		skills.add(sonicSlash);
		
		// 建立物品
		Item healingPotion = new Item();
		healingPotion.setName("Healing Potion");
		Item antidote = new Item();
		antidote.setName("Antidote");
		List<Item> items = new ArrayList<>();
		items.add(healingPotion);
		items.add(antidote);
		
		// 建立任務
		Quest wonFirstBattle = new Quest();
		wonFirstBattle.setName("Won the first battle");
		Quest boughtFirstItem = new Quest();
		boughtFirstItem.setName("Bought the first item");
		List<Quest> quests = new ArrayList<>();
		quests.add(wonFirstBattle);
		quests.add(boughtFirstItem);		
		
		// 建立角色與技能關聯
		c1.setSkills(skills);
		c2.setSkills(skills);		
		// 建立技能與角色關聯		
		fireExplosion.setCharacters(chars);
		sonicSlash.setCharacters(chars);
		
		// 建立帳號與info關聯
		ac.setPlayerInfo(info);
		// 建立info與帳號關聯
		info.setPlayerAccount(ac);
		
		// 建立帳號與角色關聯
		ac.setCharacters(chars);
		// 建立角色與帳號關聯
		c2.setPlayerAccount(ac);
		c1.setPlayerAccount(ac);
				
		// 建立帳號與物品關聯
		ac.setItems(items);
		
		// 建立帳號與任務關聯
		ac.setQuests(quests);
		
		repo.save(ac);
		
		
//		List<PlayerAccount> accounts = repo.findAll();
//		show(accounts);
		
		
		/*
		 * PlayerAccount 對 Item 單向一對多關聯測試
		 * PlayerAccount 對 Quest 單向多對多關聯測試 
		 * 使用 JoinTable
		 */
		List<PlayerAccount> allAccounts = repo.findAll();
		show(allAccounts);
		
		
		
		/*
		 * 雙向一對多 多對一關聯測試 
		 * 使用 JoinColumn
		 * Owning side 為 Character
		 */

		// 測試 PlayerAccount 對 Character 雙向一對多
//		PlayerAccountRepository playerAccountRepo = new PlayerAccountRepository();
//		Set<PlayerAccount> playerAccounts = new HashSet<>(repo.findAll());
//		show(playerAccounts);
		
		// 測試 Character 對 PlayerAccount 雙向多對一
//		CharacterRepository characterRepo = new CharacterRepository();
//		List<Character> characters = characterRepo.findAll();
//		show(characters);		
		
		/*
		 * 雙向多對多關聯測試
		 */
		
		// 測試 character 對 skill 雙向多對多關聯
//		CharacterRepository charRepo = new CharacterRepository();
//		List<Character> allChars = charRepo.findAll();
//		show(allChars);

		// 測試 skill 對 character 雙向多對多關聯
//		SkillRepository skillRepo = new SkillRepository();
//		List<Skill> allSkills = skillRepo.findAll();
//		show(allSkills);
		
		
		// 關閉 Session Factory
		Util.logInfo("關閉 Session Factory");
		HibernateUtil.closeSessionFactory();
	}
	
	/**
	 * 使用 Console INFO 顯示 qeury 結果
	 * 
	 * @param entities
	 */
	private static <T extends Persistable> void show(Iterable<T> entities) {
		// lambda syntax
		entities.forEach(entity -> Util.logInfo(entity));
	}	
	
	/**
	 * 使用 Repository 批量 persist 數筆 Query 測試用 count 筆資料
	 * 依照 count 設定伺服器(serverId)與帳號創立時間(createDate)
	 * count 為偶數時帳號設定為第 2 伺服器 奇數為第 1 伺服器
	 * count 為 0 時帳號創立時間為當下 
	 * count 為 1 時創立時間為當下時間前 1 個小時 以下依序類推
	 */
	private static List<PlayerAccount> populateTestData(int count) {
		
		List<PlayerAccount> list = new ArrayList<PlayerAccount>();
		
		for (int i=0; i<count; i++) {
			PlayerAccount ac = new PlayerAccount();
			
			// ninId 與 username 按變數 i 從 0 1 2 3 ... n 與 player0 player1 .... playern 依序命名
			ac.setUsername("player" + i);
			
			// 奇數 ninId 註冊1服 偶數 ninId 註冊2服 
			if (i%2 == 1) {
				ac.setServerId(1);
			} else {
				ac.setServerId(2);
			}
			
			// createDate 首位註冊帳號創建日期設定為現在時間 
			// 次位註冊帳號創建日期往減少1小時 以此類推
			Calendar cal = Util.getCalendar();
			cal.add(Calendar.HOUR, -i);
			ac.setCreateDate(cal.getTime());
			
			// 設定 OneToOne Association Player Info Instance
			PlayerInfo info = PlayerInfo.newInstance();
			ac.setPlayerInfo(info);
			info.setPlayerAccount(ac);
			
			list.add(ac);
		}
		return list;
	} 
	
	private void crudTest() {
		
		// 使用 PlayerAccount Repository 進行 CRUD
				PlayerAccountRepository repo = new PlayerAccountRepository();
				
				/***********************************************************
				 * Insert 功能測試
				 ***********************************************************/
				Util.logInfo("建立 48 筆測試用 PlayerAccount 與 PlayerInfo 資料");
				List<PlayerAccount> transientAccounts = populateTestData(48);
				// 保存 transient 狀態的 transientAccounts
				repo.save(transientAccounts);
				
						
				/**********************************************************
				 * Query 功能測試 1 : 使用 Sort 與 Specification 進行 Query
				 **********************************************************/		
				// 設定 Specification 以定義 SQL 的 WHERE 條件
				Specifiable twentyFourSpec = TimeSpecification.getCreatedIn24Hour(); // 條件1：玩家註冊時間在24小時以內
				Specifiable serverTwoSpec = PlayerSpecification.getServerId(2); // 條件2：玩家註冊伺服器是第2伺服器
				Specifiable andSpec = twentyFourSpec.and(serverTwoSpec); // 同時滿足條件1與條件2的條件
				
				// 設定 Sort 以定義 SQL 的 ORDER BY 條件
				Sort descByNinId = new Sort(Direction.DESC, "ninId"); // 按照 nin_id 降冪排列
				
				// 使用 Specification Sort 取得 Query 結果
				List<PlayerAccount> queryAccounts = repo.findAll(andSpec, descByNinId);
				
				// 使用 console 顯示 Query 結果
				Util.logInfo("顯示過去 24 小時內在第 2 伺服器註冊的 Player Account 總計 " + queryAccounts.size() + " 筆資料並以 nin id 反向排序");		
				show(queryAccounts);
				

				/***********************************************************
				 * Query 功能測試 2 : 使用 PageRequest 進行 Query
				 ***********************************************************/
				// 建立顯示第2頁資料 每頁10筆資料的 PageRequest  
				PageRequest pageRequest = new PageRequest(1, 10, descByNinId);
				
				// 使用 pageRequest 取得 Query 結果的 Page 實例
				Page<PlayerAccount> page = repo.findAll(pageRequest);
				
				// 顯示 Page.toStrain() 內容
				Util.logInfo("顯示 Page 詳細資料為: " + page);
				show(page);
				
						
				/***********************************************************
				 * update 功能測試
				 ***********************************************************/
				// 將 2 服所有帳號 username 字尾額外增加 "更新" 兩個字
				List<PlayerAccount> toUpdateAccounts = repo.findAll(serverTwoSpec);
				List<PlayerAccount> updatedAccounts = new ArrayList<>();
				
				for(PlayerAccount account : toUpdateAccounts) {
					String changedUsername = account.getUsername() + "更新";
					account.setUsername(changedUsername);
					updatedAccounts.add(account);
				}
				
				// 更新 detached 狀態的 changedAccounts 資料
				repo.save(updatedAccounts);
				
				// 使用 Repository 取得 update 結果
				List<PlayerAccount> queryUpdatedAccounts = repo.findAll(andSpec, descByNinId);
				
				// 使用 console 顯示 Query 結果
				Util.logInfo("顯示更新過後的 2 服 Player Account 總計 " + queryUpdatedAccounts.size() + " 筆資料");		
				show(queryUpdatedAccounts);
				
				
				/***********************************************************
				 * delete 功能測試
				 ***********************************************************/
				// 刪除 1 服所有帳號
				List<PlayerAccount> toDeleteAccounts = repo.findAll(
						PlayerSpecification.getServerId(1)
				);
				repo.delete(toDeleteAccounts);
				
				// 使用 console 顯示 Query 結果
				List<PlayerAccount> deletedAccounts = repo.findAll();
				Util.logInfo("顯示未被刪除的 2 服 Player Account 總計 " + deletedAccounts.size() + " 筆資料");		
				show(deletedAccounts);	
	}
	
}
