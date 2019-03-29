package com.endless.study.baselibrary.http.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Api 请求成功
 * @author haosiyuan
 * @date 2019/3/20 4:19 PM
 */
public class ApiSuccessResponse<T> extends ApiResponse<T> {

    /**
     * 链接正则
     */
    private Pattern LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    /**
     * 页面正则
     */
    private Pattern PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)");
    /**
     * 下个链接
     */
    private static final String NEXT_LINK = "next";

    private T body;

    private Map<String, String> links;

    public ApiSuccessResponse(T body, String linkHeader) {
        this.body = body;
        this.links = extractLinks(linkHeader);
        this.links = this.links == null ? new LinkedHashMap<>() : this.links;
    }

    /**
     * 提取链接
     * @param linkHeader
     * @return
     */
    private Map<String, String> extractLinks(String linkHeader) {

        LinkedHashMap<String, String> linksMap = new LinkedHashMap<>();

        Matcher matcher = LINK_PATTERN.matcher(linkHeader);

        while (matcher.find()) {
            int count = matcher.groupCount();

            if (count == 2) {
                //有符合条件的链接 放入map
                linksMap.put(matcher.group(2), matcher.group(1));
            }
        }
        return linksMap;
    }

    /**
     * 下一页
     * @return
     */
    public synchronized int nextPage() {

        String next = links.get(NEXT_LINK);
        Matcher pageMatcher = PAGE_PATTERN.matcher(next);

        if (!pageMatcher.find() || pageMatcher.groupCount() != 1) {
            return 0;
        } else {
            try {
                return Integer.parseInt(pageMatcher.group(1));
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public T getBody() {
        return body;
    }
}
