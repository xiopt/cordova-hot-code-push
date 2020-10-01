package com.nordnetab.chcp.main.config;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alin Stefan 01.10.2020.
 * <p/>
 * Model for hot-code-push specific preferences in capacitor.config.json.
 */
public class CapacitorConfig {

	private String configUrl;
	private boolean allowUpdatesAutoDownload;
	private boolean allowUpdatesAutoInstall;
	private int nativeInterfaceVersion;

	public static final String CAPACITOR_CONFIG_FILE_NAME = "capacitor.config.json";

	private CapacitorConfig() {

		configUrl = "";
		allowUpdatesAutoDownload = true;
		allowUpdatesAutoInstall = true;
		nativeInterfaceVersion = 1;
	}

	/**
	 * Getter for url to application config, that stored on server.
	 * This is a path to chcp.json file.
	 *
	 * @return url to application config
	 */
	public String getConfigUrl() {
		return configUrl;
	}

	/**
	 * Setter for config url on server.
	 *
	 * @param configUrl url to application config
	 */
	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	/**
	 * Setter for the flag if updates auto download is allowed.
	 *
	 * @param isAllowed set to <code>true</code> to allow automatic update downloads.
	 */
	public void allowUpdatesAutoDownload(boolean isAllowed) {
		allowUpdatesAutoDownload = isAllowed;
	}

	/**
	 * Getter for the flag if updates auto download is allowed.
	 * By default it is on, but you can disable it from JavaScript.
	 *
	 * @return <code>true</code> if automatic downloads are enabled, <code>false</code> - otherwise.
	 */
	public boolean isAutoDownloadIsAllowed() {
		return allowUpdatesAutoDownload;
	}

	/**
	 * Setter for the flag if updates auto installation is allowed.
	 *
	 * @param isAllowed set to <code>true</code> to allow automatic installation for the loaded updates.
	 */
	public void allowUpdatesAutoInstall(boolean isAllowed) {
		allowUpdatesAutoInstall = isAllowed;
	}

	/**
	 * Getter for the flag if updates auto installation is allowed.
	 * By default it is on, but you can disable it from JavaScript.
	 *
	 * @return <code>true</code> if automatic installation is enabled, <code>false</code> - otherwise.
	 */
	public boolean isAutoInstallIsAllowed() {
		return allowUpdatesAutoInstall;
	}

	/**
	 * Getter for current native interface version of the application.
	 *
	 * @return native version
	 * */
	public int getNativeInterfaceVersion() {
		return nativeInterfaceVersion;
	}

	/**
	 * Setter for current native interface version of the application.
	 *
	 * @param version version to set
	 * */
	void setNativeInterfaceVersion(int version) {
		nativeInterfaceVersion = version > 0 ? version : 1;
	}

	/**
	 * Load plugins specific preferences from Cordova's config.xml.
	 *
	 * @param context current context of the activity
	 * @return hot-code-push plugin preferences
	 */
	public static com.nordnetab.chcp.main.config.CapacitorConfig loadFromCapacitorConfig(final Context context) {
		com.nordnetab.chcp.main.config.CapacitorConfig chcpConfig = new com.nordnetab.chcp.main.config.CapacitorConfig();

		new CapacitorConfigParser().parse(context, chcpConfig);

		return chcpConfig;
	}
}
