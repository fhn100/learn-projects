package org.learn.java.sort;

/**
 * 插入排序
 * 平均O(n^2),最好O(n),最坏O(n^2);空间复杂度O(1);稳定;简单
 */
public class InsertionSort {

    /**
     * 算法描述：
     * 对于给定的一个数组，初始时假设第一个记录自成一个有序序列，其余记录为无序序列；
     * 接着从第二个记录开始，按照记录的大小依次将当前处理的记录插入到其之前的有序序列中，直至最后一个记录插入到有序序列中为止。
     * @param array
     */
    public static void insertionSort(int[] array) {
        int tmp;
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    tmp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {49, 38, 65, 97, 76, 13, 27, 50};
        insertionSort(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
