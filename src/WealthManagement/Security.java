/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WealthManagement;

/**
 *
 * @author BradWilliams1
 */
public abstract class Security extends Asset{
    protected double price;
    protected int quanity;

    public Security(String n,double a, int b) {
        super(n);
        this.price = a;
        this.quanity = b;
    }
}
