package it.poste.postspy.shell;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.handler.SQALEButtonHandler;
import it.poste.postspy.utility.LogUtility;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class SqaleShell {
	
	private final int ENTER_KEY_CODE = 13;
	
	private SQALEButtonHandler sqaleButtonHandler; 
	
	private Shell shell;
	
	private Label projectLabel;
	private Text projectText;
	private Label versionLabel;
	private Text versionText;
	private Button sqaleButton;
	private Button cancelButton;
	
	String projectName, pathSrResults;
	
	public SqaleShell(String projectName, SQALEButtonHandler sqaleButtonHandler, String pathSrResults) {
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		shell = new Shell(activeWindow.getShell());
		this.sqaleButtonHandler = sqaleButtonHandler;
		this.projectName = projectName;
		this.pathSrResults = pathSrResults;
	}
	
	public void show() {
		try {
			shell.setSize(340, 150);
			shell.setText("Insert Project informations");
			Monitor primary = shell.getDisplay().getPrimaryMonitor();
			Rectangle bounds = primary.getBounds();
			Rectangle rect = shell.getBounds();
			int x = bounds.x + (bounds.width - rect.width) / 2;
			int y = bounds.y + (bounds.height - rect.height) / 2;
			shell.setLocation(x, y);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			gridLayout.verticalSpacing = 9;
			gridLayout.horizontalSpacing = 20;
			shell.setLayout(gridLayout);
			inizializeComponent();
			
			GridData gridData;
			
			projectLabel.setText("Project:");
			
			projectText.setText(projectName);
			projectText.setEnabled(false);
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalAlignment = SWT.FILL;
			projectText.setLayoutData(gridData);
			
			versionLabel.setText("Version:");
			
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalAlignment = SWT.FILL;
			versionText.setLayoutData(gridData);
			versionText.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					versionText.setText(versionText.getText().toUpperCase());
					versionText.append("");
				}
				@Override
				public void keyPressed(KeyEvent arg0) {
					if(arg0.keyCode==ENTER_KEY_CODE) {
						clickSaveButtonAction();
					}
				}
			});
			
			sqaleButton.setText("SQALE");
//		gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalAlignment = SWT.FILL;
//		sqaleButton.setLayoutData(gridData);
			sqaleButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					clickSaveButtonAction();
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
			});
			
			cancelButton.setText("Cancel");
//		gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalAlignment = SWT.FILL;
//		cancelButton.setLayoutData(gridData);
			cancelButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					closeShell();
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
			});
			
			shell.open();			
		} catch(Exception exception) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, exception);
		}
	}
	
	private void inizializeComponent() {
		projectLabel = new Label(shell, SWT.NULL);
		projectText = new Text(shell, SWT.BORDER | SWT.SINGLE);
		versionLabel = new Label(shell, SWT.NULL);
		versionText = new Text(shell, SWT.BORDER | SWT.SINGLE);
		sqaleButton = new Button(shell, SWT.PUSH);
		cancelButton = new Button(shell, SWT.PUSH);
	}
	
	private void closeShell() {
		shell.close();
	}
	
	private boolean controlIfExist(String getResultFile) {
		File resultFile = new File(getResultFile);
		return resultFile.exists();
	}
	
	private void clickSaveButtonAction() {
		try {
			String version = versionText.getText().trim();
			String getResultFile = pathSrResults + File.separator + projectText.getText() + File.separator + version;
			if(!controlIfExist(getResultFile) || "".equals(version)) {
				MessageDialog.openInformation(
						shell,
						"Message",
						"Result path not exists");
			} else {
				sqaleButtonHandler.executeSqale(getResultFile);
				closeShell();
			}
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}

}
