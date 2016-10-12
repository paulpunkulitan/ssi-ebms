package ph.com.smesoft.hms.dto;

import java.util.Collection;
import java.util.List;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Data {
	private String pvId;
	/*private String palmusId;
	private String is_Authenticated;
*/
	public String getPvId() {
		return pvId;
	}

	public void setPvId(String pvId) {
		this.pvId = pvId;
	}
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Data fromJsonToData(String json) {
        return new JSONDeserializer<Data>()
        .use(null, Data.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Data> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Data> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Data> fromJsonArrayToData(String json) {
        return new JSONDeserializer<List<Data>>()
        .use("values", Data.class).deserialize(json);
    }

	/*public String getPalmusId() {
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
	}*/
	
}
