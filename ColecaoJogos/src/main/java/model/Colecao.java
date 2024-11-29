/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/**
 *
 * @author lefoly
 */

public class  Colecao{
    private int id;
    private String nome;
    private String genero;
    private String plataforma;
    private String localImagem;
    private String lancamento;
    private String desenvolvedora;
    private boolean possuir;


    public Colecao() {
    }
    
    public Colecao(int id, String nome, String genero, String plataforma, String localImagem, String lancamento, String desenvolvedora, boolean possuir) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.plataforma = plataforma;
        this.localImagem = localImagem;
        this.lancamento = lancamento;
        this.desenvolvedora = desenvolvedora;
        this.possuir = possuir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getLocalImagem() {
        return localImagem;
    }

    public void setLocalImagem(String localImagem) {
        this.localImagem = localImagem;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }

    public void setDesenvolvedora(String desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }

    public boolean isPossuir() {
        return possuir;
    }

    public void setPossuir(boolean possuir) {
        this.possuir = possuir;
    }
    
}
      
