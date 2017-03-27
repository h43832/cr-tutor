
package ct;
import java.io.*;
public class CTUF {
  String msg="";
  long copyN=0,copySize=0;
  public static void main(String args[]){
    CTUF uf=new CTUF();
    uf.updateFiles();
  }
  public void updateFiles(){
    String d="temp_update"+File.separator+"deploy";
    File dir=new File(d);
    if(dir.exists()){
      moveTo(d,"");
      dir=new File("temp_update");
      f_deleteDir(dir);
    }
  }

  private boolean moveTo(String sourceDir,String targetDir){
    if(targetDir.trim().length()==0) targetDir=System.getProperty("user.dir");
    String tmpFileName="";
    boolean rtn=true;
              try {
                File[] files = new File(sourceDir).listFiles();
                for( int k=0; k<files.length; k++ ) {
                  tmpFileName=files[k].getName();
                  try{
                    if(files[ k ].isDirectory()){
                      rtn=moveTo(files[ k ].getAbsolutePath(),targetDir+ (targetDir.endsWith(File.separator)? "":File.separator) +tmpFileName);
                    } else {
                              File f1=new File(targetDir + (targetDir.endsWith(File.separator)? "":File.separator) + tmpFileName);
                              File file1 =new File (targetDir+((targetDir.length()==2 && targetDir.indexOf(":")==1)? "\\":""));

                              if(!file1.exists()){
                              	if(!file1.mkdirs()) {
	                              	System.out.println("mkdirs "+file1.getAbsolutePath()+" failed!");
	                              	rtn=false;
                             }

                              }
                              file1 = new File( targetDir + File.separator + tmpFileName );

                              if(copyFile(files[k],file1)){
                                   copyN++;
                                   copySize+=files[k].length();
                                   files[k].delete();

                              }
                    }
                  }catch(IOException e){
                     msg="error localCopyDir(\""+sourceDir+"\",\""+targetDir+"\")-IOException:"+e.getMessage()+"\r\n";
                     rtn=false;
               	     e.printStackTrace(); 
                   }
                }
              } catch( Exception e ) {
                    msg="error localCopyDir(\""+sourceDir+"\",\""+targetDir+"\")-Exception:"+e.getMessage()+"\r\n";
                    rtn=false;
              	    e.printStackTrace(); 
              	  }
     return rtn;
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
  private boolean copyFile(File f1, File f2) throws IOException{
    try{

        long len = f1.length();
        FileInputStream fis = new FileInputStream(f1);
        FileOutputStream fos = new FileOutputStream(f2);
        byte[] bytes = new byte[1024*4];
        int count = fis.read(bytes);
        while(count>0){
          fos.write(bytes,0,count);
          count = fis.read(bytes);
        }
        fis.close();
        fos.close();
        f2.setLastModified( f1.lastModified() );
        return true;

    }  catch(IOException e){
    	 e.printStackTrace();
         throw new IOException("copyFile(\""+f1.getAbsolutePath()+"\",\""+f2.getAbsolutePath()+"\"):"+e.getMessage());
      }

  }

}