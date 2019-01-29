package com.hsy.study.baselibrary.http.log;

import android.text.TextUtils;

import com.hsy.study.baselibrary.utils.CharacterHandler;
import com.hsy.study.baselibrary.utils.MediaTypeUtils;
import com.hsy.study.baselibrary.utils.logger.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.Request;

/**默认OkHttp 打印格式
 * @author haosiyuan
 * @date 2019/1/27 10:06 AM
 */
public class DefaultFormatPrinter implements FormatPrinter{

    /**
     * 请求tag
     */
    private static final String TAG = "HttpLog";
    /**
     * 行分离器
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * 双行
     */
    private static final String DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;
    /**
     * 忽略请求体
     */
    private static final String[] OMITTED_REQUEST = {LINE_SEPARATOR, "Omitted request body"};
    /**
     * 忽略接受体
     */
    private static final String[] OMITTED_RESPONSE = {LINE_SEPARATOR, "Omitted response body"};

    private static final String N = "\n";
    private static final String T = "\t";
    private static final String REQUEST_UP_LINE = "   ┌────── Request ────────────────────────────────────────────────────────────────────────";
    private static final String RESPONSE_UP_LINE = "   ┌────── Response ───────────────────────────────────────────────────────────────────────";
    private static final String END_LINE = "   └───────────────────────────────────────────────────────────────────────────────────────";
    private static final String BODY_TAG = "Body:";
    private static final String URL_TAG = "URL: ";
    private static final String METHOD_TAG = "Method: @";
    private static final String HEADERS_TAG = "Headers:";
    private static final String STATUS_CODE_TAG = "Status Code: ";
    private static final String RECEIVED_TAG = "Received in: ";
    private static final String CORNER_UP = "┌ ";
    private static final String CORNER_BOTTOM = "└ ";
    private static final String CENTER_LINE = "├ ";
    private static final String DEFAULT_LINE = "│ ";

