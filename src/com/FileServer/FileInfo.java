package com.FileServer;

import java.io.Serializable;

/**
 * Created by Sar-Kerson on 2017/7/11.
 */
public class FileInfo implements Serializable{
    String UUID = null;
    String fileName = null;
    long length = 0;
    String mainHost = null;
    int mainPort = 0;
    String backHost = null;
    int backPort = 0;

    FileInfo() {}

    FileInfo(String UUID, String fileName, long length,
             String mainHost, int mainPort, String backHost, int backPort) {
        this.UUID = UUID;
        this.fileName = fileName;
        this.length = length;
        this.mainHost = mainHost;
        this.mainPort = mainPort;
        this.backHost = backHost;
        this.backPort = backPort;
    }

}
