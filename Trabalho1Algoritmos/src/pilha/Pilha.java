/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pilha;

/**
 *
 * @author Lucas
 */
public interface Pilha<T> {

    /**
     *
     * @param info
     */
    public void push(T info);

    public T pop();

    public T peek();

    public boolean estaVazia();

    public void liberar();

}
