package services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class JSONService
{

	static File dir = new File("C:\\ordemdocaos\\config");
	static File file = new File(dir + "\\character.json");

	public static void createDefaultFile(String name)
	{
		if (!file.exists())
		{
			try (Reader reader = new FileReader(
					new File(JSONService.class.getResource("/characters/" + name + ".json").toURI())))
			{
				dir.mkdirs();
				file.createNewFile();
				JsonObject obj = new JsonObject();

				JsonObject character = (JsonObject) Jsoner.deserialize(reader);
				obj.put("jogador", character.get("jogador"));

				obj.put("nome", character.get("nome"));
				obj.put("ocupacao", character.get("ocupacao"));
				obj.put("idade", character.get("idade"));
				obj.put("sexo", character.get("sexo"));
				obj.put("localNascimento", character.get("localNascimento"));
				obj.put("imgUrl", character.get("imgUrl"));

				obj.put("maxVida", character.get("maxVida"));
				obj.put("vida", character.get("vida"));
				obj.put("maxEnergia", character.get("maxEnergia"));
				obj.put("energia", character.get("energia"));
				obj.put("maxResistencia", character.get("maxResistencia"));
				obj.put("resistencia", character.get("resistencia"));
				obj.put("maxSanidade", character.get("maxSanidade"));
				obj.put("sanidade", character.get("sanidade"));

				obj.put("items", new ArrayList<String>());

				try (FileWriter fileWriter = new FileWriter(file))
				{
					fileWriter.write(obj.toJson());
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void setData(String key, Object value)
	{
		try (Reader reader = new FileReader(file))
		{
			JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
			obj.put(key, value);
			try (FileWriter fileWriter = new FileWriter(file))
			{
				fileWriter.write(obj.toJson());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Object getData(String key)
	{
		if (file.exists())
		{
			try (Reader reader = new FileReader(file))
			{
				JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
				Object object = obj.get(key);
				return object;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object getData(String key, File file)
	{
		if (file.exists())
		{
			try (Reader reader = new FileReader(file))
			{
				JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
				Object object = obj.get(key);
				return object;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int getInt(String key)
	{
		if (file.exists())
		{
			try (Reader reader = new FileReader(file))
			{
				JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
				Object object = obj.get(key);
				int value = Integer.parseInt((object.toString()));
				return value;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return -1;
	}

	public static List<?> getList(String key)
	{
		if (file.exists())
		{
			try (Reader reader = new FileReader(file))
			{
				JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
				List<?> list = (List<?>) obj.get(key);
				return list;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
