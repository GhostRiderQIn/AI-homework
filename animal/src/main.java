/**
 * @program: AI-projects
 * @description:
 * @author: qinda
 * @create: 2020-04-15 21:32
 **/
import java.io.FileNotFoundException;
import java.util.*;

public class main {
    private static List<Rule> rulesList;
    private static Set<String> animals;
    private static Set<String> middles;
    private static Set<String> features;
    public static void main(String[] args) throws FileNotFoundException {
        LoadDB loadDB = new LoadDB("plant.yml");
        rulesList = loadDB.getRulesList();
        animals = loadDB.getAnimals();
        middles = loadDB.getMiddles();
        features = loadDB.getFeatures();

        UserInput userInput = new UserInput(features);
        HashSet<String> inputFeatures = userInput.input();
        forword(inputFeatures);

    }

    public static void backword(HashSet<String> inputFeatures)
    {
        int fin = 0;
        for (String animal : animals)
        {
            int f = 0;
            for (Rule rule : rulesList)
            {
                if (rule.conclusion.equals(animal))
                {
                    for (String feature : rule.features)
                    {
                        if (!inputFeatures.contains(feature))
                        {
                            //如果是中间结论
                            if (middles.contains(feature))
                            {
                                for (Rule rule1 : rulesList)
                                {
                                    if (rule1.conclusion.equals(feature))
                                    {
                                        int ff = 1;
                                        for (String s : rule1.features)
                                        {
                                            if (!inputFeatures.contains(s))
                                            {
                                                System.out.println("请问您要推理的东西 "+s+" 吗（y/n）");
                                                String in = new Scanner(System.in).next();
                                                if (in.equals("y")||in.equals("Y"))
                                                {
                                                    inputFeatures.add(s);
                                                }
                                                else if (in.equals("n")||in.equals("N"))
                                                {
                                                    f=1;
                                                    ff = 0;
                                                    break;
                                                }
                                            }
                                        }
                                        if(ff == 1)
                                        {
                                            inputFeatures.add(feature);
                                            break;
                                        }
                                    }
                                }
                            }
                            //如果是特征
                            else if(features.contains(feature))
                            {
                                //询问用户
                                System.out.println("请问您要推理的东西 "+feature+" 吗（y/n）");
                                String in = new Scanner(System.in).next();
                                if (in.equals("y")||in.equals("Y"))
                                {
                                    inputFeatures.add(feature);
                                    continue;
                                }
                                else if (in.equals("n")||in.equals("N"))
                                {
                                    f = 1;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (f == 0)
            {
                System.out.println("推理成功！"+animal);
                fin=1;
                break;
            }
        }
        if (fin == 0)
        {
            System.out.println("条件不足，无法成功推理!");
        }
    }

    public static void forword(HashSet<String> inputFeatures)
    {

        while (true)
        {
            List<Rule> list = new ArrayList<>();
            for (Rule rule : rulesList)
            {
                int f=1;
                for (String feature : rule.features)
                {
                    if (!inputFeatures.contains(feature))
                    {
                        f=0;
                        break;
                    }
                }
                if(f == 1) //匹配
                {
                    list.add(rule);
                }
            }

            for (Rule rule : list) {
                if (animals.contains(rule.conclusion))
                {
                    System.out.print("成功匹配： ");
                    for (String feature : rule.features) {
                        System.out.print(feature+' ');
                    }
                    System.out.println("---> "+rule.conclusion);
                    return;
                }
                else if (middles.contains(rule.conclusion))
                {
                    System.out.print("中间结论匹配成功： ");
                    for (String feature : rule.features) {
                        System.out.print(feature+' ');
                    }
                    System.out.println("---> "+rule.conclusion);
                    inputFeatures.add(rule.conclusion);
                    rulesList.remove(rule);
                }
                System.out.println("当前知识库：");
                for (String inputFeature : inputFeatures) {
                    System.out.print(inputFeature+" ");
                }
                System.out.println();
            }
            if (list.isEmpty())
            {
                System.out.println("条件不足，请根据提示添加条件！");
                backword(inputFeatures);

                break;
            }

        }
    }
}
