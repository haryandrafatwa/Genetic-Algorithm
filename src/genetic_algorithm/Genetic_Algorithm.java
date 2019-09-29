/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_algorithm;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
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
        System.out.print("Panjang Kromosom: ");
        int panjangKromosom = ipt.nextInt();
        System.out.print("Banyak Populasi: ");
        int banyakPopulasi = ipt.nextInt();
        System.out.print("Banyak Generasi: ");
        int banyakGenerasi = ipt.nextInt();
        System.out.println("");
        
        while(panjangKromosom <= 0){
            System.out.println("");
            System.out.println("Panjang Kromosom Harus Positif!");
            System.out.print("Panjang Kromosom: ");
            panjangKromosom = ipt.nextInt();
            while(panjangKromosom % 2 != 0){
                System.out.println("");
                System.out.println("Panjang Kromosom Harus Genap!");
                System.out.print("Panjang Kromosom: ");
                panjangKromosom = ipt.nextInt();
            }
        }
        
        while(banyakPopulasi <= 0){
            System.out.println("");
            System.out.println("Banyak Populasi Harus Positif!");
            System.out.print("Banyak Populasi: ");
            banyakPopulasi = ipt.nextInt();
            while(banyakPopulasi % 2 != 0){
                System.out.println("");
                System.out.println("Banyak Populasi Harus Positif!");
                System.out.print("Banyak Populasi: ");
                banyakPopulasi = ipt.nextInt();
            }
        }
        
        while(banyakGenerasi <= 0){
            System.out.println("");
            System.out.println("Banyak Populasi Harus Positif!");
            System.out.print("Banyak Populasi: ");
            banyakPopulasi = ipt.nextInt();
        }
        
        popList = new Populasi[banyakGenerasi];
        
        double max = 0;
        for (int i = 0; i < popList.length; i++) {
            popList[i] = new Populasi(banyakPopulasi,panjangKromosom);
            System.out.println("Generasi - "+(i+1));
            System.out.println("");
            System.out.println("Kromosom Awal");
            popList[i].getPop();
            popList[i].setParent();
            popList[i].crossOver();
            System.out.println("");
            popList[i].mutasi();
            System.out.println("Kromosom Setelah Crossover dan Mutasi");
            popList[i].getParent();
            popList[i].getBFittest();
            System.out.println("Fittest: "+Double.parseDouble(new DecimalFormat("##.####").format(popList[i].getFittest())));
            if (popList[i].getFittest() > max) {
                max = popList[i].getFittest();
            }
            System.out.println("");
        }
        
        System.out.println("Fittest: "+Double.parseDouble(new DecimalFormat("##.####").format(max)));
        for (int i = 0; i < popList.length; i++) {
            for (int j = 0; j < popList[i].getParentList().length; j++) {
                if (popList[i].getParList(j).getFitness() == max) {
                    System.out.print("[ ");
                    popList[i].getParList(j).getGenX();
                    System.out.println("]");
                    popList[i].getParList(j).encodeFenotipe();
                    System.out.print("Fenotipe X1: "+Double.parseDouble(new DecimalFormat("##.####").format(popList[i].getParList(j).getFenX1()))+"  ");
                    System.out.println("Fenotipe X2: "+Double.parseDouble(new DecimalFormat("##.####").format(popList[i].getParList(j).getFenX2())));
                    System.out.println("Nilai Fungsi: "+Double.parseDouble(new DecimalFormat("##.####").format(popList[i].getParList(j).calcH())));
                    break;
                }
            }
        }
    }
    
}

class Populasi {
    private int panjangKromosom;
    private int ukuranPopulasi;
    private Kromosom[] kromList,parentList;
    private double[] roulette;
    private double fittest = 0;
    private double totFitness;

