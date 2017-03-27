
package ct;

import java.awt.Font;

import javax.swing.UIManager;

/**
 * @version $Revision: 1.1 $
 * @author  Benoît Mah? (bmahe@w3.org)
 */
public class Utils {

    public static void unBoldSpecificFonts() {
	Font f  = new Font("Dialog",Font.PLAIN,12); 
	Font fl = new Font("Dialog",Font.BOLD,12); 

	UIManager.put("Button.font",f); 
	UIManager.put("CheckBox.font",f); 
	UIManager.put("CheckBoxMenuItem.font",f); 
	UIManager.put("ComboBox.font",f); 
	UIManager.put("DesktopIcon.font",f); 

	UIManager.put("InternalFrame.font",f); 
	UIManager.put("InternalFrame.titleFont",f); 
	UIManager.put("Label.font",f); 
	UIManager.put("Menu.font",f); 
	UIManager.put("MenuBar.font",f); 

	UIManager.put("MenuItem.font",f); 
	UIManager.put("ProgressBar.font",f); 
	UIManager.put("RadioButton.font",f); 
	UIManager.put("RadioButtonMenuItem.font",f); 
	UIManager.put("TabbedPane.font",f); 

	UIManager.put("TitledBorder.font",f); 
	UIManager.put("ToggleButton.font",f); 
	UIManager.put("ToolBar.font",f); 
    }

}