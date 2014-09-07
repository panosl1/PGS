/*
 * Copyright (c) 2004-2013 Bright Computing, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Bright Computing, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Bright Computing, Inc.
 */

package com.bright.cmcall;

import java.util.List;
@SuppressWarnings("unused")
public class jobSubmit {

	// example JSON RPC request that has to be serialized with GSON
	// {"call":"submitJob","service":"cmjob", "args":[ "slurm" , {
	// "queue":"defq","jobname":"myjob","account":"myaccount","rundirectory":"/home/support","username":"cmsupport","groupname":"cmsupport","priority":"1","stdinfile":"/home/cmsupport/stdin-openmpi","stdoutfile":"/home/cmsupport/stdout-openmpi","stderrfile":"/home/cmsupport/stderr-openmpi","mailNotify":true,"mailOptions":"ALL","maxWallClock":"00:01:00","numberOfProcesses":1,"numberOfNodes":1,"commandLineInterpreter":"/bin/bash","executable":"mpirun","arguments":"hello_mpi","debug":true}
	// ] }

	private String call;
	private String service;

	private List<?> args;

	public void setCall(String call) {
		this.call = call;
	}

	public void setService(String service) {
		this.service = service;
	}

	/*
	 * public void setArgs(List<Map<String, jobSubmit.jobObject>> args) {
	 * this.args = args; } Improper JSON serialization
	 */

	public void setArgs(List<?> args) {
		this.args = args;
	}
	
	public static class jobObject {

		
		private List<String> modules;
		private String jobID;
		private String queue;
		private String jobname;
		private String account;
		private String rundirectory;
		private String username;
		private String groupname;
		private String priority;
		private String stdinfile;
		private String stdoutfile;
		private String stderrfile;
		private List<String> resourceList;
		private List<String> dependencies;
		private Boolean mailNotify;
		private String mailOptions;
		private String mailList;
		private String maxWallClock;
		private int numberOfProcesses;
		private int numberOfNodes;
		private List<String> nodes;
		private String commandLineInterpreter;
		private List<String> userdefined;
		private String executable;
		private String arguments;
		private Boolean debug;
		private Boolean isSlurm;
		private String childType;
		private String baseType;
		private int uniqueKey;
		private Boolean modified;
		private Boolean toBeRemoved;

		public void setJobID(String jobID) {
			this.jobID = jobID;
		}

		public void setModules(List<String> modules) {
			this.modules = modules;
		}

		public void setQueue(String queue) {
			this.queue = queue;
		}

		public void setJobname(String jobname) {
			this.jobname = jobname;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public void setRundirectory(String rundirectory) {
			this.rundirectory = rundirectory;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setGroupname(String groupname) {
			this.groupname = groupname;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		public void setStdinfile(String stdinfile) {
			this.stdinfile = stdinfile;
		}

		public void setStdoutfile(String stdoutfile) {
			this.stdoutfile = stdoutfile;
		}

		public void setStderrfile(String stderrfile) {
			this.stderrfile = stderrfile;
		}

		public void setResourceList(List<String> resourceList) {
			this.resourceList = resourceList;
		}

		public void setDependencies(List<String> dependencies) {
			this.dependencies = dependencies;
		}

		public void setMailNotify(Boolean mailNotify) {
			this.mailNotify = mailNotify;
		}

		public void setMailOptions(String mailOptions) {
			this.mailOptions = mailOptions;
		}

		public void setMailList(String mailList) {
			this.mailList = mailList;
		}

		public void setMaxWallClock(String maxWallClock) {
			this.maxWallClock = maxWallClock;
		}

		public void setNumberOfProcesses(int numberOfProcesses) {
			this.numberOfProcesses = numberOfProcesses;
		}

		public void setNumberOfNodes(int numberOfNodes) {
			this.numberOfNodes = numberOfNodes;
		}

		public void setNodes(List<String> nodes) {
			this.nodes = nodes;
		}

		public void setCommandLineInterpreter(String commandLineInterpreter) {
			this.commandLineInterpreter = commandLineInterpreter;
		}

		public void setUserdefined(List<String> userdefined) {
			this.userdefined = userdefined;
		}

		public void setExecutable(String executable) {
			this.executable = executable;
		}

		public void setArguments(String arguments) {
			this.arguments = arguments;
		}

		public void setDebug(Boolean debug) {
			this.debug = debug;
		}

		public void setIsSlurm(Boolean isSlurm) {
			this.isSlurm = isSlurm;
		}

		public void setChildType(String childType) {
			this.childType = childType;
		}

		public void setBaseType(String baseType) {
			this.baseType = baseType;
		}

		public void setUniqueKey(int uniqueKey) {
			this.uniqueKey = uniqueKey;
		}

		public void setModified(Boolean modified) {
			this.modified = modified;
		}

		public void setToBeRemoved(Boolean toBeRemoved) {
			this.toBeRemoved = toBeRemoved;
		}

	}

}
