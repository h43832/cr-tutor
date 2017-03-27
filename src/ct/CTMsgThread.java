
package ct;

/**
 *
 * @author peter
 */
public class CTMsgThread extends Thread{
    boolean runThread=false,isSleep=true,reload=false;
  CTModerator moderator;
  public void init(CTModerator moderator){
    this.moderator=moderator;
  }
  public void run(){
    runThread=true;
    while(runThread){
      isSleep=false;
      try{

        isSleep=true;
        sleep(365L*24L*60L*60L*1000L);
      } catch(InterruptedException e){

      	}
    }
  }
}