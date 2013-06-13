package org.lds.filetools.panel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.lds.filetools.file.FileHandler;
import org.lds.filetools.util.DsDialogUtil;

public class DeployPathPanel {
	private Text filePathText;
	private StyledText inputStypeText;
	private StyledText outputStypeText;
	
	public void createPanel(Composite parent) {
		Group tableComposite = new Group(parent, SWT.NULL);
		tableComposite.setText("Deploy Path");

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		tableComposite.setLayout(layout);
		
		createFileSearchBox(tableComposite);
		createInputBox(tableComposite);
		createConvertButton(tableComposite);
		createOutputBox(tableComposite);
		createGenerateButton(tableComposite);
	}
	
	private void createFileSearchBox(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		
		GridLayout compositeGridLayout = new GridLayout(3, false);
		compositeGridLayout.marginHeight = 5;
		compositeGridLayout.marginWidth = 5;
		composite.setLayout(compositeGridLayout);

		GridData compositeGridData = new GridData(SWT.NULL);
		composite.setLayoutData(compositeGridData);

		Label filePathLabel = new Label(composite, SWT.NULL);
		filePathLabel.setText("File: ");
		
		filePathText = new Text(composite, SWT.LEFT | SWT.BORDER);
		GridData filePathGridData = new GridData(SWT.NULL);
		filePathGridData.widthHint = 630;
		filePathText.setLayoutData(filePathGridData);
		filePathText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13) {
					displayInputFile(filePathText.getText());
				}
			}
		});
		
		Button browseButton = new Button(composite, SWT.BUTTON1);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String deployFilePath = "";
				deployFilePath = DsDialogUtil.openWindowFileDialog("", new String[] { "*.deploy" }, null, "Search Deploy File", SWT.OPEN);
				if (deployFilePath == null || "".equalsIgnoreCase(deployFilePath)) {
					return;
				}else{
					if(deployFilePath.startsWith("/")){
						deployFilePath = deployFilePath.substring(1);
					}
					filePathText.setText(deployFilePath);
					displayInputFile(deployFilePath);
				}
			}
		});
		GridData browseButtonData = new GridData(SWT.NULL);
		browseButtonData.widthHint = 110;
		browseButton.setLayoutData(browseButtonData);
	}
	
	private void displayInputFile(String deployFilePath) {
		inputStypeText.setText(FileHandler.LineByFileReader(deployFilePath));
	}

	private void createInputBox(Composite tableComposite) {
		Composite encryptionInfoGroup = new Composite(tableComposite, SWT.NULL);
		
		GridLayout encryptionInfoGridLayout = new GridLayout(2, false);
		encryptionInfoGridLayout.marginHeight = 5;
		encryptionInfoGridLayout.marginWidth = 5;
		encryptionInfoGroup.setLayout(encryptionInfoGridLayout);

		GridData encryptionInfoGridData = new GridData(SWT.NULL);
		encryptionInfoGridData.widthHint = 800;
		encryptionInfoGroup.setLayoutData(encryptionInfoGridData);

		Label inputLabel = new Label(encryptionInfoGroup, SWT.NULL);
		inputLabel.setText("input: ");

		inputStypeText = new StyledText(encryptionInfoGroup, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		inputStypeText.setText("");
		inputStypeText.setEditable(true);
		inputStypeText.setEnabled(true);
		GridData inputGridData = new GridData(GridData.FILL_BOTH);
		inputGridData.heightHint = 200;
		inputGridData.horizontalSpan = 7;
		inputStypeText.setLayoutData(inputGridData);
	}
	
	private void createConvertButton(Composite tableComposite) {
		
		Button createButton = new Button(tableComposite, SWT.PUSH | SWT.CENTER | SWT.END);
		createButton.setText("Convert");
		createButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent se) {
				String text = parsInputText(inputStypeText.getText());
				outputStypeText.setText(text);
			}
		});
		GridData encrytButtonGridData = new GridData(SWT.NULL);
		encrytButtonGridData.widthHint = 150;
		createButton.setLayoutData(encrytButtonGridData);
	}
	
	private String parsInputText(String text) {
		StringBuffer sb = new StringBuffer();
		String[] split = text.split("\r\n");
		for(String str : split){
			sb.append(checkInputText(str));
		}
		return sb.toString();
	}
	
	private String checkInputText(String inputText) {
		DeployFileType[] deployFileTypes = DeployFileType.values();
		for(DeployFileType deployFileType : deployFileTypes){
			String originSourceSuffix = deployFileType.getOriginSourceSuffix();
			String originSourcePath = deployFileType.getOriginSourcePath();
			if(inputText.contains(originSourceSuffix) && inputText.contains(originSourcePath)){
				return convertInputText(inputText, deployFileType);
			}
		}
		return "";
	}
	
	private String convertInputText(String inputText, DeployFileType deployFileType) {
		StringBuffer sb = new StringBuffer();
		String[] deployTargetPaths = deployFileType.getDeployTargetPath();
		for(String deployTargetPath : deployTargetPaths){
			if(deployTargetPath.contains("mccabe")){
				int lastSlashIndex = inputText.lastIndexOf("/");
				int suffixIndex = inputText.lastIndexOf(deployFileType.getOriginSourceSuffix());
				String className = inputText.substring(lastSlashIndex+1, suffixIndex);
				
				String replace = inputText.replace(deployFileType.getOriginSourcePath(), "");
				String[] split = replace.split("/");
				String classPrefix = "*";
				for(int i=1 ; i< split.length-1 ; i++){
					classPrefix += split[i] + "_";
				}
				String result = "/" + deployTargetPath + classPrefix + className + deployFileType.getDeployTargetSuffix();
				sb.append(result).append("\r\n");
			}else{
				String tmp = inputText.replace(deployFileType.getOriginSourcePath(), deployTargetPath).replace(deployFileType.getOriginSourceSuffix(), deployFileType.getDeployTargetSuffix());
				sb.append(tmp).append("\r\n");
			}
		}
		return sb.toString();
	}
	
	private void createOutputBox(Composite tableComposite) {
		Composite encryptionInfoGroup = new Composite(tableComposite, SWT.NULL);
		
		GridLayout encryptionInfoGridLayout = new GridLayout(2, false);
		encryptionInfoGridLayout.marginHeight = 5;
		encryptionInfoGridLayout.marginWidth = 5;
		encryptionInfoGroup.setLayout(encryptionInfoGridLayout);

		GridData encryptionInfoGridData = new GridData(SWT.NULL);
		encryptionInfoGridData.widthHint = 800;
		encryptionInfoGroup.setLayoutData(encryptionInfoGridData);

		Label outputLabel = new Label(encryptionInfoGroup, SWT.NULL);
		outputLabel.setText("output: ");
		
		outputStypeText = new StyledText(encryptionInfoGroup, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		outputStypeText.setText("");
		outputStypeText.setEditable(true);
		outputStypeText.setEnabled(true);
		GridData outputGridData = new GridData(GridData.FILL_BOTH);
		outputGridData.heightHint = 200;
		outputGridData.horizontalSpan = 7;
		outputStypeText.setLayoutData(outputGridData);
	}
	
	private void createGenerateButton(Composite parent) {
		Button generateButton = new Button(parent, SWT.BUTTON1);
		generateButton.setText("Generate Output File");
		generateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String deployFilePath = "";
				if(filePathText.getText() != null && !"".equalsIgnoreCase(filePathText.getText())){
					String fileName = filePathText.getText().substring(filePathText.getText().lastIndexOf("\\")+1, filePathText.getText().lastIndexOf("."));
					String filepath = filePathText.getText().substring(0, filePathText.getText().lastIndexOf("\\"));
					deployFilePath = DsDialogUtil.openWindowFileDialog(fileName+".convert", new String[] { "*.convert" }, filepath, "Search Deploy File", SWT.SAVE);
				}else{
					deployFilePath = DsDialogUtil.openWindowFileDialog(FileHandler.getFileName(".convert"), new String[] { "*.convert" }, null, "Search Deploy File", SWT.SAVE);
				}
				if (deployFilePath == null || "".equalsIgnoreCase(deployFilePath)) {
					return;
				}else{
					if(deployFilePath.startsWith("/")){
						deployFilePath = deployFilePath.substring(1);
					}
					String fileName = FileHandler.createFile(deployFilePath, FileHandler.createByteArrayInputStream(outputStypeText.getText(), "UTF-8"));
					if(fileName != null && !"".equalsIgnoreCase(fileName) ){
						MessageDialog.openInformation(new Shell(), "Generate File", "Create Completion [" + fileName + "]!!!");
					}
				}
			}
		});
		GridData generateButtonData = new GridData(SWT.NULL);
		generateButtonData.widthHint = 150;
		generateButton.setLayoutData(generateButtonData);
	}

	public void clearAllArea() {
		filePathText.setText("");
		inputStypeText.setText("");
		outputStypeText.setText("");
	}
}
