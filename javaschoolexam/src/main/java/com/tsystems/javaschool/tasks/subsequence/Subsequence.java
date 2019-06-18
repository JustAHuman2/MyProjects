package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) throws IllegalArgumentException {
        try {
            if (y == null) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < x.size(); i++) {
                if (y.contains(x.get(i))) {
                    for (int j = y.indexOf(x.get(i)); j >= 0; j--) {
                        y.remove(j);
                    }
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}