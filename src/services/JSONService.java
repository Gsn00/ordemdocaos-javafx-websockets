package services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javafx.application.Platform;

public class JSONService
{

	static File dir = new File("C:\\ordemdocaos\\config");
	static File file = new File(dir + "\\character.json");

	public static void createDefaultFile()
	{
		try
		{
			if (!file.exists())
			{
				dir.mkdirs();
				file.createNewFile();
				JsonObject obj = new JsonObject();
				obj.put("jogador", "Jogador");
				obj.put("nome", "Nome do personagem");
				obj.put("ocupacao", "Ocupação do personagem");
				obj.put("idade", "Idade do personagem");
				obj.put("sexo", "Sexo do personagem");
				obj.put("localNascimento", "Local de nascimento do personagem");

				obj.put("maxVida", 100);
				obj.put("vida", 50);
				obj.put("maxEnergia", 100);
				obj.put("energia", 50);
				obj.put("maxResistencia", 100);
				obj.put("resistencia", 50);
				obj.put("maxSanidade", 100);
				obj.put("sanidade", 50);
				
				obj.put("items", new ArrayList<String>());

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
				System.exit(0);
				Platform.exit();
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
