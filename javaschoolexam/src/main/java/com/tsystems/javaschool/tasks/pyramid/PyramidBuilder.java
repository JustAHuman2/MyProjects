package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;

public class PyramidBuilder {

    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        try {
            int[] input = new int[inputNumbers.size()];
            int amount = inputNumbers.size();
            int height = 0;
            int width = 0;

            for (int i = 0; i < input.length; i++) {
                input[i] = inputNumbers.get(i);
            }
            Arrays.sort(input);

            for (int i = 1; i < input.length; i++) {
                if (amount > 0) {
                    amount = amount - i;
                    width = (2 * i) - 1;
                    height++;
                }
            }
            if (amount != 0) {
                throw new CannotBuildPyramidException();
            }
            int[][] output = new int[height][width];
            int count = 0;
            for (int y = 0; y < height; y++) {
                for (int x = (width / 2) - y; x < (width / 2) + y + 1; x = x + 2) {
                    output[y][x] = input[count++];
                }
            }
            return output;
        } catch (Error | Exception e) {
            throw new CannotBuildPyramidException();
        }
    }
}