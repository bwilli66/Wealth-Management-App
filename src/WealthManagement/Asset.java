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
public abstract class Asset {
    protected String name;
    
    public Asset(String n){
        name = n;
    }
    
    public String toString(){
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public abstract double getAssetValue();
    
    public abstract double getMergeValue();
    
    public abstract String getDetailString();
    
}
