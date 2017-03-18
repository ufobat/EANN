package org.eann.sim.simulation.mapgeneration;

import java.util.Random;

/**
 * Created by martin on 18.03.17.
 */
public class PerlinNoiseFactory extends HeightArrayFactory {

    private static final double STRETCH_CONSTANT_2D = -0.211324865405187;    //(1/Math.sqrt(2+1)-1)/2;
    private static final double SQUISH_CONSTANT_2D = 0.366025403784439;      //(Math.sqrt(2+1)-1)/2;
    private static final double STRETCH_CONSTANT_3D = -1.0 / 6;              //(1/Math.sqrt(3+1)-1)/3;
    private static final double SQUISH_CONSTANT_3D = 1.0 / 3;                //(Math.sqrt(3+1)-1)/3;
    private static final double STRETCH_CONSTANT_4D = -0.138196601125011;    //(1/Math.sqrt(4+1)-1)/4;
    private static final double SQUISH_CONSTANT_4D = 0.309016994374947;      //(Math.sqrt(4+1)-1)/4;

    private static final double NORM_CONSTANT_2D = 47;
    private static final double NORM_CONSTANT_3D = 103;
    private static final double NORM_CONSTANT_4D = 30;

    private short[] perm;
    private short[] permGradIndex3D;

    private static final long DEFAULT_SEED = 0;
    private final Random randomGenerator;


    public PerlinNoiseFactory() {
        this(DEFAULT_SEED);
    }

    public PerlinNoiseFactory (long seed) {
        newSeed(seed);
        this.randomGenerator = new Random();
    }

    public void randomize() {
        newSeed(randomGenerator.nextLong());
    }

    public void newSeed(long seed) {
        perm = new short[256];
        permGradIndex3D = new short[256];
        short[] source = new short[256];
        for (short i = 0; i < 256; i++)
            source[i] = i;
        seed = seed * 6364136223846793005l + 1442695040888963407l;
        seed = seed * 6364136223846793005l + 1442695040888963407l;
        seed = seed * 6364136223846793005l + 1442695040888963407l;
        for (int i = 255; i >= 0; i--) {
            seed = seed * 6364136223846793005l + 1442695040888963407l;
            int r = (int)((seed + 31) % (i + 1));
            if (r < 0)
                r += (i + 1);
            perm[i] = source[r];
            permGradIndex3D[i] = (short)((perm[i] % (gradients3D.length / 3)) * 3);
            source[r] = source[i];
        }
    }


    @Override
    public double[][] buildHeightMap(int width, int length) {
        return buildHeightMap(width, length, 3, 0.25, 0.045);
    }

    private double extrapolate(int xsb, int ysb, double dx, double dy)
    {
        int index = perm[(perm[xsb & 0xFF] + ysb) & 0xFF] & 0x0E;
        return gradients2D[index] * dx
                + gradients2D[index + 1] * dy;
    }

    private double extrapolate(int xsb, int ysb, int zsb, double dx, double dy, double dz)
    {
        int index = permGradIndex3D[(perm[(perm[xsb & 0xFF] + ysb) & 0xFF] + zsb) & 0xFF];
        return gradients3D[index] * dx
                + gradients3D[index + 1] * dy
                + gradients3D[index + 2] * dz;
    }

    private double extrapolate(int xsb, int ysb, int zsb, int wsb, double dx, double dy, double dz, double dw)
    {
        int index = perm[(perm[(perm[(perm[xsb & 0xFF] + ysb) & 0xFF] + zsb) & 0xFF] + wsb) & 0xFF] & 0xFC;
        return gradients4D[index] * dx
                + gradients4D[index + 1] * dy
                + gradients4D[index + 2] * dz
                + gradients4D[index + 3] * dw;
    }

    private static int fastFloor(double x) {
        int xi = (int)x;
        return x < xi ? xi - 1 : xi;
    }

    //Gradients for 2D. They approximate the directions to the
    //vertices of an octagon from the center.
    private static byte[] gradients2D = new byte[] {
            5,  2,    2,  5,
            -5,  2,   -2,  5,
            5, -2,    2, -5,
            -5, -2,   -2, -5,
    };

