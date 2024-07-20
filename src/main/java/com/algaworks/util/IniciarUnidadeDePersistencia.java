package com.algaworks.util;

import com.algaworks.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class IniciarUnidadeDePersistencia {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("Ecommerce-PU");
        EntityManager em = entityManagerFactory.createEntityManager();

        Produto produto = em.find(Produto.class, 1);
        System.out.println(produto.getNome());

        em.close();
        entityManagerFactory.close();
    }

}
