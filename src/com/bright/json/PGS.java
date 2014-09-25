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

import com.bright.cmcall.cmDevice;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;

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
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import com.bright.utils.Delete;
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

	public static Map<String, Long> UniqueKeyMap(String cmURL,
			String cmService, String cmCall, List<Cookie> cookies) {
		// Logout and purge cookie on server
		cmMain catreq = new cmMain();
		catreq.setService(cmService);
		catreq.setCall(cmCall);

		Gson g = new Gson();
		String json = g.toJson(catreq);

		String ver_string = doRequest(json, cmURL, cookies);

		Gson gson = new Gson();

		Type listType = new TypeToken<List<cmUniqueKeyMap>>() {
		}.getType();

		// Need to de-serialize JSON as array in GSON
		List<cmUniqueKeyMap> catlist = g.fromJson(ver_string, listType);
		System.out.println("Version JSON String " + ver_string);

		Map<String, Long> map = new HashMap<String, Long>(catlist.size()); // the
		// size
		// of
		// the
		// map
		// will
		// be
		// the
		// size
		// of
		// the
		// list.
		System.out.println("Service: "+ cmService);
		for (cmUniqueKeyMap e : catlist) {
			if (cmCall.equals("getEthernetSwitches")) {
				map.put(e.getHostname(), e.getUniqueKey());
				System.out.println("Hostname: "+ e.getHostname());
				System.out.println("HKey: "+ e.getUniqueKey());

			} else {
				map.put(e.getName(), e.getUniqueKey());
			}
		}

		return map;
	}

	public static void main(String[] args) throws FileNotFoundException {
		String fileBasename = null;

		JFileChooser chooser = new JFileChooser();
		try {
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Excel Spreadsheets", "xls", "xlsx");
			chooser.setFileFilter(filter);
			chooser.setCurrentDirectory(new java.io.File(System
					.getProperty("user.home")));
			chooser.setDialogTitle("Select the Excel file");

			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
		String fileName = chooser.getSelectedFile().toString();
		InputStream inp = new FileInputStream(fileName);
		Workbook workbook = null;
		if (fileName.toLowerCase().endsWith("xlsx")) {
			try {
				workbook = new XSSFWorkbook(inp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (fileName.toLowerCase().endsWith("xls")) {
			try {
				workbook = new HSSFWorkbook(inp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Sheet nodeSheet = workbook.getSheet("Devices");
		Sheet interfaceSheet = workbook.getSheet("Interfaces");
		System.out.println("Read nodes sheet.");
		// Row nodeRow = nodeSheet.getRow(1);
		// System.out.println(row.getCell(0).toString());
		// System.exit(0);

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

		Map<String, Long> categories = UniqueKeyMap(cmURL, "cmdevice",
				"getCategories", cookies);
		Map<String, Long> networks = UniqueKeyMap(cmURL, "cmnet",
				"getNetworks", cookies);
		Map<String, Long> partitions = UniqueKeyMap(cmURL, "cmpart",
				"getPartitions", cookies);
		Map<String, Long> racks = UniqueKeyMap(cmURL, "cmpart", "getRacks",
				cookies);
		Map<String, Long> switches = UniqueKeyMap(cmURL, "cmdevice",
				"getEthernetSwitches", cookies);
		//System.out.println(switches.get("switch01"));
		//System.out.println("Size of the map: "+ switches.size());
        //System.exit(0);
		cmDevice newnode = new cmDevice();
		cmDevice.deviceObject devObj = new cmDevice.deviceObject();
		cmDevice.switchObject switchObj = new cmDevice.switchObject();
		cmDevice.netObject netObj = new cmDevice.netObject();

		List<String> emptyslist = new ArrayList<String>();

		Row nodeRow = nodeSheet.getRow(1);
		Row ifRow = interfaceSheet.getRow(1);
		// System.out.println(nodeRow.getCell(0).toString());
		// nodeRow.getCell(3).getStringCellValue()

		newnode.setService("cmdevice");
		newnode.setCall("addDevice");

		switchObj.setEthernetSwitch(switches.get(nodeRow.getCell(8)
				.getStringCellValue()));
		System.out.println(nodeRow.getCell(8).getStringCellValue());
		System.out.println(switches
				.get(nodeRow.getCell(8).getStringCellValue()));
		switchObj.setPrt((int) nodeRow.getCell(9).getNumericCellValue());
		switchObj.setBaseType("SwitchPort");
		netObj.setBaseType("NetworkInterface");

		netObj.setCardType(ifRow.getCell(3).getStringCellValue());
		netObj.setChildType(ifRow.getCell(4).getStringCellValue());
		netObj.setDhcp((ifRow.getCell(5).getNumericCellValue() > 0.1) ? true
				: false);
		netObj.setIp(ifRow.getCell(7).getStringCellValue());
		// netObj.setMac(ifRow.getCell(0).toString());
		netObj.setModified(true);
		netObj.setName(ifRow.getCell(1).getStringCellValue());
		netObj.setNetwork(networks.get(ifRow.getCell(6).getStringCellValue()));
		netObj.setOldLocalUniqueKey(0L);
		netObj.setRevision("");
		netObj.setSpeed(ifRow.getCell(9).getStringCellValue());
		netObj.setStartIf("ALWAYS");
		netObj.setToBeRemoved(false);
		netObj.setUniqueKey((long) ifRow.getCell(2).getNumericCellValue());
		// netObj.setAdditionalHostnames(emptyslist);

		devObj.setBaseType("Device");
		//devObj.setCreationTime(0L);
		devObj.setCustomPingScript("");
		devObj.setCustomPingScriptArgument("");
		devObj.setCustomPowerScript("");
		devObj.setCustomPowerScriptArgument("");
		devObj.setCustomRemoteConsoleScript("");
		devObj.setCustomRemoteConsoleScriptArgument("");
		devObj.setDisksetup("");
		devObj.setBmcPowerResetDelay(0L);
		devObj.setBurning(false);
		devObj.setEthernetSwitch(switchObj);
		devObj.setExcludeListFull("");
		devObj.setExcludeListGrab("");
		devObj.setExcludeListGrabnew("");
		devObj.setExcludeListManipulateScript("");
		devObj.setExcludeListSync("");
		devObj.setExcludeListUpdate("");
		devObj.setFinalize("");
		devObj.setRack(racks.get(nodeRow.getCell(10).getStringCellValue()));
		devObj.setRackHeight((long) nodeRow.getCell(11).getNumericCellValue());
		devObj.setRackPosition((long) nodeRow.getCell(12).getNumericCellValue());
		devObj.setIndexInsideContainer(0L);
		devObj.setInitialize("");
		devObj.setInstallBootRecord(false);
		devObj.setInstallMode("");
		devObj.setIoScheduler("");
		devObj.setLastProvisioningNode(0L);
		devObj.setMac(nodeRow.getCell(6).getStringCellValue());

		devObj.setModified(true);
		devObj.setCategory(categories.get(nodeRow.getCell(1)
				.getStringCellValue()));
		devObj.setChildType("PhysicalNode");
		devObj.setDatanode((nodeRow.getCell(5).getNumericCellValue() > 0.1) ? true
				: false);
		devObj.setHostname(nodeRow.getCell(0).getStringCellValue());
		devObj.setModified(true);
		devObj.setPartition(partitions.get(nodeRow.getCell(2)
				.getStringCellValue()));
		devObj.setUseExclusivelyFor("Category");

		devObj.setNextBootInstallMode("");
		devObj.setNotes("");
		devObj.setOldLocalUniqueKey(0L);

		devObj.setPowerControl("none");
		devObj.setManagementNetwork(0L);

		devObj.setProvisioningNetwork(1L);
		devObj.setProvisioningTransport("RSYNCDAEMON");
		devObj.setPxelabel("");
		// "rack": 90194313218,
		// "rackHeight": 1,
		// "rackPosition": 4,
		devObj.setRaidconf("");
		devObj.setRevision("");

		devObj.setSoftwareImageProxy(null);
		devObj.setStartNewBurn(false);

		devObj.setTag("00000000a000");
		devObj.setToBeRemoved(false);
		// devObj.setUcsInfoConfigured(null);
		// devObj.setUniqueKey(12345L);

		devObj.setUserdefined1("");
		devObj.setUserdefined2("");

		ArrayList<Object> mylist = new ArrayList<Object>();

		ArrayList<cmDevice.netObject> mylist2 = new ArrayList<cmDevice.netObject>();
		ArrayList<Object> emptylist = new ArrayList<Object>();

		devObj.setNetworks(mylist2);
		devObj.setFsexports(emptylist);
		devObj.setFsmounts(emptylist);
		devObj.setGpuSettings(emptylist);
		devObj.setFspartAssociations(emptylist);

		devObj.setPowerDistributionUnits(emptyslist);

		devObj.setRoles(emptylist);
		devObj.setServices(emptylist);
		devObj.setStaticRoutes(emptylist);

		mylist2.add(netObj);

		mylist.add(devObj);
		mylist.add(1);
		newnode.setArgs(mylist);

		GsonBuilder builder = new GsonBuilder();
		builder.enableComplexMapKeySerialization();

		// Gson g = new Gson();
		Gson g = builder.create();

		String json2 = g.toJson(newnode);

		// To be used from a real console and not Eclipse

		String message = JSonRequestor.doRequest(json2, cmURL, cookies);

		doLogout(cmURL, cookies);
		System.exit(0);
	}

}
