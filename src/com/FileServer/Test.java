package com.FileServer;

import java.io.*;
import java.util.*;

/**
 * Created by Sar-Kerson on 2017/7/7.
 */
class TestObject implements Serializable{
    private String name;
    private String address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}

/*
NodeName=Node1
NodeIP=127.0.0.1
NodePort=8001
RootFolder=d:/StorageFile/Node1File
Volume=100GB
FileServerIP=127.0.0.1
FileServerPort=8000
UDPServerPort=9000
 */

/*
List<StorageInfo> storageInfos = new LinkedList<>();
        storageInfos.add(
                new StorageInfo("Node1", "127.0.0.1", 8001,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node2", "127.0.0.1", 8002,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node3", "127.0.0.1", 8003,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node4", "127.0.0.1", 8004,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node5", "127.0.0.1", 8005,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node6", "127.0.0.1", 8006,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );

        File file = new File("storageinfo.dat");
        try{
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(storageInfos);
        }catch (IOException e) {
            System.out.println("StorageNode information input failed");
        }
*/
/*
    File file = new File("storageinfo.dat");
        try{
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
                List<StorageInfo> storageInfos = (List<StorageInfo>) input.readObject();
        for(StorageInfo element : storageInfos) {
        System.out.println(element.nodeName);
        }
        }catch (IOException e) {
        System.out.println("StorageNode information input failed");
        }

  */
public class Test {
    public static void main(String[] args) throws Exception{
        List<StorageInfo> storageInfos = new LinkedList<>();
        storageInfos.add(
                new StorageInfo("Node1", "127.0.0.1", 8001,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node2", "127.0.0.1", 8002,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node3", "127.0.0.1", 8003,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node4", "127.0.0.1", 8004,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node5", "127.0.0.1", 8005,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node6", "127.0.0.1", 8006,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );

        File file = new File("storageInfo.dat");
        try{
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(storageInfos);
            output.flush();
            output.close();
        }catch (IOException e) {
            System.out.println("StorageNode information input failed");
        }

        Map<String, FileInfo> fileInfoMap = new HashMap<>();
        File file1 = new File("fileInfo.dat");
        try{
            ObjectOutputStream output1 = new ObjectOutputStream(new FileOutputStream(file1));
            output1.writeObject(fileInfoMap);
            output1.flush();
            output1.close();
        }catch (IOException e) {
            System.out.println("File information input failed");
        }
    }
}
        /*Map<String, FileInfo> fileInfoMap = new HashMap<>();
        String UUID = "16a1eeef-c0ec-48a9-8b37-4a35a790a93e";
        fileInfoMap.put(UUID, new FileInfo(UUID, "test.txt", 14,
                "127.0.0.1", 8001, "127.0.0.1", 8002));
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("fileInfo.dat"));
        output.writeObject(fileInfoMap);
        output.close();*/
        /*
        List<StorageInfo> storageInfos = new LinkedList<>();
        storageInfos.add(
                new StorageInfo("Node1", "127.0.0.1", 8001,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node2", "127.0.0.1", 8002,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node3", "127.0.0.1", 8003,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node4", "127.0.0.1", 8004,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node5", "127.0.0.1", 8005,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node6", "127.0.0.1", 8006,
                        100*1024*1024*1024L, 100*1024*1024*1024L, 0, false
                )
        );*/

        /*File file = new File("storageinfo.dat");
        try{
            ObjectOutputStream output1 = new ObjectOutputStream(new FileOutputStream(file));
            output1.writeObject(storageInfos);
            output1.close();
        }catch (IOException e) {
            System.out.println("StorageNode information input failed");
        }*/
        /*File file1 = new File("fileInfo.dat");
        try
        {
            //执行反序列化读取
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file1));
            FileTransfer.fileInfoMap = (HashMap<String, FileInfo>)input.readObject();
            //System.out.println(FileTransfer.fileInfoMap.get("16a1eeef-c0ec-48a9-8b37-4a35a790a93e").fileName);
            input.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        /*
        List<StorageInfo> storageInfos = new LinkedList<>();
        storageInfos.add(
                new StorageInfo("Node1", "127.0.0.1", 8001,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node2", "127.0.0.1", 8002,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node3", "127.0.0.1", 8003,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node4", "127.0.0.1", 8004,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node5", "127.0.0.1", 8005,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );
        storageInfos.add(
                new StorageInfo("Node6", "127.0.0.1", 8006,
                        100*1024*1024*1024, 100*1024*1024*1024, 0, false
                )
        );

        File file = new File("storageinfo.dat");
        try{
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(storageInfos);
        }catch (IOException e) {
            System.out.println("StorageNode information input failed");
        }

        Collections.sort(storageInfos, Collections.reverseOrder());

        for (StorageInfo element : storageInfos) {
            System.out.println(element.nodeName);
        }*/

/*
//创建要序列化的集合对象
        File file = new File("object.adt");
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {

            List<TestObject> listObject = (List<TestObject>) out.readObject();
            for(int i = 0; i < listObject.size(); i++) {
                System.out.println(listObject.get(i).getName());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
 */

/*
        File file = new File("object.adt");
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {
            //执行反序列化读取
            TestObject[] obj = (TestObject[]) out.readObject();
            //将数组转换成List
            List<TestObject> listObject = Arrays.asList(obj);
            for(int i = 0; i < listObject.size(); i++) {
                System.out.println(listObject.get(i).getName());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
 */

