
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {                // create a seam carver object based on the given picture
        if (picture == null)
            throw new IllegalArgumentException();
        this.picture = picture;
    }
    
    public Picture picture() {                          // current picture
        return picture;
    }
    
    public     int width() {                           // width of current picture
        return picture.width();
    }
    
    public     int height() {                          // height of current picture
        return picture.height();
    }
    
    private int energyImpl(int x, int y, int idx) {
        Color a = picture.get(x - idx, y - (1-idx));
        Color b = picture.get(x + idx, y + (1-idx));
        int dar = a.getRed() - b.getRed();
        int dag = a.getGreen() - b.getGreen();
        int dab = a.getBlue() - b.getBlue();
        return dar*dar + dag*dag + dab*dab;
    }

    public  double energy(int x, int y) {              // energy of pixel at column x and row y
        if (x < 0 || y < 0 || x >= picture.width() || y >= picture.height())
            throw new IllegalArgumentException();
        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1)
            return 1000;
        return Math.sqrt(energyImpl(x, y, 0) + energyImpl(x, y, 1));
    }
    
    private interface View {
        int width();
        int height();
        double energy(int x, int y);
    };

    private class UprightView implements View {
        @Override
        public int width() {
            return picture.width();
        }

        @Override
        public int height() {
            return picture.height();
        }

        @Override
        public double energy(int x, int y) {
            return SeamCarver.this.energy(x, y);
        }
    }

    private class RotatedView implements View {
        @Override
        public int width() {
            return picture.height();
        }

        @Override
        public int height() {
            return picture.width();
        }

        @Override
        public double energy(int x, int y) {
            return SeamCarver.this.energy(y, x);
        }
    }

    private void relaxPixel(View view, int sx, double[] sEnergy, int dx, int dy, double[] dEnergy, int[][] from) {
        double newEnergy = sEnergy[sx] + view.energy(dx, dy);
        if (newEnergy < dEnergy[dx]) {
            dEnergy[dx] = newEnergy;
            from[dx][dy] = sx;
        }
    }

    // Was originally implemented for a vertical seam
    private int[] findSeam(View view) {
        double[] curEnergy = new double[view.width()];
        double[] nextEnergy = new double[view.width()];
        int[][] from = new int[view.width()][view.height()];

        for (int x = 0; x < view.width(); ++x)
            curEnergy[x] = view.energy(x, 0);

        for (int y = 0; y + 1 < view.height(); ++y) {
            for (int x = 0; x < view.width(); ++x)
                nextEnergy[x] = Double.POSITIVE_INFINITY;

            for (int x = 0; x < view.width(); ++x) {
                if (x > 0)
                    relaxPixel(view, x, curEnergy, x-1, y+1, nextEnergy, from);
                relaxPixel(view, x, curEnergy, x, y+1, nextEnergy, from);
                if (x+1 < view.width())
                    relaxPixel(view, x, curEnergy, x, y-1, nextEnergy, from);
            }

            double[] t = curEnergy;
            curEnergy = nextEnergy;
            nextEnergy = t;
        }

        // Find minimum energy
        double e = curEnergy[0];
        int x = 0;
        for (int i = 1; i < view.width(); ++i) {
            if (curEnergy[i] < e) {
                e = curEnergy[i];
                x = i;
            }
        }

        int[] seam = new int[view.height()];
        seam[view.width()-1] = x;
        for (int y = view.width()-1; y > 0; --y)
            seam[y-1] = from[seam[y]][y];
        return seam;
    }

    public   int[] findHorizontalSeam() {               // sequence of indices for horizontal seam
        return findSeam(new RotatedView());
    }
    
    public   int[] findVerticalSeam() {                 // sequence of indices for vertical seam
        return findSeam(new UprightView());
    }
    
    private void checkSeam(View view, int[] seam) {
        if (seam == null || seam.length != view.height())
            throw new IllegalArgumentException();
        for (int y = 0; y < view.height(); ++y)
            if (seam[y] < 0 || seam[y] >= view.width())
                throw new IllegalArgumentException();
        for (int y = 1; y < view.height(); ++y) {
            int dx = Math.abs(seam[y-1] - seam[y]);
            if (dx != 0 && dx != 1)
                throw new IllegalArgumentException();
        }
    }

    public    void removeHorizontalSeam(int[] seam) {   // remove horizontal seam from current picture
        checkSeam(new RotatedView(), seam);

        Picture newPic = new Picture(width(), height()-1);
        for (int y = 0; y < height(); ++y) {
            for (int x = 0; x < width(); ++x) {
                if (y < seam[x])
                    newPic.setRGB(x, y, picture.getRGB(x, y));
                else if (y > seam[x])
                    newPic.setRGB(x, y-1, picture.getRGB(x, y));
            }
        }

        picture = newPic;
    }
    
    public    void removeVerticalSeam(int[] seam) {     // remove vertical seam from current picture
        checkSeam(new UprightView(), seam);

        Picture newPic = new Picture(width()-1, height());
        for (int y = 0; y < height(); ++y) {
            for (int x = 0; x < width(); ++x) {
                if (x < seam[y])
                    newPic.setRGB(x, y, picture.getRGB(x, y));
                else if (x > seam[y])
                    newPic.setRGB(x-1, y, picture.getRGB(x, y));
            }
        }

        picture = newPic;
    }
}    