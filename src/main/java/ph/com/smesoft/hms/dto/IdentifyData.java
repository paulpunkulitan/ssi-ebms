package ph.com.smesoft.hms.dto;

public class IdentifyData {

	private String ModelNo;
	private String PVIDNo;
	private String pvId;
	private String palmusPVId;
	private String palmusId;
	private String is_Authenticated;

	public String getModelNo() {
		return ModelNo;
	}

	public void setModelNo(String modelNo) {
		ModelNo = modelNo;
	}

	public String getPVIDNo() {
		return PVIDNo;
	}

	public void setPVIDNo(String pVIDNo) {
		PVIDNo = pVIDNo;
	}

	public String getPvId() {
		return pvId;
	}

	public void setPvId(String pvId) {
		this.pvId = pvId;
	}

	public String getPalmusPVId() {
		return palmusPVId;
	}

	public void setPalmusPVId(String palmusPVId) {
		this.palmusPVId = palmusPVId;
	}

	public String getPalmusId() {
		return palmusId;
	}

	public void setPalmusId(String palmusId) {
		this.palmusId = palmusId;
	}

	public String getIs_Authenticated() {
		return is_Authenticated;
	}

	public void setIs_Authenticated(String is_Authenticated) {
		this.is_Authenticated = is_Authenticated;
	}

}
