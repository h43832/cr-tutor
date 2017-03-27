
package ct;

import java.io.FilenameFilter;

import java.util.*;
import java.io.*;
public class CTFBRemoteFile {
    boolean isfile=false;
    String name="";

    long modifiedTime=0,size=0;
    CTFBRemoteDirNode node;
    public TreeMap tmFile;

    public CTFBRemoteFile(CTFBRemoteDirNode node,long modified,String name){

        this.node=node;
        this.name=name;
        this.modifiedTime=modified;
        isfile=false;
    }
    public CTFBRemoteFile(CTFBRemoteFile file,String name){
        this.name=name;

        this.isfile=file.isFile();

        this.modifiedTime=file.lastModified();
        this.size=file.length();

    }

    public CTFBRemoteFile(String name,long modified,long len){
        this.name=name;

        this.isfile=true;

        this.modifiedTime=modified;
        this.size=len;

    }
    public int compareTo(CTFBRemoteFile file){
      return name.compareTo(file.getName());   
    }
    public boolean renameTo(CTFBRemoteFile newfile){
        this.name=newfile.getName();

        return true;
    }
    public String getAbsolutePath(){

      if(node!=null) return node.moderator.f_rdb.getAbsolutePath(node);
      else return "";
    }
    public String getName(){
        return name;
    }

    

    public long lastModified(){
        return modifiedTime;
    }
    public long length(){
        return size;
    }
    public boolean delete(){
        return true;
    }
    public boolean mkdirs(){
        return true;
    }
    public boolean exists(){
        return true;
    }
    public boolean isFile(){
        return isfile;
    }
    public boolean isDirectory(){
        return !isfile;
    }
}