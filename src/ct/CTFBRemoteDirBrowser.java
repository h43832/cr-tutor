
package ct;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Administrator
 */
public class CTFBRemoteDirBrowser extends JTree{
    CTModerator moderator;
    CTFBRemoteDirNode  root;
    private Frame   frame     = null;
    public String infinityNodeId="";
    public final String DELETE_CMD = "delete";
    ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("ct/Bundle");
    public CTFBRemoteDirBrowser(CTFBRemoteDirNode root,CTModerator fb){
        this.root=root;
        this.moderator=fb;
        this.root.init(this);

        setRootVisible(true);

	setEditable(true);
	setLargeModel(true);
	setScrollsOnExpand(true);
	addTreeWillExpandListener(twel);
        KeyStroke delK = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
	registerKeyboardAction(al, DELETE_CMD, delK, WHEN_FOCUSED);
	addMouseListener(mouseAdapter);
        addTreeSelectionListener(new treeSelListener());
    }
class treeSelListener implements TreeSelectionListener{
        @Override

         public void valueChanged(TreeSelectionEvent e) {

          CTFBRemoteDirNode node = (CTFBRemoteDirNode) getLastSelectedPathComponent();

        if (node == null) return;

        moderator.f_currentSelectedPath2=e.getPaths();

    }
  }

    CTFBRemoteDirNode getNodeFromNodeAndAbsPath(CTFBRemoteDirNode node,String absPath){
        boolean found=false;
        CTFBRemoteDirNode rtn=null;
        if(node!=null){
        CTFBRemoteFile f2=node.getFile();
        if(f2!=null && f2.getAbsolutePath().equalsIgnoreCase(absPath)) return node;
        Enumeration en=node.children();
        while(en.hasMoreElements()){
            node=(CTFBRemoteDirNode)en.nextElement();
            f2=node.getFile();
            if(f2!=null && f2.getAbsolutePath().equalsIgnoreCase(absPath)) return node;
        }
      }
        return rtn;
    }
    CTFBRemoteDirNode getNodeFromAbsPath(String absPath){
        return getNodeFromNodeAndAbsPath2(root,absPath);
    }

    CTFBRemoteDirNode getNodeFromNodeAndAbsPath2(CTFBRemoteDirNode upperNode,String absPath){
        boolean found=false;
        CTFBRemoteDirNode rtn=null;
          if(upperNode.getFile().getAbsolutePath().equals(absPath)){
            rtn=upperNode;
            found=true;
          }
          if(!found && upperNode.getChildCount()>0){

              for(int j=0;j<upperNode.getChildCount();j++){
                rtn=getNodeFromNodeAndAbsPath2((CTFBRemoteDirNode)upperNode.getChildAt(j),absPath);
               if(rtn!=null){
                found=true;
                break;
               }
             }

          } 
        return rtn;
    }

    /**
     * Our TreeWillExpandListener
     */
    TreeWillExpandListener twel = new TreeWillExpandListener() {

	public void treeWillExpand(TreeExpansionEvent event)
	throws ExpandVetoException{

	   TreeNode node = (TreeNode)event.getPath().getLastPathComponent();
	    ((CTFBRemoteDirNode)node).nodeWillExpand();
	    ((DefaultTreeModel)getModel()).reload(node);
	}

	public synchronized void treeWillCollapse(TreeExpansionEvent event)
	throws ExpandVetoException
	{

	    TreeNode node = (TreeNode)event.getPath().getLastPathComponent();
	    ((CTFBRemoteDirNode)node).nodeWillCollapse();
	    ((DefaultTreeModel)getModel()).reload(node);
	}
    };

