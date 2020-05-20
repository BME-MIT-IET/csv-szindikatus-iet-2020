package com.complexible.common.csv.logger;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessBehaviourLogger {
    private static final Logger processLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static  void logInfo(String message)
    {
        processLogger.log(Level.INFO, message);
    }
    public static void logError(String message)
    {
        processLogger.log(Level.WARNING,message);
    }
    public static void addHandler(Handler handler){
        processLogger.addHandler(handler);
    }
}
