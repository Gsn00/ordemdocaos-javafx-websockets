package services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONService {

	static File dir = new File("C:\\ordemdocaos\\config");
	static File file = new File(dir+"\\userdata.json");

	public static void createDefaultFile() {
		try {
			if (!file.exists()) {
				dir.mkdirs();
				file.createNewFile();
				FileWriter fw = new FileWriter(file);
				Map<String, Object> obj = new HashMap<>();
				obj.put("jogador", "jogador-default");
				obj.put("nome", "usuario001");
				obj.put("sobrenome", "");
				
				obj.put("maxVida", 100);
				obj.put("vida", 50);
				obj.put("maxEnergia", 100);
				obj.put("energia", 50);
				obj.put("maxResistencia", 100);
				obj.put("resistencia", 50);
				obj.put("maxSanidade", 100);
				obj.put("sanidade", 50);
				
				JSONObject json = new JSONObject(obj);
				fw.write(json.toJSONString());
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void setData(String key, Object value) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(new FileReader(file));
			obj.put(key, value);
			FileWriter fw = new FileWriter(file);
			JSONObject json = new JSONObject(obj);
			fw.write(json.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public static Object getData(String key) {
		Object value = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(new FileReader(file));
			value = obj.get(key);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return value;
	}
}
