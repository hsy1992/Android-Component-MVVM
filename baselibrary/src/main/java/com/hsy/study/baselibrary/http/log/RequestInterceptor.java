package com.hsy.study.baselibrary.http.log;

import com.hsy.study.baselibrary.http.IGlobalHttpHandler;
import com.hsy.study.baselibrary.utils.UtilCharacterHandler;
import com.hsy.study.baselibrary.utils.UtilMediaType;
import com.hsy.study.baselibrary.utils.UrlEncoderUtil;
import com.hsy.study.baselibrary.utils.ZipHelperUtil;
import com.hsy.study.baselibrary.common.logger.Logger;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**请求拦截器
 * @author haosiyuan
 * @date 2019/1/27 9:32 AM
 */
@Singleton
public class RequestInterceptor implements Interceptor {

    @Inject
    IGlobalHttpHandler mHandler;
    /**
     * 打印处理
     */
    @Inject
    IFormatPrinter formatPrinter;
    @Inject
    @LogLevel
    int printLevel;

    @Inject
    public RequestInterceptor() {}

    /**
     * 拦截器
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //是否打印请求
        boolean logRequest = printLevel == LogLevel.ALL || (printLevel != LogLevel.NONE && printLevel == LogLevel.REQUEST);

        if (logRequest){
            //打印请求信息
            if (request.body() != null && UtilMediaType.isParseable(request.body().contentType())){
                formatPrinter.printJsonRequest(request, parseParams(request));
            } else {
                formatPrinter.printFileRequest(request);
            }
        }

        //是否打印返回
        boolean logResponse = printLevel == LogLevel.ALL || (printLevel != LogLevel.NONE && printLevel == LogLevel.RESPONSE);

        //计算响应时间
        long startTime = logResponse ? System.nanoTime() : 0;

        //原始返回
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        }catch (Exception e){
            Logger.errorInfo(e.getMessage());
            throw e;
        }

        long endTime = logResponse ? System.nanoTime() : 0;

        ResponseBody responseBody = originalResponse.body();

        //打印响应结果
        String bodyString = null;
        if (responseBody != null && UtilMediaType.isParseable(responseBody.contentType())){
            bodyString = printResult(originalResponse);
        }

        if (logResponse){
            final List<String> sefmentList = request.url().encodedPathSegments();
            final String header = originalResponse.headers().toString();
            final int code = originalResponse.code();
            final boolean isSuccessful = originalResponse.isSuccessful();
            final String message = originalResponse.message();
            final String url = originalResponse.request().url().toString();

            if (bodyString != null && UtilMediaType.isParseable(responseBody.contentType())){
                formatPrinter.printJsonResponse(TimeUnit.MILLISECONDS.toMillis(endTime - startTime), isSuccessful,
                        code, header, responseBody.contentType(), bodyString, sefmentList, message, url);
            }else{
                formatPrinter.printFileResponse(TimeUnit.MILLISECONDS.toMillis(endTime - startTime), isSuccessful,
                        code, header, sefmentList, message, url);
            }
        }

        //先一步处理返回
        if (mHandler != null){
            return mHandler.onHttpResponseResult(bodyString, chain, originalResponse);
        }

        return originalResponse;
    }

    /**
     * 打印响应结果
     * @param response    {@link Response}
     * @return 解析后的响应结果
     * @throws IOException
     */
    @Nullable
    private String printResult(Response response) throws IOException {
        try {
            //读取服务器返回的结果
            ResponseBody responseBody = response.newBuilder().build().body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            //获取content的压缩类型
            String encoding = response
                    .headers()
                    .get("Content-Encoding");

            Buffer clone = buffer.clone();

            //解析response content
            return parseContent(responseBody, encoding, clone);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 解析服务器响应的内容
     * @param responseBody {@link ResponseBody}
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase(ZipHelperUtil.GZIP)) {
            //content 使用 gzip 压缩
            return ZipHelperUtil.decompressForGzip(clone.readByteArray(), convertCharset(charset));
        } else if (encoding != null && encoding.equalsIgnoreCase(ZipHelperUtil.ZLIB)) {
            //content 使用 zlib 压缩
            return ZipHelperUtil.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));
        } else {
            //content 没有被压缩, 或者使用其他未知压缩方式
            return clone.readString(charset);
        }
    }

    /**
     * 解析请求服务器的请求参数
     * @param request
     * @return
     */
    public static String parseParams(Request request){

        try {
            RequestBody requestBody = request.body();
            if (requestBody == null){
                return "";
            }

            Buffer requestBuffer = new Buffer();
            requestBody.writeTo(requestBuffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null){
                charset = contentType.charset(charset);
            }

            String json = requestBuffer.readString(charset);
            if (UrlEncoderUtil.hasUrlEncoded(json)){
                json = URLDecoder.decode(json, convertCharset(charset));
            }

            return UtilCharacterHandler.jsonFormat(json);

        } catch (IOException e) {
            e.printStackTrace();

            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 字符集转换
     * @param charset
     * @return
     */
    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1){
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }

    /**
     * 打印等级
     */
    @IntDef({
            LogLevel.NONE,
            LogLevel.REQUEST,
            LogLevel.RESPONSE,
            LogLevel.ALL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel{

        /**
         * 不打印
         */
        int NONE = 0;
        /**
         * 打印请求
         */
        int REQUEST = 1;
        /**
         * 打印返回
         */
        int RESPONSE = 2;
        /**
         * 全部打印
         */
        int ALL = 3;

    }
}
