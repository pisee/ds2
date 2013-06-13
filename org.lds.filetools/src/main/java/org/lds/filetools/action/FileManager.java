package org.lds.filetools.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.lds.filetools.file.FileHandler;

@SuppressWarnings("rawtypes")
public class FileManager implements IObjectActionDelegate {
	private IStructuredSelection structuredSelection;
	
	@Override
	public void run(IAction arg0) {
		Map<IProject, List<String>> fileMap = new HashMap<IProject, List<String>>();
		for (Iterator i = structuredSelection.iterator(); i.hasNext();) {
			Object nextSelected = i.next();
			if (nextSelected instanceof IFile) {
				IFile file = (IFile) nextSelected;
				IProject project = file.getProject();
				String path = file.getFullPath().toString();
				
				if(fileMap.containsKey(project)){
					fileMap.get(project).add(path);
				}else{
					List<String> filePaths = new ArrayList<String>();
					filePaths.add(path);
					fileMap.put(project, filePaths);
				}
				
			}
		}
		List<IFile> fileList = FileHandler.generate(fileMap, ".deploy");
		FileHandler.openFileEditor(fileList);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		structuredSelection = (IStructuredSelection) selection;
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		
	}

}
