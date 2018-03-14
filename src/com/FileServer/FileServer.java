package com.FileServer;

import java.io.*;
import java.net.*;

/**
 * Created by Sar-Kerson on 2017/7/6.
 */
public class FileServer {
    public FileServer(int port, IOStrategy ios) { 
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("File server is ready");
            while (true) {
                Socket socket = ss.accept();
                ios.service(socket); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
