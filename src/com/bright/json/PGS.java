/*
 * Copyright (c) 2004-2013 Bright Computing, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Bright Computing, Inc. ("Confidential Information").  You shall not
* disclose such Confidential Information and shall use it only in
* accordance with the terms of the license agreement you entered into
* with Bright Computing, Inc.
*/

package com.bright.json;

import com.bright.cmcall.cmLogin;
import com.bright.cmcall.cmMain;
import com.bright.cmcall.cmLogout;
import com.bright.cmcall.cmReadFile;
import com.bright.cmcall.cmUniqueKeyMap;
import com.bright.cmcall.cmgetVersion;
import com.bright.cmcall.jobGet;
import com.bright.cmcall.jobSubmit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyStore;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;









import com.bright.utils.rmDuplicateLines;

@SuppressWarnings("deprecation")
public class PGS {

	public PGS() {
	}

	private static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	public static List<Cookie> doLogin(String user, String pass, String myURL) {
		try {
			cmLogin loginReq = new cmLogin("login", user, pass);

			GsonBuilder builder = new GsonBuilder();

			Gson g = builder.create();
			String json = g.toJson(loginReq);

			/* HttpClient httpclient = new DefaultHttpClient(); */

			HttpClient httpclient = getNewHttpClient();
			CookieStore cookieStore = new BasicCookieStore();

			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			/* httpclient = WebClientDevWrapper.wrapClient(httpclient); */

			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 1000);
			HttpConnectionParams.setSoTimeout(params, 1000);
			HttpPost httppost = new HttpPost(myURL);
			StringEntity stringEntity = new StringEntity(json);
			stringEntity.setContentType("application/json");
			httppost.setEntity(stringEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost, localContext);

			System.out.println(response + "\n");
			for (Cookie c : ((AbstractHttpClient) httpclient).getCookieStore()
					.getCookies()) {
				System.out.println("\n Cookie: " + c.toString() + "\n");
			}

			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("Local cookie: " + cookies.get(i));
			}

