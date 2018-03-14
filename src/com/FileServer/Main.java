package com.FileServer;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        new FileServer(Integer.parseInt(Tools.getValueByKey("FileServerPort")), new ThreadPoolSupport(new FileTransfer()));
    }
}
