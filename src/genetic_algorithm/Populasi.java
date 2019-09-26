/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_algorithm;

import java.text.DecimalFormat;

/**
 *
 * @author User
 */
public class Populasi {
    private int panjangKromosom;
    private int ukuranPopulasi;
    private Kromosom[] kromList;
    private int fittest = 0;
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
    
    public void getPop(){
        for (int i = 0; i < kromList.length; i++) {
            System.out.print("[ ");
            kromList[i].getGenX();
            System.out.println("]");
        }
    }
    
    public void calcTotFitness(){
        for (int i = 0; i < kromList.length; i++) {
            if (kromList[i].getFitness() < 0) {
                double x = Math.abs(kromList[i].getFitness());
                totFitness = totFitness + x;
            }else{
                totFitness = totFitness + kromList[i].getFitness();
            }
            totFitness = Double.parseDouble(new DecimalFormat("##.####").format(totFitness));
        }
    }
    
    public void setPercRW(){
        for (int i = 0; i < kromList.length; i++) {
            if (kromList[i].getFitness() < 0 ) {
                double x = Math.abs(kromList[i].getFitness());
                double y = Double.parseDouble(new DecimalFormat("##.##").format(x/totFitness*100));
                kromList[i].setPercRW(y);
            }else{
                double x = Double.parseDouble(new DecimalFormat("##.##").format(kromList[i].getFitness()/totFitness*100));
                kromList[i].setPercRW(x);
            }
        }
    }

    public Kromosom getKromList(int i) {
        return kromList[i];
    }

    public int getUkuranPopulasi() {
        return ukuranPopulasi;
    }
}
