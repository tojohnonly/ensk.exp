package com.ensk.exp.jsoup;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 商品图文列表项对象
 *
 * @author 魏大义
 * @date 2020/10/21 15:26
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class HtmlItem {

    /**
     * 图文列表项类型
     */
    private Integer type;

    /**
     * 图文列表项值
     */
    private String value;

    /**
     * 图文列表项关联的链接
     */
    private String url;
}

