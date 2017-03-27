
package ct;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;
import y.ende2003.d2003;
import y.ende2003.e2003;
import y.ylib.ylib;
public class CTUpdater extends Thread{
CTModerator moderator;
String inputVersionFile="temp_update"+File.separator+"u.txt",outputVersionFile="temp_update"+File.separator+"u.txt",
        code="jl2009",versionArr[]=null;
boolean encode=false,changeValue=false,isSleep=false;
int actionType=0;
String originalId="";
TreeMap versionsOnWeb=new TreeMap(),versionsOnDisk=new TreeMap();
public CTUpdater(CTModerator moderator){
  this.moderator=moderator;
}
  public void run(){
    while(true){
      isSleep=false;
      if(chkVersion()){

      }

      boolean old_status=moderator.hasNewVersion;
      if(new File("temp_update"+File.separator+"deploy").exists() && !new File("temp_update"+File.separator+"z").exists()) moderator.hasNewVersion=true;
      if(old_status != moderator.hasNewVersion) moderator.updateTitle();
      try{
        isSleep=true;
        sleep(10L * 60L * 1000L);
      } catch(InterruptedException e){
      }
    }
  }
  public boolean isSleep(){
    return isSleep;
  }
  boolean chkVersion(){
    boolean rtn=false,needDownload=false,alreadyFeedback=false;

    if(moderator.hasNewVersion){
      if(actionType==1) {

        moderator.appendStatus(moderator.bundle2.getString("CTModerator.xy.msg35")+"\r\n");
        alreadyFeedback=true;
      } else if(actionType==2){

        String cmd2="performmessage ct.CTModerator status  "+moderator.w.e642(moderator.w.getGNS(27)+" "+moderator.bundle2.getString("CTModerator.xy.msg36"));
       moderator.w.sendToOne(cmd2,originalId);
       alreadyFeedback=true;
      }
      actionType=0;

    }
    if(new File(inputVersionFile).exists()) {
      readVersionFromDisk();
    }

    String version=moderator.w.ap.urltomultiline("http://cloud-rain.com/web/crtutor_version.txt");

    if(version.length()>0){
    readVersionFromWeb(version);

    Iterator it=versionsOnWeb.keySet().iterator();
    for(;it.hasNext();){
      String key=(String)it.next();
      String[] tmpWeb=ylib.csvlinetoarray((String)versionsOnWeb.get(key));
      if(tmpWeb[0].equals(moderator.apId)){

        versionArr=tmpWeb;
        if(tmpWeb[5].compareTo(moderator.version)>0){

          if(versionsOnDisk.size()>0){
            Iterator it2=versionsOnDisk.keySet().iterator();
            for(;it2.hasNext();){
              String key2=(String)it2.next();
              String tmpDisk[]=ylib.csvlinetoarray((String)versionsOnDisk.get(key2));
              if(tmpDisk[0].equals(moderator.apId)){

                if(tmpWeb[5].compareTo(tmpDisk[5])>0) needDownload=true;
                else if(tmpWeb[5].equals(tmpDisk[5])){
                  if(moderator.w.checkOneVar(tmpDisk[13],4)) versionArr=tmpDisk;
                  else needDownload=true;
                }
                break;
              }
            }
          }   else {needDownload=true;}
        } else{
          if(actionType==1 && !alreadyFeedback) {

            moderator.appendStatus(moderator.bundle2.getString("CTModerator.xy.msg37")+"\r\n");
          } else if(actionType==2 && !alreadyFeedback){

            String cmd2="performmessage ct.CTModerator status "+moderator.w.e642(moderator.w.getGNS(27)+" "+moderator.bundle2.getString("CTModerator.xy.msg38"));
            moderator.w.sendToOne(cmd2,originalId);
          }
          actionType=0;
          return false;
        }
        break;
      }
    }

    if(needDownload){
      if(!new File("temp_update"+File.separator+"z").exists()) new File("temp_update"+File.separator+"z").mkdirs();

      versionsOnDisk.clear();
      it=versionsOnWeb.keySet().iterator();
      for(;it.hasNext();){
        String key=(String)it.next();
        versionsOnDisk.put(key, versionsOnWeb.get(key));
      }
      String files[]=ylib.csvlinetoarray(versionArr[6]);
      for(int i=0;i<files.length;i++){

        int inx=files[i].lastIndexOf("/");
        String fname=(inx>-1? files[i].substring(inx+1):files[i]);
        if(moderator.w.ap.urlfiletodisk((files[i].toLowerCase().indexOf("http")==0? files[i]:"http://cloud-rain.com/web/"+files[i]), ylib.replace(versionArr[9],"\\",File.separator)+File.separator+(files.length>1 ? fname:versionArr[10]))){
          versionArr[13]=moderator.w.addOneVar(versionArr[13], 4);
          versionsOnDisk.put(versionArr[0],ylib.arrayToCsvLine(versionArr));
          saveVersion();

          rtn=true;
        }
      }
      boolean unzipOK=false;
      for(int i=0;i<files.length;i++){
        int inx=files[i].lastIndexOf("/");
        String fname=(inx>-1? files[i].substring(inx+1):files[i]);
        unzipOK=false;
        if(!unzip("temp_update"+File.separator+"deploy", ylib.replace(versionArr[9],"\\",File.separator)+File.separator+(files.length>1 ? fname:versionArr[10]))) break;
        unzipOK=true;
       }
      if(unzipOK){

        if(f_deleteDir(new File(ylib.replace(versionArr[9],"\\",File.separator)))){
          versionArr[13]=moderator.w.addOneVar(versionArr[13], 5);
          versionsOnDisk.put(versionArr[0],ylib.arrayToCsvLine(versionArr));
          saveVersion();

        }
      }

     if(actionType==1 && !alreadyFeedback) {

       moderator.appendStatus(moderator.bundle2.getString("CTModerator.xy.msg35")+"\r\n");
     }  else if(actionType==2 && !alreadyFeedback){

            String cmd2="performmessage ct.CTModerator status "+moderator.w.e642(moderator.w.getGNS(27)+" "+moderator.bundle2.getString("CTModerator.xy.msg36"));
            moderator.w.sendToOne(cmd2,originalId);
      }
    }
    }
    actionType=0;
    return rtn;
  }
  public void setActionType(int type,String original_id){
    actionType=type;
    this.originalId=original_id;
    if(isSleep) interrupt();
  }

