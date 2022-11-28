package it.unibo.oop.workers02;

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
        for (int i = 0; i < nthread; i++){
            listWorker.add(new Worker(matrix, n, i, nthread-1));
        }

        for (final Worker w: listWorker) {
            w.start();
        }

        long sum = 0;
        for (final Worker w: listWorker) {
            try {
                w.join();
                sum += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        return sum;
    }

    private static class Worker extends Thread {
        private double[][] matrix;
        private long res;
        private int posThread;
        private int nElem;
        private int numThread;

        public Worker(final double[][] matrix, final int n, final int pos, final int numThread ) {
            super();
            this.matrix = matrix;
            this.nElem = n;
            this.posThread = pos;
            this.numThread = numThread;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            int i = posThread * nElem / matrix.length;
            int j = posThread * nElem %  matrix[0].length;
            if( numThread == posThread){
                nElem = matrix.length * matrix[0].length - nElem * posThread;
            }
            for ( int counter = 0; counter < nElem && i < matrix.length; i++) {
                for ( ; counter < nElem && j < matrix[0].length ; ){
                    res += matrix[i][j];
                    j++;
                    counter++;
                }
                j = 0;
            }
        }

        public long getResult() {
            return this.res;
        }

    }
    
}
