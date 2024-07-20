package com.algaworks;

import com.algaworks.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class OperacoesComTransacoesTest extends EntityManagerTest {

    @Test
    public void impedirOperacaoComBD() {
        Produto produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto);
        //Para de gerenciar esse objeto e nenhum comando do DB vai ser rodado.
        //Penso que seria util noma listagem onde uma entidade de uma determinada posição da lista não pode ser alterada

        abrirTransaction();
        produto.setNome("Kindle Paper White2");
        produto.setDescricao("Para descrição não ser a antiga");
        comitarTransaction();

        entityManager.clear();
        //Note que não houve alteração
        Assertions.assertEquals("Kindle", entityManager.find(Produto.class, produto.getId()).getNome());
    }

    @Test
    public void inserirPrimeiroObjeto() {
        Produto produto = new Produto();

        produto.setId(2);
        produto.setNome("Câmera");
        produto.setDescricao("Top das tops");
        produto.setPreco(new BigDecimal(5000));

        entityManager.persist(produto);
        abrirTransaction();
        comitarTransaction();

        entityManager.clear();

        Assertions.assertNotNull(entityManager.find(Produto.class, produto.getId()));
    }

    @Test
    public void removerObjeto() {
        Produto produto = entityManager.find(Produto.class, 3);

        abrirTransaction();
        entityManager.remove(produto);
        comitarTransaction();

        Assertions.assertNull(entityManager.find(Produto.class, 3));
    }

    @Test
    public void atualizaObjeto() {
        Produto produto = new Produto(1, "Kindle Paper White", "Conheça o novo kindle", new BigDecimal(599));

        abrirTransaction();
        entityManager.merge(produto);
        comitarTransaction();

        entityManager.clear();

        Assertions.assertEquals("Kindle Paper White", entityManager.find(Produto.class, produto.getId()).getNome());
    }

    @Test
    public void atualizaObjetoGerenciado() {
        Produto produto = entityManager.find(Produto.class, 1);

        abrirTransaction();
        produto.setNome("Kindle Paper White2"); // Merge desconsiderado, pois o objeto já está na memória graças ao Find
        produto.setDescricao("Para descrição não ser a antiga");
        comitarTransaction();

        entityManager.clear();

        Assertions.assertEquals("Kindle Paper White2", entityManager.find(Produto.class, produto.getId()).getNome());
    }

    @Test
    public void inserirObjetoComMerge() {
        Produto produto = new Produto();

        produto.setId(4);
        produto.setNome("Notebook");
        produto.setDescricao("Nitro 5 O poderoso da Acer");
        produto.setPreco(new BigDecimal(6000));

        abrirTransaction();
        entityManager.merge(produto);
        comitarTransaction();

        entityManager.clear();

        Assertions.assertNotNull(entityManager.find(Produto.class, produto.getId()));
    }

    @Test
    public void DiferencaPersistMerge() {
        Produto produtoPersist = new Produto();

        produtoPersist.setId(5);
        produtoPersist.setNome("Smartphone");
        produtoPersist.setDescricao("Ligue, ligue já!");
        produtoPersist.setPreco(BigDecimal.valueOf(1500.20));

        abrirTransaction();
        entityManager.persist(produtoPersist);
        //Apenas insere, não atualiza. Ideal quando quer garantir um novo obj.
        //Pega a instancia, coloca na memória do entity para que seja gerenciada.
        produtoPersist.setNome("Smartphone update");
        //Por ja estar na memória, depois de inserido, atualiza.
        comitarTransaction();

        entityManager.clear();

        Assertions.assertNotNull(entityManager.find(Produto.class, produtoPersist.getId()));

        Produto produtoMerge = new Produto();

        produtoMerge.setId(6);
        produtoMerge.setNome("Tablet");
        produtoMerge.setDescricao("Estude em qualquer lugar!");
        produtoMerge.setPreco(BigDecimal.valueOf(3000));

        abrirTransaction();
        entityManager.merge(produtoMerge);
        //Pega uma cópia da instancia e lança na memória do entity
        produtoMerge.setNome("Tablet update");
        //Não vai ter efeito esse set, pois essa instancia não está sendo gerenciada
        //Parafuncionar o método merge, retorna a cópia

        /*
        produtoMerge = entityManager.merge(produtoMerge);
        produtoMerge.setNome("Tablet update");
         */

        comitarTransaction();

        entityManager.clear();

        Assertions.assertNotNull(entityManager.find(Produto.class, produtoMerge.getId()));
    }
}
