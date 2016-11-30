package ph.com.smesoft.ebms.dto;

import java.util.Collection;
import java.util.List;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Data {
	private String pvId;
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
}
