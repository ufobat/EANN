/**
 * Copyright (C) 2017 Martin Barth
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eann.sim.simulation;

public class Tile {
    public static final double MAX_FOOD_LEVEL = 5f;
    public static final double MIN_FOOD_LEVEL = 0f;
    public static final double GROW_FOOD_AMOUNT = 0.25;
    private final double height;
    private final int x;
    private final int y;
    private double foodLevel;

    public Tile(final double height, final int x, final int y) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.foodLevel = Tile.MIN_FOOD_LEVEL;
    }

    public void growFood() {
        double food = this.foodLevel;
        food += Tile.GROW_FOOD_AMOUNT;
        if (food > Tile.MAX_FOOD_LEVEL) {
            food = Tile.MAX_FOOD_LEVEL;
        }
        this.foodLevel = food;
    }

    public double reduceFoodLevel(final double wantToEat) {
        if (wantToEat < 0) {
            throw new IllegalArgumentException();
        }
        double ate = 0;
        if (this.foodLevel < wantToEat) {
            ate = this.foodLevel;
            this.foodLevel = Tile.MIN_FOOD_LEVEL;
        } else {
            ate = wantToEat;
            this.foodLevel -= wantToEat;
        }
        return ate;
    }

    /**
     * Calculates the FoodLevel as a normalized double value between 0 and 1
     * @return normalized FoodLevel as double between 0 and 1.
     */
    public double getFoodLevelNormalized() {
        return this.foodLevel / Tile.MAX_FOOD_LEVEL;
    }

    public boolean isWater() {
        return this.height < 0.5;
    }

    public boolean isNotAtMinFood() {
        return ! this.isAtMinFood();
    }

    public boolean isNotAtMaxFood() {
        return ! this.isAtMaxFood();
    }

    public boolean isAtMaxFood() {
        return this.foodLevel == Tile.MAX_FOOD_LEVEL;
    }

    public boolean isAtMinFood() {
        return this.foodLevel == Tile.MIN_FOOD_LEVEL;
    }

    public double getHeight() {
        return this.height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getFoodLevel() {
        return this.foodLevel;
    }
}
