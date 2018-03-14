package com.FileServer;

import java.io.Serializable;

/**
 * Created by Sar-Kerson on 2017/7/11.
 */
public class StorageInfo implements Serializable, Comparable<StorageInfo>{
    String nodeName = null;
    String host = null;
    int port = 0;
    long volume = 0;
    long volumeLeft = volume;
    int fileNumber = 0;
    boolean isOK = false;

    StorageInfo() {}

    StorageInfo(String nodeName, String host, int port, long volume,
                long volumeLeft, int fileNumber, boolean isOK) {
        this.nodeName = nodeName;
        this.host = host;
        this.port = port;
        this.volume = volume;
        this.volumeLeft = volumeLeft;
        this.fileNumber = fileNumber;
        this.isOK = isOK;
    }

    public int compareTo(StorageInfo other) {
        if(isOK && other.isOK) {
            if(this.volumeLeft > other.volumeLeft)
                return 1;
            else if(volumeLeft < other.volumeLeft)
                return -1;

            else return 0;
        }else if(isOK && !other.isOK) {
            return 1;
        }
        else if(!isOK && other.isOK) {
            return -1;
        }else {
            return 0;
        }
    }
}
