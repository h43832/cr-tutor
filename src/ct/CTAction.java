
package ct;

/**
 *
 * @author peter
 */
public class CTAction {
  String originalId="";
  int mode0=0,actionType=0;
  String stringx[];
  String data="";
  Object obj=null;
  public CTAction(String original,int mode,int type,String str[],Object o,String data2){
    originalId=original;
    mode0=mode;
    actionType=type;
    data=data2;
    stringx=str;
    obj=o;

  }
}