package org.lds.filetools.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.lds.filetools.Activator;

public class FileHandler {

	public static List<IFile> generate(Map<IProject, List<String>> fileMap, String extension) {
		List<IFile> fileList = new ArrayList<IFile>();
		Set<IProject> keySet = fileMap.keySet();
		Iterator<IProject> iterator = keySet.iterator();
		while(iterator.hasNext()){
			IProject project = iterator.next();
			List<String> list = fileMap.get(project);
			String contents = createContents(list);
			fileList.add(createFile(project, "tmp", createByteArrayInputStream(contents, "UTF-8"), extension));
		}
		return fileList;
	}

	public static InputStream createByteArrayInputStream(String contents, String encoding){
		try {
			return new ByteArrayInputStream(contents.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			Activator.errorLog("ByteArray 인코딩중 오류가 발생했습니다.", e);
		}
		return null;
	}
	
	private static String createContents(List<String> list) {
		String result ="";
		for(String path : list){
			result += path + "\n";
		}
		return result;
	}
	
	private static IFile createFile(IProject project, String path, InputStream inputStream, String extension){
		if (path != null && !path.equals("") ) {
			createFolder(project, path);
		}
		String fileName = getFileName(extension);
		
		final IFile file = project.getFile(new Path(path + "/" + fileName));
		try {
			if (file.exists()) {
				file.setContents(inputStream, true, false, null);
			} else {
				file.create(inputStream, true, null);
			}
			file.refreshLocal(IResource.DEPTH_ZERO, null);
			inputStream.close();
		} catch (CoreException e) {
			Activator.errorLog("파일 생성중 오류가 발생했습니다.", e);
		} catch (IOException e) {
			Activator.errorLog("파일 생성중 오류가 발생했습니다.", e);
		}
		return file;
	}

	public static String createFile(String fullPath, InputStream inputStream){
		File file = new File(fullPath);
		try {
			if(file.exists() && file.isDirectory()){
				FileUtils.deleteDirectory(file);
				file.mkdir();
			}
			FileUtils.copyInputStreamToFile(inputStream, file);
		} catch (IOException e) {
			Activator.errorLog("파일 생성중 오류가 발생했습니다.", e);
		}
		return file.toString();
	}

	private static void createFolder(IProject project, String path){
		try {
			IFolder folder = null;
			String[] pathArr = path.split("/");
			for (int i = 0; i < pathArr.length; i++) {
				if (i == 0)
					path = pathArr[i];
				else
					path = path + "/" + pathArr[i];
				if(path != null && !"".equalsIgnoreCase(path.trim())){
					folder = project.getFolder(path);
					if (!folder.exists()) {
						folder.create(false, true, null);
					}
				}
			}
		} catch (CoreException e) {
			Activator.errorLog("폴더 생성중 오류가 발생했습니다.", e);
		}
	}

	public static String getFileName(String extension) {
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String dateFormat = sdf.format(date);
	    
	    String userName = System.getProperty("user.name");
	    if(userName != null && !"".equalsIgnoreCase(userName)){
	    	return userName + "_" + dateFormat + extension;
	    }else{
	    	return "anonymous_" + dateFormat+ extension;
	    }
	}

	public static void openFileEditor(List<IFile> fileList) {
		for(IFile file : fileList){
			IEditorInput editorInput = new FileEditorInput(file);
			IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			try {
				page.openEditor(editorInput, "org.eclipse.ui.DefaultTextEditor");
			} catch (PartInitException e) {
				Activator.errorLog("파일을 열수 없습니다.", e);
			}
		}
	}
	
	public static String LineByFileReader(String filePath) {
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try{
			File tFile = new File(filePath);
			if(!tFile.exists()){
				return "";
			}
			in = new BufferedReader(new FileReader(filePath));
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s).append("\r\n");
			}
		} catch (Exception e) {
			Activator.errorLog("파일 읽기중 오류가 발생했습니다.", e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					Activator.errorLog("리소스 반납중에 에러가 발생했습니다.", e);
				}
			}
		}
		return sb.toString();
	}
}
