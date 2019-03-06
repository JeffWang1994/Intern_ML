package top.jeffwang.Sort;

public class Sort {

    /**
     * 选择排序
     * 经过第一轮比较，得到最小值，然后与第一位进行交换，然后寻找第二个最小值，和第二位交换...
     * @param a
     */
    public static void selectSort(int[] a){
        int i;
        int j;
        int temp = 0;
        int flag = 0;
        int n = a.length;
        for(i = 0; i<n; i++){
            temp = a[i];
            flag=i;
            for(j=i+1;j<n;j++){
                if(a[j]<temp){
                    temp = a[j];
                    flag = j;
                }
            }
            if(flag!=i){
                a[flag] = a[i];
                a[i] = temp;
            }
        }
    }

    /**
     * 插入排序
     * 假设第一个值自成一个有序序列，然后第二个值按照记录的大小一次将当前处理的记录插入到之前的有序序列当中。直到最后一个插入完成。
     * @param a
     */
    public static void insertSort(int[] a){
        if(a!=null){
            for(int i=1;i<a.length;i++){
                int temp = a[i];
                int j = i;
                if(a[j-1]>temp){
                    while(j>=1&&a[j-1]>temp){
                        a[j] = a[j-1];
                        j--;
                    }
                }
                a[j] = temp;
            }
        }
    }

    /**
     * 冒泡排序
     * 从第一个记录开始一次对相邻的两个记录进行比较，当前面的记录大于后面的记录时，交换为止，进行一轮比较和换位后，n个记录中的最大记录会位于第n位。
     * 然后对前n-1个记录一次进行第二轮比较。
     * @param array
     */
    public static void BubbleSort(int array[]){
        int i,j;
        int len = array.length;
        int tmp;
        for(i=0; i<len-1; ++i){
            for(j=len-1; j>i;--j){
                if(array[j]<array[j-1]){
                    tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                }
            }
        }
    }

    /**
     * 归并排序
     * 给定一组记录(假设共有n个记录)，首先将每两个相邻的长度为1的子序列进行归并，
     * 得到n/2(向上取整)个长度为2或1的有序子序列，再将其两两归并，反复执行此过程...
     * @param array
     * @param p
     * @param q
     * @param r
     */
    public static void Merge(int array[], int p, int q, int r){
        int i,j,k,n1,n2;
        n1 = q-p+1;
        n2 = r-q;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for(i=0,k=p;i<n1;i++,k++){
            L[i] = array[k];
        }
        for (i=0,k=q+1;i<n2;i++,k++){
            R[i] = array[k];
        }
        for (k=p,i=0,j=0;i<n1&&j<n2;k++){
            if(L[i]>R[j]){
                array[k]=L[i];
                i++;
            }else{
                array[k]=R[j];
                j++;
            }
        }
        if(i<n1){
            for (j=i;j<n1;j++,k++){
                array[k]=L[j];
            }
        }
        if(j<n2){
            for (i=j;i<n2;i++,k++){
                array[k]=R[i];
            }
        }
    }
    public static void MergeSort(int array[], int p, int r){
        if(p<r){
            int q = (p+r)/2;
            MergeSort(array,p,q);
            MergeSort(array,q+1,r);
            Merge(array,p,q,r);
        }
    }

    /**
     * 快速排序--分而治之
     * 对于一组给定的记录，通过一趟排序后，将原序列分为两部分，其中前一部分的所有记录均比后一部分的所有记录小，然后一次对前后两部分的记录进行快速排序。
     * @param array
     * @param low
     * @param high
     */
    public static void fastSort(int array[], int low, int high){
        int i,j;
        int index;
        if(low >= high){
            return;
        }
        i = low;
        j = high;
        index = array[i];
        while(i<j){
            while (i<j&&array[j]>=index){
                j--;
            }
            if (i<j){
                array[i++] = array[j];
            }
            while (i<j&&array[i]<index){
                i++;
            }
            if(i<j){
                array[j--] = array[i];
            }
        }
        array[i] = index;
        fastSort(array, low, i-1);
        fastSort(array,i+1, high);
    }
    public static void quickSort(int array[]){
        fastSort(array, 0, array.length-1);
    }

    /**
     * 希尔排序--缩小增量排序
     * 先将待排序的数组元素分成多个子序列，使得每个子序列的元素个数相对较少，然后对各个子序列分别进行直接插入排序，待整个排序序列基本有序后，最后进行插入排序。
     */

    public static void main(String[] args){
        int i = 0;
        int a[] = {5,4,9,8,7,6,0,1,3,2};
        BubbleSort(a);
        for (i=0; i<a.length;i++){
            System.out.println(a[i]+" ");
        }
        System.out.println("\n");
    }
}
