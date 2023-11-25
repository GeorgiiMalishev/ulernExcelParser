import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateTest {
    public static void main(String[] args) {
        // Создание фабрики EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sqlitePU");

        // Открытие нового EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Ваш код для работы с базой данных

        // Закрытие EntityManager и EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}