  public boolean f_deleteDir(File dir) {
    if (dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = f_deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
    return dir.delete(); 
}

  private boolean unzip(String outdir, String infile){

    String msg="";
    long status=0;
	 status=moderator.w.addOneVar(status,10);
	 boolean rtn=true;
	 int filen=0;
  	 try{
  	   File f3=new File(infile);
  	   if(f3.exists()){
  	    if(f3.length()>0){
  	    f3=new File(outdir);
  	    if(!f3.exists()) f3.mkdirs();
            ZipFile zip = new ZipFile(infile);
            Enumeration zipEnum = zip.entries();
             while (zipEnum.hasMoreElements()){
                ZipEntry item = (ZipEntry) zipEnum.nextElement();

                if (item.isDirectory()){
                    File newdir = new File(outdir + "/" + item.getName());
                    newdir.mkdir();
                }

                else {
                    String newfile = outdir + "/" + item.getName();
                    InputStream is = zip.getInputStream(item);
                    FileOutputStream fos = new FileOutputStream(newfile);
                    int ch;
                    while ((ch = is.read()) != -1){
                        fos.write(ch);
                    }
                    is.close();
                    fos.close();
                    filen++;
                }
            }
            zip.close();

           } else{
               msg="Info 031: The size of zipfile "+infile+" is zero.";

            System.out.println(msg);
			   rtn=false;
             }
           } else{
               msg="Info 032: zipfile "+infile+" does not exist.\r\n";

            System.out.println(msg);
			   rtn=false;
             }
        } catch (FileNotFoundException e) {
            msg="Info 034: "+e.getMessage()+", outdir="+outdir+",infile="+infile+".";

            System.out.println(msg);

            e.printStackTrace();
            rtn=false;
        } catch (IOException e) {
            msg="Info 035: "+e.getMessage()+", outdir="+outdir+",infile="+infile+".";

            System.out.println(msg);

            e.printStackTrace();
            rtn=false;
          }

        return rtn;
  }

  private void readVersionFromWeb(String versionStr){
      try{
        String str1="";
          StringReader sin=new StringReader(versionStr);
          BufferedReader d = new BufferedReader(sin);
          boolean firstLine=true;
          int nextB=1,nextA=1;
          while(true){
            str1=d.readLine();
            if(str1==null) {d.close(); break; }
            if(str1.length()>0){
	        if(str1.indexOf(",")==-1) {str1=d2003.de(code,str1); encode=true;}
	          else encode=false;
		    String temparray[]=ylib.csvlinetoarray(str1);
		    if(temparray.length>20) {
                      versionsOnWeb.put(temparray[0], str1);
                    }
            }
          }
      } catch (IOException e){e.printStackTrace();}

  }

  private void readVersionFromDisk(){
    String str1="",temparray[]=null;
    if(new File(inputVersionFile).exists()){
	  try{
  	    FileInputStream in=new FileInputStream(inputVersionFile);
	    BufferedReader d= new BufferedReader(new InputStreamReader(in));
	    while(true){
	      str1=d.readLine();
	      if(str1==null) {in.close(); d.close(); break; }
	      if(str1.length()>0){
	        if(str1.indexOf(",")==-1) {str1=d2003.de(code,str1); encode=true;}
	          else encode=false;
		    temparray=ylib.csvlinetoarray(str1);
		    if(temparray.length>20) {
		      if(temparray[20].equals("chkstr")) {
			      if(temparray[1].length()<1) {temparray[1]="0"; changeValue=true;}
			      if(temparray[3].length()<1) {temparray[3]="5"; changeValue=true;}
			      if(temparray[13].length()<1) {temparray[13]="0"; changeValue=true;}
			      if(moderator.w.checkOneVar(temparray[13],0)) temparray[11]=temparray[9];
			      if(changeValue) str1=ylib.arrayToCsvLine(temparray);
			      versionsOnDisk.put(temparray[0],str1);
		      }else {
			    System.out.println("Error:id="+temparray[0]+",name="+temparray[2]+" not a valid version item format, its [20] is \""+temparray[20]+"\".");
		      }
		    } else  System.out.println("Error:id='"+temparray[0]+"',name='"+(temparray.length>2? temparray[2]:"")+"' not a valid version item format, its length is "+temparray.length);
		  }
         }
    	}catch(IOException e){
			  System.out.println("Error: IOException, message: "+e.getMessage());
		      e.printStackTrace();
			}
     }  else {
          if(new File(inputVersionFile).getParentFile().exists()){
               System.out.println("Warning: version file not found, create a new one. (file name = "+ inputVersionFile+" )");
           } else {
               System.out.println("Error: \""+new File(inputVersionFile).getParent()+"\" dir does not exist, can add new version file \""+inputVersionFile+"\"");
           }

     }

  }
  

 

  private void saveVersion(){  
	StringBuffer sb=new StringBuffer();
    try{
      if(versionsOnDisk.size()>0){
        Iterator its=versionsOnDisk.values().iterator();
        for(;its.hasNext();){
          String str=(String)its.next();
          if(encode)  str=e2003.en("jl2009",str);
          if(str.length()>0) sb.append(str).append("\r\n");
        }
      }
      File f2=new File(outputVersionFile);
      FileOutputStream out = new FileOutputStream ( f2);
      byte [] b=sb.toString().getBytes();
      out.write(b);
      out.close();
      changeValue=false;
    } catch (IOException e){
          	e.printStackTrace(); 
          }
  }

}