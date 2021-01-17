import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SortDemo {

    public static void main(String[] args) {

        // 冒泡排序算法实现
        bubbleSort();

        // 选择排序算法实现
        selectionSort();

        // 插入排序算法实现
        insertSort();

        // 希尔排序算法实现
        shellSort();

        // 生成一个随机数组
        int[] arr = getRoundArray(10);
        // 归并排序算法实现
        mergeSort(arr, 0, arr.length - 1);

        // 生成一个随机数组
        arr = getRoundArray(10);
        // 快速排序算法实现
        quickSort(arr, 0, arr.length - 1);
        // 打印排序中的数组
        printArray(arr, "快速排序后：");

        // 计数排序算法实现
        countSort();

        // 桶排序算法实现
        bucketSort();
    }

    private static void bucketSort(){
        // 生成一个随机数组
        int[] arr = getRoundArray(10);
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        //桶数
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for(int i = 0; i < bucketNum; i++){
            bucketArr.add(new ArrayList<Integer>());
        }

        //将每个元素放入桶
        for(int i = 0; i < arr.length; i++){
            int num = (arr[i] - min) / (arr.length);
            bucketArr.get(num).add(arr[i]);
        }

        //对每个桶进行排序
        for(int i = 0; i < bucketArr.size(); i++){
            Collections.sort(bucketArr.get(i));
        }

        System.out.println(bucketArr.toString());

    }
    /**
     * 计数排序算法：<br/>
     * 构建3个数组：待排序数组、计数数组、排序后数组<br/>
     * 求出待排序数组的最大值，最小值<br/>
     * 计数数组用来记录每个元素之前出现的元素个数<br/>
     * 计算arr每个数字应该在排序后数组中应该处于的位置<br/>
     * 根据计数数组求得排序后的数组<br/>
     */
    private static void countSort(){

        // 生成一个随机数组
        int[] arr = getRoundArray(10);

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        // 找出数组中的最大最小值
        for (int array : arr) {
            max = Math.max(max, array);
            min = Math.min(min, array);
        }

        // 构建用于计数的数组
        int[] countArr = new int[max - min + 1];

        // 每个数字出现的次数
        for (int array : arr) {
            int index = array - min;
            countArr[index]++;
        }

        // 计算每个数字应该在排序后数组中应该处于的位置
        for(int i = 1; i < countArr.length; i++){
            countArr[i] = countArr[i - 1] + countArr[i];
        }

        // 构建排序后数组
        int[] resultArr = new int[arr.length];
        // 遍历待排序数组，组成排序后数组
        for (int i = arr.length - 1 ;i >= 0; i--) {
            resultArr[--countArr[arr[i] - min]] = arr[i];
        }
        // 打印排序中的数组
        printArray(resultArr, "计数排序后：");
    }

    /**
     * 快速排序算法：<br/>
     * 从右往左找到第一个小于初始值的数交换位置<br/>
     * 从左往右找到第一个大于初始值的数交换位置<br/>
     * 结束一次循环，此时，对于基准值来说，左右两边就是有序的了。<br/>
     * 比较左右两边的数组序列<br/>
     * @param arr 待排序数组
     * @param low 左边
     * @param high 右边
     */
    private static void quickSort(int[] arr,int low,int high){

        int start = low;
        int end = high;
        // 数组第一个值作为初始值
        int key = arr[low];

        // start=end，左右两个指针到了同一位置，左边的值都比初始值小，右边的值都比初始值大
        while (end > start) {
            // 从右往左找到第一个小于key的数
            while (end > start && arr[end] >= key)
                end--;
            // 如果右侧的值比初始值小，则交换
            if (arr[end] <= key) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }
            // 从左往右找到第一个大于key的数
            while (end > start && arr[start] <= key)
                start++;
            // 如果左侧的值比初始值大，则交换
            if (arr[start] >= key) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }

        // 分别递归调用初始值左侧和右侧的数组序列
        // 防止数组下标越界
        if (start > low)
            // 左边序列。第一个索引位置到关键值索引-1
            quickSort(arr, low, start - 1);

        if (end < high)
            // 右边序列。从关键值索引+1到最后一个
            quickSort(arr, end + 1, high);
    }

    /**
     * 归并排序算法：<br/>
     * 第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素。<br/>
     * 第二, 治理: 对每个子序列分别调用归并排序, 进行递归操作。<br/>
     * 第三, 合并: 合并两个排好序的子序列,生成排序结果。<br/>
     * 稳定排序方式，空间换时间<br/>
     * @param arr 待排序数组
     * @param low 左边
     * @param high 右边
     */
    private static void mergeSort(int[] arr, int low, int high) {

        // int mid = (low + high) / 2;
        // 在数组长度可能会很长的时候，可以用这样的加法防止(low+high)溢出
        int mid = low + (high - low) / 2;
        if (low < high) {
            // 进行左侧归并
            mergeSort(arr, low, mid);
            // 进行右侧归并
            mergeSort(arr, mid + 1, high);
            // 进行左右归并
            merge(arr, low, mid, high);
        }
        // 打印排序中的数组
        printArray(arr, "归并排序中：");

    }

    /**
     * 归并操作执行
     * @param arr 待排序数组
     * @param low 左边
     * @param mid 中间
     * @param high 右边
     */
    private static void merge(int[] arr, int low, int mid, int high) {
        int[] tempArr = new int[high - low + 1];

        // 左指针
        int i = low;

        // 右指针
        int j = mid + 1;
        int k = 0;

        // 将较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                tempArr[k++] = arr[i++];
            } else {
                tempArr[k++] = arr[j++];
            }
        }

        // 将左侧剩余的数移入数组
        while (i <= mid) {
            tempArr[k++] = arr[i++];
        }

        // 将右侧剩余的数移入数组
        while (j <= high) {
            tempArr[k++] = arr[j++];
        }

        // 将新数组中的数覆盖arr数组
        // System.arraycopy(tempArr, 0, arr, 0 + low, tempArr.length);
        for (int k2 = 0; k2 < tempArr.length; k2++) {
            arr[k2 + low] = tempArr[k2];
        }
    }

    /**
     * 希尔排序算法：<br/>
     * 假如有初始数据：25  11  45  26  12  78。<br/>
     * 1、第一轮排序，将该数组分成 6/2=3 个数组序列，
     *      第1个数据和第4个数据为一对，<br/>
     *      第2个数据和第5个数据为一对，<br/>
     *      第3个数据和第6个数据为一对，<br/>
     *      每对数据进行比较排序，排序后顺序为：[25, 11, 45, 26, 12, 78]。<br/>
     * 2、第二轮排序 ，将上轮排序后的数组分成6/4=1个数组序列，<br/>
     *      此时逐个对数据比较，按照插入排序对该数组进行排序，<br/>
     *      排序后的顺序为：[11, 12, 25, 26, 45, 78]。<br/>
     */
    private static void shellSort() {

        // 生成一个随机数组
        int[] arr = getRoundArray(10);

        // i表示希尔排序中的第n/2+1个元素（或者n/4+1）
        // j表示希尔排序中从0到n/2的元素（n/4）
        // r表示希尔排序中n/2+1或者n/4+1的值
        int i, j, r, tmp;

        // 划组排序
        for (r = arr.length / 2; r >= 1; r = r / 2) {
            for (i = r; i < arr.length; i++) {
                tmp = arr[i];
                j = i - r;

                // 一轮排序
                while (j >= 0 && tmp < arr[j]) {
                    arr[j+r] = arr[j];
                    j -= r;
                }
                arr[j+r] = tmp;
            }
        }

        // 打印排序后的数组
        printArray(arr, "希尔排序后：");
    }

    /**
     * 插入排序算法：<br/>
     * 按照从右往左的顺序分别与其左边的元素比较，<br/>
     * 遇到比其大的元素便将元素右移，<br/>
     * 直到找到比该元素小的元素或者找到最左面发现其左侧的元素都比它大，停止；<br/>
     * 此时会出现一个空位，将该元素放入到空位中，<br/>
     * 此时该元素左侧的元素都比它小，右侧的元素都比它大；<br/>
     * 指针向后移动一位，重复上述过程。<br/>
     * 每操作一轮，左侧有序元素都增加一个，右侧无序元素都减少一个。<br/>
     */
    private static void insertSort(){
        // 生成一个随机数组
        int[] arr = getRoundArray(10);

        // 外层向右的index，即作为比较对象的数据的index
        for (int i = 1; i < arr.length; i++) {

            // 用作比较的数据
            int temp = arr[i];
            int leftIndex = i - 1;

            // 当比到最左边或者遇到比temp小的数据时，结束循环
            while (leftIndex >= 0 && arr[leftIndex] > temp) {
                arr[leftIndex + 1] = arr[leftIndex];
                leftIndex--;
            }

            // 把temp放到空位上
            arr[leftIndex + 1] = temp;
        }
        // 打印排序后的数组
        printArray(arr, "插入排序后：");
    }
    /**
     * 选择排序算法:<br/>
     * 第1趟排序，在待排序数据arr[1]~arr[n]中选出最小的数据，将它与arrr[1]交换；<br/>
     * 第2趟，在待排序数据arr[2]~arr[n]中选出最小的数据，将它与r[2]交换；<br/>
     * 以此类推，第i趟在待排序数据arr[i]~arr[n]中选出最小的数据，将它与r[i]交换，直到全部排序完成。<br/>
     */
    private static void selectionSort() {

        // 生成一个随机数组
        int[] arr = getRoundArray(10);

        // 做第i趟排序
        for (int i = 0; i < arr.length - 1; i++) {
            int k = i;

            // 选最小的记录
            for (int j = k + 1; j < arr.length; j++) {
                if (arr[j] < arr[k]) {

                    // 记下目前找到的最小值所在的位置
                    k = j;
                }
            }

            // 在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if (i != k) {

                // 交换a[i]和a[k]
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
        // 打印排序后的数组
        printArray(arr, "选择排序后：");
    }

    /**
     * 冒泡排序算法:<br/>
     * 依次比较相邻的两个数，将小数放在前面，大数放在后面。<br/>
     * 第一趟：首先比较第1个和第2个数，将小数放前，大数放后。<br/>
     * 然后比较第2个数和第3个数，将小数放前，大数放后，<br/>
     * 如此继续，直至比较最后两个数，将小数放前，大数放后。<br/>
     * 重复第一趟步骤，直至全部排序完成。<br/>
     */
    private static void bubbleSort() {

        int[] arr = getRoundArray(10);

        // 外层循环控制排序趟数
        for (int i = 0; i < arr.length - 1; i++) {

            // 内层循环控制每一趟排序多少次
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // 打印排序后的数组
        printArray(arr, "冒泡排序后：");
    }

    /**
     * 获得随机数组
     * @param num 生成多少个随机数
     * @return 返回随机数组
     */
    private static int[] getRoundArray(int num) {
        Random random = new Random();

        // 10个数的数组
        int arr[] = new int[num];

        // 随机生成1-100的num个随机数组
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt(100) + 1;
        }

        // 打印新产生的数组
        printArray(arr, "新做成的数组：");

        return arr;
    }

    /**
     * 打印数组
     * @param arr 待打印数组
     */
    private static void printArray(int[] arr, String message) {
        System.out.println(message + Arrays.toString(arr));
    }
}