    //Gradients for 3D. They approximate the directions to the
    //vertices of a rhombicuboctahedron from the center, skewed so
    //that the triangular and square facets can be inscribed inside
    //circles of the same radius.
    private static byte[] gradients3D = new byte[] {
            -11,  4,  4,     -4,  11,  4,    -4,  4,  11,
            11,  4,  4,      4,  11,  4,     4,  4,  11,
            -11, -4,  4,     -4, -11,  4,    -4, -4,  11,
            11, -4,  4,      4, -11,  4,     4, -4,  11,
            -11,  4, -4,     -4,  11, -4,    -4,  4, -11,
            11,  4, -4,      4,  11, -4,     4,  4, -11,
            -11, -4, -4,     -4, -11, -4,    -4, -4, -11,
            11, -4, -4,      4, -11, -4,     4, -4, -11,
    };

    //Gradients for 4D. They approximate the directions to the
    //vertices of a disprismatotesseractihexadecachoron from the center,
    //skewed so that the tetrahedral and cubic facets can be inscribed inside
    //spheres of the same radius.
    private static byte[] gradients4D = new byte[] {
            3,  1,  1,  1,      1,  3,  1,  1,      1,  1,  3,  1,      1,  1,  1,  3,
            -3,  1,  1,  1,     -1,  3,  1,  1,     -1,  1,  3,  1,     -1,  1,  1,  3,
            3, -1,  1,  1,      1, -3,  1,  1,      1, -1,  3,  1,      1, -1,  1,  3,
            -3, -1,  1,  1,     -1, -3,  1,  1,     -1, -1,  3,  1,     -1, -1,  1,  3,
            3,  1, -1,  1,      1,  3, -1,  1,      1,  1, -3,  1,      1,  1, -1,  3,
            -3,  1, -1,  1,     -1,  3, -1,  1,     -1,  1, -3,  1,     -1,  1, -1,  3,
            3, -1, -1,  1,      1, -3, -1,  1,      1, -1, -3,  1,      1, -1, -1,  3,
            -3, -1, -1,  1,     -1, -3, -1,  1,     -1, -1, -3,  1,     -1, -1, -1,  3,
            3,  1,  1, -1,      1,  3,  1, -1,      1,  1,  3, -1,      1,  1,  1, -3,
            -3,  1,  1, -1,     -1,  3,  1, -1,     -1,  1,  3, -1,     -1,  1,  1, -3,
            3, -1,  1, -1,      1, -3,  1, -1,      1, -1,  3, -1,      1, -1,  1, -3,
            -3, -1,  1, -1,     -1, -3,  1, -1,     -1, -1,  3, -1,     -1, -1,  1, -3,
            3,  1, -1, -1,      1,  3, -1, -1,      1,  1, -3, -1,      1,  1, -1, -3,
            -3,  1, -1, -1,     -1,  3, -1, -1,     -1,  1, -3, -1,     -1,  1, -1, -3,
            3, -1, -1, -1,      1, -3, -1, -1,      1, -1, -3, -1,      1, -1, -1, -3,
            -3, -1, -1, -1,     -1, -3, -1, -1,     -1, -1, -3, -1,     -1, -1, -1, -3,
    };

