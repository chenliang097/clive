package com.rongtuoyouxuan.chatlive.crtlog.upload;

import android.os.Environment;

import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.rongtuoyouxuan.chatlive.crtlog.upload.Utils.checkNotNull;
import static com.rongtuoyouxuan.chatlive.crtlog.upload.Utils.isEmpty;

class UCsvFormatStrategy implements FormatStrategy {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NEW_LINE_REPLACEMENT = " <br> ";
    private static final String SEPARATOR = ",";

    @NonNull private final Date date;
    @NonNull private final SimpleDateFormat dateFormat;
    @NonNull private final LogStrategy logStrategy;
    @Nullable private final String tag;

    private UCsvFormatStrategy(@NonNull UCsvFormatStrategy.Builder builder) {
        checkNotNull(builder);

        date = builder.date;
        dateFormat = builder.dateFormat;
        logStrategy = builder.logStrategy;
        tag = builder.tag;
    }

    @NonNull public static UCsvFormatStrategy.Builder newBuilder() {
        return new UCsvFormatStrategy.Builder();
    }

    @Override public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
        checkNotNull(message);

        String tag = formatTag(onceOnlyTag);

        date.setTime(System.currentTimeMillis());

        StringBuilder builder = new StringBuilder();

        builder.append(dateFormat.format(date));

        // level
        builder.append(SEPARATOR);
        builder.append(Utils.logLevel(priority));

        // tag
        builder.append(SEPARATOR);
        builder.append(tag);

        // message
        if (message.contains(NEW_LINE)) {
            // a new line would break the CSV format, so we replace it here
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }
        builder.append(SEPARATOR);
        builder.append(message);

        // new line
        builder.append(NEW_LINE);

        logStrategy.log(priority, tag, builder.toString());
    }

    @Nullable private String formatTag(@Nullable String tag) {
        if (!isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static final class Builder {
        private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

        Date date;
        SimpleDateFormat dateFormat;
        LogStrategy logStrategy;
        String tag = "PRETTY_LOGGER";
        String product = "";

        private Builder() {
        }

        @NonNull
        public UCsvFormatStrategy.Builder date(@Nullable Date val) {
            date = val;
            return this;
        }

        @NonNull public UCsvFormatStrategy.Builder dateFormat(@Nullable SimpleDateFormat val) {
            dateFormat = val;
            return this;
        }

        @NonNull public UCsvFormatStrategy.Builder logStrategy(@Nullable LogStrategy val) {
            logStrategy = val;
            return this;
        }

        @NonNull public UCsvFormatStrategy.Builder tag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        @NonNull public UCsvFormatStrategy.Builder product(@Nullable String product) {
            this.product = product;
            return this;
        }

        @NonNull public UCsvFormatStrategy build() {
            if (date == null) {
                date = new Date();
            }
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.CHINA);
            }
            if (logStrategy == null) {
                String logsPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separatorChar + product + File.separatorChar + "logs";
                logStrategy = new DiskLogStrategy(UWriteHandler.defaultWriteHandler(logsPath));
            }
            return new UCsvFormatStrategy(this);
        }
    }
}
