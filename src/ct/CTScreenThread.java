
package ct;

/**
 *
 * @author peter
 */
public class CTScreenThread extends Thread{
  CTModerator moderator;
  public void init(CTModerator moderator){
    this.moderator=moderator;
  }
  public void run(){
        boolean onoff=true;
	long nextTime=0,nowTime=0,sleepTime=500;
          nextTime=System.currentTimeMillis();
	  while(true){
              nextTime=nextTime+sleepTime;
		try{
                    if(moderator.srn_hasNewValue) {
                      moderator.srn_hasNewValue=false;
                      moderator.sPanel.setImages(moderator.srn_imgMap);
                    }
		    if(onoff) onoff=false;
			else onoff=true;
                     nowTime=System.currentTimeMillis();
		     if(nextTime-nowTime>0) Thread.sleep(nextTime-nowTime);
                     else nextTime=nowTime;
		} catch (Exception e){
                     e.printStackTrace();
		  }
	  }

  }
}