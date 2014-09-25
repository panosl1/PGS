package com.bright.cmcall;

import com.bright.cmcall.cmDevice.switchObject;

public class cmUniqueKeyMap {

	private long uniqueKey;
	private String name;
	private String hostname;
	private switchObject ethernetSwitch;

	public long getUniqueKey() {
		return uniqueKey;
	}

	public String getName() {
		return name;
	}

	public String getHostname() {
		return hostname;
	}

	public switchObject getEthernetSwitch() {
		return ethernetSwitch;
	}

	public void setEthernetSwitch(switchObject ethernetSwitch) {
		this.ethernetSwitch = ethernetSwitch;
	}



}
