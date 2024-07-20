package com.algaworks;

import com.algaworks.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsultandoRegistrosTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador(){
        Produto produto = entityManager.find(Produto.class, 1);
        //Ao fazer umna asserção ele faz uma busca no banco.
        Assertions.assertNotNull(produto);
    }

    @Test
    public void atualizarReferencia() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone");
        entityManager.refresh(produto);
        Assertions.assertEquals("Kindle", produto.getNome());
    }
}
