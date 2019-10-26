package it.poste.postspy.utility;

import it.poste.postspy.constant.ResourcesConstant;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class ProxyUtil {
	
	public static void setSystemProxySettings() throws Exception {
		try {
			Properties proxyProperties = PropertiesUtility.getProxyProperties();
			if(proxyProperties!=null && controlProxyValueIfNull(proxyProperties)) {
				String address = proxyProperties.getProperty(ResourcesConstant.PROXY_ADDRESS);
				String port = proxyProperties.getProperty(ResourcesConstant.PROXY_PORT);
				final String username = proxyProperties.getProperty(ResourcesConstant.PROXY_USERNAME);
				String encryptedPassword = proxyProperties.getProperty(ResourcesConstant.PROXY_PASSWORD);
				final String password = CryptographyUtil.decrypt(encryptedPassword);
				Authenticator.setDefault(new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password.toCharArray());
					}
				});
				Properties systemProperties = System.getProperties();
				systemProperties.setProperty(ResourcesConstant.SYSTEM_PROXY_HOST_PROPERTY, address);
				systemProperties.setProperty(ResourcesConstant.SYSTEM_PROXY_PORT_PROPERTY, port);
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	private static boolean controlProxyValueIfNull(Properties proxyProperties) throws Exception {
		try {
			String address = proxyProperties.getProperty(ResourcesConstant.PROXY_ADDRESS);
			String port = proxyProperties.getProperty(ResourcesConstant.PROXY_PORT);
			String username = proxyProperties.getProperty(ResourcesConstant.PROXY_USERNAME);
			String password = proxyProperties.getProperty(ResourcesConstant.PROXY_PASSWORD);
			if("".equals(address) || "".equals(port) || "".equals(username) || "".equals(password)) {
				return false;
			} else {
				return true;
			}
		} catch(Exception e) {
			throw e;
		}
	}

}
