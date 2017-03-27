
package ct;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Administrator
 */
public class CTFBRemoteDirNode extends DefaultMutableTreeNode {

    protected CTFBRemoteFile           file     = null;

    protected CTFBRemoteDirBrowser    browser  = null;
    CTModerator moderator;
    public CTFBRemoteDirNode(String name,CTModerator fb){
        super(name);

        this.moderator=fb;
        this.file=new CTFBRemoteFile(this,0,name);
    }

    public void init(CTFBRemoteDirBrowser browser){
        this.browser=browser;
    }
        /**
     * Child Constructor
     * @param browser the FileBrowser
     * @param parent the parent Node
     * @param file the encapsuled file
     * @param filter the Filename filter
     * Q.此與internal constructor有何不同?=>（internal constructor是指C:\等node,而child node是指更下層的node）
     */

    public CTFBRemoteDirNode(CTFBRemoteDirBrowser browser,String name,CTModerator fb)  {
	super(name);
        this.browser = browser;
	this.file    = new CTFBRemoteFile(this,System.currentTimeMillis(),name);
        file.node=this;

        this.moderator=fb;
    }
public void setFile(CTFBRemoteFile f){
  this.file=f;
  file.node=this;
}
          /**
     * Internal Constructor
     * @param parent the parent Node
     * @param file the encapsuled file
     * @param filter the Filename filter
     * Q.此與child constructor有何不同?=>（此是指C:\等node,而child node是指更下層的node）
     */

    

  public CTFBRemoteFile getFile(){
      return file;
  }
     /**
     # Returns true if the receiver is a leaf.
     Q.作用是什麼?
       =>if (node.isLeaf()){
          // do child stuff
         } else {
            // do parent stuff
           }
     */
      @Override 
    public boolean isLeaf() {
	return (parent == null ? false : file.isFile());
    }

    public void nodeWillExpand() {

       if(file.getAbsolutePath().length()>0 && !this.equals(moderator.f_rdb.root)){

                    moderator.f_currentRemoteNode=this;
                    moderator.f_current_remote_absolutePath=file.getAbsolutePath();

                    long now=System.currentTimeMillis();

                    if(!moderator.f_lastGetRemoteDir.equals(file.getAbsolutePath()) || moderator.lastGetDir_time+1000L <now){
                    String cmd="";
                    moderator.f_currentRandom=""+Math.random();

                    cmd="performcommand ct.CTModerator f_getdirandfile "+moderator.w.e642(file.getAbsolutePath())+" "+moderator.f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
                    moderator.w.sendToOne(cmd, browser.infinityNodeId);
                    moderator.f_lastGetRemoteDir=file.getAbsolutePath();
                    moderator.f_lastGetRemoteNode=moderator.f_currentRemoteNode;
                    moderator.lastGetDir_time=System.currentTimeMillis();
                    }

       }
    }

    /**
     * Invoked whenever this node is about to be collapsed.
     */

    public void nodeWillCollapse() {

       if(file.getAbsolutePath().length()>0){
                  if(file.tmFile==null){
                    

                  } 
                 }
    }
}