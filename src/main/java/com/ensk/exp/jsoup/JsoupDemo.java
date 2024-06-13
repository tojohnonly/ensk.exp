package com.ensk.exp.jsoup;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

public class JsoupDemo {

    public static void main(String[] args) {
        String html = "<p><img src=\"https://dev-assets-api.tianhong.cn/assets/ds/img/product/20240328/4ec34e8d46a34187a1f6f8f4caa9f9e5.jpg\" width=\"100%\"></p><p>测试数据1</p><p>换行</p><p>哈哈哈啊哈</p><p>12345</p><p><img src=\"https://dev-assets-api.tianhong.cn/assets/ds/img/product/20240524/79d3eaf397dc434e8054130549f65c34.jpg\"  width=\"100%\"></p><p>111</p><p>22</p><p><br></p><p><br></p>";
        List<HtmlItem> result = traverse(html);
        System.out.println(result);
    }

    public static List<HtmlItem> traverse(String html) {
        if (StringUtils.isBlank(html)){
            return null;
        }
        // 解析Html
        String cleanHtml = html.replace("&nbsp;", "");
        Document root = null;
        try {
            root = Jsoup.parse(cleanHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 遍历Document
        Node node = root;
        int depth = 0;
        List<HtmlItem> elementList = new ArrayList<>();
        while (node != null) {
            // 访问节点
            if (node instanceof TextNode) {
                // 文本
                TextNode textNode = (TextNode)node;
                String text = textNode.getWholeText();
                if (StringUtils.isNotBlank(text)) {
                    Node parent = node.parentNode();
                    String url = null;
                    if (parent != null && parent instanceof Element && ((Element)parent).tag().getName().equals("a")){
                        url = parent.attr("href");
                    }
                    elementList.add(new HtmlItem(1, text, url));
                }
            } else if (node instanceof Element) {
                Element element = (Element)node;
                if (element.tag().getName().equals("a")) {
                    // 超链接
                    // elementList.add(element.get());
                } else if (element.tag().getName().equals("img")) {
                    // 图片
                    Node parent = element.parentNode();
                    String url = null;
                    if (parent != null && parent instanceof Element && ((Element)parent).tag().getName().equals("a")){
                        url = parent.attr("href");
                    }
                    elementList.add(new HtmlItem(2, element.attr("src"), url));
                } else if (element.tag().getName().equals("input")) {
                    // input标签里面的图片
                    if ("image".equals(element.attr("type")) && StringUtils.isNotBlank(element.attr("src"))) {
                        elementList.add(new HtmlItem(2, element.attr("src"), null));
                    }
                }  else if (element.isBlock() || element.tag().getName().equals("br")) {
                    // elementList.add(" ");
                }
            }

            // 深度优先遍历
            if (node.childNodeSize() > 0) {
                node = node.childNode(0);
                depth++;
            } else {
                while (node.nextSibling() == null && depth > 0) {
                    node = node.parentNode();
                    depth--;
                }
                if (node == root)
                    break;
                node = node.nextSibling();
            }
        }
        return elementList;
    }
}
