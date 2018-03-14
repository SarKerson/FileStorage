package com.FileServer;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Created by Sar-Kerson on 2017/7/6.
 */
public class FileTransfer implements IOStrategy{
    //一个存文件的静态同步合集
    static Map<String, FileInfo> fileInfoMap = Collections.synchronizedMap(new HashMap<>());
    //这里应该要有一个存节点的静态同步合集
    static List<StorageInfo> storageInfos = Collections.synchronizedList(new LinkedList<>());

    public void service(Socket socket) {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            int command = dis.readInt();

            switch (command) {
                //上传文件
                case 1:
                    try{
                        String UUID = Tools2.getUUID(); //获取UUID
                        String fileName = dis.readUTF();//获取文件原始名
                        long length = dis.readLong();//获取文件大小
                        String mainHost = "127.0.0.1";//预先指定一些无效值
                        int mainPort = 9000;
                        String backHost = "127.0.0.1";
                        int backPort = 9000;

                        //根据结点负载排序
                        Collections.sort(storageInfos, Collections.reverseOrder());
                        //分配结点
                        if(storageInfos.size() > 2) {
                            if(storageInfos.get(0).isOK) {
                                mainHost = storageInfos.get(0).host;
                                mainPort = storageInfos.get(0).port;
                                storageInfos.get(0).volumeLeft -= length;
                                storageInfos.get(0).fileNumber++;
                            }else {
                                System.out.println("No StorageNode work");
                            }
                            if(storageInfos.get(1).isOK) {
                                backHost = storageInfos.get(1).host;
                                backPort = storageInfos.get(1).port;
                                storageInfos.get(1).volumeLeft -= length;
                                storageInfos.get(1).fileNumber++;
                            }else {
                                System.out.println("No Back StorageNode");
                            }
                        }else if(storageInfos.size() == 1) {
                            if(storageInfos.get(0).isOK) {
                                mainHost = storageInfos.get(0).host;
                                mainPort = storageInfos.get(0).port;
                                storageInfos.get(0).volumeLeft -= length;
                                storageInfos.get(0).fileNumber++;
                            }else {
                                System.out.println("No StorageNode work");
                            }

                        }else {
                            System.out.println("No StorageNode exit");
                            System.exit(1);
                        }

                        //生成UUID，new一个fileInfo对象，存到静态同步合集里
                        fileInfoMap.put(UUID, new FileInfo(UUID, fileName, length, mainHost, mainPort, backHost, backPort));
                        //System.out.println("new fileInfo succeed");

                        //将信息序列化到文件里存储
                        ObjectOutputStream output = null;
                        try {
                            output = new ObjectOutputStream(new FileOutputStream("fileInfo.dat"));
                            output.writeObject(fileInfoMap);
                            output.flush();
                            output.close();
                        }catch (IOException e) {
                            if(output != null) {
                                output.close();
                            }
                            System.out.println("File Serializable failed");
                        }
                        ObjectOutputStream output1 = null;
                        try {
                            output1 = new ObjectOutputStream(new FileOutputStream("storageInfo.dat"));
                            output1.writeObject(storageInfos);
                            output1.flush();
                            output1.close();
                        }catch (IOException e) {
                            if(output1 != null) {
                                output1.close();
                            }
                            System.out.println("File Serializable failed");
                        }

                        dos.writeUTF(UUID);//返回编码名
                        dos.writeUTF(mainHost);//返回Storage主节点地址
                        dos.writeInt(mainPort);//返回Storage主节点端口
                        dos.writeUTF(backHost);//返回备用节点地址
                        dos.writeInt(backPort);//返回备用节点端口
                        dos.flush();

                        System.out.println(UUID);
                        System.out.println(mainHost);
                        System.out.println(mainPort);
                        System.out.println(backHost);
                        System.out.println(backPort);

                        socket.close();
                    }catch (Exception e) {
                        socket.close();
                        System.out.println("File upload failed");
                    }
                    break;
                //下载文件
                case 2:
                    try{
                        String UUID = dis.readUTF();//获取文件编码名

                        System.out.println(UUID);

                        FileInfo fileInfo = fileInfoMap.get(UUID);//读取文件信息集合

                        System.out.println(fileInfo.fileName);

                        if(fileInfo == null) {
                            dos.writeBoolean(false);
                        }else {
                            dos.writeBoolean(true);
                        }
                        dos.flush();

                        dos.writeUTF(fileInfo.fileName);//告知原始文件名
                        dos.writeLong(fileInfo.length);//告知长度
                        dos.writeUTF(fileInfo.mainHost);//告知主节点地址
                        dos.writeInt(fileInfo.mainPort);//告知端口号
                        dos.writeUTF(fileInfo.backHost);//告知备用节点地址
                        dos.writeInt(fileInfo.backPort);//告知备用节点端口
                        dos.flush();

                        System.out.println(fileInfo.fileName);

                        socket.close();
                    }catch (Exception e) {
                        socket.close();
                        System.out.println("File download failed");
                    }

                    break;
                //删除文件
                case 3:
                    try{
                        String UUID = dis.readUTF();//获取文件编码名
                        FileInfo fileInfo = fileInfoMap.get(UUID);//读取文件信息集合

                        if(fileInfo == null) {
                            dos.writeBoolean(false);
                        }else {
                            dos.writeBoolean(true);
                        }
                        dos.flush();

                        System.out.println("write succeed");

                        dos.writeUTF(fileInfo.mainHost);//告知主节点地址
                        dos.writeInt(fileInfo.mainPort);//告知端口号
                        dos.writeUTF(fileInfo.backHost);//告知备用节点地址
                        dos.writeInt(fileInfo.backPort);//告知备用节点端口
                        dos.flush();

                        //更改存储节点的fileNumber和volumeLeft的信息
                        for(StorageInfo element : storageInfos) {
                            if(element.host.equals(fileInfo.mainHost)
                                    && element.port == fileInfo.mainPort) {
                                element.fileNumber--;
                                element.volumeLeft += fileInfo.length;
                            }
                            if(element.host.equals(fileInfo.backHost)
                                    && element.port == fileInfo.backPort) {
                                element.fileNumber--;
                                element.volumeLeft += fileInfo.length;
                            }
                        }

                        System.out.println("Changed succeed");

                        fileInfoMap.remove(UUID);//从文件列表里将其删除

                        ObjectOutputStream output = null;
                        try {
                            output = new ObjectOutputStream(new FileOutputStream("fileInfo.dat"));
                            output.writeObject(fileInfoMap);
                            output.flush();
                            output.close();
                        }catch (IOException e) {
                            if(output != null) {
                                output.close();
                            }
                            System.out.println("File Serializable failed");
                        }
                        ObjectOutputStream output1 = null;
                        try {
                            output1 = new ObjectOutputStream(new FileOutputStream("storageInfo.dat"));
                            output1.writeObject(storageInfos);
                            output1.flush();
                            output1.close();
                        }catch (IOException e) {
                            if(output1 != null) {
                                output1.close();
                            }
                            System.out.println("File Serializable failed");
                        }

                        socket.close();
                        System.out.println("File Serializable succeed");

                    }catch (Exception e) {
                        socket.close();
                        System.out.println("File delete failed");
                    }

                    break;
                //监控程序请求文件信息
                case 4:
                    try{
                        Set<FileInfo> fileInfoSet = new HashSet<>(fileInfoMap.values());

                        dos.writeInt(fileInfoMap.size());//返回文件信息映射表的大小
                        dos.flush();

                        for(FileInfo element : fileInfoSet) {
                            dos.writeUTF(element.UUID);
                            dos.writeUTF(element.fileName);
                            dos.writeLong(element.length);
                            dos.writeUTF(element.mainHost);
                            dos.writeInt(element.mainPort);
                            dos.writeUTF(element.backHost);
                            dos.writeInt(element.backPort);
                            dos.flush();
                        }

                        socket.close();
                    }catch (Exception e) {
                        socket.close();
                        System.out.println("Monitor fileInoformation failed");
                    }

                    //监控程序请求节点信息
                case 5:
                    try{
                        dos.writeInt(storageInfos.size());//返回节点信息线性表的大小
                        dos.flush();

                        for(StorageInfo element : storageInfos) {
                            dos.writeUTF(element.nodeName);
                            dos.writeUTF(element.host);
                            dos.writeInt(element.port);
                            dos.writeLong(element.volume);
                            dos.writeLong(element.volumeLeft);
                            dos.writeInt(element.fileNumber);
                            dos.writeBoolean(element.isOK);
                            dos.flush();
                        }
                        socket.close();

                    }catch (Exception e) {
                        socket.close();
                        System.out.println("Monitor storageInformation failed");
                    }
            }
            System.out.println("File transfer succeed");
        }catch (Exception e) {
            System.out.println("File transfer failed.");
        }
    }
}