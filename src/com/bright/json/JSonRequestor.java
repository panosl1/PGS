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
import com.bright.cmcall.cmgetVersion;
import com.bright.cmcall.jobGet;
import com.bright.cmcall.jobSubmit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

import com.bright.utils.Delete;
import com.bright.utils.rmDuplicateLines;

@SuppressWarnings("deprecation")
public class JSonRequestor {

	public JSonRequestor() {
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
			cmLogin loginReq = new cmLogin("login",user,pass);
			

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

			System.out
					.println("executing request " + httppost.getRequestLine() + System.getProperty("line.separator") + jsonReq );
			
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
		cmgetVersion getVer = new Gson().fromJson(ver_string, cmgetVersion.class);
		System.out.println("Version JSON String " + ver_string);
		String message = getVer.getCmdaemonBuild().toString();
		@SuppressWarnings("resource")
		Scanner resInt = new Scanner(message).useDelimiter("[^0-9]+");
		int build_ver = resInt.nextInt();
		if (build_ver < Constants.CMDAEM0N_MIN_BUILD){
			JOptionPane.showMessageDialog(null, "You need CMDaemon revision " + Constants.CMDAEM0N_MIN_BUILD + " or later.");
			System.out.println("You need CMDaemon revision " + Constants.CMDAEM0N_MIN_BUILD + " or later.");
			System.exit(0); 
			System.out.println("Succesfully Logged Off");
		}

		
	}
 
