package MC;

import java.util.HashMap;

/**
 *
 * 测试类
 * 没有用
 */
public class test
{
    public static void main(String[] args) throws CloneNotSupportedException {
        HashMap<Status,String> hashMap = new HashMap<Status, String>();
        Status status = new Status(1,1,1);
        Status status1 = new Status(1,1,1);
        hashMap.put(status,"1");
        System.out.println(hashMap.get(status1));
    }
}
