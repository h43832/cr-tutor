
package ct;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.*;
import javax.imageio.ImageIO;
public class CTSldImgPanel extends javax.swing.JPanel {
  CTModerator moderator;
  Image img;
  BufferedImage bImg;
  int imgW=0,imgH=0;
  public int x0=0,x1=0,x2=0,x3=0,y0=0,y1=0,y2=0,y3=0;

  Vector dataV=null,laserDataV=null;
  int lastX,lastY;
  Color bgColor=Color.BLACK,originalBG=bgColor;
  /** Creates new form CTImgPanel */
  public CTSldImgPanel(CTModerator esb) {
    initComponents();
    this.moderator=esb;
  }

    public int getImgH(){
        return imgH;
    }
    public int getImgW(){
        return imgW;
    }
    public void setBImg(BufferedImage b){
        this.bImg=b;
        img=null;
        if(img==null && bImg==null) {imgW=0; imgH=0;}
        else {imgH=bImg.getHeight(null); imgW=bImg.getWidth(null);}
        repaint();
    }
    public void setImg(Image i){
        this.img=i;
        bImg=null;
        if(img==null && bImg==null) {imgW=0; imgH=0;}
        else {imgH=img.getHeight(null); imgW=img.getWidth(null); }
    }
    public void setImg2(Image i,Vector dataV,Vector laserDataV){
        this.dataV=dataV;
        this.laserDataV=laserDataV;
        setImg(i);
        repaint();
    }
    public void clearWriting(int inx){
        dataV=null;
        repaint();
    }
    public void setBG(Color co){
        bgColor=co;
        setImg2(null,null,null);
    }
    public void setDataV(Vector v){
        this.dataV=v;
        repaint();
    }
   public void setLaserDataV(Vector v){
        this.laserDataV=v;
        repaint();
    }
    public void resetBG(){
        bgColor=originalBG;
    }
    protected void paintComponent(Graphics g) {
        Insets insets = getInsets(); 
        Dimension d=getSize();
        Graphics2D g2=(Graphics2D) g;

        g.setColor(bgColor); 
        g.fillRect(0,0, insets.left + d.width, insets.top + d.height); 

        double h=(double)imgH,w=(double)imgW;

        double r1=((double)w)/((double)h);
        double r2=((double)d.width)/((double)d.height);
        int ah=0,aw=0; double h3=0.0,w3=0.0;
        int newX0=0,newY0=0,newW=0,newH=0;
        x3=d.width;
        y3=d.height;
        if(r1>r2){
            h3=(h/w*d.getWidth());
            ah= (int)(d.getHeight()/2.0-h3/2.0);
            if(img!=null) g.drawImage(img, insets.left,ah, d.width, (int)h3, this);
            else g.drawImage(bImg, insets.left,ah, d.width, (int)h3, this);
            newX0=insets.left; newY0=ah; newW=d.width; newH=(int)h3;
            x1=0; x2=x3; y1=ah; y2=y3-ah;
        } else {
            w3=(w/h*d.getHeight());
            aw= (int)(d.getWidth()/2.0-w3/2.0);
            if(img!=null) g.drawImage(img, aw,insets.top, (int)w3, d.height, this);
            else g.drawImage(bImg, aw,insets.top,(int)w3, d.height, this);
            newX0=aw; newY0=insets.top; newW= (int)w3; newH=d.height;
            y1=0; y2=y3; x1=aw; x2=x3-aw;
        }
    if(dataV!=null){
       g.setColor(Color.RED);
      for(int i=0;i<dataV.size();i++){
        CTSketchData data=(CTSketchData)dataV.get(i);
        int newX=getXFromRatio(data.values[1]);
        int newY=getYFromRatio(data.values[2]);
        switch(data.values[0]){
         case 1:
          if(data.values[4]==1) g.drawOval(data.values[1], data.values[2], 1, 1);
          else g.fillOval(newX-(int)(((double)data.values[4])/2.0), newY-(int)(((double)data.values[4])/2.0), data.values[4], data.values[4]);
          lastX = newX; lastY = newY;
          break;
        case 2:

          g2.setStroke(new BasicStroke(data.values[4]));
          g2.draw(new Line2D.Float(lastX, lastY, newX, newY));
          lastX = newX; lastY = newY;
          break;
      }
      }
    }
    if(laserDataV!=null){
       g.setColor(Color.RED);
      for(int i=0;i<laserDataV.size();i++){
        CTSketchData data=(CTSketchData)laserDataV.get(i);
        int newX=getXFromRatio(data.values[1]);
        int newY=getYFromRatio(data.values[2]);
          if(data.values[4]==1) g.drawOval(data.values[1], data.values[2], 1, 1);
          else g.fillOval(newX-(int)(((double)data.values[4])/2.0), newY-(int)(((double)data.values[4])/2.0), data.values[4], data.values[4]);

      }
    }
    }

public int getXRatio(int x0,int x1,int x2,int x3,int x){
    int xr=0;
    xr=(int)(((double)(x-x1))/((double)(x2-x1))*10000D);
    return xr;
}
public int getXRatio(int x){
    return getXRatio(x0,x1,x2,x3,x);
}

public int getYRatio(int y0,int y1,int y2,int y3,int y){
    return getXRatio(y0,y1,y2,y3,y);
}
public int getYRatio(int y){
    return getXRatio(y0,y1,y2,y3,y);
}
public int getXFromRatio(int x0,int x1,int x2,int x3,int xr){
    int x=0;
    x=x1+(int)(((double)(x2-x1))*(((double)(xr))/10000D));
    return x;
}
public int getXFromRatio(int xr){
    return getXFromRatio(x0,x1,x2,x3,xr);
}
public int getYFromRatio(int y0,int y1,int y2,int y3,int yr){
    return getXFromRatio(y0,y1,y2,y3,yr);
}
public int getYFromRatio(int yr){
    return getXFromRatio(y0,y1,y2,y3,yr);
}

