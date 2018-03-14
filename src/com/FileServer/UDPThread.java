package com.FileServer;

import java.net.*;

/**
 * Created by Sar-Kerson on 2017/7/6.
 */
public class UDPThread extends Thread{
    public void run() {
        int counter = 0;
        long time[] = new long[10];
        long startMili = System.currentTimeMillis();// 开始时间对应的毫秒数
        for(long element : time) {
            element = startMili;
        }
        //System.out.println(startMili);
        long endMili = startMili;
        while (true) {
            try {
                DatagramSocket server = new DatagramSocket(Integer.parseInt(Tools.getValueByKey("UDPServerPort")));

                byte[] recvBuf = new byte[100];
                DatagramPacket recvPacket
                        = new DatagramPacket(recvBuf , recvBuf.length);
                server.receive(recvPacket);

                String recvStr = new String(recvPacket.getData() , 0 , recvPacket.getLength());
                System.out.println("recvStr: " + recvStr);
                server.close();

                for(StorageInfo element : FileTransfer.storageInfos) {
                    if(element.nodeName.equals(recvStr)) {
                        time[counter] = System.currentTimeMillis();
                        element.isOK = true;
                        counter++;
                        break;
                    }else {
                        counter++;
                    }
                }

                endMili = System.currentTimeMillis();
                for(int i = 0; (i < time.length) && (i < FileTransfer.storageInfos.size()); i++) {
                    //System.out.println("time[" + i + "]: " + time[i]);
                    if(endMili - time[i] > 1500) {
                        FileTransfer.storageInfos.get(i).isOK = false;
                    }
                }
                counter = 0;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
