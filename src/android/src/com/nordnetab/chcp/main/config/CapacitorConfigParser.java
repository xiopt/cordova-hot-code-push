package com.nordnetab.chcp.main.config;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.cordova.ConfigXmlParser;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Alin Stefan on 01.10.2020
 * <p/>
 * JSON parser for Capacitor config.
 * Used to read plugin specific preferences.
 *
 * @see CapacitorConfig
 */
class CapacitorConfigParser {

	private static class JsonKeys {

		private JsonKeys() {
		}

		public static final String CONFIG_FILE_URL = "configUrl";
		public static final String AUTO_DOWNLOAD = "autoDownload";
		public static final String AUTO_INSTALL = "autoInstall";
		public static final String NATIVE_INTERFACE_VERSION = "nativeInterfaceVersion";

	}

	private CapacitorConfig chcpConfig;

	/**
	 * Parse capacitor.config.json.
	 * Result is set into passed CapacitorConfig instance.
	 *
	 * @param context    current context
	 * @param chcpConfig config instance to which we will set preferences from capacitor.config.json
	 * @see CapacitorConfig
	 */
	public void parse(final Context context, final CapacitorConfig chcpConfig) {
		this.chcpConfig = chcpConfig;

		final AssetManager assetManager = context.getResources().getAssets();
		final StringBuilder returnString = new StringBuilder();
		BufferedReader reader = null;

		try {
			InputStreamReader isr = new InputStreamReader(assetManager.open(CapacitorConfig.CAPACITOR_CONFIG_FILE_NAME));
			reader = new BufferedReader(isr);
			String line;
			while ((line = reader.readLine()) != null) {
				returnString.append(line);
			}
			JsonNode json = new ObjectMapper().readTree(returnString.toString()).get("plugins").get("HotCodePush");

			if(json.has(JsonKeys.CONFIG_FILE_URL)) {
				chcpConfig.setConfigUrl(json.get(JsonKeys.CONFIG_FILE_URL).asText());
			}

			if(json.has(JsonKeys.AUTO_DOWNLOAD)) {
				chcpConfig.allowUpdatesAutoDownload(json.get(JsonKeys.AUTO_DOWNLOAD).asBoolean());
			}

			if(json.has(JsonKeys.AUTO_INSTALL)) {
				chcpConfig.allowUpdatesAutoInstall(json.get(JsonKeys.AUTO_INSTALL).asBoolean());
			}

			if(json.has(JsonKeys.NATIVE_INTERFACE_VERSION)) {
				chcpConfig.setNativeInterfaceVersion(json.get(JsonKeys.NATIVE_INTERFACE_VERSION).asInt());
			}

		} catch (Exception e) {
			Log.d("CHCP", "Failed to read " + CapacitorConfig.CAPACITOR_CONFIG_FILE_NAME + " from assets", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e2) {
				Log.d("CHCP", "Failed to clear resources after reading  " + CapacitorConfig.CAPACITOR_CONFIG_FILE_NAME +  " from the assets", e2);
			}
		}


	}



}
