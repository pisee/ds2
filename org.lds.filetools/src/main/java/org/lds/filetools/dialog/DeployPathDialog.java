package org.lds.filetools.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.lds.filetools.panel.DeployPathPanel;


public class DeployPathDialog extends Dialog{
	DeployPathPanel pannel = new DeployPathPanel();
	public DeployPathDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Getting Deploy Path List");
//		Image image = new Image(shell.getDisplay(), "ds.gif");
//		shell.setImage(image);
		shell.setBounds(0,0,850,750);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 1;
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.CENTER));
		
		pannel.createPanel(parent);
		return parent;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, "Clear",	false);
		createButton(parent, IDialogConstants.CLOSE_ID, IDialogConstants.CLOSE_LABEL, false);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == 0) {
			okPressed();
			close();
		} else if (1 == buttonId){
//			cancelPressed();
			pannel.clearAllArea();
		} else if (12 == buttonId){
			close();
		}
	}
}
