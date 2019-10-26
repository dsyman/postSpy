package it.poste.postspy.utility;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.constant.ResourcesConstant;
import it.poste.postspy.entity.Projects;
import it.poste.postspy.entity.Projects.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.Platform;

public class ProjectsUtility {
	
	public static File getProjectsFile() {
		File projectsFile = null;
		try {
			String workspacePath = Platform.getLocation().toString();
			projectsFile = new File(workspacePath + File.separator + ResourcesConstant.PROJECTS_FILE_NAME);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return projectsFile;
	}
	
	public static void saveProjectsFile(Projects projects) {
		try {
			XMLUtility.saveFileFromObject(projects, getProjectsFile().getAbsolutePath());
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	public static Projects getProjects() {
		Projects projects = null;
		try {
			if(getProjectsFile().exists()) {
				projects = (Projects) XMLUtility.getObjFromXMLFile(getProjectsFile(), Projects.class);
			} else {
				projects = new Projects();
				saveProjectsFile(projects);
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return projects;
	}
	
	public static void addProject(Project project) {
		try {
			Projects projects = getProjects();
			projects.getProject().add(project);
			saveProjectsFile(projects);
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	public static String getProjectLastVersionMD5(String name) {
		String md5 = null;
		try {
			List<Project> projectsList = getProjectsFromName(name);
			Collections.sort(projectsList, new Comparator<Project>() {
				  public int compare(Project o1, Project o2) {
				      return o1.getDate().compare(o2.getDate());
				  }
				});
			md5 = projectsList.get(projectsList.size()-1).getMd5();
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return md5;
	}
	
	private static List<Project> getProjectsFromName(String name) {
		List<Project> projectsList = new ArrayList<Projects.Project>();
		try {
			Projects projects = getProjects();
			for(Project project:projects.getProject()) {
				if(project.getName().equalsIgnoreCase(name)) {
					projectsList.add(project);
				}
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
		return projectsList;
	}

}
