package ph.com.smesoft.hms.domain;

import java.util.Collection;
import java.util.List;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Identify {

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Identify fromJsonToIdentify(String json) {
        return new JSONDeserializer<Identify>()
        .use(null, Identify.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Identify> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Identify> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Identify> fromJsonArrayToIdentify(String json) {
        return new JSONDeserializer<List<Identify>>()
        .use("values", Identify.class).deserialize(json);
    }
}
