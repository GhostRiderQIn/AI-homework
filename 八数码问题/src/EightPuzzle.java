import org.omg.PortableInterceptor.INACTIVE;

import javax.swing.*;
import java.util.*;

/**
 * 主类
 */
public class EightPuzzle
{
    /**
     * 起始状态
     */
    public Status startStatus;

    /**
     * 目标状态,因为全局唯一，所以static
     */
    public static Status endStatus;

    /**
     * 目标状态各个数字的坐标
     */
    public static Map<Integer, Point> indexMap;

    /**
     * open表，优先队列，根据 f = g + h 升序排列
     */
    public Queue<Status> open;

    /**
     * 该状态是否访问过的标记map
     */
    private Map<String,String> isVisit;

    /**
     * 比较器，优先队列中用到,按升序排列
     */
    static Comparator<Status> cmp = new Comparator<Status>() {
        public int compare(Status e1, Status e2) {
            return e1.getF() - e2.getF();
        }
    };

    /**
     * 路径栈
     */
    public Stack<Status> ansStack;

    /**
     * 到达目标状态所走的步数
     */
    public int count;

    /**
     * 扩展的节点数
     */
    public int expendCount;


    /**
     * 构造函数，初始化目标状态的indexMap，初始化优先队列
     * @param startStatus 起始状态
     * @param endStatus 目标状态
     *
     */
    public EightPuzzle(Status startStatus, Status endStatus) {
        this.startStatus = startStatus;
        EightPuzzle.endStatus = endStatus;
        EightPuzzle.indexMap = getEndStatusIndexMap();

        open = new PriorityQueue<>(cmp);
        isVisit = new HashMap<>();
        ansStack = new Stack<>();
    }

    /**
     * 得到目标状态的indexMap
     * @return 返回map
     */
    private Map<Integer, Point> getEndStatusIndexMap()
    {
        Map<Integer, Point> map = new HashMap<Integer, Point>();
        int[][] matrix = endStatus.getMatrix();
        for(int i=0;i<matrix.length;i++)
            for(int j = 0;j<matrix[i].length;j++)
                map.put(matrix[i][j], new Point(i,j));
        return map;
    }


    /**
     * A*算法寻路
     */
    public void AStar()
    {
        int[][] dis = {{0,1},{0,-1},{-1,0},{1,0}};
        //计算H
        startStatus.culH();
        //第0层
        startStatus.setG(0);
        //计算F
        startStatus.setF(startStatus.getG()+startStatus.getH());
        //起始节点的父亲为null;
        startStatus.parent = null;
        //标记起始节点已访问
        isVisit.put(startStatus.getMatrixString(),"1");
        //将起始节点放到open表中
        open.add(startStatus);
        expendCount = 1;
        Status findEndStatus = null;
        while(!open.isEmpty())
        {
            Status nowStatus = open.poll();
            //到达目标状态
            if (nowStatus.equals(endStatus))
            {
                //记录目标节点
                findEndStatus = nowStatus;
                break;
            }

            //得到当前状态时，0的位置
            Point zero = new Point(nowStatus.getZeroIndex().x,nowStatus.getZeroIndex().y);
            //四个方向
            for (int[] di : dis)
            {
                int x, y;
                x = zero.x + di[0];
                y = zero.y + di[1];
                if (x >= 0 && x < 3 && y >= 0 && y < 3) //可以拓展
                {
                    int[][] matrix = nowStatus.getCopyMatrix();

                    //交换拓展的值与0
                    int t = matrix[x][y];
                    matrix[x][y] = 0;
                    matrix[zero.x][zero.y] = t;

                    //声明新状态
                    Status status = new Status(matrix);
                    //如果没访问过
                    if (isVisit.get(status.getMatrixString()) == null)
                    {
                        //计算H
                        status.culH();
                        //节点深度加一
                        status.setG(nowStatus.getG() + 1);
                        //计算F
                        status.setF(status.getG() + status.getH());
                        //设置0的位置
                        status.setZeroIndex(new Point(x, y));
                        //记录他的父亲
                        status.parent = nowStatus;
                        //加入open表中
                        open.add(status);
                        //拓展的节点数加一
                        expendCount ++;
                        //设置已访问
                        isVisit.put(status.getMatrixString(), "1");
                    }

                }
            }
        }
        while(findEndStatus!=null)
        {
            ansStack.push(findEndStatus);
            findEndStatus = findEndStatus.parent;
        }
        count = ansStack.size();
        printAns();

    }

    /**
     * 验证用户输入的字符串是否符合标准
     * @param input 用户输入的字符串
     * @return  true|false
     */
    public static boolean validateInput(String input)
    {
        if(input.length()!=9)
            return false;
        for(int i=0;i<input.length();i++)
            if(input.charAt(i)>'8' || input.charAt(i)<'0')
                return false;
        return true;
    }

    /**
     * 输入与解决问题
     */
    public static void solve()
    {
        while (true)
        {
            System.out.println("请输入起始序列，用一串字符串表示，空的地方用0表示:");
            Scanner in = new Scanner(System.in);
            String startStatusString = in.nextLine();
            if(!validateInput(startStatusString))
            {
                System.out.println("输入的格式有误!请重新输入!");
                continue;
            }
            System.out.println("请输入目标序列，用一串字符串表示，空的地方用0表示:");
            String endStatusString = in.nextLine();
            if(!validateInput(endStatusString))
            {
                System.out.println("输入的格式有误!请重新输入!");
                continue;
            }

            Status startStatus = new Status(startStatusString);
            Status endStatus = new Status(endStatusString);

            EightPuzzle ep = new EightPuzzle(startStatus, endStatus);


            ep.AStar();
            break;
        }


    }

    /**
     * 输出答案，步数，拓展节点个数
     */
    public void printAns()
    {
        System.out.println("步骤:");
        int cnt = 0;
        while(!ansStack.isEmpty())
        {
            Status status = ansStack.pop();
            System.out.println("第"+cnt+++"步: ");
            for(int i=0;i<status.getMatrix().length;i++)
            {
                for(int j = 0;j<status.getMatrix()[i].length;j++)
                    if(status.getMatrix()[i][j] == 0)
                        System.out.print("  ");
                    else
                        System.out.print(status.getMatrix()[i][j]+" ");
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }

        System.out.println("步数:"+(count-1));
        System.out.println("扩展节点数:"+expendCount);

    }
}
