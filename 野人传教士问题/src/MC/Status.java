package MC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Status extends Plan
{
    /**
     * boatOnLeft 为1表示船在左侧。0表示船在右侧
     */
    public int boatOnLeft; // 1 表示船在左，0表示在右
    /**
     * 构造函数
     * @param m 传教士数量
     * @param c 野人数量
     * @param dis   船停靠在哪边
     */
    public Status(int m, int c, int dis) {
        super(m, c);
        this.boatOnLeft = dis;
    }

    /**
     * 重写比较函数，用于HashMap判重
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Status status = (Status) o;
        return m == status.m &&
                c == status.c&&boatOnLeft == status.boatOnLeft;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), boatOnLeft);
    }

    @Override
    public String toString() {
        return "Status{" +
                "boatOnLeft=" + boatOnLeft +
                ", m=" + m +
                ", c=" + c +
                '}';
    }

}
