import java.util.*;

/**
 * @program: AI-projects
 * @description:
 * @author: qinda
 * @create: 2020-04-16 14:22
 **/
public class UserInput {
    private Set<String> features;

    public UserInput(Set<String> features) {
        this.features = features;
    }

    public HashSet<String> input()
    {
        HashSet<String> inputSet = new HashSet<String>();
        for (String feature : features) {
            System.out.print(feature+" ");
        }
        System.out.println();
        System.out.println("请选择以上特征输入:(中间以一个空格分开，回车结尾)");
        String s = new Scanner(System.in).nextLine();
        String inputFeatures[] = s.split(" ");
        for (String inputFeature : inputFeatures) {
            if(features.contains(inputFeature))
                inputSet.add(inputFeature);
            else
            {
                for (String feature : features) {
                    if(feature.indexOf(inputFeature)!=-1)
                    {
                        inputSet.add(feature);
                        break;
                    }
                }
            }

        }
        System.out.println("您输入的特征为："+inputSet);
        return inputSet;
    }
}
