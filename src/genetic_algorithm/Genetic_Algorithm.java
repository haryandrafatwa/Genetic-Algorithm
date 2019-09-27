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
        
        Populasi[] popList;
        
        Scanner ipt = new Scanner(System.in);
        
        System.out.println("Genetic Algorithm");
        System.out.println("");
//        System.out.print("Panjang Kromosom: ");
//        int panjangKromosom = ipt.nextInt();
//        System.out.print("Banyak Populasi: ");
//        int banyakPopulasi = ipt.nextInt();
            
        popList = new Populasi[4];
        
        double max = 0;
        for (int i = 0; i < popList.length; i++) {
            popList[i] = new Populasi(4,4);
            //populasi.encFenotipe();
            popList[i].getPop(); 
            System.out.println("");
            System.out.println("");
            popList[i].getPopDetail();
            popList[i].getRoulette();
            popList[i].setParent();
            System.out.println("");
            popList[i].getParent();
            popList[i].crossOver();
            System.out.println("");
            popList[i].getParent();
            popList[i].mutasi();
            System.out.println("");
            popList[i].getParent();
            System.out.println("");
            popList[i].getBFittest();
            System.out.println(popList[i].getFittest());
            if (popList[i].getFittest() > max) {
                max = popList[i].getFittest();
            }
        }
        System.out.println("");
        System.out.println(max);
    }
    
}
