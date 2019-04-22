package vnu.uet.boatsafe.service.log;

import android.content.Context;

import vnu.uet.boatsafe.utils.AppConstants;
import vnu.uet.boatsafe.utils.FileLogUtility;

public class LoggerManager {
    private Context mContext;

    private FileLogUtility mAccLogger;
    private FileLogUtility mVGPSLogger;
    private FileLogUtility mVACCLogger;
    private FileLogUtility mAccFilter;
    private FileLogUtility mAccAfterKFilter;
    private FileLogUtility mSpeed;
    private FileLogUtility mLocation;
    private FileLogUtility mOrientation;


    private static LoggerManager instances;

    public static LoggerManager getInstances(Context context) {
        if (instances == null) {
            instances = new LoggerManager(context);
        }

        return instances;
    }

    public LoggerManager(Context context) {
        this.mContext = context;

        mAccLogger = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(0));
        mAccAfterKFilter = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(1));
        mSpeed = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(2));
        mVGPSLogger = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(3));
        mVACCLogger = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(4));
        mAccFilter = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(5));
        mLocation = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(6));
        mOrientation = new FileLogUtility(mContext, "logs", AppConstants.LIST_FILE_LOGGER.get(7));
    }

    public void open() {
        mAccLogger.openFile();
        mVGPSLogger.openFile();
        mVACCLogger.openFile();
        mLocation.openFile();
        mAccFilter.openFile();
        mAccAfterKFilter.openFile();
        mSpeed.openFile();
        mOrientation.openFile();
    }

    public void close() {
        mAccLogger.closeFile();
        mVGPSLogger.closeFile();
        mVACCLogger.closeFile();
        mLocation.closeFile();
        mAccFilter.closeFile();
        mAccAfterKFilter.closeFile();
        mSpeed.closeFile();
        mOrientation.closeFile();
    }

    // Log accelerometer sensor data
    public void writeACCLogger(String message) {
        mAccLogger.writeLog(message);
    }

//    public void writeAccAfterKFilterLogger(String message) {
//        mAccAfterKFilter.writeLog(message);
//    }

    public void writeUpdateSpeedLogger(String message) {
        mSpeed.writeLog(message);
    }
    // Log speed base on GPS
    public void writeVGPSLogger(String message) {
        mVGPSLogger.writeLog(message);
    }

    // Log speed base on accelerometer
    public void writeVACCLogger(String message) {
        mVACCLogger.writeLog(message);
    }


    //log accFilter
    public void writeAccFilterLogger(String message){
        mAccFilter.writeLog(message);
    }

    public void writeUpdateLocatioLogger(String message) {
        mLocation.writeLog(message);
    }

    public void writeAccAfterKFilterLogger(String message) {
        mAccAfterKFilter.writeLog(message);
    }

    public void writeOrientation(String message){
        mOrientation.writeLog(message);
    }
}