    private static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || N.equals(line) || T.equals(line) || TextUtils.isEmpty(line.trim());
    }

    /**
     * 打印网络请求信息，{@link okhttp3.RequestBody} 可以解析的情况
     * @param request
     * @param bodyString 发送给服务器的请求体中的数据(已解析)
     */
    @Override
    public void printJsonRequest(@NonNull Request request, @NonNull String bodyString) {
        final String requestBody = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString;
        final String tag = getTag(true);

        Logger.debugInfo(tag, REQUEST_UP_LINE);
        //请求url
        logLines(tag, new String[]{URL_TAG + request.url()}, true);
        logLines(tag, getRequest(request),true);
        logLines(tag, requestBody.split(LINE_SEPARATOR), true);
        Logger.debugInfo(tag, END_LINE);

    }

    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 为 {@code null} 或不可解析的情况
     * @param request
     */
    @Override
    public void printFileRequest(@NonNull Request request) {
        final String tag = getTag(true);

        Logger.debugInfo(tag, REQUEST_UP_LINE);
        logLines(tag, new String[]{URL_TAG + request.url()}, true);
        logLines(tag, getRequest(request), true);
        logLines(tag, OMITTED_REQUEST,true);
        Logger.debugInfo(tag, END_LINE);

    }

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 可以解析的情况
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param contentType  服务器返回数据的数据类型
     * @param bodyString   服务器返回的数据(已解析)
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    @Override
    public void printJsonResponse(long chainMs, boolean isSuccessful, int code, @NonNull String headers,
                                  @Nullable MediaType contentType, @Nullable String bodyString,
                                  @NonNull List<String> segments, @NonNull String message, @NonNull String responseUrl) {
        //根据类型进行转换
        bodyString = MediaTypeUtils.isJson(contentType) ? CharacterHandler.jsonFormat(bodyString) :
                MediaTypeUtils.isXml(contentType) ? CharacterHandler.xmlFormat(bodyString) : bodyString;

        final String[] urlLine = new String[]{URL_TAG + responseUrl, N};
        final String responseBody = LINE_SEPARATOR + BODY_TAG +LINE_SEPARATOR + bodyString;
        final String tag = getTag(false);

        Logger.debugInfo(tag, RESPONSE_UP_LINE);
        logLines(tag, urlLine, true);
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true);
        logLines(tag, responseBody.split(LINE_SEPARATOR), true);
        Logger.debugInfo(tag, END_LINE);
    }

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 为 {@code null} 或不可解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    @Override
    public void printFileResponse(long chainMs, boolean isSuccessful, int code, @NonNull String headers,
                                  @NonNull List<String> segments, @NonNull String message, @NonNull String responseUrl) {
        final String tag = getTag(false);
        final String[] urlLine = {URL_TAG + responseUrl, N};

        Logger.debugInfo(tag, RESPONSE_UP_LINE);
        logLines(tag, urlLine, true);
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true);
        logLines(tag, OMITTED_RESPONSE, true);
        Logger.debugInfo(tag, END_LINE);
    }

    /**
     * 对 lines 中的信息进行逐行打印
     * @param tag 打印的tag
     * @param lines 逐行打印的信息
     * @param withLineSize 为 true 时, 每行的信息长度不会超过110, 超过则自动换行
     */
    private static final void logLines(String tag, String[] lines, boolean withLineSize){

        for (String line : lines){
            int lineLength = line.length();
            int MAX_LONG_SIEZ = withLineSize ? 110 : lineLength;

            //逐行打印超过最大值后换行
            for (int i = 0; i < lineLength / MAX_LONG_SIEZ; i++) {
                int start = i * MAX_LONG_SIEZ;
                int end = (i + 1) * MAX_LONG_SIEZ;
                end = end > line.length() ? line.length() : end;
                Logger.debugInfo(resolveTag(tag), DEFAULT_LINE + line.substring(start, end));
            }

        }
    }


    /**
     * 此方法是为了解决在 AndroidStudio v3.1 以上 Logcat 输出的日志无法对齐的问题
     * 改变每次打印tag
     * @param tag
     */
    private static String resolveTag(String tag) {
        return computeKey() + tag;
    }

    /**
     * 多线程 last初始值
     */
    private static ThreadLocal<Integer> last = new ThreadLocal<Integer>(){
        @Nullable
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    private static final String[] ARMS = new String[]{"-H-", "-S-", "-Y-"};

    /**
     * 计算key
     * @return
     */
    private static String computeKey() {
        if (last.get() >= ARMS.length) {
            last.set(0);
        }
        String s = ARMS[last.get()];
        last.set(last.get() + 1);
        return s;
    }

    /**
     * 获取 打印 Request Method 有请求头则打印
     * @param request
     * @return
     */
    private static String[] getRequest(Request request){
        String log;
        String header = request.headers().toString();
        log = METHOD_TAG + request.method() + DOUBLE_SEPARATOR +
                (isEmpty(header) ? "" : HEADERS_TAG + LINE_SEPARATOR + dotHeaders(header));

        return log.split(LINE_SEPARATOR);
    }

    /**
     * 返回值
     * @param header         请求头
     * @param chainMs        服务器响应耗时(单位毫秒)
     * @param code           响应码
     * @param isSuccessful   请求是否成功
     * @param segments       域名后面的资源地址
     * @param message        响应信息
     * @return
     */
    private static String[] getResponse(String header, long chainMs, int code, boolean isSuccessful,
                                 List<String> segments, String message) {
        String log;
        String segmentString = slashSegments(segments);

        log = ((!TextUtils.isEmpty(segmentString) ? segmentString + " - " : "") + "is success : "
                + isSuccessful + " - " + RECEIVED_TAG + chainMs + "ms" + DOUBLE_SEPARATOR + STATUS_CODE_TAG +
                code + " / " + message + DOUBLE_SEPARATOR + (isEmpty(header) ? "" : HEADERS_TAG + LINE_SEPARATOR +
                dotHeaders(header)));

        return log.split(LINE_SEPARATOR);
    }

    /**
     * 将传入的List 添加 /
     * @param segments
     * @return
     */
    private static String slashSegments(List<String> segments) {
        StringBuilder segmentString = new StringBuilder();
        for (String segment : segments) {
            segmentString.append("/").append(segment);
        }
        return segmentString.toString();
    }

    /**
     * 头部 方法转换
     * @param header
     * @return
     */
    private static String dotHeaders(String header) {
        String[] headers = header.split(LINE_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        String tag = "- ";

        if (headers.length > 1){
            for (int i = 0; i < headers.length; i++) {
                if (i == 0){
                    tag = CORNER_UP;
                }else if (i == headers.length - 1){
                    tag = CORNER_BOTTOM;
                }else{
                    tag = CENTER_LINE;
                }

                builder.append(tag).append(headers[i]).append("\n");
            }
        }else{
            for (String item : headers) {
                builder.append(tag).append(item).append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * 获取打印标识
     * @param isRequest
     * @return
     */
    private static String getTag(boolean isRequest) {
        if (isRequest) {
            return TAG + "-Request";
        } else {
            return TAG + "-Response";
        }
    }
}
