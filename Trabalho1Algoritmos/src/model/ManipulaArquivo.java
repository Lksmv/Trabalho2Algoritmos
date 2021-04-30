/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lista.ListaEncadeada;
import pilha.PilhaLista;

/**
 *
 * @author Lucas
 */
public class ManipulaArquivo {
    
    private BufferedReader arquivo;
    private String texto = "";
    private ListaEncadeada<Tag> listaTag = new ListaEncadeada<>();
    private PilhaLista<String> pilha = new PilhaLista<>();

    public BufferedReader getArquivo() {
        return arquivo;
    }

    public void setArquivo(String caminhoArquivo) throws FileNotFoundException {
        this.arquivo = new BufferedReader(new FileReader(caminhoArquivo));
    }
    
    private boolean isSingletonTag(String tag) {
        if (tag.equals("meta")
                || tag.equals("base")
                || tag.equals("br")
                || tag.equals("col")
                || tag.equals("commands")
                || tag.equals("embed")
                || tag.equals("hr")
                || tag.equals("img")
                || tag.equals("input")
                || tag.equals("link")
                || tag.equals("param")
                || tag.equals("source")
                || tag.equals("!DOCTYPE")) {
            return true;
        }
        return false;
    }
    
    private void lerArquivo() throws IOException {
        String linha = getArquivo().readLine();
        int linhaAtual = 0;
        String tagNome = "";
        while (linha != null) {
            linhaAtual++;
            for (int i = 0; i < linha.length() - 1;) {
                if (linha.charAt(i) == '<') {
                    if (linha.charAt(i + 1) != '/') {
                        i++;
                        while (linha.charAt(i) != '>' && linha.charAt(i) != ' ') {
                            tagNome += linha.charAt(i);
                            i++;
                        }
                        if (!isSingletonTag(tagNome)) {
                            pilha.push(tagNome);
                        }
                        Tag tag = existeTag(tagNome);
                        if (tag != null) {
                            tag.incrementaQuantidade();
                        } else {
                            tag = new Tag(tagNome);
                            listaTag.inserir(tag);
                        }
                        tagNome = "";
                        
                    } else {
                        i = i + 2;
                        while (linha.charAt(i) != '>') {
                            tagNome += linha.charAt(i);
                            i++;
                        }
                        if (pilha.peek().equals(tagNome)) {
                            pilha.pop();
                            tagNome = "";
                        } else {
                            texto += "Linha " + linhaAtual + ": encontrado " + tagNome + " e o esperado era: " + pilha.pop() + "\n";
                            tagNome = "";
                        }
                    }
                } else {
                    i++;
                }
            }
            linha = getArquivo().readLine();
        }
    }
    
    public String validarHtml(String caminhoArquivo) throws FileNotFoundException, IOException {
        setArquivo(caminhoArquivo);
        lerArquivo();
        if (!pilha.estaVazia()) {
            texto += "Existe tag(s) que não foram fechadas, verificar!" + "\n";
        }
        if (texto.equals("")) {
            texto = "O arquivo está bem formatado!";
        }
        System.out.println(texto);
        
        return getTexto();
    }
    
    private String getTexto() {
        return texto;
    }
    
    public ListaEncadeada<Tag> getLista() {
        return listaTag;
    }
    
    public Tag existeTag(String nomeTag) {
        for (int i = 0; i < listaTag.obterComprimento(); i++) {
            if (listaTag.obterNo(i).getInfo().getTagNome().equals(nomeTag)) {
                return listaTag.obterNo(i).getInfo();
            }
        }
        return null;
    }
    
    public String getExtensao(String file) {
	String extensao = "";
	int i = file.lastIndexOf('.');
	if (i > 0) {
            extensao = file.substring(i + 1);
	}
	return extensao;
    }
}
