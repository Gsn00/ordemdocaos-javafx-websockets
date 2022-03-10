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
	
	public Character()
	{
		syncData();
	}

	public Character(String jogador, String nome, String ocupacao, String idade, String sexo, String localNascimento)
	{
		setJogador(jogador);
		setNome(nome);
		setIdade(idade);
		setSexo(sexo);
		setLocalNascimento(localNascimento);
	}

	public String getJogador()
	{
		return jogador;
	}

	public void setJogador(String jogador)
	{
		this.jogador = jogador;
		JSONService.setData("jogador", jogador);
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
		JSONService.setData("nome", nome);
	}

	public String getOcupacao()
	{
		return ocupacao;
	}

	public void setOcupacao(String ocupacao)
	{
		this.ocupacao = ocupacao;
		JSONService.setData("ocupacao", ocupacao);
	}

	public String getIdade()
	{
		return idade;
	}

	public void setIdade(String idade)
	{
		this.idade = idade;
		JSONService.setData("idade", idade);
	}

	public String getSexo()
	{
		return sexo;
	}

	public void setSexo(String sexo)
	{
		this.sexo = sexo;
		JSONService.setData("sexo", sexo);
	}

	public String getLocalNascimento()
	{
		return localNascimento;
	}

	public void setLocalNascimento(String localNascimento)
	{
		this.localNascimento = localNascimento;
		JSONService.setData("localNascimento", localNascimento);
	}
	
	public void syncData()
	{
		setJogador((String) JSONService.getData("jogador"));
		setNome((String) JSONService.getData("nome"));
		setOcupacao((String) JSONService.getData("ocupacao"));
		setIdade((String) JSONService.getData("idade"));
		setSexo((String) JSONService.getData("sexo"));
		setLocalNascimento((String) JSONService.getData("localNascimento"));
	}
}
