/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import WealthManagement.*;
import java.util.Vector;
/**
 *
 * @author BradWilliams1
 */
public class MergeSort {
    /* uncomment this main and run the file if you want to test.
    public static void main(String[] args) {
        Vector<Asset> vec = new Vector<Asset>();
        vec.add(new BankAccount("10", 10));
        vec.add(new BankAccount("1", 1));
        vec.add(new BankAccount("3", 3));
        vec.add(new BankAccount("2", 2));
        vec.add(new BankAccount("5", 5));
        vec.add(new BankAccount("-1", -1));  
        
        for (Asset a : vec)
            System.out.println(a.toString());
        
        vec = mergeSort(vec);
        
        System.out.println("---------");
        for (Asset a : vec)
            System.out.println(a.toString());
    }*/
    
    public static Vector<Asset> mergeSort(Vector<Asset> A)
    {
        if(A.size() <= 1)
        {
            return A;
        }

        Vector<Asset> left = new Vector<Asset>();
        Vector<Asset> right = new Vector<Asset>();
        
        int midpoint = A.size() / 2;
        
        for (int i = 0; i < midpoint; i++)
        {
             left.add(A.elementAt(i));
        }
        
        for (int j = midpoint; j < A.size(); j++)
        {
            right.add(A.elementAt(j));
        }
        
        left = mergeSort(left);
        right = mergeSort(right);
        
        Vector<Asset> result = merge(left, right);
        
        return result;
    }
    
    public static Vector<Asset> merge(Vector<Asset> left, Vector<Asset> right)
    {
        Vector<Asset> result = new Vector<Asset>();
        int indexL = 0;
        int indexR = 0;
        
        while (indexL < left.size() || indexR < right.size())
        {            
            if (indexL < left.size() && indexR < right.size())
            {
                if (left.elementAt(indexL).getMergeValue() >= right.elementAt(indexR).getMergeValue())
                {
                    result.add(left.elementAt(indexL));
                    indexL++;
                }
                else
                {
                    result.add(right.elementAt(indexR));
                    indexR++;
                }
            }   
            else if (indexL < left.size())
            {
                result.add(left.elementAt(indexL));
                indexL++;
            }
            else
            {
                result.add(right.elementAt(indexR));
                indexR++;
            }
        }
        
        return result;
    }
}
