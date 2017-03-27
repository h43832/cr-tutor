
package ct;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import java.io.*;

import java.text.SimpleDateFormat;

import java.util.*;
import javax.swing.event.*;

/**
 * @version $Revision: 1.2 $
 * @author  Benoît Mah? (bmahe@w3.org)
 */
public class CTFBDirBrowser extends JTree {

    public static final String DELETE_CMD = "delete";

    private Frame            frame     = null;
    static CTFBDirNode root;
    static CTModerator moderator;
    ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("ct/Bundle");
    public CTFBDirBrowser(CTFBDirNode root,CTModerator fb) {
	super(root);
        this.moderator=fb;
	setRootVisible(true);
	setEditable(true);
        root.name=bundle2.getString("CTModerator.f_label1.text");
	setLargeModel(true);
	setScrollsOnExpand(true);
	addTreeWillExpandListener(twel);
	KeyStroke delK = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
	registerKeyboardAction(al, DELETE_CMD, delK, WHEN_FOCUSED);
	addMouseListener(mouseAdapter);
    }

    /**
     * Our TreeWillExpandListener
     */
    TreeWillExpandListener twel = new TreeWillExpandListener() {
	public void treeWillExpand(TreeExpansionEvent event)
	throws ExpandVetoException
	{
	    TreeNode node = 
	    (TreeNode)event.getPath().getLastPathComponent();
	    ((CTFBDirNode)node).nodeWillExpand();
	    ((DefaultTreeModel)getModel()).reload(node);
	}

	public synchronized void treeWillCollapse(TreeExpansionEvent event)
	throws ExpandVetoException
	{
	    TreeNode node = 
	    (TreeNode)event.getPath().getLastPathComponent();
	    ((CTFBDirNode)node).nodeWillCollapse();
	    ((DefaultTreeModel)getModel()).reload(node);
	}
    };

    /**
     * The popup menu action listener.
     */
    ActionListener pmal = new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
	    setCursor(Cursor.WAIT_CURSOR);
	    String command = evt.getActionCommand();
	    if (command.equals("del")) {
		deleteSelectedFiles();
	    } else if (command.equals("upload")) {
		upload();
	    } else if (command.equals("newfolder")) {
		createNewFolder();
	    } else if (command.equals("rename")) {
                renameDir();
            } else if (command.equals("info")) {

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
                if(tp.length>0){
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
            

            CTFBDirNode node = (CTFBDirNode)getLastSelectedPathComponent();

	    if (SwingUtilities.isRightMouseButton(e)){
		if (node != null && !node.equals(root))  getPopupMenu(node).show(e.getComponent(), e.getX(), e.getY());
	    } else if(e.getClickCount()==1){

                if (node != null && !node.equals(root)){
		  String dirName=node.getName(),absolutePath=dirName;
                  CTFBDirNode pNode=(CTFBDirNode)node.getParent();
                  while(pNode.getParent()!=null){
                      absolutePath=pNode.getName()+(pNode.getName().charAt(pNode.getName().length()-1)==File.separatorChar? "":File.separator)+absolutePath;
                      pNode=(CTFBDirNode)pNode.getParent();
                  }

                    DefaultComboBoxModel model = (DefaultComboBoxModel)moderator.cbb_fList1.getModel();
                    if(model.getIndexOf(absolutePath) == -1 ) {
                      moderator.f_skipComboBox1ItemStateChanged=true;
                     model.addElement(absolutePath);
                     moderator.f_skipComboBox1ItemStateChanged=false;
                    }
                    moderator.f_skipComboBox1ItemStateChanged=true;

                    moderator.cbb_fList1.setSelectedItem(absolutePath);
                    moderator.f_skipComboBox1ItemStateChanged=false;
                    moderator.f_currentLocalNode=node;

                  moderator.f_showLocalDir(absolutePath,moderator.f_local_orderby,moderator.f_local_asc);
                }

            }

	}
    };

