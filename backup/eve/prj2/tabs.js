function stAba(menu,conteudo)
	{
		this.menu = menu;
		this.conteudo = conteudo;
	}
 
	var arAbas = new Array();
	arAbas[0] = new stAba('td_anonimo','div_anonimo');
	arAbas[1] = new stAba('td_facebook','div_facebook');
	//arAbas[2] = new stAba('td_manutencao','div_manutencao');
 
	function AlternarAbas(menu,conteudo)
	{
		for (i=0;i<arAbas.length;i++)
		{
			m = document.getElementById(arAbas[i].menu);
			m.className = 'menu';
			c = document.getElementById(arAbas[i].conteudo)
			c.style.display = 'none';
		}
		m = document.getElementById(menu)
		m.className = 'menu-sel';
		c = document.getElementById(conteudo)
		c.style.display = '';
	}
 