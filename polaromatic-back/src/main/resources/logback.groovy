import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

final String PATTERN = '%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %5p %c{1}:%L - %m%n'
final String LOG_NAME = 'log/polaromatic.log'

appender "CONSOLE", ConsoleAppender, {
    encoder PatternLayoutEncoder, {
        pattern = PATTERN
    }
}

appender "FILE", RollingFileAppender, {
    file = LOG_NAME
    rollingPolicy TimeBasedRollingPolicy, {
        fileNamePattern = "${LOG_NAME}.%d{yyyy-MM-dd}"
        maxHistory = 365
    }
    encoder PatternLayoutEncoder, {
        pattern = PATTERN
    }
}

root INFO, ["CONSOLE", "FILE"]

logger "polaromatic", DEBUG, ["CONSOLE", "FILE"], false
