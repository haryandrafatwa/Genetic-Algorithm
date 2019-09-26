/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_algorithm;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Genetic_Algorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int[] kromList;
        
        Scanner ipt = new Scanner(System.in);
        
        System.out.println("Genetic Algorithm");
        System.out.println("");
//        System.out.print("Panjang Kromosom: ");
//        int panjangKromosom = ipt.nextInt();
//        System.out.print("Banyak Populasi: ");
//        int banyakPopulasi = ipt.nextInt();
        
        Populasi populasi = new Populasi(10,10);
        //populasi.encFenotipe();
        populasi.getPop(); 
        System.out.println("");
        System.out.println("");
        populasi.getPopDetail();
        
    }
    
}
