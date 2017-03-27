
package ct;

public class CTSketchData {
  public int values[];
  public CTSketchData(int type,int x,int y,int z,int width,int lineColor){

    values=new int[]{type, x,y,z,width,lineColor};
  }
    public CTSketchData(int values[]){

    this.values=values;
  }
}