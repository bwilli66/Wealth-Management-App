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
public abstract class Property extends Asset{

    protected double value;
    protected double debt;
    
    public Property(String n, double a, double b) {
        super(n);
        this.value = a;
        this.debt = b;     
    }
    
    public double getDebtAmount(){
        return this.debt;
    }
    
    @Override
    public double getMergeValue(){
        
        return this.value - this.debt;
    }
    
    @Override
    public double getAssetValue(){
        return this.value;
    } 
    
    public void setAssetValue(double val){
        this.value = val;
    }

    public void setDebt(double debt){
        this.debt = debt;
    }
    
    @Override
    public String getDetailString(){
        return this.name + ", " + this.getClass().getSimpleName() + ", Value: " 
                + String.format("%.2f", this.value) + ", Debt: " + String.format("%.2f", this.debt);
    }
    

}
