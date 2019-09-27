/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_algorithm;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author User
 */
public class Populasi {
    private int panjangKromosom;
    private int ukuranPopulasi;
    private Kromosom[] kromList,parentList;
    private double[] roulette;
    private double fittest = 0;
    private double totFitness;

    public Populasi(int ukuranPopulasi, int panjangKromosom) {
        this.panjangKromosom = panjangKromosom;
        this.ukuranPopulasi = ukuranPopulasi;
        kromList = new Kromosom[ukuranPopulasi];
        for (int i = 0; i < kromList.length; i++) {
            kromList[i] = new Kromosom(panjangKromosom);
        }
        calcTotFitness();
        setPercRW();
        setRoulette();
        selectParent();
    }
    
    public void encFenotipe(){
        for (int i = 0; i < kromList.length; i++) {
            kromList[i].encodeFenotipe();
        }
    }
    
    public void getPopDetail() {
        
        for (int i = 0; i < kromList.length; i++) {
            System.out.println("Kromosom - "+(i+1));
            kromList[i].getGen();
            System.out.println("Total Fitness: "+totFitness);
            System.out.println("Persentase RW: "+kromList[i].getPercRW());
            System.out.println("");
        }
    }
    
    public void setRoulette(){
        roulette = new double[kromList.length];
        for (int i = 0; i < kromList.length; i++) {
            roulette[i] = kromList[i].getPercRW();
        }
        
        Arrays.sort(roulette);
        int length = removeDuplicateElements(roulette, roulette.length);
        double[] tmp = new double[length];
        for (int i = 0; i < length; i++) {
            tmp[i] = roulette[i];
        }
        
        roulette = tmp;
    }
    
    public void selectParent(){
        parentList = new Kromosom[panjangKromosom];
        for (int j = 0; j < parentList.length; j++) {
            int x = new Random().nextInt(roulette.length);
            parentList[j] = new Kromosom(kromList.length);
            if (j==0) {
                parentList[j].setPercRW(roulette[x]); 
            }else{
                while(parentList[j-1].getPercRW()==roulette[x]){
                    x = new Random().nextInt(roulette.length);
                }
                if (parentList[j-1].getPercRW()!=roulette[x]) {
                    parentList[j].setPercRW(roulette[x]);
                }
            }
        }
    }
    
    public void getBFittest(){
        double max = 0;
        for (int i = 0; i < parentList.length; i++) {
            
            if (max < parentList[i].getFitness()) {
                max = parentList[i].getFitness();
            }
        }
        this.fittest = max;
    }
    
    public void getPop(){
        for (int i = 0; i < kromList.length; i++) {
            System.out.print("[ ");
            kromList[i].getGenX();
            System.out.println("]");
        }
    }
    
    public void calcTotFitness(){
        for (int i = 0; i < kromList.length; i++) {
            totFitness = totFitness + kromList[i].getFitness();
//            if (kromList[i].getFitness() < 0) {
//                double x = Math.abs(kromList[i].getFitness());
//                totFitness = totFitness + x;
//            }else{
//                totFitness = totFitness + kromList[i].getFitness();
//            }
            //totFitness = Double.parseDouble(new DecimalFormat("##.####").format(totFitness));
        }
    }
    
    public void setPercRW(){
        for (int i = 0; i < kromList.length; i++) {
//            if (kromList[i].getFitness() < 0 ) {
//                double x = Math.abs(kromList[i].getFitness());
//                double y = Double.parseDouble(new DecimalFormat("##.##").format(x/totFitness*100));
//                kromList[i].setPercRW(y);
//            }else{
//                double x = Double.parseDouble(new DecimalFormat("##.##").format(kromList[i].getFitness()/totFitness*100));
//                kromList[i].setPercRW(x);
//            }
            double x = Double.parseDouble(new DecimalFormat("##.####").format(kromList[i].getFitness()/totFitness*100));
            //double x = kromList[i].getFitness()/totFitness*5;
            kromList[i].setPercRW(x);
        }
    }

    public Kromosom getKromList(int i) {
        return kromList[i];
    }
    
    public void getRoulette(){
        for (int i = 0; i < roulette.length; i++) {
            System.out.println((i+1)+". "+roulette[i]+", ");
        }
    }
    
    public void setParent(){
        for (int i = 0; i < parentList.length; i++) {
            for (int j = 0; j < kromList.length; j++) {
                if (parentList[i].getPercRW() == kromList[j].getPercRW()) {
                    parentList[i] = kromList[j];
                }
            }
        }
    }
    
    public void crossOver(){
        int z = parentList.length/2;
        for (int i = 0; i < parentList.length; i= i+2) {
            int x = new Random().nextInt(parentList[i].getGens().length);
            int y = 0;
            int[] genP1 = parentList[i].getGens();
            int[] genP2 = parentList[i+1].getGens();
            int[] temp = new int[parentList[i].getGens().length];
            int[] temp2 = new int[parentList[i].getGens().length];
//            for (int j = x+1; j < genP1.length; j++) {
//                temp[y] = genP1[j];
//                genP1[j] = genP2[j];
//                genP2[j] = temp[y];
//                parentList[i].setGen(genP1);
//                parentList[i+1].setGen(genP2);
//                y++;
//            }
            for (int j = 0; j < parentList.length; j++) {
                if (j <= x) {
                    temp[j] = genP1[j];
                    temp2[j] = genP2[j];
                }else{
                    temp[j] = genP2[j];
                    temp2[j] = genP1[j];
                }
                parentList[i].setGen(temp);
                parentList[i+1].setGen(temp2);
            }
        }
    }
    
    public void mutasi(){
        for (int i = 0; i < parentList.length; i++) {
            int isiGen = (int) getRandomIntegerBetweenRange(0, 9);
            int indexRan = new Random().nextInt(parentList[i].getGens().length);
            //int indexRan = 2;
            
            int[] gen = parentList[i].getGens();
            
            gen[indexRan] = isiGen;
            
            parentList[i].setGen(gen);
        }
    }
    
    public void getParent(){
        for (int i = 0; i < parentList.length; i++) {
            System.out.print("[ ");
            parentList[i].getGenX();
            System.out.println("]");
        }
    }
    
    
    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+0.001))+min;
        return x;
    }
    
    public static int removeDuplicateElements(double arr[], int n){  
        if (n==0 || n==1){  
            return n;  
        }  
        double[] temp = new double[n];  
        int j = 0;  
        for (int i=0; i<n-1; i++){  
            if (arr[i] != arr[i+1]){  
                temp[j++] = arr[i];  
            }  
         }  
        temp[j++] = arr[n-1];     
        // Changing original array  
        for (int i=0; i<j; i++){  
            arr[i] = temp[i];  
        }  
        return j;  
    }

    public double getFittest() {
        return fittest;
    }
}
