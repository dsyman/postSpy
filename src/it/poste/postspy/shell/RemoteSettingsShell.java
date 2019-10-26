package it.poste.postspy.shell;

import it.poste.postspy.constant.MessageConstant;
import it.poste.postspy.entity.RemoteSettings;
import it.poste.postspy.utility.LogUtility;
import it.poste.postspy.utility.RemoteSettingsUtility;

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

public class RemoteSettingsShell {
	
	private final int ENTER_KEY_CODE = 13;
	
	private Shell shell;
	
	private Label basePathLabel;
	private Text basePathText;
	private Label ipAddressLabel;
	private Text ipAddressText;
	private Label macAddressLabel;
	private Text macAddressText;
	private Button saveButton;
	private Button cancelButton;
	
	public RemoteSettingsShell(Shell shell) {
		this.shell = new Shell(shell);
	}
	
	public void show() {
		try {
			shell.setSize(400, 300);
			shell.setText("Insert Remote Server Informations");
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
			
			basePathLabel.setText("Services Base Path:");
			
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalAlignment = SWT.FILL;
			basePathText.setLayoutData(gridData);
			
			ipAddressLabel.setText("Local Ip Address:");
			
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalAlignment = SWT.FILL;
			ipAddressText.setLayoutData(gridData);
			
			macAddressLabel.setText("Local Mac Address:");
			
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalAlignment = SWT.FILL;
			macAddressText.setLayoutData(gridData);
			macAddressText.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					
				}
				@Override
				public void keyPressed(KeyEvent arg0) {
					if(arg0.keyCode==ENTER_KEY_CODE) {
						clickSaveButtonAction();
					}
				}
			});
			
			saveButton.setText("Save");
			saveButton.addMouseListener(new MouseListener() {
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
			
			fillFields();
			
			shell.open();
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private void inizializeComponent() {
		basePathLabel = new Label(shell, SWT.NULL);
		basePathText = new Text(shell, SWT.BORDER | SWT.SINGLE);
		ipAddressLabel = new Label(shell, SWT.NULL);;
		ipAddressText = new Text(shell, SWT.BORDER | SWT.SINGLE);
		macAddressLabel = new Label(shell, SWT.NULL);;
		macAddressText = new Text(shell, SWT.BORDER | SWT.SINGLE);
		saveButton = new Button(shell, SWT.PUSH);
		cancelButton = new Button(shell, SWT.PUSH);
	}
	
	private void closeShell() {
		shell.close();
	}
	
	private void clickSaveButtonAction() {
		try {
			String basePath = basePathText.getText();
			String ipAddress = ipAddressText.getText();
			String macAddress = macAddressText.getText();
			RemoteSettings remoteSettings = new RemoteSettings();
			remoteSettings.setServicesBasePath(basePath);
			remoteSettings.setLocalIpAddress(ipAddress);
			remoteSettings.setLocalMacAddress(macAddress);
			
			RemoteSettingsUtility.saveRemoteSettingsFile(remoteSettings);
			
			closeShell();
		} catch(Exception e) {
			LogUtility.logError(MessageConstant.GENERIC_ERROR, e);
		}
	}
	
	private void fillFields() {
		try {
			RemoteSettings remoteSettings = RemoteSettingsUtility.getRemoteSettings();
			basePathText.setText(remoteSettings.getServicesBasePath());
			basePathText.append("");
			ipAddressText.setText(remoteSettings.getLocalIpAddress());
			ipAddressText.append("");
			macAddressText.setText(remoteSettings.getLocalMacAddress());
			macAddressText.append("");
		} catch(Exception e) {
			
		}
	}

}