    public Populasi(int ukuranPopulasi, int panjangKromosom) {
        this.panjangKromosom = panjangKromosom;
        this.ukuranPopulasi = ukuranPopulasi;
        kromList = new Kromosom[this.ukuranPopulasi];
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
        parentList = new Kromosom[ukuranPopulasi];
        for (int j = 0; j < parentList.length; j++) {
            int x = new Random().nextInt(roulette.length);
            parentList[j] = new Kromosom(panjangKromosom);
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
            parentList[i].calcFitness();
            if (max < parentList[i].getFitness()) {
                max = parentList[i].getFitness();
                this.fittest = max;
            }
        }
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
        }
    }
    
    public void setPercRW(){
        for (int i = 0; i < kromList.length; i++) {
            double x = Double.parseDouble(new DecimalFormat("##.####").format(kromList[i].getFitness()/totFitness*100));
            kromList[i].setPercRW(x);
        }
    }

    public Kromosom getKromList(int i) {
        return kromList[i];
    }
    
    public Kromosom getParList(int i) {
        return parentList[i];
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
        for (int i = 0; i < parentList.length; i= i+2) {
            int x = new Random().nextInt(parentList[i].getGens().length);
            int[] genP1 = parentList[i].getGens();
            int[] genP2 = parentList[i+1].getGens();
            int[] temp = new int[parentList[i].getGens().length];
            int[] temp2 = new int[parentList[i].getGens().length];
            for (int j = 0; j < parentList[i].getGens().length; j++) {
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
        for (int i=0; i<j; i++){  
            arr[i] = temp[i];  
        }  
        return j;  
    }

    public double getFittest() {
        return fittest;
    }

    public Kromosom[] getParentList() {
        return parentList;
    }
    
    public void getParDetail() {
        
        for (int i = 0; i < parentList.length; i++) {
            System.out.println("Kromosom - "+(i+1));
            parentList[i].getGen();
            System.out.println("Total Fitness: "+totFitness);
            System.out.println("Persentase RW: "+parentList[i].getPercRW());
            System.out.println("");
        }
    }
    
}

class Kromosom {
    private int panjangKromosom;
    private int[] gen;
    private double fenX1, fenX2, fitness,percRW,h;

    public Kromosom() {
    }

    public Kromosom(int panjangKromosom) {
        this.panjangKromosom = panjangKromosom;
        gen = new int[panjangKromosom];
        int x = panjangKromosom/2;
        for (int i = 0; i < x; i++) {
            gen[i] = (int) getRandomIntegerBetweenRange(0, 9);
        }
        
        for (int i = x; i < gen.length; i++) {
            gen[i] = (int) getRandomIntegerBetweenRange(0,9);
        }
        encodeFenotipe();
        calcFitness();
    }

    public void getGen() {
        int x = gen.length/2;
        for (int i = 0; i < x; i++) {
            System.out.print(gen[i]+", ");
        }
        for (int i = x; i < gen.length; i++) {
            System.out.print(gen[i]+", ");
        }
        System.out.println("");
        System.out.println("Fenotipe X1: "+fenX1);
        System.out.println("Fenotipe X2: "+fenX2);
        System.out.println("Nilai h: "+calcH());
        System.out.println("Nilai Fitness: "+fitness);
    }
    
    public void encodeFenotipe(){
        int x = gen.length/2;
        double totX1 = 0,totX2 = 0;
        double totxxx1 = 0,totxxx2 = 0;
        double totalX1,totalX2;
        double x1 = 0,x2 = 0;
        for (int i = 0; i < x; i++) {
            x1= gen[i]*Math.pow(10,-(i+1));
            totX1 = totX1 + x1;
            double xx1 = Math.pow(10, -(i+1)) ;
            totxxx1 = totxxx1 + xx1;
        }
        int j = 1;
        for (int i = x; i < gen.length; i++) {
            x2= gen[i]*Math.pow(10,-(j));
            totX2 = totX2 + x2;
            double xx2 = Math.pow(10, -(j)) ;
            totxxx2 = totxxx2 + xx2;
            j = j + 1;
        }
        totalX1 = ((6/(9*totxxx1))*totX1)-3;
        totalX2 = ((4/(9*totxxx2))*totX2)-2;
        
        fenX1 = totalX1;
        fenX2 = totalX2;
    }
    
    public double calcH(){
        double x = ((4-((Math.pow(fenX1, 2))*2.1)+(Math.pow(fenX1, 4)/3))*Math.pow(fenX1, 2) + fenX1*fenX2+(-4+(Math.pow(fenX2, 2))*4)*Math.pow(fenX2, 2));
        setH(x);
        return x;
    }
    
    public void calcFitness(){
        double x = 1/(calcH()+0.1);
        double y = Math.pow(x, 2);
        setFitness(y);
    }
    
    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }

    public void setPercRW(double percRW) {
        this.percRW = percRW;
    }

    public double getPercRW() {
        return percRW;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    public void getGenX(){
        for (int i = 0; i < gen.length; i++) {
            System.out.print(gen[i]+" ");
        }
    }
    
    public int[] getGens(){
        return this.gen;
    }

    public void setGen(int[] gen) {
        this.gen = gen;
    }

    public double getFenX1() {
        return fenX1;
    }

    public double getFenX2() {
        return fenX2;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