			HttpEntity resEntity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
				System.out.println("Chunked?: " + resEntity.isChunked());
				String message = new String(EntityUtils.toString(resEntity));
				System.out.println(message);
				return cookies;
			}
			EntityUtils.consume(resEntity);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	public static String doRequest(String jsonReq, String myURL,
			List<Cookie> cookies) {
		try {

			/* HttpClient httpclient = new DefaultHttpClient(); */

			HttpClient httpclient = getNewHttpClient();
			CookieStore cookieStore = new BasicCookieStore();
			Cookie[] cookiearray = cookies.toArray(new Cookie[0]);
			cookieStore.addCookie(cookiearray[0]);
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			/* httpclient = WebClientDevWrapper.wrapClient(httpclient); */

			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 1000);
			HttpConnectionParams.setSoTimeout(params, 1000);
			HttpPost httppost = new HttpPost(myURL);
			StringEntity stringEntity = new StringEntity(jsonReq);
			stringEntity.setContentType("application/json");
			httppost.setEntity(stringEntity);

			System.out.println("executing request " + httppost.getRequestLine()
					+ System.getProperty("line.separator") + jsonReq);

			HttpResponse response = httpclient.execute(httppost, localContext);

			System.out.println(response + "\n");
			for (Cookie c : ((AbstractHttpClient) httpclient).getCookieStore()
					.getCookies()) {
				System.out.println("\n Cookie: " + c.toString() + "\n");
			}

			HttpEntity resEntity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
				System.out.println("Chunked?: " + resEntity.isChunked());
				String message = new String(EntityUtils.toString(resEntity));
				System.out.println(message);
				return message;
			}
			EntityUtils.consume(resEntity);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	public static int fileSpooler() {

		return 0;
	}

	public static void doLogout(String cmURL, List<Cookie> cookies) {
		// Logout and purge cookie on server

		cmLogout logoutReq = new cmLogout();
		logoutReq.setService("logout");
		Gson g = new Gson();
		String json = g.toJson(logoutReq);

		JSonRequestor.doRequest(json, cmURL, cookies);

		System.out.println("Succesfully Logged Off");
	}

	public static void chkVersion(String cmURL, List<Cookie> cookies) {
		// Logout and purge cookie on server
		cmMain mainreq = new cmMain();
		mainreq.setService("cmmain");
		mainreq.setCall("getVersion");

		Gson g = new Gson();
		String json = g.toJson(mainreq);

		String ver_string = doRequest(json, cmURL, cookies);
		cmgetVersion getVer = new Gson().fromJson(ver_string,
				cmgetVersion.class);
		System.out.println("Version JSON String " + ver_string);
		String message = getVer.getCmdaemonBuild().toString();
		@SuppressWarnings("resource")
		Scanner resInt = new Scanner(message).useDelimiter("[^0-9]+");
		int build_ver = resInt.nextInt();
		if (build_ver < Constants.CMDAEM0N_MIN_BUILD) {
			JOptionPane.showMessageDialog(null, "You need CMDaemon revision "
					+ Constants.CMDAEM0N_MIN_BUILD + " or later.");
			System.out.println("You need CMDaemon revision "
					+ Constants.CMDAEM0N_MIN_BUILD + " or later.");
			System.exit(0);
			System.out.println("Succesfully Logged Off");
		}

	}

	public static int countLines(String text, String search) {
		int count = text.split(search).length - 1;
		return count;
	}
	public static Map<String, Long>  UniqueKeyMap(String cmURL, String cmService, String cmCall, List<Cookie> cookies) {
		// Logout and purge cookie on server
		cmMain catreq = new cmMain();
		catreq.setService(cmService);
		catreq.setCall(cmCall);

		Gson g = new Gson();
		String json = g.toJson(catreq);

		String ver_string = doRequest(json, cmURL, cookies);

		Gson gson = new Gson();
	
		Type listType = new TypeToken<List<cmUniqueKeyMap>>(){}.getType();
		
		//Need to de-serialize JSON as array in GSON
		List<cmUniqueKeyMap> catlist = g.fromJson(ver_string, listType);
		System.out.println("Version JSON String " + ver_string);

		Map<String, Long> map = new HashMap<String, Long>(catlist.size()); // the size of the map will be the size of the list.
		for(cmUniqueKeyMap e: catlist) {
		  map.put(e.getName(), e.getUniqueKey());
		  
		}

        return map;
		}

	
	
	
	
	public static void main(String[] args) {
		String fileBasename = null;
		
		JFileChooser chooser = new JFileChooser("/Users/panos/STR_GRID");
		try {

			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select the input directory");

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): "
						+ chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : "
						+ chooser.getSelectedFile());

				// String fileBasename =
				// chooser.getSelectedFile().toString().substring(chooser.getSelectedFile().toString().lastIndexOf(File.separator)+1,chooser.getSelectedFile().toString().lastIndexOf("."));
				fileBasename = chooser
						.getSelectedFile()
						.toString()
						.substring(
								chooser.getSelectedFile().toString()
										.lastIndexOf(File.separator) + 1);
				System.out.println("Base name: " + fileBasename);



			} else {
				System.out.println("No Selection ");

			}
		} catch (Exception e) {

			System.out.println(e.toString());

		}

		JTextField uiHost = new JTextField("demo.brightcomputing.com");
		// TextPrompt puiHost = new
		// TextPrompt("demo.brightcomputing.com",uiHost);
		JTextField uiUser = new JTextField("root");
		// TextPrompt puiUser = new TextPrompt("root", uiUser);
		JTextField uiPass = new JPasswordField("x5deix5dei");
		// TextPrompt puiPass = new TextPrompt("x5deix5dei", uiPass);
		

		JPanel myPanel = new JPanel(new GridLayout(5, 1));
		myPanel.add(new JLabel("Bright HeadNode hostname:"));
		myPanel.add(uiHost);
		// myPanel.add(Box.createHorizontalStrut(1)); // a spacer
		myPanel.add(new JLabel("Username:"));
		myPanel.add(uiUser);
		myPanel.add(new JLabel("Password:"));
		myPanel.add(uiPass);
		

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please fill in all the fields.", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.out.println("Input received.");

		}

	
		String rhost = uiHost.getText();
		String ruser = uiUser.getText();
		String rpass = uiPass.getText();
		



		String cmURL = "https://" + rhost + ":8081/json";
		List<Cookie> cookies = doLogin(ruser, rpass, cmURL);
		chkVersion(cmURL, cookies);
		
		Map<String, Long> categories = UniqueKeyMap(cmURL, "cmdevice","getCategories", cookies);
		Map<String, Long> networks = UniqueKeyMap(cmURL, "cmnet","getNetworks", cookies);
		Map<String, Long> partitions = UniqueKeyMap(cmURL, "cmpart","getPartitions", cookies);
		
		System.out.println(categories.get("default"));
		
		doLogout(cmURL, cookies);
		System.exit(0);
	}

}
