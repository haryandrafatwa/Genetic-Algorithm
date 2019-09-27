/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author User
 */
public class Kromosom {
    private int panjangKromosom;
    private int[] gen;
    private double fenX1, fenX2, fitness,percRW;

    public Kromosom() {
    }

    public Kromosom(int panjangKromosom) {
        this.panjangKromosom = panjangKromosom;
        gen = new int[panjangKromosom];
        int x = panjangKromosom/2;
        int counter;
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
        
        //totalX1 = Double.parseDouble(new DecimalFormat("##.##").format(totalX1));
        //totalX2 = Double.parseDouble(new DecimalFormat("##.##").format(totalX2));
        
        fenX1 = totalX1;
        fenX2 = totalX2;
    }
    
    public double calcH(){
        double x = ((4-((Math.pow(fenX1, 2))*2*1)+(Math.pow(fenX1, 4)/3))*Math.pow(fenX1, 2) + fenX1*fenX2+(-4+(Math.pow(fenX2, 2))*4)*Math.pow(fenX2, 2));
        //x = Double.parseDouble(new DecimalFormat("##.####").format(x));
        return x;
    }
    
    public void calcFitness(){
        //double x = Double.parseDouble(new DecimalFormat("##.####").format(1/(calcH()+0.1)));
        double x = 1/(calcH()+0.1);
        double y = Math.pow(x, 2);;
        BigDecimal a = new BigDecimal(y);
        BigDecimal roundOff = a.setScale(4, BigDecimal.ROUND_HALF_EVEN);
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
}
