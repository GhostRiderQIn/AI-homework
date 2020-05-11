import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

/**
 * @program: AI-projects
 * @description:
 * @author: qinda
 * @create: 2020-04-16 13:22
 **/
public class LoadDB {
    private String url;
    private Map map;
    public LoadDB(String url) throws FileNotFoundException {
        this.url = url;
        map = this.getMap();
    }
    public Map getMap() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        URL url = main.class.getClassLoader().getResource(this.url);
        Object obj =yaml.load(new FileInputStream(url.getFile()));
        Map map = yaml.load(new FileInputStream(url.getFile()));
        return map;
    }
    public List<Map<String, List<String>>> getRules() throws FileNotFoundException {
        Map map = this.getMap();
        return (List<Map<String,List<String>>>) map.get("rules");
    }
    public List<Rule> getRulesList() throws FileNotFoundException
    {
        List<Map<String, List<String>>> rules = this.getRules();
        List<Rule> ruleList = new ArrayList<>();
        for (Map<String, List<String>> stringListMap : rules)
        {
            for(Map.Entry<String,List<String>> entry:stringListMap.entrySet())
            {
                Rule rule = new Rule();
                rule.conclusion = entry.getKey();
                rule.features = entry.getValue();
                ruleList.add(rule);
            }
        }
        return ruleList;
    }
    public Set<String> getFeatures() throws FileNotFoundException {
        return new HashSet<String>((List<String>) map.get("feature"));
    }
    public Set<String> getMiddles() throws FileNotFoundException {
        return new HashSet<String>((List<String>) map.get("middle"));
    }
    public Set<String> getAnimals() throws FileNotFoundException {
        return new HashSet<String>((List<String>) map.get("animal"));
    }
}