    void fillOval(Graphics g,int x,int y,int a,int newX0,int newY0,int newW,int newH){
        int cSize=((int)(((double)a)/((double)(img!=null? imgW*imgH:bImg.getWidth()*bImg.getHeight())) * 100.0))*10;

        if(cSize>100) cSize=100;
        if(cSize<5) cSize=5;
        int cLine=(int)(((double)cSize)/10.0);
        if(cLine<1) cLine=1;

        cSize=(int)(((double)cSize)*((double) (newW*newH))/((double)(img!=null? imgW*imgH:bImg.getWidth()*bImg.getHeight())));
        if(cSize>100) cSize=100;
        if(cSize<5) cSize=5;
        cLine=(int)(((double)cSize)/10.0);
        if(cLine<1) cLine=1;

        int newX=newX0+(int)(((double)(x*newW))/((double)(img!=null? imgW:bImg.getWidth()))),newY=newY0+(int)(((double)(y*newW))/((double)(img!=null? imgW:bImg.getWidth())));
        for(int i=0;i<cLine;i++){
          g.fillOval((int)((double)newX-((double)cSize)/2.0), (int)((double)newY-((double)cSize)/2.0), cSize-i, cSize-i);
        }
    }

    void drawOval(Graphics g,int x,int y,int a,int newX0,int newY0,int newW,int newH){
        int cSize=((int)(((double)a)/((double)(img!=null? imgW*imgH:bImg.getWidth()*bImg.getHeight())) * 100.0))*10;

        if(cSize>100) cSize=100;
        if(cSize<5) cSize=5;
        int cLine=(int)(((double)cSize)/10.0);
        if(cLine<1) cLine=1;

        cSize=(int)(((double)cSize)*((double) (newW*newH))/((double)(img!=null? imgW*imgH:bImg.getWidth()*bImg.getHeight())));
        if(cSize>100) cSize=100;
        if(cSize<5) cSize=5;
        cLine=(int)(((double)cSize)/10.0);
        if(cLine<1) cLine=1;

        int newX=newX0+(int)(((double)(x*newW))/((double)(img!=null? imgW:bImg.getWidth()))),newY=newY0+(int)(((double)(y*newW))/((double)(img!=null? imgW:bImg.getWidth())));
        for(int i=0;i<cLine;i++){
          g.drawOval((int)((double)newX-((double)cSize)/2.0), (int)((double)newY-((double)cSize)/2.0), cSize-i, cSize-i);
        }
    }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        formMouseClicked(evt);
      }
      public void mousePressed(java.awt.event.MouseEvent evt) {
        formMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        formMouseReleased(evt);
      }
    });
    addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        formMouseDragged(evt);
      }
    });
    addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        formKeyReleased(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 300, Short.MAX_VALUE)
    );
  }

  private void formMouseDragged(java.awt.event.MouseEvent evt) {

    if (SwingUtilities.isLeftMouseButton(evt)){
    if((moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)) && moderator.w.checkOneVar(moderator.auOne_asAMember, 11)){
      if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_laserDataV.clear();
           moderator.sld_laserDataV.add(new CTSketchData(1,getXRatio(evt.getX()),getYRatio(evt.getY()),0,15,Color.RED.getRGB()));
           moderator.sld_imgPanel.setLaserDataV(moderator.sld_laserDataV);

             String cmd="performcommand ct.CTModerator sld_laser "+moderator.sld_currentFN+" "+getXRatio(evt.getX())+" "+getYRatio(evt.getY())+" 15 "+Color.RED.getRGB();
           String to=(String) moderator.cbb_sldMList.getSelectedItem();
         if(moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)){

         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              } 

        }
      }else if((moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)) && moderator.w.checkOneVar(moderator.auOne_asAMember, 12)){
         if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_dataV=(Vector)moderator.sld_dataVs.get(moderator.sld_currentFN);
           moderator.sld_dataV.add(new CTSketchData(2,getXRatio(evt.getX()),getYRatio(evt.getY()),0,5,Color.RED.getRGB()));
           moderator.sld_dataVs.put(moderator.sld_currentFN, moderator.sld_dataV);
           moderator.sld_imgPanel.setDataV(moderator.sld_dataV);

             String cmd="performcommand ct.CTModerator sld_writing "+moderator.sld_currentFN+" 2 "+getXRatio(evt.getX())+" "+getYRatio(evt.getY())+" 5 "+Color.RED.getRGB();
           String to=(String) moderator.cbb_sldMList.getSelectedItem();
         if(moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)){

         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              } 

         }
   } 
  }
  }

  private void formMouseClicked(java.awt.event.MouseEvent evt) {

    if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) {
      if (evt.getButton()==evt.BUTTON3) {
          moderator.sld_popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
     } else if(moderator.w.checkOneVar(moderator.auOne_asAMember, 11)){
      if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_laserDataV.clear();
           moderator.sld_laserDataV.add(new CTSketchData(1,getXRatio(evt.getX()),getYRatio(evt.getY()),0,15,Color.RED.getRGB()));
           moderator.sld_imgPanel.setLaserDataV(moderator.sld_laserDataV);

             String cmd="performcommand ct.CTModerator sld_laser "+moderator.sld_currentFN+" "+getXRatio(evt.getX())+" "+getYRatio(evt.getY())+" 15 "+Color.RED.getRGB();
           String to=(String) moderator.cbb_sldMList.getSelectedItem();
         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              }
      }else if(moderator.w.checkOneVar(moderator.auOne_asAMember, 12)){
         if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_dataV=(Vector)moderator.sld_dataVs.get(moderator.sld_currentFN);
           moderator.sld_dataV.add(new CTSketchData(2,getXRatio(evt.getX()),getYRatio(evt.getY()),0,5,Color.RED.getRGB()));
           moderator.sld_dataVs.put(moderator.sld_currentFN, moderator.sld_dataV);
           moderator.sld_imgPanel.setDataV(moderator.sld_dataV);

             String cmd="performcommand ct.CTModerator sld_writing "+moderator.sld_currentFN+" 2 "+getXRatio(evt.getX())+" "+getYRatio(evt.getY())+" 5 "+Color.RED.getRGB();
           String to=(String) moderator.cbb_sldMList.getSelectedItem();
         if(moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)){
         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              }

         }
    } else moderator.sld_gotoSlide(-2,true);
   }
  }

  private void formKeyReleased(java.awt.event.KeyEvent evt) {
    int keycode=evt.getKeyCode();

  switch(keycode){
    case 27:
      moderator.sld_inputNo="";
      break;
    case 38:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(-1,true);
      moderator.sld_inputNo="";
      break;
    case 40:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(-2,true);
      moderator.sld_inputNo="";
      break;
    case 37:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(-1,true);
      moderator.sld_inputNo="";
      break;
    case 39:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.tutorMode) moderator.sld_gotoSlide(-2,true);
      moderator.sld_inputNo="";
      break;
    case 48:
    case 49:
    case 50:
    case 51:
    case 52:
    case 53:
    case 54:
    case 55:
    case 56:
    case 57:
      moderator.sld_inputNo=moderator.sld_inputNo+(keycode-48);
      break;
    case 33:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(-1,true);
      moderator.sld_inputNo="";
      break;
    case 34:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(-2,true);
      moderator.sld_inputNo="";
      break;
    case 36:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(0,true);
      moderator.sld_inputNo="";
      break;
    case 35:

      if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(-3,true);
      moderator.sld_inputNo="";
      break;
    case 10:
      if(moderator.sld_inputNo.length()>0){
        int inx2=Integer.parseInt(moderator.sld_inputNo);
        if(inx2>0) inx2--;

        if(moderator.w.checkOneVar(moderator.auOne_asAMember, 2) ||  moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId"))) moderator.sld_gotoSlide(inx2,true);
      }
      moderator.sld_inputNo="";
      break;
    default:
      moderator.sld_inputNo="";
      break;
  }
  }

  private void formMousePressed(java.awt.event.MouseEvent evt) {
    if (evt.getButton()!=evt.BUTTON3) {
    if(moderator.w.checkOneVar(moderator.auOne_asAMember, 11)){
      if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_laserDataV.clear();
           moderator.sld_laserDataV.add(new CTSketchData(1,getXRatio(evt.getX()),getYRatio(evt.getY()),0,5,Color.RED.getRGB()));
           moderator.sld_imgPanel.setLaserDataV(moderator.sld_laserDataV);

             String cmd="performcommand ct.CTModerator sld_laser "+moderator.sld_currentFN+" "+getXRatio(evt.getX())+" "+getYRatio(evt.getY())+" 5 "+Color.RED.getRGB();
           String to=(String) moderator.cbb_sldMList.getSelectedItem();
         if(moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)){

         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              } 

        }
      }else if(moderator.w.checkOneVar(moderator.auOne_asAMember, 12)){
         if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_dataV=(Vector)moderator.sld_dataVs.get(moderator.sld_currentFN);
           moderator.sld_dataV.add(new CTSketchData(1,getXRatio(evt.getX()),getYRatio(evt.getY()),0,5,Color.RED.getRGB()));
           moderator.sld_dataVs.put(moderator.sld_currentFN, moderator.sld_dataV);
           moderator.sld_imgPanel.setDataV(moderator.sld_dataV);

             String cmd="performcommand ct.CTModerator sld_writing "+moderator.sld_currentFN+" 1 "+getXRatio(evt.getX())+" "+getYRatio(evt.getY())+" 5 "+Color.RED.getRGB();

             if(moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)){
           String to=(String) moderator.cbb_sldMList.getSelectedItem();

         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              } 

              } 

         }
  }
  }

  private void formMouseReleased(java.awt.event.MouseEvent evt) {
    if((moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)) && moderator.w.checkOneVar(moderator.auOne_asAMember, 11)){
      if(moderator.sld_currentFN!=null && moderator.sld_currentFN.length()>0){
           moderator.sld_laserDataV.clear();
           moderator.sld_imgPanel.setLaserDataV(null);

             String cmd="performcommand ct.CTModerator sld_laseroff "+moderator.sld_currentFN;
           String to=(String) moderator.cbb_sldMList.getSelectedItem();
         if(moderator.w.getGNS(1).equals(moderator.w.getAHVar("moderatorId")) || moderator.w.checkOneVar(moderator.auOne_asAMember, 2)){

         if(to==null && to.length()<1) return;
    else if(to.equals(moderator.allNodesName))  moderator.w.sendToGroupExceptMyself(cmd, moderator.w.getGNS(11));
    else if(to.equals(moderator.myNodeName)) moderator.w.sendToOne(cmd, moderator.w.getGNS(1));
    else moderator.w.sendToOneInGroup(cmd, moderator.w.getGNS(11), (String)moderator.nameIdMap.get(to));
              }

        }
      }else if(moderator.w.checkOneVar(moderator.auOne_asAMember, 12)){
   } 
  }

  public void saveImage(String filename){
     try {

       if(filename.indexOf(".")>0){
         File outputfile = new File(filename);
         BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        this.print(img.getGraphics()); 
         ImageIO.write(img, "png", outputfile);

       } else {
           System.out.println("filename "+filename+" is not a valid image filename (no extension name).");
       }
      } catch (IOException e) {
         e.printStackTrace();
      }
  }

}