package org.lds.filetools.util;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class DsDialogUtil {
	
	public static String openWindowFileDialog(String fileName, String[] filterExtensions, String filterPath, String dialogName, int dialogStyle) {
		FileDialog dialog = new FileDialog(new Shell(), dialogStyle);
		dialog.setText(dialogName);
		dialog.setFileName(fileName);
		dialog.setFilterExtensions(filterExtensions);	// new String[] { "*.jar" }
		dialog.setFilterPath(filterPath);	// "c:"
		String selectedFileName = dialog.open();
		if (selectedFileName == null || selectedFileName.length() == 0)
			return null;
		
		return selectedFileName;
	}
}
