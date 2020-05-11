import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 状态类
 */
public class Status
{
    /**
     * 当前状态矩阵
     */
    private int matrix[][];

    /**
     * h值
     */
    private int h;  // h 总步数

    /**
     * g值
     */
    private int g;  // g 深度

    /**
     * f = g + h
     */
    private int f;  // f=g+h

    /**
     * 改状态下0的位置
     */
    private Point zeroIndex;

    /**
     * 到达该状态的父亲指针，用于回溯路径
     */
    public Status parent;

    /**
     * 将字符串矩阵化，计算当前状态与目标状态的 h 值
     * @param matrix 矩阵字符串
     */
    public Status(String matrix) {
        zeroIndex = new Point();
        this.matrix = stringMatrixify(matrix);
        this.g = 0;
        this.f = 0;

    }
    public Status(int[][] matrix) {
        zeroIndex = new Point();
        this.matrix = matrix;
        this.g = 0;
        this.f = 0;

    }


    /**
     * 将字符串矩阵化,并将0记录
     * 123456789    ->
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * @param matrix  矩阵字符串
     * @return  返回二维矩阵
     */
    public int[][] stringMatrixify(String matrix)
    {
        int[][] m = new int[3][3];
        int cnt = 0;
        for(int i=0;i<3;i++)
            for(int j = 0;j<3;j++)
            {
                if(matrix.charAt(cnt)-'0' == 0)
                {
                    zeroIndex.x = i;
                    zeroIndex.y = j;
                }
                m[i][j] = matrix.charAt(cnt)-'0';
                cnt++;
            }
        return m;
    }


    /**
     *得到每个数字的坐标
     * @return  返回一个Map，map中相应的值为每个数的坐标，point.x为行   point.y为列
     */
    public Map<Integer, Point> getEveryNumIndex()
    {
        Map<Integer, Point> map = new HashMap<Integer, Point>();
        for(int i=0;i<matrix.length;i++)
            for(int j = 0;j<matrix[i].length;j++)
                map.put(matrix[i][j]-'0', new Point(i,j));
        return map;
    }

    /**
     * 计算 h 值
     * 并将 h 赋值
     */
    public void culH()
    {
        int sumH = 0;
        for(int i=0;i<matrix.length;i++)
        {
            for(int j = 0;j<matrix[i].length;j++)
            {
                if(matrix[i][j] == 0)
                    continue;
                int absX = Math.abs(EightPuzzle.indexMap.get(matrix[i][j]).x - i);
                int absY = Math.abs(EightPuzzle.indexMap.get(matrix[i][j]).y - j);
                sumH += (absX+absY);
            }
        }
        this.h = sumH;
    }


    public int[][] getMatrix() {
        return matrix;
    }

    public int getH() {
        return h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Point getZeroIndex() {
        return zeroIndex;
    }

    public void setZeroIndex(Point zeroIndex) {
        this.zeroIndex = zeroIndex;
    }

    /**
     * 用于得到当前矩阵，由于要做修改，所以复制一份
     * @return  返回与当前矩阵一模一样的矩阵
     */
    public int[][] getCopyMatrix()
    {
        int[][] m = new int[matrix.length][matrix[0].length];
        for(int i = 0;i < matrix.length;i++)
            System.arraycopy(matrix[i], 0, m[i], 0, matrix[i].length);
        return m;
    }

    /**
     * 重载比较函数
     * @param o
     * @return 两个矩阵中的值完全一样时返回真，否则假
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        for(int i = 0;i<status.matrix.length;i++)
        {
            for(int j = 0;j<status.matrix[i].length;j++)
            {
                if(status.matrix[i][j] != this.matrix[i][j])
                    return false;
            }
        }
        return true;
    }


    /**
     * 得到当前矩阵的字符串化，用于标记此状态是否访问过
     * @return 矩阵的字符串
     */
    public String getMatrixString()
    {
        StringBuilder stringBuffer = new StringBuilder();
        for (int[] ints : matrix)
            for (int anInt : ints) stringBuffer.append(String.valueOf(anInt));
        return stringBuffer.toString();
    }

}
