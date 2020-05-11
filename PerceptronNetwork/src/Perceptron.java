import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Arrays;

/**
 * @program: AI-projects
 * @description: 单层感知器与线性拟合分类
 * @author: qinda
 * @create: 2020-05-07 11:57
 **/
public class Perceptron
{
    /**
     * 响应函数
     */
    private static final int SIGN = 1;
    private static final int LINER = 0;
    /**
     * 单层感知器（训练数据）
     */
    private double[][] p=new double[][]
            {
                    {-0.5,-0.5},
                    {-0.5,0.5},
                    {0.3,-0.5},
                    {0.0,1.0},
            };
    private double[][] t= new double[][] {{1.0} ,  {1.0},  {0.0}, {0.0}};

    /**
     * 3输入4输出单层神经网络（训练数据）
     */
    private double[][] p1=new double[][]
            {
                    {1    , -1   , 2   },
                    {1.5  , 2    , 1   },
                    {1.2  , 3    , -1.6},
                    {-0.3 , -0.5 , 0.9 }
            };
    private double[][] t1= new double[][]
            {
                    {0.5,1.1,3,-1},
                    {3,-1.2,0.2,0.1},
                    {-2.2,1.7,-1.8,-1.0},
                    {1.4,-0.4,-0.4,0.6}
            };

    /**
     * 学习率
     */
    private double learningRate = 0.1;
    /**
     * 迭代次数
     */
    private int T = 100;

    /**
     * 矩阵乘法
     * @param x1
     * @param x2
     * @param i
     * @param j
     * @return 返回乘积合
     */
    private double matrixMultiMu(double[][] x1, double[][] x2, int i,int j)
    {
        double sum = 0;
        for (int i1 = 0; i1 < Array.getLength(x1[i]); i1++)
            sum += x1[i][i1]*x2[j][i1];
        return sum;
    }

    /**
     * 加偏置矩阵 b
     * @param a
     * @param b
     */
    private void plusB(double[] a,double[] b)
    {
        for (int i = 0; i < Array.getLength(a); i++)
            a[i] += b[i];
    }

    /**
     * 反向传播，更新矩阵
     * @param w
     * @param p
     * @param res
     * @param t
     * @param i
     */
    private void update(double[][] w,double[][] p,double[] res,double[][] t,int i)
    {
        for (int j = 0;j<Array.getLength(w);j++)
            for(int k=0;k<Array.getLength(w[j]);k++)
                w[j][k] += learningRate * (t[i][j] - res[j]) * p[i][k];
    }

    /**
     * 更新偏置矩阵
     * @param res
     * @param b
     * @param i
     */
    private void updateB(double[] res,double[] b,int i)
    {
        for (int i1 = 0; i1 < Array.getLength(res); i1++)
            b[i1]+=learningRate*(t1[i][i1] - res[i1]);
    }

    /**
     * sign激活函数
     * @param x
     */
    private void sign(double[] x)
    {
        for (int i = 0; i < x.length; i++)
            x[i] = x[i]>0?1:0;
    }

    /**
     * 线性响应函数
     * @param x
     */
    private void liner(double[] x)
    {
    }

    /**
     * relu响应函数
     * @param x
     */
    private void relu(double[] x)
    {
        for (int i = 0; i < Array.getLength(x); i++)
            x[i] = x[i] <= 0 ? 0 : x[i];
    }
//    public void trainPerceptron()
//    {
//        int now = 1;
//        double e = 0.001;
//        double d = 1;
//        while (T>=now && d >= e)
//        {
//            d = 0;
//            for (int i=0;i<4;i++)
//            {
//                double res = this.sign(this.matrixMulti(i) + b);
//                W[0][0] += learningRate * (t[i] - res) * p1[i];
//                W[0][1] += learningRate * (t[i] - res) * p2[i];
//                System.out.println("w:{"+W[0][0]+", "+W[0][1]+"}"+", "+"b:"+b);
//                b += learningRate * (t[i] - res);
//                d += (t[i]-res)*(t[i]-res);
//            }
//            now += 1;
//        }
//        System.out.println("learn rate:"+learningRate);
//        System.out.println("T:"+T);
//        for (int i = 0; i < 4; i++)
//            System.out.print(this.sign(this.matrixMulti(i) + b)+" ");
//        System.out.println(W[0][0]+" "+W[0][1]);
//    }

    /**
     * 获得loss值
     * @param res
     * @param t1
     * @param i
     * @return
     */
    private double getLoss(double[] res,double[][] t1,int i)
    {
        double sum = 0;
        for (int i1 = 0; i1 < res.length; i1++)
            sum+=(t1[i][i1]-res[i1])*(t1[i][i1]-res[i1]);
        return sum;
    }

    /**
     * 格式化小数
     * @param d
     * @return
     */
    private static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(20);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }

    /**
     * 训练
     * @param p 训练数据
     * @param t 标注结果
     * @param w w权重矩阵
     * @param b 偏置矩阵
     * @param f 响应函数
     */
    public int trainThreeFour(double[][] p,double[][] t,double[][] w,double[] b,int f)
    {
        w = new double[t[0].length][p[0].length];
        b = new double[w.length];
        int now = 1;
        double e = 0.01;
        double d = 1;
        while (T>=now&&d>=e)
        {
            d = 0;
            for (int i=0;i<p.length;i++)
            {
                double[] res = new double[t[0].length];
                for (int i1 = 0; i1 < w.length; i1++)
                    res[i1] = this.matrixMultiMu(w,p,i1,i);
                this.plusB(res,b);
                if (f == SIGN)
                    this.sign(res);
                else if(f == LINER)
                    this.liner(res);
                this.update(w,p,res,t,i);
                this.updateB(res,b,i);
                double loss = this.getLoss(res,t,i);
                d+=loss;
            }
            String s = formatDouble(d);
            System.out.println("loss :"+s);
            now += 1;
        }
        System.out.println("learn rate:"+learningRate);
        System.out.println("T:"+T);
        System.out.println("W:");
        for (int i = 0; i < w.length; i++) {
            for (int i1 = 0; i1 < w[i].length; i1++)
                System.out.print(w[i][i1]+" ");
            System.out.println();
        }
        System.out.println("B:");
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]+" ");
        }
        System.out.println();
        System.out.println("分类结果：");
        double[] res;
        for (int i=0;i<p.length;i++) {
            res = new double[t[0].length];
            for (int i1 = 0; i1 < w.length; i1++)
                res[i1] = this.matrixMultiMu(w, p, i1, i);
            this.plusB(res, b);
            if (f == SIGN)
                this.sign(res);
            else if (f == LINER)
                this.liner(res);
            System.out.println(Arrays.toString(res));
        }
        return now;
    }

    public static void main(String[] args) {
        Perceptron perceptron = new Perceptron();
        double[][] w = null;
        double[] b = null;
        int i = perceptron.trainThreeFour(perceptron.p1, perceptron.t1, w, b, LINER);
        System.out.println("t为："+i);
    }
}
