package entities;

import services.JSONService;

public class Character
{
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
	
	public Character()
	{
		getJSONData();
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

	public void setJSONData()
	{
		JSONService.setData("jogador", jogador);
		JSONService.setData("nome", nome);
		JSONService.setData("ocupacao", ocupacao);
		JSONService.setData("idade", idade);
		JSONService.setData("sexo", sexo);
		JSONService.setData("localNascimento", localNascimento);
		JSONService.setData("imgUrl", imgUrl);
		
		JSONService.setData("vida", vida);
		JSONService.setData("maxVida", maxVida);
		JSONService.setData("energia", energia);
		JSONService.setData("maxEnergia", maxEnergia);
		JSONService.setData("resistencia", resistencia);
		JSONService.setData("maxResistencia", maxResistencia);
		JSONService.setData("sanidade", sanidade);
		JSONService.setData("maxSanidade", maxSanidade);
	}
	
	public void getJSONData()
	{
		jogador = (String) JSONService.getData("jogador");
		nome =(String) JSONService.getData("nome");
		ocupacao =(String) JSONService.getData("ocupacao");
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
	}
}
