package MC;

import java.util.Objects;

public class Plan
{
    public int m; //传教士
    public int c; //野人

    public Plan(int m, int c) {
        this.m = m;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return m == plan.m &&
                c == plan.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, c);
    }

    @Override
    public String toString() {
        return "Plan{" +
                "m=" + m +
                ", c=" + c +
                '}';
    }
}
