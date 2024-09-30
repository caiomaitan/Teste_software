package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestar() {
        if (disponivel) {
            disponivel = false;
        } else {
            throw new IllegalStateException("O livro já está emprestado.");
        }
    }

    public void devolver() {
        disponivel = true;
    }

    public void alterarAutor(String novoAutor) {
        this.autor = novoAutor;
    }
}

// Classe Biblioteca
class Biblioteca {
    private List<Livro> livros;

    public Biblioteca() {
        this.livros = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(titulo)) {
                return livro;
            }
        }
        return null;
    }

    public int contarLivrosDisponiveis() {
        int count = 0;
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                count++;
            }
        }
        return count;
    }

    public void emprestarLivro(String titulo) {
        Livro livro = buscarLivroPorTitulo(titulo);
        if (livro != null && livro.isDisponivel()) {
            livro.emprestar();
        } else {
            throw new IllegalStateException("Livro não disponível para empréstimo.");
        }
    }
}

// Classe com os testes JUnit
public class Main {

    @Test
    public void testCriacaoLivro() {
        Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien");
        assertEquals("O Senhor dos Anéis", livro.getTitulo());
        assertEquals("J.R.R. Tolkien", livro.getAutor());
        assertTrue(livro.isDisponivel());
    }

    @Test
    public void testEmprestarLivro() {
        Livro livro = new Livro("O Hobbit", "J.R.R. Tolkien");
        livro.emprestar();
        assertFalse(livro.isDisponivel());
    }

    @Test
    public void testDevolverLivro() {
        Livro livro = new Livro("O Hobbit", "J.R.R. Tolkien");
        livro.emprestar();
        livro.devolver();
        assertTrue(livro.isDisponivel());
    }

    @Test
    public void testEmprestarLivroIndisponivel() {
        Livro livro = new Livro("O Hobbit", "J.R.R. Tolkien");
        livro.emprestar();
        assertThrows(IllegalStateException.class, livro::emprestar);
    }

    @Test
    public void testAlterarAutor() {
        Livro livro = new Livro("O Hobbit", "Desconhecido");
        livro.alterarAutor("J.R.R. Tolkien");
        assertEquals("J.R.R. Tolkien", livro.getAutor());
    }

    @Test
    public void testAdicionarLivro() {
        Biblioteca biblioteca = new Biblioteca();
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien");
        Livro livro2 = new Livro("O Hobbit", "J.R.R. Tolkien");
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        assertEquals(2, biblioteca.contarLivrosDisponiveis());
    }

    @Test
    public void testRemoverLivro() {
        Biblioteca biblioteca = new Biblioteca();
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien");
        Livro livro2 = new Livro("O Hobbit", "J.R.R. Tolkien");
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.removerLivro(livro1);
        assertEquals(1, biblioteca.contarLivrosDisponiveis());
    }

    @Test
    public void testBuscarLivroPorTitulo() {
        Biblioteca biblioteca = new Biblioteca();
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien");
        Livro livro2 = new Livro("O Hobbit", "J.R.R. Tolkien");
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        Livro livro = biblioteca.buscarLivroPorTitulo("O Hobbit");
        assertNotNull(livro);
        assertEquals("O Hobbit", livro.getTitulo());
    }

    @Test
    public void testContarLivrosDisponiveis() {
        Biblioteca biblioteca = new Biblioteca();
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien");
        Livro livro2 = new Livro("O Hobbit", "J.R.R. Tolkien");
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        assertEquals(2, biblioteca.contarLivrosDisponiveis());
        livro1.emprestar();
        assertEquals(1, biblioteca.contarLivrosDisponiveis());
    }

    public static void main(String[] args) {

    }
}
