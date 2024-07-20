package com.algaworks;

import com.algaworks.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityManagerTest {
    protected static EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        entityManager.close();
    }

    public void abrirTransaction() {
        entityManager.getTransaction().begin();
    }

    public void comitarTransaction() {
        entityManager.getTransaction().commit();
    }

}
