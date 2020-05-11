package MC;

import java.util.*;

/**
 * MC问题主类
 */
public class MC
{
    private int n; // n个传教士，n个野人
    private int k; // 一只船最多承载k人
    private HashMap<Status,String> isVisit; // 判断状态是否访问过， 以左岸为判断依据
    Status startStatus ; //开始状态
    Status endStatus ;  //结束状态
    private List<Plan> allPlans; //所有的操作集
    private LinkedList<Status> ans; //答案栈
    private int count = 1;
    public MC(int n, int k)
    {
        this.n = n;
        this.k = k;
        startStatus = new Status(n, n, 1);// 初始化状态，船在左岸，并且左岸有n个传教士，n个野人
        endStatus = new Status(0, 0, 0);// 结束状态，船在右岸，并且传教士，野人都在右岸。
        isVisit = new HashMap<Status, String>();
        ans = new LinkedList<Status>();
    }

    /**
     * 得到所有渡船方案
     * @param k 船的承载量
     * @return  返回所有操作集合，比如当承载量为2时
     * planList： 野人         传教士
     *              0              1
     *              0              2
     *              1              1
     *              1              0
     *              2              0
     *                          共 5 种操作
     */
    public List<Plan> getAllPlans(int k)
    {
        List<Plan> planList = new ArrayList<Plan>();
        for(int mCnt=0;mCnt<=k;mCnt++)
            for(int cCnt = 0;cCnt<=k;cCnt++)
                if((mCnt+cCnt) != 0 && (mCnt+cCnt) <= k)
                    planList.add(new Plan(mCnt,cCnt));
        return planList;
    }

    /**
     *
     * 解决问题
     *
     */
    public void solveProblem()
    {
        this.allPlans = this.getAllPlans(this.k);
        for (Plan allPlan : this.allPlans)
        {
            System.out.println(allPlan);
        }
        this.crossRiverByDFS();
    }
    /**
     * DFS找答案
     */
    public void crossRiverByDFS()
    {
        isVisit.put(startStatus,"1");
        ans.add(startStatus);
        dfs(startStatus);
    }
    /**
     *
     * 递归函数
     * @param nowStatus 当前状态
     */
    public void dfs(Status nowStatus)
    {
        if(nowStatus.equals(endStatus))
        {
            int cnt = 1;
            System.out.println("方案:"+count++);
            System.out.printf("步数\t左岸人数(传教士，野人)\t船的岸(1左0右)\n");
            for (Status an : ans)
            {
                System.out.printf("%d\t\t %d , %d \t\t\t\t\t\t%d\n",cnt,an.m,an.c,an.boatOnLeft);
                cnt++;
            }
            return ;
        }
        for (Plan plan : allPlans)
        {
            Status afterCrossLeftStatus;
            Status afterCrossRightStatus;
            if(nowStatus.boatOnLeft == 1)
            {
                afterCrossLeftStatus = new Status(nowStatus.m - plan.m, nowStatus.c - plan.c, 0);
                afterCrossRightStatus = new Status(n - afterCrossLeftStatus.m, n-afterCrossLeftStatus.c, 0);
            }
            else
            {
                afterCrossLeftStatus = new Status(nowStatus.m + plan.m, nowStatus.c + plan.c, 1);
                afterCrossRightStatus = new Status(n - afterCrossLeftStatus.m, n-afterCrossLeftStatus.c, 1);
            }
            //如果渡船后左岸传教士大于等于野人，并且渡船后右岸传教士大于等于野人,并且没有访问过
            if(check(afterCrossLeftStatus,afterCrossRightStatus))
            {
                isVisit.put(afterCrossLeftStatus,"1");
                ans.add(afterCrossLeftStatus);
                dfs(afterCrossLeftStatus);
                isVisit.remove(afterCrossLeftStatus);
                ans.removeLast();
            }
        }
    }

    /**
     * 判断是否可以拓展
     * @param afterCrossLeftStatus  在渡船后左岸的状态
     * @param afterCrossRightStatus 在渡船后右岸的状态
     * @return  返回是否可以渡船
     */
    public boolean check(Status afterCrossLeftStatus, Status afterCrossRightStatus)
    {
        return afterCrossLeftStatus.m <= n
                && afterCrossLeftStatus.c >= 0
                && afterCrossLeftStatus.c <= n

                && afterCrossRightStatus.m <= n
                && afterCrossRightStatus.c >= 0
                && afterCrossRightStatus.c <= n

                && (afterCrossLeftStatus.m >= afterCrossLeftStatus.c || afterCrossLeftStatus.m == 0)
                && (afterCrossRightStatus.m >= afterCrossRightStatus.c || afterCrossRightStatus.m == 0)
                && isVisit.get(afterCrossLeftStatus) == null;
    }
}
