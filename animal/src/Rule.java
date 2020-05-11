import java.util.List;

/**
 * @program: AI-projects
 * @description:
 * @author: qinda
 * @create: 2020-04-15 22:57
 **/
public class Rule {
    public String conclusion;
    public List<String> features;

    @Override
    public String toString() {
        return "Rule{" +
                "conclusion='" + conclusion + '\'' +
                ", features=" + features +
                '}';
    }
}
