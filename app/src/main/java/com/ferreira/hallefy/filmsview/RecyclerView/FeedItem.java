package com.ferreira.hallefy.filmsview.RecyclerView;

/**
 * Created by HallefyPC on 12/06/2017.
 */

public class FeedItem {

    private String title,urlImagem,rate,descricao;
    private int id_filme;

    public String getTitle() {
        return title;
    }

    public void setTitle(String eventName) {
        this.title = eventName;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_filme() {
        return id_filme;
    }

    public void setId_filme(int id_filme) {
        this.id_filme = id_filme;
    }
}