package test.hibernate.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class HibernateTest {
	
	private static EntityManagerFactory ENTITY_MANAGER_FACTORY = null;

	public static void main(String[] args) {

		try {
			ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("HibernateTest");
		} catch (Exception e) {
			e.printStackTrace();
		}

		create("Alice", 22);
		create("Bob", 20);
		create("Charlie", 25);

		upate("Bob", 25);

		delete("Bob");

		List<StudentDetail> students = read("SELECT s FROM StudentDetails s");
		print(students);
		
		List<Object> resultList = executeSelectNativeQuery(Object.class, 
					"select student_name from student_details s group by student_name", null);
		resultList.forEach(r -> System.out.println(r));
		
		

		ENTITY_MANAGER_FACTORY.close();
	}
	
	public static void create(String name, int age) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		try {
			manager = ENTITY_MANAGER_FACTORY.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();

			StudentDetail stu = new StudentDetail();
			stu.setName(name);
			stu.setAge(age);

			manager.persist(stu);

			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public static List<StudentDetail> read(String jpql) {

		List<StudentDetail> students = null;

		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			transaction = manager.getTransaction();
			transaction.begin();

			students = manager.createQuery(jpql, StudentDetail.class).getResultList();

			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			manager.close();
		}
		return students;
	}

	public static void delete(String name) {
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			transaction = manager.getTransaction();
			transaction.begin();

			CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
			CriteriaQuery<StudentDetail> criteriaQuery = criteriaBuilder.createQuery(StudentDetail.class);
			Root<StudentDetail> from = criteriaQuery.from(StudentDetail.class);
			criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("name"), name));
			TypedQuery<StudentDetail> query = manager.createQuery(criteriaQuery);
			List<StudentDetail> resultList = query.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				StudentDetail stu = resultList.get(0);

				manager.remove(stu);
			}
			

			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public static void upate(String name, int age) {
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			transaction = manager.getTransaction();
			transaction.begin();
			
			CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
			CriteriaQuery<StudentDetail> criteriaQuery = criteriaBuilder.createQuery(StudentDetail.class);
			Root<StudentDetail> from = criteriaQuery.from(StudentDetail.class);
			criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("name"),name));
			TypedQuery<StudentDetail> query = manager.createQuery(criteriaQuery);
			List<StudentDetail> resultList = query.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				StudentDetail stu = resultList.get(0);
				stu.setAge(age);

				manager.persist(stu);
			}
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			manager.close();
		}
	}
	
	public static <T> List<T> executeSelectNativeQuery(Class<T> claz, String nativeQuery, 
			Map<String, Object> paramMap)
					 {
		Query query = ENTITY_MANAGER_FACTORY.createEntityManager().createNativeQuery(nativeQuery);
		if(paramMap != null)
		{
			Set<Entry<String,Object>> paramSet = paramMap.entrySet();
			for(Entry<String,Object> entry : paramSet)
				query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}
	
	private static void print(List<StudentDetail> students) {
		if (students != null) {
			for (StudentDetail stu : students) {
				System.out.println(stu);
			}
		}
	}
}
