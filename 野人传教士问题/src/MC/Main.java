package MC;

import java.util.Scanner;

public class Main
{
    /**
     * 主函数运行
     * @param args
     */
    public static void main(String[] args) {
        int n,k;
        System.out.println("请输入野人与传教士的个数n与船的最大承载量k,中间以空格隔开:");
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        k = in.nextInt();
        MC mc = new MC(n,k);
        mc.solveProblem();
    }
}