	public static int countLines(String text, String search) {
	    int count = text.split(search).length - 1;
	    return count;
	}
	
	
	public static void main(String[] args) {
		String fileBasename = null;
		String[] zipArgs = null;
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

				zipArgs = new String[] {
						chooser.getSelectedFile().toString(),
						chooser.getCurrentDirectory().toString()
								+ File.separator + fileBasename + ".zip" };
				com.bright.utils.ZipFile.main(zipArgs);

			} else {
				System.out.println("No Selection ");

			}
		} catch (Exception e) {

			System.out.println(e.toString());

		}

		JTextField uiHost = new JTextField("ucs-head.brightcomputing.com");
		//TextPrompt puiHost = new TextPrompt("hadoop.brightcomputing.com",uiHost);
		JTextField uiUser = new JTextField("nexus");
		//TextPrompt puiUser = new TextPrompt("nexus", uiUser);
		JTextField uiPass = new JPasswordField("system");
		//TextPrompt puiPass = new TextPrompt("", uiPass);
		JTextField uiWdir = new JTextField("/home/nexus/pp1234");
		//TextPrompt puiWdir = new TextPrompt("/home/nexus/nexus_workdir", uiWdir);
		JTextField uiOut = new JTextField("foo");
		//TextPrompt puiOut = new TextPrompt("foobar123", uiOut);

		JPanel myPanel = new JPanel(new GridLayout(5, 1));
		myPanel.add(new JLabel("Bright HeadNode hostname:"));
		myPanel.add(uiHost);
		// myPanel.add(Box.createHorizontalStrut(1)); // a spacer
		myPanel.add(new JLabel("Username:"));
		myPanel.add(uiUser);
		myPanel.add(new JLabel("Password:"));
		myPanel.add(uiPass);
		myPanel.add(new JLabel("Working Directory:"));
		myPanel.add(uiWdir);
		// myPanel.add(Box.createHorizontalStrut(1)); // a spacer
		myPanel.add(new JLabel("Output Study Name ( -s ):"));
		myPanel.add(uiOut);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please fill in all the fields.", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.out.println("Input received.");

		}

		String rfile = uiWdir.getText();
		String rhost = uiHost.getText();
		String ruser = uiUser.getText();
		String rpass = uiPass.getText();
		String nexusOut = uiOut.getText();

		String[] myarg = new String[] { zipArgs[1],
				ruser + "@" + rhost + ":" + rfile, nexusOut, fileBasename };
		com.bright.utils.ScpTo.main(myarg);

		String cmURL = "https://" + rhost + ":8081/json";
		List<Cookie> cookies = doLogin(ruser, rpass, cmURL);
		chkVersion(cmURL, cookies);

		jobSubmit myjob = new jobSubmit();
		jobSubmit.jobObject myjobObj = new jobSubmit.jobObject();

		myjob.setService("cmjob");
		myjob.setCall("submitJob");

		myjobObj.setQueue("defq");
		myjobObj.setJobname("myNexusJob");
		myjobObj.setAccount(ruser);
		myjobObj.setRundirectory(rfile);
		myjobObj.setUsername(ruser);
		myjobObj.setGroupname("cmsupport");
		myjobObj.setPriority("1");
		myjobObj.setStdinfile(rfile + "/stdin-mpi");
		myjobObj.setStdoutfile(rfile + "/stdout-mpi");
		myjobObj.setStderrfile(rfile + "/stderr-mpi");
		myjobObj.setResourceList(Arrays.asList(""));
		myjobObj.setDependencies(Arrays.asList(""));
		myjobObj.setMailNotify(false);
		myjobObj.setMailOptions("ALL");
		myjobObj.setMaxWallClock("00:10:00");
		myjobObj.setNumberOfProcesses(1);
		myjobObj.setNumberOfNodes(1);
		myjobObj.setNodes(Arrays.asList(""));
		myjobObj.setCommandLineInterpreter("/bin/bash");
		myjobObj.setUserdefined(Arrays.asList("cd " + rfile, "date", "pwd"));
		myjobObj.setExecutable("mpirun");
		myjobObj.setArguments("-env I_MPI_FABRICS shm:tcp "
				+ Constants.NEXUSSIM_EXEC + " -mpi -c " + rfile + "/"
				+ fileBasename + "/" + fileBasename + " -s " + rfile + "/"
				+ fileBasename + "/" + nexusOut);
		myjobObj.setModules(Arrays.asList("shared", "nexus", "intel-mpi/64"));
		myjobObj.setDebug(false);
		myjobObj.setBaseType("Job");
		myjobObj.setIsSlurm(true);
		myjobObj.setUniqueKey(0);
		myjobObj.setModified(false);
		myjobObj.setToBeRemoved(false);
		myjobObj.setChildType("SlurmJob");
		myjobObj.setJobID("Nexus test");

		// Map<String,jobSubmit.jobObject > mymap= new HashMap<String,
		// jobSubmit.jobObject>();
		// mymap.put("Slurm",myjobObj);
		ArrayList<Object> mylist = new ArrayList<Object>();
		mylist.add("slurm");
		mylist.add(myjobObj);
		myjob.setArgs(mylist);

		GsonBuilder builder = new GsonBuilder();
		builder.enableComplexMapKeySerialization();

		// Gson g = new Gson();
		Gson g = builder.create();

	
		String json2 = g.toJson(myjob);
		

		// To be used from a real console and not Eclipse
		Delete.main(zipArgs[1]);
		String message = JSonRequestor.doRequest(json2, cmURL, cookies);
		@SuppressWarnings("resource")
		Scanner resInt = new Scanner(message).useDelimiter("[^0-9]+");
		int jobID = resInt.nextInt();
		System.out.println("Job ID: " + jobID);

		JOptionPane optionPane = new JOptionPane(message);
		JDialog myDialog = optionPane.createDialog(null, "CMDaemon response: ");
		myDialog.setModal(false);
		myDialog.setVisible(true);

		ArrayList<Object> mylist2 = new ArrayList<Object>();
		mylist2.add("slurm");
		String JobID = Integer.toString(jobID);
		mylist2.add(JobID);
		myjob.setArgs(mylist2);
		myjob.setService("cmjob");
		myjob.setCall("getJob");
		String json3 = g.toJson(myjob);
		System.out.println("JSON Request No. 4 " + json3);

		cmReadFile readfile = new cmReadFile();
		readfile.setService("cmmain");
		readfile.setCall("readFile");
		readfile.setUserName(ruser);
		
		int fileByteIdx= 1;
		
		readfile.setPath(rfile + "/" + fileBasename + "/" + fileBasename
				+ ".sum@+" + fileByteIdx );
		String json4 = g.toJson(readfile);
		
		String monFile = JSonRequestor.doRequest(json4, cmURL, cookies).replaceAll("^\"|\"$", "");
		if(monFile.startsWith("Unable")){
		monFile = "";	
		}
		else{
			fileByteIdx += countLines(monFile, "\\\\n");
			System.out.println("");
		}
				
		
		StringBuffer output = new StringBuffer();
		// Get the correct Line Separator for the OS (CRLF or LF)
		String nl = System.getProperty("line.separator");
		String filename = chooser.getCurrentDirectory().toString()
							+ File.separator + fileBasename + ".sum.txt";
		System.out.println("Local monitoring file: " +  filename);
		
		output.append(monFile.replaceAll("\\\\n",
				System.getProperty("line.separator")));
		
		
		
		String getJobJSON = JSonRequestor.doRequest(json3, cmURL, cookies);
		jobGet getJobObj = new Gson().fromJson(getJobJSON, jobGet.class);
		System.out.println("Job " + jobID + " status: "
				+ getJobObj.getStatus().toString());

		while (getJobObj.getStatus().toString().equals("RUNNING")
				|| getJobObj.getStatus().toString().equals("COMPLETING")) {
			try {

				getJobJSON = JSonRequestor.doRequest(json3, cmURL, cookies);
				getJobObj = new Gson().fromJson(getJobJSON, jobGet.class);
				System.out.println("Job " + jobID + " status: "
						+ getJobObj.getStatus().toString());
				
				readfile.setPath(rfile + "/" + fileBasename + "/" + fileBasename
						+ ".sum@+" + fileByteIdx );
				json4 = g.toJson(readfile);
				monFile = JSonRequestor.doRequest(json4, cmURL, cookies).replaceAll("^\"|\"$", "");
				if(monFile.startsWith("Unable")){
					monFile = "";	
					}
				else{
					
				
				output.append(monFile.replaceAll("\\\\n",
						System.getProperty("line.separator")));
				System.out.println("FILE INDEX:" + fileByteIdx);
				fileByteIdx += countLines(monFile, "\\\\n");
				}
				Thread.sleep(Constants.STATUS_CHECK_INTERVAL);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		}

		Gson gson_nice = new GsonBuilder().setPrettyPrinting().create();
		String json_out = gson_nice.toJson(getJobJSON);
		System.out.println(json_out);
		System.out.println("JSON Request No. 5 " + json4);
		
		readfile.setPath(rfile + "/" + fileBasename + "/" + fileBasename
				+ ".sum@+" + fileByteIdx );
		json4 = g.toJson(readfile);
		monFile = JSonRequestor.doRequest(json4, cmURL, cookies).replaceAll("^\"|\"$", "");
		if(monFile.startsWith("Unable")){
			monFile = "";	
			}
		else{
			
		
		output.append(monFile.replaceAll("\\\\n",
				System.getProperty("line.separator")));
		fileByteIdx += countLines(monFile, "\\\\n");
		}
		System.out.println("FILE INDEX:" + fileByteIdx);
		
/*		System.out.print("Monitoring file: "
				+ monFile.replaceAll("\\n",
						System.getProperty("line.separator")));
		try {
			FileUtils.writeStringToFile(
					new File(chooser.getCurrentDirectory().toString()
							+ File.separator + fileBasename + ".sum.txt"),
					monFile.replaceAll("\\n",
							System.getProperty("line.separator")));
		} catch (IOException e) {

			e.printStackTrace();
		}*/

		if (getJobObj.getStatus().toString().equals("COMPLETED")) {
			String[] zipArgs_from = new String[] {
					chooser.getSelectedFile().toString(),
					chooser.getCurrentDirectory().toString() + File.separator
							+ fileBasename + "_out.zip" };
			String[] myarg_from = new String[] {
					ruser + "@" + rhost + ":" + rfile + "/" + fileBasename
							+ "_out.zip", zipArgs_from[1], rfile, fileBasename };
			com.bright.utils.ScpFrom.main(myarg_from);

			

			
			
			JOptionPane optionPaneS = new JOptionPane(
					"Job execution completed without errors!");
			JDialog myDialogS = optionPaneS.createDialog(null, "Job status: ");
			myDialogS.setModal(false);
			myDialogS.setVisible(true);

		} else {
			JOptionPane optionPaneF = new JOptionPane("Job execution FAILED!");
			JDialog myDialogF = optionPaneF.createDialog(null, "Job status: ");
			myDialogF.setModal(false);
			myDialogF.setVisible(true);
		}
		
		try {
			System.out.println("Local monitoring file: " +  filename);
			
			  BufferedWriter out = new BufferedWriter(
			                       new FileWriter(filename));
			  String outText = output.toString();
			  String newString = outText.replace("\\\\n", nl);
			  
			  System.out.println("Text: " + outText);
			  out.write(newString);
		
			  out.close();
			  rmDuplicateLines.main(filename);
			    }
			catch (IOException e)
			    {
			    e.printStackTrace();
			    }
		doLogout(cmURL, cookies);
        System.exit(0);
	}

}