    //2D OpenSimplex Noise.
    public double noise(double x, double y) {

        //Place input coordinates onto grid.
        double stretchOffset = (x + y) * STRETCH_CONSTANT_2D;
        double xs = x + stretchOffset;
        double ys = y + stretchOffset;

        //Floor to get grid coordinates of rhombus (stretched square) super-cell origin.
        int xsb = fastFloor(xs);
        int ysb = fastFloor(ys);

        //Skew out to get actual coordinates of rhombus origin. We'll need these later.
        double squishOffset = (xsb + ysb) * SQUISH_CONSTANT_2D;
        double xb = xsb + squishOffset;
        double yb = ysb + squishOffset;

        //Compute grid coordinates relative to rhombus origin.
        double xins = xs - xsb;
        double yins = ys - ysb;

        //Sum those together to get a value that determines which region we're in.
        double inSum = xins + yins;

        //Positions relative to origin point.
        double dx0 = x - xb;
        double dy0 = y - yb;

        //We'll be defining these inside the next block and using them afterwards.
        double dx_ext, dy_ext;
        int xsv_ext, ysv_ext;

        double value = 0;

        //Contribution (1,0)
        double dx1 = dx0 - 1 - SQUISH_CONSTANT_2D;
        double dy1 = dy0 - 0 - SQUISH_CONSTANT_2D;
        double attn1 = 2 - dx1 * dx1 - dy1 * dy1;
        if (attn1 > 0) {
            attn1 *= attn1;
            value += attn1 * attn1 * extrapolate(xsb + 1, ysb + 0, dx1, dy1);
        }

        //Contribution (0,1)
        double dx2 = dx0 - 0 - SQUISH_CONSTANT_2D;
        double dy2 = dy0 - 1 - SQUISH_CONSTANT_2D;
        double attn2 = 2 - dx2 * dx2 - dy2 * dy2;
        if (attn2 > 0) {
            attn2 *= attn2;
            value += attn2 * attn2 * extrapolate(xsb + 0, ysb + 1, dx2, dy2);
        }

        if (inSum <= 1) { //We're inside the triangle (2-Simplex) at (0,0)
            double zins = 1 - inSum;
            if (zins > xins || zins > yins) { //(0,0) is one of the closest two triangular vertices
                if (xins > yins) {
                    xsv_ext = xsb + 1;
                    ysv_ext = ysb - 1;
                    dx_ext = dx0 - 1;
                    dy_ext = dy0 + 1;
                } else {
                    xsv_ext = xsb - 1;
                    ysv_ext = ysb + 1;
                    dx_ext = dx0 + 1;
                    dy_ext = dy0 - 1;
                }
            } else { //(1,0) and (0,1) are the closest two vertices.
                xsv_ext = xsb + 1;
                ysv_ext = ysb + 1;
                dx_ext = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
                dy_ext = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
            }
        } else { //We're inside the triangle (2-Simplex) at (1,1)
            double zins = 2 - inSum;
            if (zins < xins || zins < yins) { //(0,0) is one of the closest two triangular vertices
                if (xins > yins) {
                    xsv_ext = xsb + 2;
                    ysv_ext = ysb + 0;
                    dx_ext = dx0 - 2 - 2 * SQUISH_CONSTANT_2D;
                    dy_ext = dy0 + 0 - 2 * SQUISH_CONSTANT_2D;
                } else {
                    xsv_ext = xsb + 0;
                    ysv_ext = ysb + 2;
                    dx_ext = dx0 + 0 - 2 * SQUISH_CONSTANT_2D;
                    dy_ext = dy0 - 2 - 2 * SQUISH_CONSTANT_2D;
                }
            } else { //(1,0) and (0,1) are the closest two vertices.
                dx_ext = dx0;
                dy_ext = dy0;
                xsv_ext = xsb;
                ysv_ext = ysb;
            }
            xsb += 1;
            ysb += 1;
            dx0 = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
            dy0 = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
        }

        //Contribution (0,0) or (1,1)
        double attn0 = 2 - dx0 * dx0 - dy0 * dy0;
        if (attn0 > 0) {
            attn0 *= attn0;
            value += attn0 * attn0 * extrapolate(xsb, ysb, dx0, dy0);
        }

        //Extra Vertex
        double attn_ext = 2 - dx_ext * dx_ext - dy_ext * dy_ext;
        if (attn_ext > 0) {
            attn_ext *= attn_ext;
            value += attn_ext * attn_ext * extrapolate(xsv_ext, ysv_ext, dx_ext, dy_ext);
        }

        return value / NORM_CONSTANT_2D;
    }

    public double[][] buildHeightMap(int width, int height, int octaves, double roughness, double scale){
        double[][] totalNoise = new double[width][height];
        double layerFrequency = scale;
        double layerWeight = 1;
        double weightSum = 0;

        for (int octave = 0; octave < octaves; octave++) {
            //Calculate single layer/octave of simplex noise, then add it to total noise
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    totalNoise[x][y] += (double) noise(x * layerFrequency,y * layerFrequency) * layerWeight;
                }
            }

            //Increase variables with each incrementing octave
            layerFrequency *= 2;
            weightSum += layerWeight;
            layerWeight *= roughness;

        }
        normalize(totalNoise);
        return totalNoise;
    }
}
