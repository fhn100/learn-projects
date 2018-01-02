package org.learn.java.sort;

/**
 * 选择排序
 * 平均O(n^2),最好O(n^2),最坏O(n^2);空间复杂度O(1);不稳定;简单
 */
public class SelectionSort {

    /**
     * 算法描述：
     * 对于给定的一组记录，经过第一轮比较后得到最小的记录，然后将该记录与第一个记录的位置进行交换；
     * 接着对不包括第一个记录以外的其他记录进行第二次比较，得到最小的记录并与第二个记录进行位置交换；
     * 重复该过程，直到进行比较的记录只有一个为止。
     *
     * @param array
     */
    public static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int k = i;
            // 找出最小值的小标
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[k]) {
                    k = j;
                }
            }
            // 将最小值放到排序序列末尾
            if (k > i) {
                int tmp = array[i];
                array[i] = array[k];
                array[k] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {49, 38, 65, 97, 76, 13, 27, 50};
        selectionSort(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
