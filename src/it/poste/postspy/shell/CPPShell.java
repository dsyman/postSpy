package it.poste.postspy.shell;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.wizard.SrSecondWizardPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class CPPShell {
	
	private SrSecondWizardPage secondWizardPage;
	
	private Shell shell;
	
	private Group radioGroup;
	private Button genericRadio;
	private Button unixRadio;
	private Button winRadio;
	private Button saveButton;
	private Button cancelButton;
	
	public CPPShell(Shell shell, SrSecondWizardPage secondWizardPage) {
		this.shell = new Shell(shell);
		this.secondWizardPage = secondWizardPage;
	}
	
	public void show() {
		try {
			secondWizardPage.getShell().setEnabled(false);
			shell.setSize(260, 150);
			shell.setText("Select OS");
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
			
			radioGroup.setText("Target Platform");
			radioGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
			
			genericRadio.setText("Generic");
			
			unixRadio.setText("Unix/Linux");
			
			winRadio.setText("Windows");
			
			saveButton.setText("Save");
			saveButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					if(genericRadio.getSelection()) {
						secondWizardPage.setSelection(SrSecondWizardPage.C_GENERIC_OPTION);
					} else if(unixRadio.getSelection()) {
						secondWizardPage.setSelection(SrSecondWizardPage.C_UNIX_OPTION);
					} else if(winRadio.getSelection()) {
						secondWizardPage.setSelection(SrSecondWizardPage.C_WIN_OPTION);
					}
					secondWizardPage.completeCPPParameters();
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
		radioGroup = new Group(shell, SWT.SHADOW_IN);
		genericRadio = new Button(radioGroup, SWT.RADIO);
		unixRadio = new Button(radioGroup, SWT.RADIO);
		winRadio = new Button(radioGroup, SWT.RADIO);
		saveButton = new Button(shell, SWT.PUSH);
		cancelButton = new Button(shell, SWT.PUSH);
	}
	
	private void closeShell() {
		shell.close();
	}

}
