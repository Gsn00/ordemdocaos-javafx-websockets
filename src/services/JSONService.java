package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class JSONService
{

	static File dir = new File("C:\\ordemdocaos\\config");
	public static File file = new File(dir + "\\character.json");

	public static void createDefaultFile(String name)
	{
		if (!file.exists())
		{
			try
			{
				dir.mkdirs();
				file.createNewFile();
				JsonObject obj = new JsonObject();

				obj.put("jogador", getData("jogador", name));

				obj.put("nome", getData("nome", name));
				obj.put("ocupacao", getData("ocupacao", name));
				obj.put("idade", getData("idade", name));
				obj.put("sexo", getData("sexo", name));
				obj.put("localNascimento", getData("localNascimento", name));
				obj.put("imgUrl", getData("imgUrl", name));

				obj.put("maxVida", getData("maxVida", name));
				obj.put("vida", getData("vida", name));
				obj.put("maxEnergia", getData("maxEnergia", name));
				obj.put("energia", getData("energia", name));
				obj.put("maxResistencia", getData("maxResistencia", name));
				obj.put("resistencia", getData("resistencia", name));
				obj.put("maxSanidade", getData("maxSanidade", name));
				obj.put("sanidade", getData("sanidade", name));

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
	
	public static void setData(JsonObject jsonObject)
	{
		try (FileWriter fileWriter = new FileWriter(file))
		{
			fileWriter.write(jsonObject.toJson());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void setData(String key, Object value)
	{
		try (Reader reader = new FileReader(file))
		{
			if (reader != null)
			{
				JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
				obj.put(key, value);
				try (FileWriter fileWriter = new FileWriter(file))
				{
					fileWriter.write(obj.toJson());
				}
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

	public static Object getData(String key, String name)
	{
		InputStream input = JSONService.class.getResourceAsStream("/characters/" + name + ".json");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input)))
		{
			JsonObject obj = (JsonObject) Jsoner.deserialize(reader);
			Object object = obj.get(key);
			return object;
		} catch (Exception e)
		{
			e.printStackTrace();
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
	
	public static boolean exists(String name)
	{
		InputStream input = JSONService.class.getResourceAsStream("/characters/" + name + ".json");
		if (input == null)
		{
			return false;
		}
		return true;
	}
}
