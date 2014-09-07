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
public class cmProc {

	private String service;
	private String call;
	private List<?> args;

	// other setter and getter

	public void setService(String service) {
		this.service = service;
	}

	public void setArgs(List<?> args) {
		this.args = args;
	}

	public void setCall(String call) {
		this.call = call;
	}
	
	public static class runcmdObject {

		private int priority;
		private int uniqueKey;
		private int oldLocalUniqueKey;
		private String revision;
		private String user;
		private Boolean startInShell;
		private String cmd;
		private List<String> args;
		private List<String> env;
		private String datacin;
		private Boolean mergeCoutCerr;
		private int maxruntime;
		private int updateinterval;
		private int tracker;
		private Boolean modified;
		private Boolean toBeRemoved;

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public void setUniqueKey(int uniqueKey) {
			this.uniqueKey = uniqueKey;
		}

		public void setOldLocalUniqueKey(int oldLocalUniqueKey) {
			this.oldLocalUniqueKey = oldLocalUniqueKey;
		}

		public void setRevision(String revision) {
			this.revision = revision;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public void setStartInShell(Boolean startInShell) {
			this.startInShell = startInShell;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public void setArgs(List<String> args) {
			this.args = args;
		}

		public void setEnv(List<String> env) {
			this.env = env;
		}

		public void setDatacin(String datacin) {
			this.datacin = datacin;
		}

		public void setMergeCoutCerr(Boolean mergeCoutCerr) {
			this.mergeCoutCerr = mergeCoutCerr;
		}

		public void setMaxruntime(int maxruntime) {
			this.maxruntime = maxruntime;
		}

		public void setUpdateinterval(int updateinterval) {
			this.updateinterval = updateinterval;
		}

		public void setTracker(int tracker) {
			this.tracker = tracker;
		}

		public void setModified(Boolean modified) {
			this.modified = modified;
		}

		public void setToBeRemoved(Boolean toBeRemoved) {
			this.toBeRemoved = toBeRemoved;
		}

	}

}
