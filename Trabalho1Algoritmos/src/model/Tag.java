/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lucas
 */
public class Tag {

    private String tagNome;
    private int tagQuantidade;

    public Tag(String tagNome) {
        this.tagNome = tagNome;
        this.tagQuantidade = 1;
    }

    public String getTagNome() {
        return tagNome;
    }

    public int getTagQuantidade() {
        return tagQuantidade;
    }

    public void incrementaQuantidade() {
        tagQuantidade++;
    }
}
