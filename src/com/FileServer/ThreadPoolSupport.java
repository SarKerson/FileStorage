package com.FileServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadPoolSupport implements IOStrategy { // ThreadPoolSupport.java
	private ArrayList threads = new ArrayList();
	private UDPThread udpThread = new UDPThread();
	private final int INIT_THREADS = 50;
	private final int MAX_THREADS = 100;
	private IOStrategy ios = null;

	public ThreadPoolSupport(IOStrategy ios) { // 创建线程池
		//从文件里序列化读取存储节点信息
        File file = new File("storageInfo.dat");
        try
        {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            FileTransfer.storageInfos = (LinkedList<StorageInfo>) input.readObject();
            System.out.println("StorageNode imformation: ");
            for(int i = 0; i < FileTransfer.storageInfos.size(); i++) {
                System.out.println(FileTransfer.storageInfos.get(i).nodeName + ' ' +
                        FileTransfer.storageInfos.get(i).host + ' ' +
                        FileTransfer.storageInfos.get(i).port + ' ' +
                        FileTransfer.storageInfos.get(i).volume + ' ' +
                        FileTransfer.storageInfos.get(i).volumeLeft + ' ' +
                        FileTransfer.storageInfos.get(i).fileNumber + ' ' +
                        FileTransfer.storageInfos.get(i).isOK);
            }
            input.close();
        }
        catch (Exception e)
        {
        	System.out.println("storageInfo get falied");
            e.printStackTrace();
        }

        //从文件里序列化读取文件信息
        File file1 = new File("fileInfo.dat");
        try
        {
            //执行反序列化读取
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file1));
            FileTransfer.fileInfoMap = (HashMap)input.readObject();
            input.close();
        }
        catch (Exception e)
        {
        	System.out.println("fileInfo get failed");
            e.printStackTrace();
        }

        udpThread.start();

		this.ios = ios;
		for (int i = 0; i < INIT_THREADS; i++) {
			IOThread2 t = new IOThread2(ios); // 传递协议对象，但是还没有socket
			t.start(); // 启动线程，进入线程体后都是wait
			threads.add(t);
		}
		try {
			Thread.sleep(300);
		} catch (Exception e) {
		} // 等待线程池的线程都“运行”
	}

	public void service(Socket socket) { // 遍历线程池，找到一个空闲的线程，把客户端交给“它”处理
		IOThread2 t = null;
		boolean found = false;
		for (int i = 0; i < threads.size(); i++) {
			t = (IOThread2) threads.get(i);
			if (t.isIdle()) {
				found = true;
				break;
			}
		}
		if (!found) // 线程池中的线程都忙，没有办法了，只有创建
		{ // 一个线程了，同时添加到线程池中。
			if(threads.size() < MAX_THREADS) {
				t = new IOThread2(ios);
				t.start();
				try {
					Thread.sleep(300);
				} catch (Exception e) {
				}
				threads.add(t);
			}else{
				//待填写

			}

		}
		t.setSocket(socket); // 将服务器端的socket对象传递给这个空闲的线程,让其开始执行协议，向客户端提供服务
	}
}
