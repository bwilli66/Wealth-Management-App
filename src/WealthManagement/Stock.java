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
public class Stock extends Security {
    
    public Stock(String a, double b, int c){
       super(a, b, c);        
    }
    
    public double getMergeValue(){
        
        return this.price * this.quanity;
    }
    
    @Override
    public double getAssetValue() {
        return this.price * this.quanity;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public int getQuanity() {
        return this.quanity;
    }
    
    public void setPrice(double price){
        this.price = price;
    }

    public void setQuanity(int quan){
        this.quanity = quan;
    }
    

    @Override
    public String getDetailString(){
        return this.name + ", " + this.getClass().getSimpleName() + ", Shares "
                + "Owned: " + this.quanity + ", Price: " + String.format("%.2f", this.price) + 
                ", Value; " + String.format("%.2f", this.quanity * this.price);
    }
}
