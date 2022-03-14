package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonObject;

import services.JSONService;

public class Character implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String jogador;
	private String nome;
	private String ocupacao;
	private String idade;
	private String sexo;
	private String localNascimento;
	private String imgUrl;
	private Integer vida;
	private Integer maxVida;
	private Integer energia;
	private Integer maxEnergia;
	private Integer resistencia;
	private Integer maxResistencia;
	private Integer sanidade;
	private Integer maxSanidade;
	
	private List<String> items = new ArrayList<>();
	
	public Character()
	{
		getJSONData();
	}

	public Character(Character clone)
	{
		this.jogador = clone.jogador;
		this.nome = clone.nome;
		this.ocupacao = clone.ocupacao;
		this.idade = clone.idade;
		this.sexo = clone.sexo;
		this.localNascimento = clone.localNascimento;
		this.imgUrl = clone.imgUrl;
		this.vida = clone.vida;
		this.maxVida = clone.maxVida;
		this.energia = clone.energia;
		this.maxEnergia = clone.maxEnergia;
		this.resistencia = clone.resistencia;
		this.maxResistencia = clone.maxResistencia;
		this.sanidade = clone.sanidade;
		this.maxSanidade = clone.maxSanidade;
		this.items = clone.items;
	}
	
	public String getJogador()
	{
		return jogador;
	}

	public void setJogador(String jogador)
	{
		this.jogador = jogador;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getOcupacao()
	{
		return ocupacao;
	}

	public void setOcupacao(String ocupacao)
	{
		this.ocupacao = ocupacao;
	}

	public String getIdade()
	{
		return idade;
	}

	public void setIdade(String idade)
	{
		this.idade = idade;
	}

	public String getSexo()
	{
		return sexo;
	}

	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}

	public String getLocalNascimento()
	{
		return localNascimento;
	}

	public void setLocalNascimento(String localNascimento)
	{
		this.localNascimento = localNascimento;
	}
	
	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public Integer getVida()
	{
		return vida;
	}

	public void setVida(Integer vida)
	{
		this.vida = vida;
	}

	public Integer getMaxVida()
	{
		return maxVida;
	}

	public void setMaxVida(Integer maxVida)
	{
		this.maxVida = maxVida;
	}

	public Integer getEnergia()
	{
		return energia;
	}

	public void setEnergia(Integer energia)
	{
		this.energia = energia;
	}

	public Integer getMaxEnergia()
	{
		return maxEnergia;
	}

	public void setMaxEnergia(Integer maxEnergia)
	{
		this.maxEnergia = maxEnergia;
	}

	public Integer getResistencia()
	{
		return resistencia;
	}

	public void setResistencia(Integer resistencia)
	{
		this.resistencia = resistencia;
	}

	public Integer getMaxResistencia()
	{
		return maxResistencia;
	}

	public void setMaxResistencia(Integer maxResistencia)
	{
		this.maxResistencia = maxResistencia;
	}

	public Integer getSanidade()
	{
		return sanidade;
	}

	public void setSanidade(Integer sanidade)
	{
		this.sanidade = sanidade;
	}

	public Integer getMaxSanidade()
	{
		return maxSanidade;
	}

	public void setMaxSanidade(Integer maxSanidade)
	{
		this.maxSanidade = maxSanidade;
	}

	public List<String> getItems()
	{
		return items;
	}

	public void setJSONData()
	{
		
		JsonObject obj = new JsonObject();
		
		obj.put("jogador", jogador);
		obj.put("nome", nome);
		obj.put("ocupacao", ocupacao);
		obj.put("idade", idade);
		obj.put("sexo", sexo);
		obj.put("localNascimento", localNascimento);
		obj.put("imgUrl", imgUrl);
		
		obj.put("vida", vida);
		obj.put("maxVida", maxVida);
		obj.put("energia", energia);
		obj.put("maxEnergia", maxEnergia);
		obj.put("resistencia", resistencia);
		obj.put("maxResistencia", maxResistencia);
		obj.put("sanidade", sanidade);
		obj.put("maxSanidade", maxSanidade);
		
		obj.put("items", items);
		
		JSONService.setData(obj);
	}
	
	@SuppressWarnings("unchecked")
	public void getJSONData()
	{
		jogador = (String) JSONService.getData("jogador");
		nome = (String) JSONService.getData("nome");
		ocupacao = (String) JSONService.getData("ocupacao");
		idade = (String) JSONService.getData("idade");
		sexo = (String) JSONService.getData("sexo");
		localNascimento = (String) JSONService.getData("localNascimento");
		imgUrl = (String) JSONService.getData("imgUrl");
		
		vida = JSONService.getInt("vida");
		maxVida = JSONService.getInt("maxVida");
		energia = JSONService.getInt("energia");
		maxEnergia = JSONService.getInt("maxEnergia");
		resistencia = JSONService.getInt("resistencia");
		maxResistencia = JSONService.getInt("maxResistencia");
		sanidade = JSONService.getInt("sanidade");
		maxSanidade = JSONService.getInt("maxSanidade");
		
		items = (List<String>) JSONService.getList("items");
	}
}
