package com.bright.cmcall;

import java.util.List;

@SuppressWarnings("unused")
public class cmDevice {

	private String call;
	private String service;

	private List<?> args;

	public void setCall(String call) {
		this.call = call;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setArgs(List<?> args) {
		this.args = args;
	}

	public static class deviceObject {

		private String baseType;
		private Long bmcPowerResetDelay;
		private List<?> burnConfig;
		private Boolean burning;
		private Long category;
		private String childType;
		private String cmdaemonUrl;
		private String cpuspeedGovernor;
		private Long creationTime;
		private String customPingScript;
		private String customPingScriptArgument;
		private String customPowerScript;
		private String customPowerScriptArgument;
		private String customRemoteConsoleScript;
		private String customRemoteConsoleScriptArgument;
		private Boolean datanode;
		private String disksetup;
		private String excludeListFull;
		private String excludeListGrab;
		private String excludeListGrabnew;
		private String excludeListManipulateScript;
		private String excludeListSync;
		private String excludeListUpdate;
		private String finalize;
		private List<?> fsexports;
		private List<?> fsmounts;
		private List<?> fspartAssociations;
		private List<?> gpuSettings;
		private String hostname;
		private Long indexInsideContainer;
		private String initialize;
		private Boolean installBootRecord;
		private String installMode;
		private String ioScheduler;
		private Long lastProvisioningNode;
		private String mac;
		private Long managementNetwork;
		private Boolean modified;
		private switchObject ethernetSwitch;

		private String nextBootInstallMode;
		private String notes;
		private Long oldLocalUniqueKey;
		private Long partition;
		private String powerControl;
		private List<String> powerDistributionUnits;
		private Long provisioningNetwork;
		private String provisioningTransport;
		private String pxelabel;
		private Long rack;
		private Long rackHeight;
		private Long rackPosition;
		private String raidconf;
		private String revision;
		private List<?> roles;
		private List<?> services;
		private String softwareImageProxy;
		private Boolean startNewBurn;
		private List<?> staticRoutes;
		private String tag;
		private Boolean toBeRemoved;
		private Boolean ucsInfoConfigured;
		private Long uniqueKey;
		private String useExclusivelyFor;
		private String userdefined1;
		private String userdefined2;
		private List<netObject> networks;

		public switchObject getEthernetSwitch() {
			return ethernetSwitch;
		}

		public void setEthernetSwitch(switchObject ethernetSwitch) {
			this.ethernetSwitch = ethernetSwitch;
		}

		public String getBaseType() {
			return baseType;
		}

		public void setBaseType(String baseType) {
			this.baseType = baseType;
		}

		public Long getBmcPowerResetDelay() {
			return bmcPowerResetDelay;
		}

		public void setBmcPowerResetDelay(Long bmcPowerResetDelay) {
			this.bmcPowerResetDelay = bmcPowerResetDelay;
		}

		public List<?> getBurnConfig() {
			return burnConfig;
		}

		public void setBurnConfig(List<?> burnConfig) {
			this.burnConfig = burnConfig;
		}

		public Boolean getBurning() {
			return burning;
		}

		public void setBurning(Boolean burning) {
			this.burning = burning;
		}

		public Long getCategory() {
			return category;
		}

		public void setCategory(Long category) {
			this.category = category;
		}

		public String getChildType() {
			return childType;
		}

		public void setChildType(String childType) {
			this.childType = childType;
		}

		public String getCmdaemonUrl() {
			return cmdaemonUrl;
		}

		public void setCmdaemonUrl(String cmdaemonUrl) {
			this.cmdaemonUrl = cmdaemonUrl;
		}

		public String getCpuspeedGovernor() {
			return cpuspeedGovernor;
		}

		public void setCpuspeedGovernor(String cpuspeedGovernor) {
			this.cpuspeedGovernor = cpuspeedGovernor;
		}

		public Long getCreationTime() {
			return creationTime;
		}

		public void setCreationTime(Long creationTime) {
			this.creationTime = creationTime;
		}

		public String getCustomPingScript() {
			return customPingScript;
		}

		public void setCustomPingScript(String customPingScript) {
			this.customPingScript = customPingScript;
		}

		public String getCustomPingScriptArgument() {
			return customPingScriptArgument;
		}

		public void setCustomPingScriptArgument(String customPingScriptArgument) {
			this.customPingScriptArgument = customPingScriptArgument;
		}

		public String getCustomPowerScript() {
			return customPowerScript;
		}

		public void setCustomPowerScript(String customPowerScript) {
			this.customPowerScript = customPowerScript;
		}

		public String getCustomPowerScriptArgument() {
			return customPowerScriptArgument;
		}

		public void setCustomPowerScriptArgument(
				String customPowerScriptArgument) {
			this.customPowerScriptArgument = customPowerScriptArgument;
		}

		public String getCustomRemoteConsoleScript() {
			return customRemoteConsoleScript;
		}

		public void setCustomRemoteConsoleScript(
				String customRemoteConsoleScript) {
			this.customRemoteConsoleScript = customRemoteConsoleScript;
		}

		public String getCustomRemoteConsoleScriptArgument() {
			return customRemoteConsoleScriptArgument;
		}

		public void setCustomRemoteConsoleScriptArgument(
				String customRemoteConsoleScriptArgument) {
			this.customRemoteConsoleScriptArgument = customRemoteConsoleScriptArgument;
		}

		public Boolean getDatanode() {
			return datanode;
		}

		public void setDatanode(Boolean datanode) {
			this.datanode = datanode;
		}

		public String getDisksetup() {
			return disksetup;
		}

		public void setDisksetup(String disksetup) {
			this.disksetup = disksetup;
		}

		public String getExcludeListFull() {
			return excludeListFull;
		}

		public void setExcludeListFull(String excludeListFull) {
			this.excludeListFull = excludeListFull;
		}

		public String getExcludeListGrab() {
			return excludeListGrab;
		}

		public void setExcludeListGrab(String excludeListGrab) {
			this.excludeListGrab = excludeListGrab;
		}

		public String getExcludeListGrabnew() {
			return excludeListGrabnew;
		}

		public void setExcludeListGrabnew(String excludeListGrabnew) {
			this.excludeListGrabnew = excludeListGrabnew;
		}

		public String getExcludeListManipulateScript() {
			return excludeListManipulateScript;
		}

		public void setExcludeListManipulateScript(
				String excludeListManipulateScript) {
			this.excludeListManipulateScript = excludeListManipulateScript;
		}

		public String getExcludeListSync() {
			return excludeListSync;
		}

		public void setExcludeListSync(String excludeListSync) {
			this.excludeListSync = excludeListSync;
		}

		public String getExcludeListUpdate() {
			return excludeListUpdate;
		}

		public void setExcludeListUpdate(String excludeListUpdate) {
			this.excludeListUpdate = excludeListUpdate;
		}

		public String getFinalize() {
			return finalize;
		}

		public void setFinalize(String finalize) {
			this.finalize = finalize;
		}

		public List<?> getFsexports() {
			return fsexports;
		}

		public void setFsexports(List<?> fsexports) {
			this.fsexports = fsexports;
		}

		public List<?> getFsmounts() {
			return fsmounts;
		}

		public void setFsmounts(List<?> fsmounts) {
			this.fsmounts = fsmounts;
		}

		public List<?> getFspartAssociations() {
			return fspartAssociations;
		}

		public void setFspartAssociations(List<?> fspartAssociations) {
			this.fspartAssociations = fspartAssociations;
		}

		public List<?> getGpuSettings() {
			return gpuSettings;
		}

		public void setGpuSettings(List<?> gpuSettings) {
			this.gpuSettings = gpuSettings;
		}

		public String getHostname() {
			return hostname;
		}

		public void setHostname(String hostname) {
			this.hostname = hostname;
		}

		public Long getIndexInsideContainer() {
			return indexInsideContainer;
		}

		public void setIndexInsideContainer(Long indexInsideContainer) {
			this.indexInsideContainer = indexInsideContainer;
		}

		public String getInitialize() {
			return initialize;
		}

		public void setInitialize(String initialize) {
			this.initialize = initialize;
		}

		public Boolean getInstallBootRecord() {
			return installBootRecord;
		}

		public void setInstallBootRecord(Boolean installBootRecord) {
			this.installBootRecord = installBootRecord;
		}

		public String getInstallMode() {
			return installMode;
		}

		public void setInstallMode(String installMode) {
			this.installMode = installMode;
		}

		public String getIoScheduler() {
			return ioScheduler;
		}

		public void setIoScheduler(String ioScheduler) {
			this.ioScheduler = ioScheduler;
		}

		public Long getLastProvisioningNode() {
			return lastProvisioningNode;
		}

		public void setLastProvisioningNode(Long lastProvisioningNode) {
			this.lastProvisioningNode = lastProvisioningNode;
		}

		public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public Long getManagementNetwork() {
			return managementNetwork;
		}

		public void setManagementNetwork(Long managementNetwork) {
			this.managementNetwork = managementNetwork;
		}

		public Boolean getModified() {
			return modified;
		}

		public void setModified(Boolean modified) {
			this.modified = modified;
		}

		public String getNextBootInstallMode() {
			return nextBootInstallMode;
		}

		public void setNextBootInstallMode(String nextBootInstallMode) {
			this.nextBootInstallMode = nextBootInstallMode;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public Long getOldLocalUniqueKey() {
			return oldLocalUniqueKey;
		}

		public void setOldLocalUniqueKey(Long l) {
			this.oldLocalUniqueKey = l;
		}

		public Long getPartition() {
			return partition;
		}

		public void setPartition(Long partition) {
			this.partition = partition;
		}

		public String getPowerControl() {
			return powerControl;
		}

		public void setPowerControl(String powerControl) {
			this.powerControl = powerControl;
		}

		public List<String> getPowerDistributionUnits() {
			return powerDistributionUnits;
		}

		public void setPowerDistributionUnits(
				List<String> powerDistributionUnits) {
			this.powerDistributionUnits = powerDistributionUnits;
		}

		public Long getProvisioningNetwork() {
			return provisioningNetwork;
		}

		public void setProvisioningNetwork(Long provisioningNetwork) {
			this.provisioningNetwork = provisioningNetwork;
		}

		public String getProvisioningTransport() {
			return provisioningTransport;
		}

		public void setProvisioningTransport(String provisioningTransport) {
			this.provisioningTransport = provisioningTransport;
		}

		public String getPxelabel() {
			return pxelabel;
		}

		public void setPxelabel(String pxelabel) {
			this.pxelabel = pxelabel;
		}

		public Long getRack() {
			return rack;
		}

		public void setRack(Long rack) {
			this.rack = rack;
		}

		public Long getRackHeight() {
			return rackHeight;
		}

		public void setRackHeight(Long rackHeight) {
			this.rackHeight = rackHeight;
		}

		public Long getRackPosition() {
			return rackPosition;
		}

		public void setRackPosition(Long rackPosition) {
			this.rackPosition = rackPosition;
		}

		public String getRaidconf() {
			return raidconf;
		}

		public void setRaidconf(String raidconf) {
			this.raidconf = raidconf;
		}

		public String getRevision() {
			return revision;
		}

		public void setRevision(String revision) {
			this.revision = revision;
		}

		public List<?> getRoles() {
			return roles;
		}

		public void setRoles(List<?> roles) {
			this.roles = roles;
		}

		public List<?> getServices() {
			return services;
		}

		public void setServices(List<?> services) {
			this.services = services;
		}

		public String getSoftwareImageProxy() {
			return softwareImageProxy;
		}

		public void setSoftwareImageProxy(String softwareImageProxy) {
			this.softwareImageProxy = softwareImageProxy;
		}

		public Boolean getStartNewBurn() {
			return startNewBurn;
		}

		public void setStartNewBurn(Boolean startNewBurn) {
			this.startNewBurn = startNewBurn;
		}

		public List<?> getStaticRoutes() {
			return staticRoutes;
		}

		public void setStaticRoutes(List<?> staticRoutes) {
			this.staticRoutes = staticRoutes;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public Boolean getToBeRemoved() {
			return toBeRemoved;
		}

		public void setToBeRemoved(Boolean toBeRemoved) {
			this.toBeRemoved = toBeRemoved;
		}

		public Boolean getUcsInfoConfigured() {
			return ucsInfoConfigured;
		}

		public void setUcsInfoConfigured(boolean b) {
			this.ucsInfoConfigured = b;
		}

		public Long getUniqueKey() {
			return uniqueKey;
		}

		public void setUniqueKey(Long uniqueKey) {
			this.uniqueKey = uniqueKey;
		}

		public String getUseExclusivelyFor() {
			return useExclusivelyFor;
		}

		public void setUseExclusivelyFor(String useExclusivelyFor) {
			this.useExclusivelyFor = useExclusivelyFor;
		}

		public String getUserdefined1() {
			return userdefined1;
		}

		public void setUserdefined1(String userdefined1) {
			this.userdefined1 = userdefined1;
		}

		public String getUserdefined2() {
			return userdefined2;
		}

		public void setUserdefined2(String userdefined2) {
			this.userdefined2 = userdefined2;
		}

		public List<netObject> getNetworks() {
			return networks;
		}

		public void setNetworks(List<netObject> networks) {
			this.networks = networks;
		}

	}

	public static class netObject {
		private List<String> additionalHostnames;
		private String baseType;
		private String bringupduringinstall;

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}

		public String getChildType() {
			return childType;
		}

		public void setChildType(String childType) {
			this.childType = childType;
		}

		private String cardType;
		private String childType;
		private String NetworkPhysicalInterface;
		private Boolean dhcp;
		private String ip;
		private String mac;
		private Boolean modified;
		private String name;
		private Long network;
		private Long oldLocalUniqueKey;
		private String revision;
		private String speed;
		private String startIf;
		private Boolean toBeRemoved;
		private Long uniqueKey;

		public List<String> getAdditionalHostnames() {
			return additionalHostnames;
		}

		public void setAdditionalHostnames(List<String> emptyslist) {
			this.additionalHostnames = emptyslist;
		}

		public String getBaseType() {
			return baseType;
		}

		public void setBaseType(String baseType) {
			this.baseType = baseType;
		}

		public String getBringupduringinstall() {
			return bringupduringinstall;
		}

		public void setBringupduringinstall(String bringupduringinstall) {
			this.bringupduringinstall = bringupduringinstall;
		}

		public String getNetworkPhysicalInterface() {
			return NetworkPhysicalInterface;
		}

		public void setNetworkPhysicalInterface(String networkPhysicalInterface) {
			NetworkPhysicalInterface = networkPhysicalInterface;
		}

		public Boolean getDhcp() {
			return dhcp;
		}

		public void setDhcp(Boolean dhcp) {
			this.dhcp = dhcp;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public Boolean getModified() {
			return modified;
		}

		public void setModified(Boolean modified) {
			this.modified = modified;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getNetwork() {
			return network;
		}

		public void setNetwork(Long network) {
			this.network = network;
		}

		public Long getOldLocalUniqueKey() {
			return oldLocalUniqueKey;
		}

		public void setOldLocalUniqueKey(Long oldLocalUniqueKey) {
			this.oldLocalUniqueKey = oldLocalUniqueKey;
		}

		public String getRevision() {
			return revision;
		}

		public void setRevision(String revision) {
			this.revision = revision;
		}

		public String getSpeed() {
			return speed;
		}

		public void setSpeed(String speed) {
			this.speed = speed;
		}

		public String getStartIf() {
			return startIf;
		}

		public void setStartIf(String startIf) {
			this.startIf = startIf;
		}

		public Boolean getToBeRemoved() {
			return toBeRemoved;
		}

		public void setToBeRemoved(Boolean toBeRemoved) {
			this.toBeRemoved = toBeRemoved;
		}

		public Long getUniqueKey() {
			return uniqueKey;
		}

		public void setUniqueKey(Long uniqueKey) {
			this.uniqueKey = uniqueKey;
		}

	}

	public static class switchObject {
		private String baseType;
		private String childType;
		private Long ethernetSwitch;
		private Boolean modified;
		private Long oldLocalUniqueKey;
		private int prt;
		private String revision;
		private Boolean toBeRemoved;
		private Long uniqueKey;

		public String getBaseType() {
			return baseType;
		}

		public void setBaseType(String baseType) {
			this.baseType = baseType;
		}

		public String getChildType() {
			return childType;
		}

		public void setChildType(String childType) {
			this.childType = childType;
		}

		public Long getEthernetSwitch() {
			return ethernetSwitch;
		}

		public void setEthernetSwitch(Long ethernetSwitch) {
			this.ethernetSwitch = ethernetSwitch;
		}

		public Boolean getModified() {
			return modified;
		}

		public void setModified(Boolean modified) {
			this.modified = modified;
		}

		public Long getOldLocalUniqueKey() {
			return oldLocalUniqueKey;
		}

		public void setOldLocalUniqueKey(Long oldLocalUniqueKey) {
			this.oldLocalUniqueKey = oldLocalUniqueKey;
		}

		public int getPrt() {
			return prt;
		}

		public void setPrt(int prt) {
			this.prt = prt;
		}

		public String getRevision() {
			return revision;
		}

		public void setRevision(String revision) {
			this.revision = revision;
		}

		public Boolean getToBeRemoved() {
			return toBeRemoved;
		}

		public void setToBeRemoved(Boolean toBeRemoved) {
			this.toBeRemoved = toBeRemoved;
		}

		public Long getUniqueKey() {
			return uniqueKey;
		}

		public void setUniqueKey(Long uniqueKey) {
			this.uniqueKey = uniqueKey;
		}

	}
}

/*
 * { "args": [ { "baseType": "Device", "bmcPowerResetDelay": 0, "burnConfig": {
 * "baseType": "BurnConfig", "childType": "", "configuration": "",
 * "description": "", "modified": false, "name": "", "oldLocalUniqueKey": 0,
 * "revision": "", "toBeRemoved": false, "uniqueKey": 7 }, "burning": false,
 * "category": 12884901889, "childType": "PhysicalNode", "cmdaemonUrl": "",
 * "cpuspeedGovernor": "", "creationTime": 0, "customPingScript": "",
 * "customPingScriptArgument": "", "customPowerScript":
 * "/root/koen/cmdaemon/trunk/kslave_power", "customPowerScriptArgument": "",
 * "customRemoteConsoleScript": "", "customRemoteConsoleScriptArgument": "",
 * "dataNode": false, "disksetup": "", "ethernetSwitch": { "baseType":
 * "SwitchPort", "childType": "", "ethernetSwitch": 0, "modified": false,
 * "oldLocalUniqueKey": 0, "prt": 0, "revision": "", "toBeRemoved": false,
 * "uniqueKey": 2 }, "excludeListFull": "", "excludeListGrab": "",
 * "excludeListGrabnew": "", "excludeListManipulateScript": "",
 * "excludeListSync": "", "excludeListUpdate": "", "finalize": "", "fsexports":
 * [], "fsmounts": [], "fspartAssociations": [], "gpuSettings": [], "hostname":
 * "node061", "indexInsideContainer": 0, "initialize": "", "installBootRecord":
 * false, "installMode": "", "ioScheduler": "", "lastProvisioningNode": 0,
 * "mac": "00:00:00:00:00:00", "managementNetwork": 0, "modified": true,
 * "networks": [ { "additionalHostnames": [], "baseType": "NetworkInterface",
 * "bringupduringinstall": "NO", "cardtype": "Ethernet", "childType":
 * "NetworkPhysicalInterface", "dhcp": false, "ip": "10.141.0.61", "mac":
 * "00:00:00:00:00:00", "modified": false, "name": "BOOTIF", "network":
 * 17179869185, "oldLocalUniqueKey": 0, "revision": "", "speed": "1Gb/s",
 * "startIf": "ALWAYS", "toBeRemoved": false, "uniqueKey": 4 }, {
 * "additionalHostnames": [], "baseType": "NetworkInterface",
 * "bringupduringinstall": "NO", "childType": "NetworkBmcInterface", "dhcp":
 * false, "gateway": "0.0.0.0", "ip": "10.148.0.61", "mac": "00:00:00:00:00:00",
 * "modified": false, "name": "ipmi0", "network": 17179869187,
 * "oldLocalUniqueKey": 0, "revision": "", "startIf": "ALWAYS", "toBeRemoved":
 * false, "uniqueKey": 5, "vlanid": 0 }, { "additionalHostnames": [],
 * "baseType": "NetworkInterface", "bringupduringinstall": "NO", "childType":
 * "NetworkAliasInterface", "dhcp": false, "ip": "10.148.100.1", "mac":
 * "00:00:00:00:00:00", "modified": false, "name": "BOOTIF:2", "network":
 * 17179869187, "oldLocalUniqueKey": 0, "revision": "", "startIf": "ALWAYS",
 * "toBeRemoved": false, "uniqueKey": 6 } ], "nextBootInstallMode": "", "notes":
 * "dude\n", "oldLocalUniqueKey": 0, "partition": 21474836481, "powerControl":
 * "custom", "powerDistributionUnits": [], "provisioningNetwork": 4,
 * "provisioningTransport": "RSYNCDAEMON", "pxelabel": "", "rack": 90194313218,
 * "rackHeight": 1, "rackPosition": 4, "raidconf": "", "revision": "", "roles":
 * [], "services": [], "softwareImageProxy": null, "startNewBurn": false,
 * "staticRoutes": [], "tag": "00000000a000", "toBeRemoved": false,
 * "ucsInfoConfigured": null, "uniqueKey": 1, "useExclusivelyFor": "Category",
 * "userdefined1": "koen101", "userdefined2": "" }, 0 ], "call": "addDevice",
 * "service": "cmdevice"
 */