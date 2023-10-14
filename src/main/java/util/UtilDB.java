package util;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datamodel.Person; // Transaction must be referenced using its package, as it conflicts. 

public class UtilDB {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }
   
   public static List<Person> queryAllPeople() {
	   List<Person> result = new ArrayList<>();
	   
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null; 

	   try {
		   tx = session.beginTransaction();
		   
		   List<?> transactions = session.createQuery("FROM Person").list();
         
		   // functionally cast results.
		   result = transactions.stream()
				   .map(p -> (Person) p)
				   .collect(Collectors.toList());
         
		   tx.commit();
	   } catch (HibernateException e) {
		   if (tx != null)
			   tx.rollback();
		   e.printStackTrace();
	   } finally {
		   session.close();
	   }

	   return result;
   }
   
   public static List<datamodel.Transaction> queryAllTransactions() {
	   List<datamodel.Transaction> result = new ArrayList<>();
	   
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null; 

	   try {
		   tx = session.beginTransaction();
		   
		   List<?> transactions = session.createQuery("FROM Transaction").list();
         
		   // functionally cast results.
		   result = transactions.stream()
				   .map(t -> (datamodel.Transaction) t)
				   .collect(Collectors.toList());
         
		   tx.commit();
	   } catch (HibernateException e) {
		   if (tx != null)
			   tx.rollback();
		   e.printStackTrace();
	   } finally {
		   session.close();
	   }

	   return result;
   }

   public static <T> void insertTableEntry(T row) {
      Session session = getSessionFactory().openSession();

      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(row);
         tx.commit();
      } 
      catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } 
      finally {
         session.close();
      }
   }
}
