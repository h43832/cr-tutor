package ct;

import java.awt.*;
import java.util.*;
import infinity.client.*;
public class CTMsgBlinker2 extends Thread {
	CTModerator moderator=null;

	boolean onoff=true;
	long nextTime=0,nowTime=0,sleepTime=500;
	public CTMsgBlinker2(CTModerator mBoard){

		this.moderator=mBoard;
	}
	public void run(){
          nextTime=System.currentTimeMillis();
	  while(true){
              nextTime=nextTime+sleepTime;
		try{
                   moderator.msg_setBlink(onoff);
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