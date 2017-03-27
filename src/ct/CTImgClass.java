
package ct;
import java.awt.*;
  public class CTImgClass{
    public int inx=-1;
    public String nodeId="",nodeName="",randomCode="";

    public Image img;
    public CTImgClass(int inx,Image img,String nodeId,String nodeName,String randomCode){
      this.inx=inx;
      this.img=img;
      this.nodeId=nodeId;
      this.nodeName=nodeName;

      this.randomCode=randomCode;
    }
  }