package com.ensk.exp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

public class YamlParser {
    public static void main(String[] args) throws FileNotFoundException {
        // Load YAML file
        InputStream input = new FileInputStream("D:\\OneDrive\\Teck\\VPN\\Clash\\Mojie.yml");
        // Create YAML parser
        Yaml yaml = new Yaml();
        Map<String, Object> yamlData = yaml.load(input);
        // Get proxies
        List<Map<String, Object>> proxies = (List<Map<String, Object>>)yamlData.get("proxies");
        /** Group proxies */
        List<String> enskNodes = new ArrayList<>();
        List<String> autoSelect = new ArrayList<>();
        List<String> aiNodes = new ArrayList<>();
        // Pattern autoSelectPattern = Pattern.compile("^(?=.*(?:香港|HK|日本|JP|新加坡|SG|台湾|TW))(?=.*(?:1x|1X)).*$");
        Pattern autoSelectPattern = Pattern.compile("(?=.*(香港|HK|日本|JP|新加坡|SG|台湾|TW))^((?!(ENSKAI|AI|ai)).)*$");
        // Pattern autoSelectPattern = Pattern.compile(".*(?:香港|HK|日本|JP|新加坡|SG|台湾|TW).*");
        // Pattern aiNodesPattern = Pattern.compile("^(?=.*(?:日本|JP|新加坡|SG|台湾|TW))(?=.*(?:1x|1X)).*$");
        Pattern aiNodesPattern = Pattern.compile(".*(?:ENSKAI|AI|ai).*");
        for (Map<String, Object> proxie : proxies) {
            String nodeName = (String)proxie.get("name");
            if (null == nodeName) {
                continue;
            }
            enskNodes.add(nodeName);
            if (autoSelectPattern.matcher(nodeName).matches()) {
                autoSelect.add(nodeName);
            }
            if (aiNodesPattern.matcher(nodeName).matches()) {
                aiNodes.add(nodeName);
            }
        }
        /** Print proxies groups */
        System.out.println("EnskNodes:");
        System.out.println("    proxies: " + enskNodes);
        System.out.println();
        System.out.println("AutoSelect:");
        System.out.println("    proxies: " + autoSelect);
        System.out.println();
        System.out.println("AiNodes:");
        System.out.println("    proxies: " + aiNodes);
        System.out.println();
    }
}
