package it.poste.postspy.shell;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.wizard.SRWizard;
import it.poste.postspy.wizard.SrSecondWizardPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class TrustedApplicationShell {
	
	private SrSecondWizardPage secondWizardPage;
	
	private Shell shell;
	
	private Button dbQueriesCheck;
	private Button socketCheck;
	private Button environmentCheck;
	private Button servletCheck;
	private Button saveButton;
	private Button cancelButton;
	
	public TrustedApplicationShell(Shell shell, SrSecondWizardPage secondWizardPage) {
		this.shell = new Shell(shell);
		this.secondWizardPage = secondWizardPage;
	}
	
	public void show() {
		try {
			secondWizardPage.getShell().setEnabled(false);
			shell.setSize(350, 150);
			shell.setText("Trusted Options");
			shell.addShellListener(new ShellListener() {
				@Override
				public void shellIconified(ShellEvent arg0) {
					
				}
				@Override
				public void shellDeiconified(ShellEvent arg0) {
					
				}			
				@Override
				public void shellDeactivated(ShellEvent arg0) {
					
				}			
				@Override
				public void shellClosed(ShellEvent arg0) {
					secondWizardPage.getShell().setEnabled(true);
				}			
				@Override
				public void shellActivated(ShellEvent arg0) {
					
				}
			});
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			gridLayout.verticalSpacing = 9;
			gridLayout.horizontalSpacing = 40;
			shell.setLayout(gridLayout);
			inizializeComponent();
			
			GridData gridData;
			
			dbQueriesCheck.setText("DB Queries");
			
			socketCheck.setText("Socket");
			
			servletCheck.setText("Servlet/WS Request");
			
			environmentCheck.setText("Environment Variables");
			
			saveButton.setText("Save");
			gridData = new GridData(GridData.VERTICAL_ALIGN_END);
			gridData.horizontalSpan = 2;
			saveButton.setLayoutData(gridData);
			saveButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					if(dbQueriesCheck.getSelection()) {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setDbQueries(true);
					} else {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setDbQueries(false);
					}
					if(socketCheck.getSelection()) {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setSocket(true);
					} else {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setSocket(false);
					}
					if(environmentCheck.getSelection()) {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setEnvironmentVariables(true);
					} else {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setEnvironmentVariables(false);
					}
					if(servletCheck.getSelection()) {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setServletRequest(true);
					} else {
						( (SRWizard) secondWizardPage.getWizard()).getParametersFilter().setServletRequest(false);
					}
					closeShell();
				}
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					
				}
			});
			
			cancelButton.setText("Cancel");
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
		dbQueriesCheck = new Button(shell, SWT.CHECK);
		socketCheck = new Button(shell, SWT.CHECK);
		servletCheck = new Button(shell, SWT.CHECK);
		environmentCheck = new Button(shell, SWT.CHECK);
		saveButton = new Button(shell, SWT.PUSH);
		cancelButton = new Button(shell, SWT.PUSH);
	}
	
	private void closeShell() {
		shell.close();
	}

}
