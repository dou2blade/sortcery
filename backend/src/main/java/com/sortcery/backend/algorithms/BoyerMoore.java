package com.sortcery.backend.algorithms;

public final class BoyerMoore {
    private BoyerMoore() {}

    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int n = text.length();
        int m = pattern.length();

        if (m == 0) return 0;
        if (m > n) return -1;

        int[] badChar = new int[256];

        for (int i = 0; i < badChar.length; i++) {
            badChar[i] = -1;
        }

        for (int i = 0; i < m; i++) {
            badChar[pattern.charAt(i)] = i;
        }

        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;

            while (
                j >= 0 &&
                pattern.charAt(j) == text.charAt(shift + j)
            ) {
                j--;
            }

            if (j < 0) {
                return shift;
            }

            shift += Math.max(
                1,
                j - badChar[text.charAt(shift + j)]
            );
        }

        return -1;
    }
}
