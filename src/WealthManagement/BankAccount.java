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
public class BankAccount extends Asset{
    private double balance;

    public BankAccount(String n, double a){
        super(n);
        
        this.balance = a;
    }
    
    @Override
    public double getMergeValue(){
        
        return balance;
    }

    @Override
    public double getAssetValue(){
        
        return balance;
    }
    
    public void setAssetValue(double bal)
    {
        this.balance = bal;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
    @Override
    public String getDetailString(){
        return this.name + ", " + this.getClass().getSimpleName() + ", Balance: " 
                + String.format("%.2f", this.balance);
    }
}
