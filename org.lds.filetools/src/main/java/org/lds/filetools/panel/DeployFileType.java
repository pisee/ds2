package org.lds.filetools.panel;


public enum DeployFileType {
	ONLINE_JAVA_Accounting_Application(".java", "Accounting_Application/src/main/java/", "*", new String[]{"Accounting_Application/workspace/BUILD/classes/", "Accounting_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_AdjustSettlement_Application(".java", "AdjustSettlement_Application/src/main/java/", "*", new String[]{"AdjustSettlement_Application/workspace/BUILD/classes/", "AdjustSettlement_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_CheckMgr_Application(".java", "CheckMgr_Application/src/main/java/", "*", new String[]{"CheckMgr_Application/workspace/BUILD/classes/", "CheckMgr_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_Claims_Application(".java", "Claims_Application/src/main/java/", "*", new String[]{"Claims_Application/workspace/BUILD/classes/", "Claims_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_Common_Application(".java", "Common_Application/src/main/java/", "*", new String[]{"Common_Application/workspace/BUILD/classes/", "Common_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_ContractMgr_Application(".java", "ContractMgr_Application/src/main/java/", "*", new String[]{"ContractMgr_Application/workspace/BUILD/classes/", "ContractMgr_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_Customer_Application(".java", "Customer_Application/src/main/java/", "*", new String[]{"Customer_Application/workspace/BUILD/classes/", "Customer_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_CustomerService_Application(".java", "CustomerService_Application/src/main/java/", "*", new String[]{"CustomerService_Application/workspace/BUILD/classes/", "CustomerService_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_DataWarehouse_Application(".java", "DataWarehouse_Application/src/main/java/", "*", new String[]{"DataWarehouse_Application/workspace/BUILD/classes/", "DataWarehouse_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_EXReceiveSend_Application(".java", "EXReceiveSend_Application/src/main/java/", "*", new String[]{"EXReceiveSend_Application/workspace/BUILD/classes/", "EXReceiveSend_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_Figures_Application(".java", "Figures_Application/src/main/java/", "*", new String[]{"Figures_Application/workspace/BUILD/classes/", "Figures_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_Marketing_Application(".java", "Marketing_Application/src/main/java/", "*", new String[]{"Marketing_Application/workspace/BUILD/classes/", "Marketing_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_NuclearEnergy_Application(".java", "NuclearEnergy_Application/src/main/java/", "*", new String[]{"NuclearEnergy_Application/workspace/BUILD/classes/", "NuclearEnergy_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_RiskMgr_Application(".java", "RiskMgr_Application/src/main/java/", "*", new String[]{"RiskMgr_Application/workspace/BUILD/classes/", "RiskMgr_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_RiskSurvey_Application(".java", "RiskSurvey_Application/src/main/java/", "*", new String[]{"RiskSurvey_Application/workspace/BUILD/classes/", "RiskSurvey_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA_Sample_Application(".java", "Sample_Application/src/main/java/", "*", new String[]{"Sample_Application/workspace/BUILD/classes/", "Sample_Application/workspace/BUILD/classes/com/mccabe/" }), 
	ONLINE_JAVA(".java", "Underwriting_Application/src/main/java/", "*", new String[]{"Underwriting_Application/workspace/BUILD/classes/", "Underwriting_Application/workspace/BUILD/classes/com/mccabe/" }), 

	ONLINE_XFDL(".xfdl", "webapps/", ".xfdl", new String[]{"workspace/webapps/"}), 
	ONLINE_XML(".xml", "src/main/resource/", ".xml", new String[]{"workspace/BUILD/classes/"}), 
	BATCH_JAVA(".java", "/KoreanreBatch/src/main/java/", ".class", new String[]{"/KoreanreBatch/workspace/build/"}),
	BATCH_XML(".xml", "/KoreanreBatch/src/main/java/", ".xml", new String[]{"/KoreanreBatch/workspace/build/"});
	
	private final String originSourceSuffix;
	private final String originSourcePath;
	private final String deployTargetSuffix;
	private final String[] deployTargetPath;
	
	DeployFileType(String originSourceSuffix, String originSourcePath, String deployTargetSuffix, String[] deployTargetPath) {
		this.originSourceSuffix = originSourceSuffix;
		this.originSourcePath = originSourcePath;
		this.deployTargetSuffix = deployTargetSuffix;
		this.deployTargetPath = deployTargetPath;
	}

	public String getOriginSourceSuffix() {
		return originSourceSuffix;
	}

	public String getOriginSourcePath() {
		return originSourcePath;
	}

	public String getDeployTargetSuffix() {
		return deployTargetSuffix;
	}

	public String[] getDeployTargetPath() {
		return deployTargetPath;
	}
	
	
}
