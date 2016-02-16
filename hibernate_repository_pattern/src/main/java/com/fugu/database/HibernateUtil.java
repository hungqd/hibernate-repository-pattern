package com.fugu.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.fugu.database.utility.Util;

/**
 * 建立取得或關閉 Hibernate Session Factory 實例
 * 
 * @see org.hibernate.SessionFactory
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();
    
    /**
     * 應用啟動時建立 Session Factory 並測試與資料庫連線是否成功
     * 
     * @return 連線成功返回 true
     */
	public static boolean initSessionFactory() {
		
		Session session = sessionFactory.openSession();
		
		try {
			session.createSQLQuery("SELECT 1;").list();
			Util.logInfo("Connection initialization is completed.");
			return true;
		} catch (RuntimeException ex) {
			Util.logException(ex);
		} finally {
			session.close();
		}
		
		Util.logError("Connection initialization failed.");
		return false;		
	}
    
	/**
	 * 取得 Hibernate Session Factory
	 * 
	 * @return Hibernate Session Factory instance
	 */
    public static SessionFactory getSessionFactory() {
    	return sessionFactory;
    }
    
    /**
     * 應用結束時關閉 Session Factory
     */
    public static void closeSessionFactory() {
		sessionFactory.close();
		Util.logInfo("Connection and Session Factory is closed.");
    }
    
    /**
     * 根據 hibernate.cfg.xml 設定檔建立 Session Factory 
     * 
     * @return Hibernate Session Factory instance
     */
    private static SessionFactory buildSessionFactory() {
    	
        try {
            // 指定設定檔位置 hibernate.cfg.xml
        	Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        	StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        	StandardServiceRegistry standardServiceRegistry = standardServiceRegistryBuilder.build();
        	return configuration.buildSessionFactory(standardServiceRegistry);
        }
        catch (HibernateException ex) {
            System.err.println("Session factory initialization failed. " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
        
    }
	
}