    public static CTFBRemoteDirBrowser getRemoteDirBrowser(String rootname,CTModerator fb) {

	CTFBRemoteDirNode root = new CTFBRemoteDirNode(rootname,fb);
	CTFBRemoteDirBrowser browser = new CTFBRemoteDirBrowser(root,fb);
        DefaultTreeModel model = (DefaultTreeModel)browser.getModel();
        model.setRoot(root);

	return browser;
    }
        protected void createNewFolder() {
          

     CTFBRemoteDirNode node = (CTFBRemoteDirNode)getLastSelectedPathComponent();
     CTFBRemoteFile rf=node.file;
     int i=0;
     String newDirName="NewFolder";
     Iterator it=rf.tmFile.values().iterator();
     for(;it.hasNext();){
       CTFBRemoteFile file=(CTFBRemoteFile)it.next();
       if(file.name.equals(newDirName)) newDirName="NewFolder"+(++i);
     }
     if(new File(rf.getAbsolutePath())!=null){
      if(moderator.f_rename2==null) moderator.f_rename2=new CTFBRename(moderator,true);
        moderator.f_rename2.setValue(rf.getAbsolutePath(), newDirName, 4,1);
        moderator.f_rename2.setVisible(true);
     }
    }
   /**
     * Filter the TreePath array. Remove all nodes that have one of their
     * parent in this array.
     * @param paths the TreePath array
     * @return the filtered array
     */
    protected TreePath[] removeDescendants(TreePath[] paths) {
	if (paths == null)
	    return null;
	Vector newpaths = new Vector();
	for (int i = 0 ; i < paths.length ; i++) {
	    TreePath currentp = paths[i];
	    boolean hasParent = false;
	    for (int j = 0 ; j < paths.length ; j++) {
		if ((!(j == i)) && (paths[j].isDescendant(currentp)))
		    hasParent = true;
	    }
	    if (! hasParent)
		newpaths.addElement(currentp);
	}
	TreePath[] filteredPath = new TreePath[newpaths.size()];
	newpaths.copyInto(filteredPath);
	return filteredPath;
    }
    public void setCursor(int cursor) {
	getFrame().setCursor(Cursor.getPredefinedCursor(cursor));
    }
        protected Frame getFrame() {
	if (frame == null) {
	    frame = 
		(Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
	}
	return frame;
    }
    protected void deleteSelectedFiles() {
      

        int an=JOptionPane.showConfirmDialog(null, bundle2.getString("CTModerator.xy.msg50"), bundle2.getString("CTModerator.xy.msg49"), JOptionPane.YES_NO_CANCEL_OPTION);
        if(an==JOptionPane.YES_OPTION){
          TreeMap deleteTM=new TreeMap();
        TreePath[] tp= removeDescendants(getSelectionPaths());
        for(int i=0;i<tp.length;i++){
          CTFBRemoteDirNode fnode=(CTFBRemoteDirNode)tp[i].getLastPathComponent();
          String abs=this.getAbsolutePath(fnode);
          if(!moderator.f_isRoot(abs)) deleteTM.put(abs, "1");
        }
          if(deleteTM.size()>0) moderator.f_deleteRemote(deleteTM);
        }
    }
    protected void download() {
      

        TreeMap downloadTM=new TreeMap();
        TreePath[] tp= removeDescendants(getSelectionPaths());
        for(int i=0;i<tp.length;i++){
          CTFBRemoteDirNode fnode=(CTFBRemoteDirNode)tp[i].getLastPathComponent();
          String abs=this.getAbsolutePath(fnode);
          downloadTM.put(abs, "1");
        }
           moderator.f_currentRandom=""+Math.random();

           moderator.f_download(downloadTM);
    }

    /**
     * Get the popup menu relative to the selected resource.
     * @param file the selected file
     * @return a JPopupMenu instance
     */
    protected JPopupMenu getPopupMenu(CTFBRemoteDirNode node) {
	JPopupMenu popupMenu = new JPopupMenu("Actions");
	JMenuItem  menuItem  = null;

	String name = (String)node.getUserObject();

	if (getSelectionCount() > 1) {
            menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg19"));
            menuItem.addActionListener(pmal);
            menuItem.setActionCommand("download");
	    popupMenu.add(menuItem);
	    popupMenu.addSeparator();
	    menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg18"));
	    menuItem.addActionListener(pmal);
	    menuItem.setActionCommand("del");
	    if(moderator.tutorMode || moderator.w.checkOneVar(moderator.auOne_asAMember, 13)) popupMenu.add(menuItem);

	} else {
	    CTFBRemoteFile file = node.getFile();
	    if (file != null) {
		StringBuffer descr = new StringBuffer(name);
                if(file.lastModified()>0){
		Date date = new Date(file.lastModified());
		descr.append(", ");
		descr.append(moderator.format1.format(date));
                }
		if (file.isFile()) {
		    long size = file.length();
		    String s = null;
		    if (size > 1023) {
			s = " ["+(size/1024)+" Kb]";
		    } else {
			s = " ["+size+" bytes]";
		    }
		    descr.append(s);
		}

		menuItem = new JMenuItem(descr.toString());
		menuItem.addActionListener(pmal);
		menuItem.setActionCommand("info");
		popupMenu.add(menuItem);

		popupMenu.addSeparator();
		menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg19"));
		menuItem.addActionListener(pmal);
      		menuItem.setActionCommand("download");
		popupMenu.add(menuItem);
		popupMenu.addSeparator();

		if (file.isDirectory() && (getSelectionCount() == 1)) {
		    menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg16"));
		    menuItem.addActionListener(pmal);
		    menuItem.setActionCommand("newfolder");
		    if(moderator.tutorMode || moderator.w.checkOneVar(moderator.auOne_asAMember, 13)) popupMenu.add(menuItem);
		}
                popupMenu.addSeparator();
		menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg18"));
		menuItem.addActionListener(pmal);
		menuItem.setActionCommand("del");
		if(moderator.tutorMode || moderator.w.checkOneVar(moderator.auOne_asAMember, 13)) popupMenu.add(menuItem);
                menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg17"));
		menuItem.addActionListener(pmal);
		menuItem.setActionCommand("rename");
                if(moderator.tutorMode || moderator.w.checkOneVar(moderator.auOne_asAMember, 13)) popupMenu.add(menuItem);
	    } else {
		 menuItem = new JMenuItem(name);
		 popupMenu.add(menuItem);
	    }
	}
	return popupMenu;
    }
        /**
     * The popup menu action listener.
     */
    ActionListener pmal = new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
	    setCursor(Cursor.WAIT_CURSOR);
	    String command = evt.getActionCommand();
	    if (command.equals("del")) {
		deleteSelectedFiles();
	    } else if (command.equals("download")) {
		download();
	    } else if (command.equals("newfolder")) {
		createNewFolder();
	    } else if (command.equals("rename")) {
		renameDir();
	    }
	    setCursor(Cursor.DEFAULT_CURSOR);
	}
    };

    /**
     * Our ActionListener
     */
    ActionListener al = new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
	    String command = evt.getActionCommand();
	    if (command.equals(DELETE_CMD)) {
		deleteSelectedFiles();
	    }
	}
    };

    /**
     * Our MouseListener
     */
    MouseAdapter mouseAdapter = new MouseAdapter() {

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	    maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {

           if(SwingUtilities.isRightMouseButton(e)){
              int selRow = getRowForLocation(e.getX(), e.getY());
              TreePath selPath = getPathForLocation(e.getX(), e.getY());
              TreePath tp[]=getSelectionModel().getSelectionPaths();
              if (selRow>-1){
                boolean selected=false;
                if(tp!=null && tp.length>0){
                  for(int i=0;i<tp.length;i++){
                      if(tp[i].equals(selPath)){selected=true; break;}
                  }
                }
                if(!selected){
                    setSelectionPath(selPath); 
                    setSelectionRow(selRow); 
                }
              }
               

           }
            CTFBRemoteDirNode node = (CTFBRemoteDirNode)getLastSelectedPathComponent();

            DefaultTreeModel model2 = (DefaultTreeModel)getModel();
	    if (SwingUtilities.isRightMouseButton(e)){
		if (node != null && !node.equals(root))   getPopupMenu(node).show(e.getComponent(), e.getX(), e.getY());
	    } else if(e.getClickCount()==1){
                if (node != null && !node.equals(root)){
		  String absolutePath=getAbsolutePath(node);
                  if(absolutePath.indexOf(moderator.f_remoteFileSeparator)>-1){

                  DefaultComboBoxModel model = (DefaultComboBoxModel)moderator.cbb_fList2.getModel();
                  if(model.getIndexOf(absolutePath) == -1 ) {
                     moderator.f_skipComboBox2ItemStateChanged=true;
                     model.addElement(absolutePath);
                     moderator.f_skipComboBox2ItemStateChanged=false;
                  }

                  moderator.f_skipComboBox2ItemStateChanged=true;
                  moderator.cbb_fList2.setSelectedItem(absolutePath);
                  moderator.f_skipComboBox2ItemStateChanged=false;
                  moderator.f_currentRemoteNode=node;
                  moderator.f_current_remote_absolutePath=absolutePath;

               if(absolutePath.length()>0){
               if(!moderator.f_lastGetRemoteDir.equals(absolutePath) || moderator.lastGetDir_time+1000L <System.currentTimeMillis()){
               String cmd="";
               moderator.f_currentRandom=""+Math.random();

               cmd="performcommand ct.CTModerator f_getdirandfile "+moderator.w.e642(absolutePath)+" "+moderator.f_currentRandom+" 0 0 0 0  0 0 0 0 0 ";
               moderator.w.sendToOne(cmd, infinityNodeId);
               moderator.f_lastGetRemoteDir=absolutePath;
               moderator.f_lastGetRemoteNode=node;
               moderator.lastGetDir_time=System.currentTimeMillis();
               }
            }
          }
	}
        }
        }
    };
  String getAbsolutePath(CTFBRemoteDirNode node){

            String abs=(String)node.getUserObject();
            CTFBRemoteDirNode absNode=node;
            while(absNode.getParent()!=null){
              absNode=(CTFBRemoteDirNode)absNode.getParent();
              if(absNode.getParent()==null) break;
              abs=(String)absNode.getUserObject()+(((String)absNode.getUserObject()).endsWith(moderator.f_remoteFileSeparator)? "":moderator.f_remoteFileSeparator)+abs;
            }

            return abs;
  }
   void renameDir(){

     CTFBRemoteDirNode node = (CTFBRemoteDirNode)getLastSelectedPathComponent();
     CTFBRemoteFile rf=node.file;
     if(!moderator.f_isRoot(rf.getAbsolutePath())){
      if(moderator.f_rename2==null) moderator.f_rename2=new CTFBRename(moderator,true);
        String absolu=rf.getAbsolutePath();
        if(absolu.endsWith(moderator.f_remoteFileSeparator)) absolu=absolu.substring(0,absolu.length()-1);
        int inx=absolu.lastIndexOf(moderator.f_remoteFileSeparator);
        String parent=(inx>-1? absolu.substring(0, inx):"");
        String name=absolu.substring(inx+1);
        moderator.f_rename2.setValue(parent, name, 6,1);
        moderator.f_rename2.setVisible(true);
     }else{JOptionPane.showMessageDialog(null, "Can not rename a root directory.");}
    }
}