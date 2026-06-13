package com.sortcery.backend.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MergeSort {

    private MergeSort() {}

    public static <T> List<T> sort(
        List<T> items,
        Comparator<T> comparator
    ) {
        if (items.size() <= 1) return items;

        int mid = items.size() / 2;

        List<T> left = sort(
            new ArrayList<>(items.subList(0, mid)),
            comparator
        );

        List<T> right = sort(
            new ArrayList<>(items.subList(mid, items.size())),
            comparator
        );

        return merge(left, right, comparator);
    }

    private static <T> List<T> merge(
        List<T> left,
        List<T> right,
        Comparator<T> comparator
    ) {
        List<T> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }

        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }
}
