package com.hsy.study.myproject.dbt;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import android.util.Log;

/**
 * @author haosiyuan
 * @date 2019/1/15 9:59 AM
 */
public class SqlWrapper {

    /**
     * 默认打印
     */
    static final Logger DEFAULT_LOGGER = new Logger() {
        @Override public void log(String message) {
            Log.d("SqlWrapper", message);
        }
    };
    public static final class Builder {
        private Logger logger = DEFAULT_LOGGER;

        @CheckResult
        public Builder logger(@NonNull Logger logger) {
            if (logger == null) {
                throw new NullPointerException("logger == null");
            }
            this.logger = logger;
            return this;
        }

        @CheckResult
        public SqlWrapper build() {
            return new SqlWrapper(logger);
        }
    }

    final Logger logger;

    SqlWrapper(@NonNull Logger logger) {
        this.logger = logger;
    }


    /**
     * 自定义打印函数
     */
    public interface Logger {
        void log(String message);
    }
}