    void renameDir(){

      CTFBDirNode node = (CTFBDirNode)getLastSelectedPathComponent();
      File f=node.file;
      if(!moderator.f_isRoot(f.getAbsolutePath())) {
      if(moderator.f_rename2==null) moderator.f_rename2=new CTFBRename(moderator,true);
        moderator.f_rename2.setValue(f.getParentFile().getAbsolutePath(), f.getName(), 5,1);
        moderator.f_rename2.setVisible(true);
      } else JOptionPane.showMessageDialog(null, "Can not rename a root directory.");
    }
    CTFBDirNode getNodeFromNodeAndFile(CTFBDirNode node,File f1){
        File f2=node.getFile();
        if(f2!=null && f2.getAbsolutePath().equalsIgnoreCase(f1.getAbsolutePath())) return node;
        Enumeration en=node.children();
        while(en.hasMoreElements()){
            node=(CTFBDirNode)en.nextElement();

            f2=node.getFile();
            if(f2!=null && f2.getAbsolutePath().equalsIgnoreCase(f1.getAbsolutePath())) return node;
        }
        return null;
    }
class treeSelListener implements TreeSelectionListener{
        @Override
         public void valueChanged(TreeSelectionEvent e) {
          CTFBDirNode node = (CTFBDirNode) getLastSelectedPathComponent();

        if (node == null) return;

        moderator.f_currentSelectedPath1=e.getPaths();
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

    protected void createNewFolder() {
      

      CTFBDirNode node = (CTFBDirNode)getLastSelectedPathComponent();
      File f=node.file;

      if(moderator.f_rename2==null) moderator.f_rename2=new CTFBRename(moderator,true);
        moderator.f_rename2.setValue(f.getAbsolutePath(), "NewFolder", 3,1);
        moderator.f_rename2.setVisible(true);

    }

    protected void deleteSelectedFiles() {
	TreePath path[] = removeDescendants(getSelectionPaths());
	if (path != null) {
	    int result = 
		JOptionPane.showConfirmDialog(this,"Delete selected directory"+(path.length>1? "s":"")+"?", 
					      "Delete directory"+(path.length>1? "s":""), 
					      JOptionPane.YES_NO_OPTION);
	    if (result == JOptionPane.YES_OPTION) {
		DefaultTreeModel model = (DefaultTreeModel)getModel();

		for (int i = 0 ; i < path.length ; i++) {
		    CTFBDirNode fnode = (CTFBDirNode)path[i].getLastPathComponent();
		    File file = fnode.getFile();
		    if ((file != null) && moderator.f_deleteDir(file)) {
			MutableTreeNode node = (MutableTreeNode)path[i].getLastPathComponent();
			model.removeNodeFromParent(node);

		    } else {
			JOptionPane.showMessageDialog(this,"Can't delete \""+file.getAbsolutePath()+"\"!","Error",JOptionPane.ERROR_MESSAGE);
                        moderator.jTextArea3.append(moderator.format5.format(new Date())+"Local can't delete \""+file.getAbsolutePath()+"\"!\r\n");

		    }

         	}

	    }
	}
    }
    protected void upload() {
	TreePath path[] = removeDescendants(getSelectionPaths());
	if (path != null) {
             TreeMap uploadTM=new TreeMap();
	DefaultTreeModel model = (DefaultTreeModel)getModel();
	for (int i = 0 ; i < path.length ; i++) {
		    CTFBDirNode fnode = (CTFBDirNode)path[i].getLastPathComponent();
		    File file = fnode.getFile();
                    uploadTM.put(file.getAbsolutePath(), "2");
		}

           moderator.f_upload(uploadTM);
        }
    }

    /**
     * Get the popup menu relative to the selected resource.
     * @param file the selected file
     * @return a JPopupMenu instance
     */
    protected JPopupMenu getPopupMenu(CTFBDirNode node) {
	JPopupMenu popupMenu = new JPopupMenu("Actions");
	JMenuItem  menuItem  = null;

	String name = node.getName();
	if (getSelectionCount() > 1) {
	  menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg15"));
          menuItem.addActionListener(pmal);
	  menuItem.setActionCommand("upload");
	  if(moderator.tutorMode || moderator.w.checkOneVar(moderator.auOne_asAMember, 13)) popupMenu.add(menuItem);
	  popupMenu.addSeparator();
	    menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg18"));
	    menuItem.addActionListener(pmal);
	    menuItem.setActionCommand("del");
	    popupMenu.add(menuItem);

	} else {
	    File file = node.getFile();
	    if (file != null) {
		Date date = new Date(file.lastModified());
		StringBuffer descr = new StringBuffer(name);
		descr.append(", ");
		descr.append(moderator.format1.format(date));

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
		menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg15"));
                menuItem.addActionListener(pmal);
                menuItem.setActionCommand("upload");
                if(moderator.tutorMode || moderator.w.checkOneVar(moderator.auOne_asAMember, 13)) popupMenu.add(menuItem);
		popupMenu.addSeparator();

		if (file.isDirectory() && (getSelectionCount() == 1)) {
		    menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg16"));
		    menuItem.addActionListener(pmal);
		    menuItem.setActionCommand("newfolder");
		    popupMenu.add(menuItem);
		}

		menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg18"));
		menuItem.addActionListener(pmal);
		menuItem.setActionCommand("del");
		popupMenu.add(menuItem);
		menuItem = new JMenuItem(bundle2.getString("CTModerator.xy.msg17"));
                menuItem.addActionListener(pmal);
                menuItem.setActionCommand("rename");
                popupMenu.add(menuItem);
	    } else {
		 menuItem = new JMenuItem(name);
                 menuItem.setActionCommand("newfolder");
                 menuItem.setActionCommand("null");
		 popupMenu.add(menuItem);
	    }
	}
	return popupMenu;
    }

    protected void removeNodeFromParent(CTFBDirNode node) {
	((DefaultTreeModel) getModel()).removeNodeFromParent(node);
    }

    protected void addNode(CTFBDirNode parent, CTFBDirNode child) {
	((DefaultTreeModel) getModel()).insertNodeInto(child, parent, 0);
	child.setParent(parent);
    }

    protected Frame getFrame() {
	if (frame == null) {
	    frame = 
		(Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
	}
	return frame;
    }

    public void setCursor(int cursor) {
	getFrame().setCursor(Cursor.getPredefinedCursor(cursor));
    }

    public static CTFBDirBrowser getDirBrowser(String rootname,CTModerator fb) {
	FilenameFilter filter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return (name.charAt(0) != '.');
	    }
	};
	root    = new CTFBDirNode("Locale Computer", filter);
	fb.f_browser = new CTFBDirBrowser(root,fb);
	root.initializeRootNode(fb.f_browser);
	return fb.f_browser;
    }

}