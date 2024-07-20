package com.algaworks;

import com.algaworks.model.Cliente;
import com.algaworks.model.SexoCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrudClienteTest extends EntityManagerTest {

    @Test
    public void inserirCliente() {
        Cliente cliente = new Cliente();

        cliente.setId(3);
        cliente.setNome("Thico");

        entityManager.persist(cliente);
        abrirTransaction();
        comitarTransaction();

        entityManager.clear();

        Assertions.assertNotNull(entityManager.find(Cliente.class, cliente.getId()));
    }

    @Test
    public void buscarClientePorID(){
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Assertions.assertNotNull(cliente);
    }

    @Test
    public void atualizaObjeto() {
        Cliente cliente = new Cliente(4, "Eduardo", SexoCliente.MASCULINO);

        abrirTransaction();
        entityManager.merge(cliente);
        comitarTransaction();

        entityManager.clear();

        Assertions.assertEquals("Eduardo", entityManager.find(Cliente.class, cliente.getId()).getNome());
    }

    @Test
    public void removerCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 4);

        abrirTransaction();
        entityManager.remove(cliente);
        comitarTransaction();

        Assertions.assertNull(entityManager.find(Cliente.class, 4));
    }
}
