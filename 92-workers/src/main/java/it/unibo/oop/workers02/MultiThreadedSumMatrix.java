package it.unibo.oop.workers02;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedSumMatrix implements SumMatrix{
    
    private final int nthread;
    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
    }

    @Override
    public double sum(double[][] matrix) {
        // TODO Auto-generated method stub
        List<Worker> listWorker = new ArrayList<>(nthread);
        int n = (matrix.length * matrix[0].length) / nthread;
        for (int i = 0; i < nthread - 1; i++){
            listWorker.add(new Worker(matrix, i ,n));
        }
    }

    private static class Worker extends Thread{
        private double[][] matrix;
        private int posThread;
        private int n;

        public Worker(final double[][] matrix, final int posThread, final int n ){
            this.matrix = matrix;
            this.n = n;
            this.posThread = posThread;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            
        }

    }
    
}
