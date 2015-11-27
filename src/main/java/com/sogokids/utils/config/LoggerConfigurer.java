package com.sogokids.utils.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import cn.momia.common.config.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hoze on 15/7/16.
 */
public class LoggerConfigurer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConfigurer.class);

    private Configuration conf;

    public void setConf(Configuration conf)
    {
        this.conf = conf;
    }

    public void init()
    {
        if (!reload()) throw new RuntimeException("fail to init logger configurer");
    }

    public boolean reload()
    {
        String loggerLevel = conf.getString("Logger.Level");
        if (StringUtils.isBlank(loggerLevel)) return false;

        LOGGER.info("set logger level to: {}", loggerLevel);

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.toLevel(loggerLevel));

        return true;
    }
}

