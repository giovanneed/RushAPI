package servicos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

/**

 * @author Hiparc Geotecnologia
 * @version 1.1.8,  &nbsp; 19-MAR-2009
 * @since SDK1.5
 **/


public class Croqui
{
	/**
	 * Exceção padrão gerada pelo componente
	 */
	public class CroquiException extends Exception {

		private static final long serialVersionUID = 3870277218575058015L;

		public CroquiException(String mensagem){
			super(mensagem);
		}
	}

	/**
	 * Exceção que herda de CroquiException que indica se o Indice Cadastral não é de uma quadra
	 */
	public class IndiceCadastralNaoEncontradaParaAQuadraCroquiException extends CroquiException
	{
		private static final long serialVersionUID = -2614815258432512050L;

		public IndiceCadastralNaoEncontradaParaAQuadraCroquiException(String mensagem){
			super(mensagem);
		}
	}

	/**
	 * Constante que indica o nome da coluna OBJECTID que é definida em várias consultas ao banco
	 */
	private final String OBJECTID = "OBJECTID";

	/**
	 * Constante que indica o nome da coluna GEOLOC que é definida em várias consultas ao banco
	 */
	private final String GEOLOC = "GEOLOC";

	/**
	 * Constante que indica o nome da coluna CENTROID que é definida em várias consultas ao banco
	 */
	private final String CENTROID = "CENTROID";

	/**
	 * Constante que indica o nome da coluna VALOR(coluna especial que possui elementos separados
	 * por ;)que � definida em v�rias consultas ao banco
	 */
	private final String VALOR = "VALOR";
	/**
	 * Constante que indica o nome da coluna AREA que é definida em várias consultas ao banco
	 */
	private final String AREA = "AREA";
	/**
	 * Constante que indica o nome da coluna NULOG que é definida em várias consultas ao banco
	 */
	private final String NULOG = "NULOG";
	/**
	 * Constante que indica o nome da coluna PONTO que é definida em várias consultas ao banco
	 */
	private final String PONTO = "PONTO";

	/**
	 * Constante que indica o nome da tabela da Quadra do banco de dados
	 */
	private final String QUADRA_CTM = "QUADRA_CTM";

	/**
	 * Constante que indica o nome da tabela de Logradouros do banco de dados
	 */
	private final String TRECHO = "TRECHO";

	/**
	 * Constante que indica o nome da tabela de Lotes do banco de dados
	 */
	private final String LOTE_CTM = "LOTE_CTM";

	/**
	 * Constante que indica o nome da tabela de Edificação do banco de dados
	 */
	private final String EDIFICACAO = "EDIFICACAO";

	/**
	 * Constante que indica o nome da tabela de Bairro do banco de dados
	 */
	private final String BAIRRO_POPULAR = "BAIRRO_POPULAR";

	/**
	 * Constante que indica o nome da tabela de IPTU do banco de dados
	 */
	private final String IPTU_CTM_GEO = "IPTU_CTM_GEO";

	/**
	 * Constantes que indica a fonte para mostra na legenda(no caso tamanho 12 e negrito)
	 */
	private final Font m_FonteCabecalho = new Font("Dialog", Font.BOLD, 12);
	private final Font m_FonteCabecalhoLogradouros = new Font("Dialog", Font.BOLD, 9);

	/**
	 * Constante que indica o valor do pulo de linha em pixels de valores da coluna
	 * valor
	 */
	private final int PULO_DE_LINHA = 10;

	/**
	 * Constante que indica a correção que se deve ter para que os pontos que indicam
	 * os segmentos de uma reta ficam mais próximos das posições que deveriam ficar
	 */
	private static int CORRECAO_PARA_CARACTER_FICAR_EM_CIMA_DO_PONTO = 7;

	/**
	 * Constante que indica a altura utilizada para cabeçalho do croqui 1. Utilizada
	 * para ao criar o croqui a imagem "descer"
	 */
	private static int ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_1 = 240;

	/**
	 * Constante que indica a altura utilizada para cabeçalho do croqui 2. Utilizada
	 * para ao criar o croqui a imagem "descer"
	 */
	private static int ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_2 = 120;

	/**
	 * Constante que indica a altura utilizada para cabeçalho do croqui 3. Utilizada
	 * para ao criar o croqui a imagem "descer"
	 */
	private static int ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_3 = 120;

	/**
	 * Membro que indica o maior valor de x que se encontrou no shape. Necessário para fazer
	 * as conversões de UTM para o croqui
	 */
	private Double m_MaiorX = Double.NaN;

	/**
	 * Membro que indica o menor valor de x que se encontrou no shape. Necessário para fazer
	 * as conversões de UTM para o croqui
	 */
	private Double m_MaiorY = Double.NaN;

	/**
	 * Membro que indica o maior valor de x que se encontrou no shape. Necess�rio para fazer
	 * as convers�es de UTM para o croqui
	 */
	private Double m_MenorX = Double.NaN;

	/**
	 * Membro que indica o maior valor de y que se encontrou no shape. Necess�rio para fazer
	 * as convers�es de UTM para o croqui
	 */
	private Double m_MenorY = Double.NaN;

	/**
	 * Membro que indica para cada logradouro(a chave da Hash) sua cor. Necess�rio para desenhar
	 * os logradouros
	 */
	private Hashtable<String, Color> m_MapeamentoDasCoresDoLogradouro = new Hashtable<String, Color>();

	/**
	 * Membro que indica o tamanho da imagem a ser gerada(Altura e Largura). � sempre igual ao menor valor
	 * em Altura e Largura
	 */
	private int m_Tamanho = 0;

    	/**
	 * Membro que indica o tamanho da imagem a ser gerada(Altura e Largura). � sempre igual ao menor valor
	 * em Altura e Largura
	 */
	private int m_Margem = 0;

	/**
	 * Membro que indica uma lista de cores poss�veis para os logradouros
	 */
	private final ArrayList<Color> m_CoresPossiveisDoLogradouros = GetCoresPossiveisDosLogradouros();

	/**
	 * Membro que indica  a conex�o a ser utilizada pelo componente
	 */
	private Connection m_Conexao = null;

	/**
	 * Membro que guarda a cor da quadra
	 */
	private Color m_CorQuadra = Color.RED;
	private Color m_CorLote = Color.BLUE;
	//private Color m_CorEdificacao = new Color(250, 235, 215);
	private Color m_CorEdificacao = new Color(212, 212, 212);
	private Color m_CorLoteCtm = Color.GREEN;
	private Color m_CorLoteIptu = Color.MAGENTA;
	private Color m_CorLoteEndereco = new Color(153, 0, 102);

	/**
	 * Fun��es get e set das cores utilizadas no croqui
	 */
	public Color getCorLoteCtm() {
		return m_CorLoteCtm;
	}

	public void setCorLoteCtm(Color p_CorLoteCtm) {
		m_CorLoteCtm = p_CorLoteCtm;
	}

	public Color getCorLoteIptu() {
		return m_CorLoteIptu;
	}

	public void setCorLoteIptu(Color p_CorLoteIptu) {
		m_CorLoteIptu = p_CorLoteIptu;
	}

	public Color getCorLoteEndereco() {
		return m_CorLoteEndereco;
	}

	public void setCorLoteEndereco(Color corLoteEndereco) {
		m_CorLoteEndereco = corLoteEndereco;
	}

	public Color getCorEdificacao() {
		return m_CorEdificacao;
	}

	public void setCorEdificacao(Color p_CorEdificacao) {
		m_CorEdificacao = p_CorEdificacao;
	}

	public Color getCorLote() {
		return m_CorLote;
	}

	public void setCorLote(Color p_CorLote) {
		m_CorLote = p_CorLote;
	}

	public Color getCorQuadra() {
		return m_CorQuadra;
	}

	public void setCorQuadra(Color p_CorQuadra) {
		m_CorQuadra = p_CorQuadra;
	}

	/**
	 * �nico construtor que deve receber como par�metro uma conex�o
	 */
	public Croqui(Connection p_Conexao)
	{
		m_Conexao = p_Conexao;
	}

	/**
	 * Função que gera o croqui da quadra, com todos os seus lotes e destacando alguns lotes,
	 * alêm de mostrar o entorno dos logradouros
	 * @return Retorna o bufferedImage que representa o croqui
	 * @param p_Tamanho Tamanho da imagem a ser gerada(A4, A3, A2)
	 * @param p_PKQuadra Número Lote CTM da quadra
	 * @param p_PKsLotesADestacar Número de lote ctm dos lotes a destacar, mostrando edificações, iptu, ctm e n�mero im�vel
	 * @param p_OwnerTabelaQuadraCtm Indica o owner da tabela de quadra
	 * @param p_OwnerTabelaTrecho Indica o owner da tabela de trecho
	 * @param p_OwnerTabelaLoteCtm Indica o owner da tabela de lote
	 * @param p_OwnerTabelaEdificacao Indica o owner da tabela de edificação
	 * @param p_OwnerTabelaBairroPopular Indica o owner da tabela de bairro popular
	 * @param p_OwnerIptuGeoCtm Indica o owner da tabela de iptu
	 * @exception SQLException
	 * @exception CroquiException
	 */
	public BufferedImage gerarCroquiTipo1(com.lowagie.text.Rectangle p_Tamanho,
			int p_PKQuadra, ArrayList<String> p_PKsLotesADestacar, int p_MargemPixel,
			String p_NomeTabelaQuadraCtm,
			String p_NomeTabelaTrecho, String p_NomeTabelaLoteCtm,
			String p_NomeTabelaEdificacao, String p_NomeTabelaBairroPopular,
			String p_NomeTabelaIptuGeoCtm,
                        String geometria_quadra,String quadra_ctm_numero_da_quadra,
                        String geometria_trecho,String trecho_id_logradouro,String trecho_mi_prinx, String trecho_tipo_logradouro,String trecho_numero_do_logradouro,
                        String geometria_lote,
                        String geometria_edificacao,
                        String geometria_bairro_popular, String nome_bairro_popular,
                        String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_area_construida,String iptu_ctm_geo_numero_do_lote,String iptu_ctm_geo_numero_do_imovel,
                        String lote_ctm_numero_do_lote) throws SQLException,
			CroquiException
			{

		limparCampos();

        m_Margem = p_MargemPixel;
		BufferedImage v_BufferedImagem = CriarImagemInicial(p_Tamanho, p_MargemPixel);

		Graphics2D v_Graficos = (Graphics2D) v_BufferedImagem.getGraphics();

		double v_DesvioParaCaberElementosAoRedor = 0.95;
		int v_AlturaUtilizada = ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_1 + p_MargemPixel;

		/*String v_NomeTabelaQuadraCtm = p_OwnerTabelaQuadraCtm + "." + QUADRA_CTM;
		String v_NomeTabelaTrecho = p_OwnerTabelaTrecho + "." + TRECHO;
		String v_NomeTabelaLoteCtm = p_OwnerTabelaLoteCtm + "." + LOTE_CTM;
		String v_NomeTabelaEdifParticular = p_OwnerTabelaEdificacao + "." + EDIFICACAO;
		String v_NomeTabelaIptuGeoCtm = p_OwnerIptuGeoCtm + "." + IPTU_CTM_GEO;*/

		String v_ConsultaQuadra = "select g."+geometria_quadra+" " + GEOLOC;
		v_ConsultaQuadra += " from " + p_NomeTabelaQuadraCtm + " g";
		v_ConsultaQuadra += " where g."+quadra_ctm_numero_da_quadra+" = " + p_PKQuadra;

		preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, null, m_CorQuadra, null, null, null, null, v_ConsultaQuadra, null, v_DesvioParaCaberElementosAoRedor);

		//desenha camada dos logradouros
		String v_ConsultaLogradouro = getConsultaLogradouro(p_NomeTabelaTrecho,geometria_trecho,trecho_id_logradouro,trecho_mi_prinx,trecho_tipo_logradouro,trecho_numero_do_logradouro);

		preencherLogradouro(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, v_ConsultaLogradouro, v_DesvioParaCaberElementosAoRedor,p_NomeTabelaTrecho,
                        geometria_trecho,trecho_id_logradouro,trecho_mi_prinx);

		/*String v_ConsultaLote = "select l.num_lote_ctm " + OBJECTID + ", concat(substr(ipt.indice_cadastral, 7, 4), concat(';',concat(substr(l.num_lote_ctm, -3, 3), concat(';', ipt.numero_imovel)))) " + VALOR + ", l.geoloc " + GEOLOC + ", sdo_geom.sdo_centroid(l.geoloc, u.DIMINFO) " + CENTROID + ",sdo_geom.sdo_area(l.geoloc,'5e-8','unit=sq_m') " + AREA;
		v_ConsultaLote += " from " + p_NomeTabelaLoteCtm + " l, " +  p_NomeTabelaQuadraCtm + " q, " + p_NomeTabelaIptuGeoCtm + " ipt, user_sdo_geom_metadata u ";
		v_ConsultaLote += " where u.TABLE_NAME = '" + (String) p_NomeTabelaLoteCtm.subSequence(p_NomeTabelaLoteCtm.indexOf(".") + 1, p_NomeTabelaLoteCtm.length()) + "' AND u.COLUMN_NAME = 'GEOLOC'";
		v_ConsultaLote += " AND q.NUQDRCTM = " + p_PKQuadra;
		v_ConsultaLote += " AND ipt.NULOTCTM = l.num_lote_ctm";
		v_ConsultaLote += " AND sdo_relate(l.geoloc, q.geoloc, 'mask=anyinteract') = 'TRUE'";*/

        String v_ConsultaLote = "select l."+lote_ctm_numero_do_lote+" " + OBJECTID + ", concat(substr(ipt."+iptu_ctm_geo_indice_cadastral+", 7, 4), concat(';',concat(substr(l."+lote_ctm_numero_do_lote+", -3, 3), concat(';', ipt."+iptu_ctm_geo_numero_do_imovel+")))) " + VALOR + ", l."+geometria_lote+" " + GEOLOC + ", sdo_geom.sdo_centroid(l."+geometria_lote+", .05) " + CENTROID + ",sdo_geom.sdo_area(l."+geometria_lote+",'5e-8','unit=sq_m') " + AREA;
		v_ConsultaLote += " from " + p_NomeTabelaLoteCtm + " l, " +  p_NomeTabelaQuadraCtm + " q, " + p_NomeTabelaIptuGeoCtm + " ipt ";
		v_ConsultaLote += " where q."+quadra_ctm_numero_da_quadra+" = " + p_PKQuadra;
		v_ConsultaLote += " AND ipt."+iptu_ctm_geo_numero_do_lote+" = l."+lote_ctm_numero_do_lote;
		v_ConsultaLote += " AND sdo_relate(l."+geometria_lote+", q."+geometria_quadra+", 'mask=anyinteract') = 'TRUE'";

		String v_ConsultaDeLotes = "";
		for(int c_LoteADestacar = 0; c_LoteADestacar < p_PKsLotesADestacar.size(); c_LoteADestacar ++)
		{
			String v_Pk = p_PKsLotesADestacar.get(c_LoteADestacar);
			v_ConsultaDeLotes += "'" + v_Pk + "'";

			if (c_LoteADestacar != (p_PKsLotesADestacar.size() - 1))
			{
				v_ConsultaDeLotes += ",";
			}
		}

		if (!v_ConsultaDeLotes.equals(""))
		{
			v_ConsultaLote += " AND l."+lote_ctm_numero_do_lote+" in(" + v_ConsultaDeLotes + ")";
		}
		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(v_ConsultaLote);

		while(v_ResultSet.next())
		{
			if (p_PKsLotesADestacar.contains(v_ResultSet.getString(OBJECTID)))
			{
				/*String v_ConsultaEdificacao = "select e.geoloc " + GEOLOC + ",sdo_geom.sdo_centroid(e.geoloc, u.DIMINFO) " + CENTROID + ", sdo_geom.sdo_area(e.geoloc,'5e-8','unit=sq_m') " + AREA;
				v_ConsultaEdificacao += " from " + p_NomeTabelaLoteCtm + " l, " + p_NomeTabelaEdificacao + " e, user_sdo_geom_metadata u ";
				v_ConsultaEdificacao += " where u.TABLE_NAME = '" + (String) p_NomeTabelaEdificacao.subSequence(p_NomeTabelaEdificacao.indexOf(".") + 1, p_NomeTabelaEdificacao.length()) + "' AND u.COLUMN_NAME = 'GEOLOC'";
				v_ConsultaEdificacao += " AND l.num_lote_ctm = " + v_ResultSet.getString(OBJECTID);
				v_ConsultaEdificacao += " AND (SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E.geoloc,.5), 'DETERMINE',l.geoloc, 0.5) = 'INSIDE' ";
				v_ConsultaEdificacao += " OR SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E.geoloc,.5), 'DETERMINE',l.geoloc, 0.5) = 'COVEREDBY') ";
				v_ConsultaEdificacao += "AND sdo_relate(e.geoloc, l.geoloc, 'mask=anyinteract') = 'TRUE' ";*/

                String v_ConsultaEdificacao = "select e."+geometria_edificacao+" " + GEOLOC + ",sdo_geom.sdo_centroid(e."+geometria_edificacao+", .05) " + CENTROID + ", sdo_geom.sdo_area(e."+geometria_edificacao+",'5e-8','unit=sq_m') " + AREA;
				v_ConsultaEdificacao += " from " + p_NomeTabelaLoteCtm + " l, " + p_NomeTabelaEdificacao + " e ";
				v_ConsultaEdificacao += " where l."+lote_ctm_numero_do_lote+" = " + v_ResultSet.getString(OBJECTID);
				v_ConsultaEdificacao += " AND (SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+",.5), 'DETERMINE',l."+geometria_lote+", 0.5) = 'INSIDE' ";
				v_ConsultaEdificacao += " OR SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+",.5), 'DETERMINE',l."+geometria_lote+", 0.5) = 'COVEREDBY') ";
				v_ConsultaEdificacao += "AND sdo_relate(e."+geometria_edificacao+", l."+geometria_lote+", 'mask=anyinteract') = 'TRUE' ";

				preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, m_CorEdificacao, Color.BLACK, null, null, null, null, v_ConsultaEdificacao, null, v_DesvioParaCaberElementosAoRedor);
			}
		}

		Color[] v_CorCentroids = new Color[3];
		v_CorCentroids[0] = m_CorLoteIptu;
		v_CorCentroids[1] = m_CorLoteCtm;
		v_CorCentroids[2] = m_CorLoteEndereco;

		preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, null, m_CorLote, null, null, null, v_CorCentroids, v_ConsultaLote, null, v_DesvioParaCaberElementosAoRedor);

		String v_ConsultaLoteNaoDestacar = "select l."+lote_ctm_numero_do_lote+" " + OBJECTID + " , l."+geometria_lote+" " + GEOLOC;
		v_ConsultaLoteNaoDestacar += " from " + p_NomeTabelaLoteCtm + " l, " +  p_NomeTabelaQuadraCtm + " q";
		v_ConsultaLoteNaoDestacar += " where q."+quadra_ctm_numero_da_quadra+" = " + p_PKQuadra;
		v_ConsultaLoteNaoDestacar += " AND sdo_relate(l."+lote_ctm_numero_do_lote+", q."+geometria_quadra+", 'mask=anyinteract') = 'TRUE'";

		if (!v_ConsultaDeLotes.equals(""))
		{
			v_ConsultaLoteNaoDestacar += " AND l."+lote_ctm_numero_do_lote+" not in(" + v_ConsultaDeLotes + ")";
		}

		preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, null, m_CorLote, null, null, null, null, v_ConsultaLoteNaoDestacar, null, v_DesvioParaCaberElementosAoRedor);

		escreverCabecalhoTipo1(v_BufferedImagem, v_Graficos, m_CorQuadra, m_CorLote, m_CorEdificacao, m_CorLoteIptu, m_CorLoteCtm, m_CorLoteEndereco, p_PKQuadra, p_NomeTabelaQuadraCtm, p_NomeTabelaEdificacao, p_NomeTabelaBairroPopular, p_NomeTabelaIptuGeoCtm,geometria_bairro_popular, nome_bairro_popular,
                        geometria_quadra,quadra_ctm_numero_da_quadra,iptu_ctm_geo_indice_cadastral,iptu_ctm_geo_numero_do_lote);

		BufferedImage v_ImagemTranformada = CriarImagemTranformada(v_BufferedImagem, p_MargemPixel);
		return v_ImagemTranformada;
			}

	/**
	 * Funcão que gera o croqui do lote, com todos as suas edificações e comprimento e �rea das edificações
	 * e do próprio lote
	 * @return Retorna o bufferedImage que representa o croqui
	 * @param p_Tamanho Tamanho da imagem a ser gerada(A4, A3, A2)
	 * @param p_PKLote Número Lote CTM do lote
	 * @param p_OwnerTabelaLoteCtm Indica o owner da tabela de lote
	 * @param p_OwnerTabelaEdificacao Indica o owner da tabela de edificação
	 * @param p_OwnerIptuGeoCtm Indica o owner da tabela de iptu
	 * @param p_OwnerFuncaoGetDistancia Indica o owner da função que calcula a distância entre 2 pontos em metros
	 * @exception SQLException
	 */
	public BufferedImage gerarCroquiTipo2(com.lowagie.text.Rectangle p_Tamanho,
			String p_PKLote, int p_MargemPixel,
			String p_NomeTabelaLoteCtm,
			String p_NomeTabelaEdificacao, String p_NomeTabelaIptuCtmGeo,
                        String geometria_edificacao,
                        String geometria_lote,String lote_ctm_numero_do_lote,
                        String iptu_ctm_indice_cadastral,String iptu_ctm_geo_area_construida,
                        String iptu_ctm_geo_numero_do_lote,String iptu_ctm_geo_numero_do_imovel)
                        throws SQLException
			{
		limparCampos();

        m_Margem = p_MargemPixel;
		BufferedImage v_BufferedImagem = CriarImagemInicial(p_Tamanho, p_MargemPixel);
		Graphics2D v_Graficos = (Graphics2D) v_BufferedImagem.getGraphics();

		Color v_CorCoordenadasSegmentoEdificacao = Color.MAGENTA;
		Color v_CorCentroidEdificacao = new Color(153, 0, 102);
		Color v_CorCoordenadasSegmentoLote = Color.BLACK;
		Color v_CorCentroidLote = Color.GREEN;
		Color v_CorDivisaoSegmentosEdificacao = Color.RED;

		double v_DesvioParaCaberElementosAoRedor = 1;
		int v_AlturaUtilizada = escreverCabecalhoTipo2(v_BufferedImagem, v_Graficos, m_CorLote, m_CorEdificacao, p_PKLote, p_NomeTabelaIptuCtmGeo,iptu_ctm_indice_cadastral,iptu_ctm_geo_numero_do_lote);
        v_AlturaUtilizada += p_MargemPixel;
		/*String v_NomeTabelaLoteCtm = p_OwnerTabelaLoteCtm + "." + LOTE_CTM;
		String v_NomeTabelaEdifParticular = p_OwnerTabelaEdificacao + "." + EDIFICACAO;*/

		String v_Consulta = getConsultaLote(p_NomeTabelaLoteCtm, p_PKLote,geometria_lote,lote_ctm_numero_do_lote);
        preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, null, m_CorLote, v_CorCoordenadasSegmentoLote, v_CorDivisaoSegmentosEdificacao, v_CorCentroidLote, null, v_Consulta,(String) p_NomeTabelaLoteCtm.subSequence(p_NomeTabelaLoteCtm.indexOf(".") + 1, p_NomeTabelaLoteCtm.length()), v_DesvioParaCaberElementosAoRedor);
        
		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(v_Consulta);

		while(v_ResultSet.next())
		{
			/*String v_ConsultaEdificacao = "select e.geoloc " + GEOLOC + ",sdo_geom.sdo_centroid(e.geoloc, u.DIMINFO) " + CENTROID + ", sdo_geom.sdo_area(e.geoloc,'5e-8','unit=sq_m') " + AREA;
			v_ConsultaEdificacao += " from " + p_NomeTabelaLoteCtm + " l, " + p_NomeTabelaEdificacao + " e, user_sdo_geom_metadata u ";
			v_ConsultaEdificacao += " where u.TABLE_NAME = '" + (String) p_NomeTabelaEdificacao.subSequence(p_NomeTabelaEdificacao.indexOf(".") + 1, p_NomeTabelaEdificacao.length()) + "' AND u.COLUMN_NAME = 'GEOLOC'";
			v_ConsultaEdificacao +=  " AND l.num_lote_ctm = " + v_ResultSet.getString(OBJECTID);
			v_ConsultaEdificacao += " AND (SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E.geoloc,.5), 'DETERMINE',l.geoloc, 0.5) = 'INSIDE' ";
			v_ConsultaEdificacao += " OR SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E.geoloc,.5), 'DETERMINE',l.geoloc, 0.5) = 'COVEREDBY') ";
			v_ConsultaEdificacao += " AND sdo_relate(e.geoloc, l.geoloc, 'mask=anyinteract') = 'TRUE' ";*/

            String v_ConsultaEdificacao = "select e."+geometria_edificacao+" " + GEOLOC + ",sdo_geom.sdo_centroid(e."+geometria_edificacao+", .05) " + CENTROID + ", sdo_geom.sdo_area(e."+geometria_edificacao+",'5e-8','unit=sq_m') " + AREA;
			v_ConsultaEdificacao += " from " + p_NomeTabelaLoteCtm + " l, " + p_NomeTabelaEdificacao + " e ";
			v_ConsultaEdificacao += " where l."+lote_ctm_numero_do_lote+" = " + v_ResultSet.getString(OBJECTID);
			v_ConsultaEdificacao += " AND (SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+",.5), 'DETERMINE',l."+geometria_lote+", 0.5) = 'INSIDE' ";
			v_ConsultaEdificacao += " OR SDO_GEOM.RELATE(SDO_GEOM.SDO_CENTROID(E."+geometria_edificacao+",.5), 'DETERMINE',l."+geometria_lote+", 0.5) = 'COVEREDBY') ";
			v_ConsultaEdificacao += " AND sdo_relate(e."+geometria_edificacao+", l."+geometria_lote+", 'mask=anyinteract') = 'TRUE' ";


			preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, m_CorEdificacao, Color.BLACK, v_CorCoordenadasSegmentoEdificacao, v_CorDivisaoSegmentosEdificacao, v_CorCentroidEdificacao, null, v_ConsultaEdificacao, (String) p_NomeTabelaEdificacao.subSequence(p_NomeTabelaEdificacao.indexOf(".") + 1, p_NomeTabelaEdificacao.length()), v_DesvioParaCaberElementosAoRedor);
		}

		v_Consulta = getConsultaLote(p_NomeTabelaLoteCtm, p_PKLote,geometria_lote,lote_ctm_numero_do_lote);
		preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, null, m_CorLote, v_CorCoordenadasSegmentoLote, v_CorDivisaoSegmentosEdificacao, v_CorCentroidLote, null, v_Consulta, (String) p_NomeTabelaLoteCtm.subSequence(p_NomeTabelaLoteCtm.indexOf(".") + 1, p_NomeTabelaLoteCtm.length()), v_DesvioParaCaberElementosAoRedor);

        BufferedImage v_ImagemTranformada = CriarImagemTranformada(v_BufferedImagem, p_MargemPixel);
		return v_ImagemTranformada;
			}

	/**
	 * Func�o que gera o croqui do lote com e comprimento e �rea
	 * @return Retorna o bufferedImage que representa o croqui
	 * @param p_Tamanho Tamanho da imagem a ser gerada(A4, A3, A2, LETTER, ...)
	 * @param p_PKLote N�mero Lote CTM do lote
	 * @param p_OwnerTabelaLoteCtm Indica o owner da tabela de lote
	 * @param p_OwnerIptuGeoCtm Indica o owner da tabela de iptu
	 * @param p_OwnerFuncaoGetDistancia Indica o owner da fun��o que calcula a dist�ncia entre 2 pontos em metros
	 * @exception SQLException
	 */
	public BufferedImage gerarCroquiTipo3(com.lowagie.text.Rectangle p_Tamanho,
			String p_PKLote, int p_MargemPixel,
			String p_NomeTabelaLoteCtm,
			String p_NomeTabelaIptuCtmGeo,
                        String iptu_ctm_indice_cadastral,
                        String iptu_ctm_geo_numero_do_lote,
                        String geometria_lote,String lote_ctm_numero_do_lote) throws SQLException
			{
		limparCampos();

        m_Margem = p_MargemPixel;
		BufferedImage v_BufferedImagem = CriarImagemInicial(p_Tamanho, p_MargemPixel);
		Graphics2D v_Graficos = (Graphics2D) v_BufferedImagem.getGraphics();

		Color v_CorCoordenadasLote = Color.MAGENTA;
		Color v_CorCentroidLote = Color.RED;
		Color v_CorDivisaoSegmentosLote = Color.RED;

		double v_DesvioParaCaberElementosAoRedor = 1;
		int v_AlturaUtilizada = escreverCabecalhoTipo3(v_BufferedImagem, v_Graficos, m_CorLote, p_PKLote, p_NomeTabelaIptuCtmGeo,iptu_ctm_indice_cadastral,iptu_ctm_geo_numero_do_lote);

		//String v_NomeTabelaLoteCtm = p_OwnerTabelaLoteCtm + "." + LOTE_CTM;

		String v_Consulta = getConsultaLote(p_NomeTabelaLoteCtm, p_PKLote,geometria_lote,lote_ctm_numero_do_lote);

		preencherGeometria(v_BufferedImagem, v_Graficos, v_AlturaUtilizada, null, m_CorLote, v_CorCoordenadasLote, v_CorDivisaoSegmentosLote, v_CorCentroidLote, null, v_Consulta, (String) p_NomeTabelaLoteCtm.subSequence(p_NomeTabelaLoteCtm.indexOf(".") + 1, p_NomeTabelaLoteCtm.length()), v_DesvioParaCaberElementosAoRedor);

		BufferedImage v_ImagemTranformada = CriarImagemTranformada(v_BufferedImagem, p_MargemPixel);
		return v_ImagemTranformada;
			}

	/**
	 * Função que cria a imagem inicial do croqui, com seus tamanhos(Altura e Largura já
	 * definidos e desenha um retângulo branco em toda a imagem
	 * @param p_Tamanho Tamanho da imagem a ser gerada(A4, A3, A2, LETTER, ...)
	 */
	private static BufferedImage CriarImagemInicial(com.lowagie.text.Rectangle p_PageSize, int p_MargemPixel)
	{
		BufferedImage v_BufferedImage = new BufferedImage(Math.round(p_PageSize.getWidth() - p_MargemPixel), Math.round(p_PageSize.getHeight() - p_MargemPixel), BufferedImage.TYPE_INT_ARGB);
		Graphics2D v_Graficos =  v_BufferedImage.createGraphics();

		EscreverRetangulo(v_Graficos,Color.WHITE, 0, 0, Math.round(p_PageSize.getWidth()), Math.round(p_PageSize.getHeight()), true);

		return v_BufferedImage;
	}

	/**
	 * Func�o que cria a imagem j� tranformada do croqui com as devidas margens
	 * @param p_BufferedImagemOriginal Imagem original sem a margem
	 * @param p_MargemPixel Margem em pixel devida na imagem
	 */
	private static BufferedImage CriarImagemTranformada(BufferedImage p_BufferedImagemOriginal, int p_MargemPixel)
	{
		AffineTransform v_AffineTransform = new AffineTransform();
		AffineTransformOp v_AffineTransformOp = new AffineTransformOp(v_AffineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		BufferedImage v_ImagemTransformada = new BufferedImage(Math.round(p_BufferedImagemOriginal.getWidth() + p_MargemPixel), Math.round(p_BufferedImagemOriginal.getHeight() + p_MargemPixel), BufferedImage.TYPE_INT_ARGB);
		Graphics2D v_Graficos =  v_ImagemTransformada.createGraphics();

		EscreverRetangulo(v_Graficos,Color.WHITE, 0, 0, Math.round(v_ImagemTransformada.getWidth()), Math.round(v_ImagemTransformada.getHeight()), true);
		v_Graficos.drawImage(p_BufferedImagemOriginal, v_AffineTransformOp, p_MargemPixel / 2, p_MargemPixel / 2);

		return v_ImagemTransformada;
	}

	/**
	 * Procedimento que limpa dos todos membros do croqui para iniciar uma nova gera��o de croqui
	 */
	private void limparCampos()
	{
		m_MapeamentoDasCoresDoLogradouro = new Hashtable<String, Color>();
		m_MaiorX = Double.NaN;
		m_MaiorY = Double.NaN;
		m_MenorX = Double.NaN;
		m_MenorY = Double.NaN;
        m_Tamanho = 0;
        m_Margem = 0;
	}

	/**
	 * Procedimento que escreve um texto na imagem
	 * @param p_Graficos Gr�fico que ser� gerada a imagem
	 * @param p_Cor Cor do texto a escrever
	 * @param p_Texto Texto a escrever
	 */
	private void escreverTexto(Graphics2D p_Graficos,
			Color p_Cor, String p_Texto, int p_X, int p_Y)
	{
		p_Graficos.setPaint(p_Cor);
		p_Graficos.drawString(p_Texto, p_X, p_Y);
	}

	/**
	 * Procedimento que escreve um texto na imagem
	 * @param p_Graficos Gr�fico que ser� gerada a imagem
	 * @param p_Cor Cor do texto a escrever
	 * @param p_Preencher Colocar preenchimento no ret�ngulo
	 * @param p_Texto Texto a escrever
	 * @param p_RetanguloX Coordenada x inicial do ret�ngulo a ser desenhado
	 * @param p_RetanguloY Coordenada y inicial do ret�ngulo a ser desenhado
	 * @param p_TamanhoRetanguloX Largura do x
	 * @param p_TamanhoRetanguloY Largura do y
	 */
	private void escreverTextoComRetangulo(Graphics2D p_Graficos, Color p_Cor, boolean p_Preencher,
			String p_Texto, int p_RetanguloX,
			int p_RetanguloY, int p_TamanhoRetanguloX, int p_TamanhoRetanguloY)
	{
		EscreverRetangulo(p_Graficos, p_Cor, p_RetanguloX, p_RetanguloY, p_TamanhoRetanguloX, p_TamanhoRetanguloY, p_Preencher);
		escreverTexto(p_Graficos, p_Cor, p_Texto, p_RetanguloX + p_TamanhoRetanguloX + 2, p_RetanguloY + p_TamanhoRetanguloY);
	}

	/**
	 * Procedimento que escreve um texto na imagem
	 * @param p_Graficos Gr�fico que ser� gerada a imagem
	 * @param p_CorRetangulo Cor do ret�ngulo a ser desenhado
	 * @param p_Preencher Colocar preenchimento no ret�ngulo
	 * @param p_RetanguloX Coordenada x inicial do ret�ngulo a ser desenhado
	 * @param p_RetanguloY Coordenada y inicial do ret�ngulo a ser desenhado
	 * @param p_TamanhoRetanguloX Largura do x
	 * @param p_TamanhoRetanguloY Largura do y
	 * @param p_Preencher Indica se � para preencher o ret�ngulo ou s� desenhar
	 */
	private static void EscreverRetangulo(Graphics2D p_Graficos, Color p_CorRetangulo, int p_RetanguloX,
			int p_RetanguloY, int p_TamanhoRetanguloX, int p_TamanhoRetanguloY, boolean p_Preencher)
	{
		Rectangle v_Retangulo = new Rectangle();
		v_Retangulo.setBounds(p_RetanguloX, p_RetanguloY, p_TamanhoRetanguloX, p_TamanhoRetanguloY);

		p_Graficos.setPaint(p_CorRetangulo);

		if (p_Preencher)
		{
			p_Graficos.fill(v_Retangulo);
		}

		else
		{
			p_Graficos.draw(v_Retangulo);
		}

	}

	/**
	 * Procedimento que escreve a legenda o croqui 1.
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Gr�fico que ser� gerada a imagem
	 * @param p_CorLote Cor do lote
	 * @param p_CorEdificacao Cor da edificação
	 * @param p_LoteIptu Cor do lote iptu
	 * @param p_LoteCtm Cor do lote ctm
	 * @param p_CorImovel Cor do im�vel
	 * @param p_PKQuadra N�mero lote ctm da quadra
	 * @param p_OwnerTabelaQuadra Owner da tabela quadra
	 * @param p_OwnerTabelaLoteCtm Owner da tabela de lote
	 * @param p_OwnerTabelaBairroPopular Owner da tabela de bairro
	 * @param p_OwnerTabelaIptuGeoCtm Owner da tabela de iptu
	 * @exception SQLException
	 * @exception IndiceCadastralNaoEncontradaParaAQuadraCroquiException
	 */
	private void escreverCabecalhoTipo1(BufferedImage p_BufferedImage, Graphics2D p_Graficos,
			Color p_CorQuadra, Color p_CorLote, Color p_CorEdificacao, Color p_LoteIptu,
			Color p_LoteCtm,  Color p_CorImovel, int p_PKQuadra,
			String p_NomeTabelaQuadra, String p_NomeTabelaLoteCtm,
			String p_NomeTabelaBairroPopular,
			String p_NomeTabelaIptuGeoCtm,
                        String geometria_bairro_popular, String nome_bairro_popular,
                        String geometria_quadra,String quadra_ctm_numero_da_quadra,
                        String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_numero_do_lote
                        ) throws SQLException,
			IndiceCadastralNaoEncontradaParaAQuadraCroquiException
			{
		Font v_FonteOriginal = p_Graficos.getFont();

		p_Graficos.setFont(m_FonteCabecalho);

		Rectangle v_Retangulo = new Rectangle();
		v_Retangulo.setBounds(0, 0, p_BufferedImage.getWidth(), ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_1);

		//prrenche toda a �rea do cabecalho para em branco para que s� seja mostrado cabe�alho
		p_Graficos.setPaint(Color.WHITE);
		p_Graficos.fill(v_Retangulo);

		String v_NomeBairro = getNomeBairroDaQuadra(p_PKQuadra, p_NomeTabelaQuadra, p_NomeTabelaBairroPopular,geometria_bairro_popular,nome_bairro_popular, geometria_quadra,quadra_ctm_numero_da_quadra);
		String v_IndiceCadastral = getIndiceCadastralDaQuadra(p_PKQuadra, p_NomeTabelaIptuGeoCtm,iptu_ctm_geo_indice_cadastral,iptu_ctm_geo_numero_do_lote);
		String v_CtmSetor = new Integer(p_PKQuadra).toString();

		escreverTexto(p_Graficos, Color.BLACK, "LOCALIZAÇÃO DOS LOTES", 10, 30);
		escreverTexto(p_Graficos, Color.BLACK, "Bairro: " + v_NomeBairro, 10, 60);
		escreverTexto(p_Graficos, Color.BLACK, "IPTU Zona Fiscal: " + v_IndiceCadastral.substring(0, 3), 10, 90);
		escreverTexto(p_Graficos, Color.BLACK, "IPTU Quarteirão: " + v_IndiceCadastral.substring(3, 7), 10, 120);
		escreverTexto(p_Graficos, p_LoteIptu, "Lote IPTU: -", 10, 150);

		escreverTexto(p_Graficos, Color.BLACK, "CTM - Setor: " + v_CtmSetor.substring(0, 2), 200, 90);
		escreverTexto(p_Graficos, Color.BLACK, "Quadra: " + v_CtmSetor.substring(2, 7), 200, 120);
		escreverTexto(p_Graficos, p_LoteCtm, "Lote CTM: -", 200, 150);

		escreverTextoComRetangulo(p_Graficos, p_CorQuadra, false, "QUADRA", 390, 80, 30, 10);
		escreverTextoComRetangulo(p_Graficos, p_CorLote, false, "LOTE", 390, 100, 30, 10);
		escreverTextoComRetangulo(p_Graficos, p_CorEdificacao, true, "EDIFICAÇÃO", 390, 120, 30, 10);
		escreverTexto(p_Graficos, p_CorImovel, "Nº Imóvel: -", 390, 150);

		escreverRetanguloNoCabecalho(p_BufferedImage, p_Graficos, ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_1);

		preencherLogradourosNoCabecalho(p_Graficos, 170);

		p_Graficos.setFont(v_FonteOriginal);
			}

	/**
	 * Procedimento que escreve o nome dos logradouros no cabeçalho
	 * @param p_Graficos Gráfico que será gerada a imagem
	 * @param p_YInicial Indica o valor inicial do Y para começar a escrever os logradouros
	 */
	private void preencherLogradourosNoCabecalho(Graphics2D p_Graficos, int p_YInicial)
	{
		int v_PuloDeLinha = 30;
		int v_ColunaX1 = 10;
		int v_ColunaX2 = 200;
		int v_ColunaX3 = 390;

		int v_X = v_ColunaX1;
		int v_Y = p_YInicial;
		Enumeration<String> v_Chaves = m_MapeamentoDasCoresDoLogradouro.keys();
		int v_QuantidadeMaximaDeLogradourosAMostrar = 9;

		if (m_MapeamentoDasCoresDoLogradouro.size() < v_QuantidadeMaximaDeLogradourosAMostrar)
		{
			v_QuantidadeMaximaDeLogradourosAMostrar = m_MapeamentoDasCoresDoLogradouro.size();
		}

		String[] v_ChavesOrdenadas = new String[v_QuantidadeMaximaDeLogradourosAMostrar];

		for(int c_Cor = 0; c_Cor < v_QuantidadeMaximaDeLogradourosAMostrar; c_Cor ++)
		{
			v_ChavesOrdenadas[c_Cor] = v_Chaves.nextElement();
		}

		Arrays.sort(v_ChavesOrdenadas);

		for(int c_Cor = 0; c_Cor < v_QuantidadeMaximaDeLogradourosAMostrar; c_Cor ++)
		{
			String v_Nome = v_ChavesOrdenadas[c_Cor];
			//int v_TamanhoMaximoDaSubstring = 24;
            int v_TamanhoMaximoDaSubstring = 31;

			if (v_Nome.length() < v_TamanhoMaximoDaSubstring)
			{
				v_TamanhoMaximoDaSubstring = v_Nome.length();
			}

			p_Graficos.setPaint(m_MapeamentoDasCoresDoLogradouro.get(v_Nome));

			v_Nome = v_Nome.substring(0, v_TamanhoMaximoDaSubstring);

            p_Graficos.setFont(m_FonteCabecalhoLogradouros);
			p_Graficos.drawString(v_Nome , v_X, v_Y);
            p_Graficos.setFont(m_FonteCabecalho);

			if (v_X == v_ColunaX3)
			{
				v_X = v_ColunaX1;
				v_Y = v_Y + v_PuloDeLinha;
			}
			else
			{
				if (v_X == v_ColunaX1)
				{
					v_X = v_ColunaX2;
				}

				else
				{
					v_X = v_ColunaX3;
				}
			}
		}
	}

	/**
	 * Procedimento que escreve a legenda o croqui 2.
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Gráfico que será gerada a imagem
	 * @param p_CorLote Colocar preenchimento no retângulo
	 * @param p_CorEdificacao Coordenada x inicial do retângulo a ser desenhado
	 * @param p_PKLote Número de lote ctm do lote
	 * @param p_OwnerTabelaIptuGeoCtm Indica o owner da tabela de iptu
	 * @exception SQLException
	 */
	private int escreverCabecalhoTipo2(BufferedImage p_BufferedImage, Graphics2D p_Graficos,
			Color p_CorLote, Color p_CorEdificacao, String p_PKLote, String p_NomeTabelaIptuCtmGeo,
                        String iptu_ctm_indice_cadastral,String iptu_ctm_geo_numero_do_lote) throws SQLException
			{
		Font v_FonteOriginal = p_Graficos.getFont();

		p_Graficos.setFont(m_FonteCabecalho);

		String v_CodigoIptu = getCodigoIptu(p_PKLote, p_NomeTabelaIptuCtmGeo,iptu_ctm_indice_cadastral,iptu_ctm_geo_numero_do_lote);

		escreverTexto(p_Graficos, Color.BLACK, "MEDIDAS DAS EDIFICAÇÕES DO LOTE", 10, 30);
		escreverTexto(p_Graficos, Color.BLACK, "CTM: " + p_PKLote, 10, 60);
		escreverTexto(p_Graficos, Color.BLACK, "IPTU: " + v_CodigoIptu, 10, 90);

		escreverTextoComRetangulo(p_Graficos, p_CorLote, false, "LOTE", 200, 40, 30, 30);
		escreverTextoComRetangulo(p_Graficos, p_CorEdificacao, true, "EDIFICAÇÃO", 200, 80, 30, 30);

		escreverRetanguloNoCabecalho(p_BufferedImage, p_Graficos, ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_2);

		p_Graficos.setFont(v_FonteOriginal);

		return ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_2;
			}

	/**
	 * Procedimento que escreve a legenda o croqui 3.
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Gráfico que será gerada a imagem
	 * @param p_CorLote Colocar preenchimento no retângulo
	 * @param p_PKLote Número de lote ctm do lote
	 * @param p_OwnerTabelaIptuGeoCtm Indica o owner da tabela de iptu
	 * @exception SQLException
	 */
	private int escreverCabecalhoTipo3(BufferedImage p_BufferedImage, Graphics2D p_Graficos,
			Color p_CorLote, String p_PKLote, String p_NomeTabelaIptuCtmGeo,
                        String iptu_ctm_indice_cadastral,String iptu_ctm_geo_numero_do_lote) throws SQLException
			{
		String v_CodigoIptu = getCodigoIptu(p_PKLote, p_NomeTabelaIptuCtmGeo,
                        iptu_ctm_indice_cadastral,iptu_ctm_geo_numero_do_lote);

		Font v_FonteOriginal = p_Graficos.getFont();

		p_Graficos.setFont(m_FonteCabecalho);

		escreverTexto(p_Graficos, Color.BLACK, "MEDIDAS DO LOTE", 10, 30);
		escreverTexto(p_Graficos, Color.BLACK, "CTM: " + p_PKLote, 10, 60);
		escreverTexto(p_Graficos, Color.BLACK, "IPTU: " + v_CodigoIptu, 10, 90);

		escreverTextoComRetangulo(p_Graficos, p_CorLote, false, "LOTE", 200, 40, 30, 30);

		escreverRetanguloNoCabecalho(p_BufferedImage, p_Graficos, ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_3);

		p_Graficos.setFont(v_FonteOriginal);

		return ALTURA_UTILIZADA_PARA_CABECALHO_CROQUI_3;
			}

	/**
	 * Procedimento que escreve o ret�ngulo a moldura da legenda
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Gráfico que será gerada a imagem
	 * @param p_Altura Define a altura que o retângulo deverá ter
	 */
	private void escreverRetanguloNoCabecalho(BufferedImage p_BufferedImage, Graphics2D p_Graficos, int p_Altura)
	{
		Color v_CorRetangulo = Color.BLACK;
		EscreverRetangulo(p_Graficos, v_CorRetangulo, 1, 1, p_BufferedImage.getWidth() - 2, p_Altura - 2, false);
	}

	/**
	 * Função que retorna o código iptu do lote
	 * @return Retorna o código iptu do lote
	 * @param p_PKLote Número de lote ctm do lote
	 * @param p_Graficos Gráfico que será gerada a imagem
	 * @param p_OwnerTabelaIptuGeoCtm Indica o owner da tabela de iptu
	 */
	private String getCodigoIptu(String p_PKLote, String p_NomeTabelaIptuCtmGeo,String iptu_ctm_indice_cadastral,String iptu_ctm_geo_numero_do_lote) throws SQLException
	{
		Statement v_Statement = m_Conexao.createStatement();
		//String v_NomeIptuGeomCtm = p_OwnerIptuGeomCtm + "." + IPTU_CTM_GEO;

//		String v_Consulta = "select substr(indice_cadastral, 1, 10) from " + v_NomeIptuGeomCtm;
		String v_Consulta = "select "+iptu_ctm_indice_cadastral+" from " + p_NomeTabelaIptuCtmGeo;
		v_Consulta += " where "+iptu_ctm_geo_numero_do_lote+" = " + p_PKLote;
		ResultSet v_ResultSet = v_Statement.executeQuery(v_Consulta);

		v_ResultSet.next();

		return v_ResultSet.getString(1);
	}

	/**
	 * Fun��o que retorna o nome do bairro que cont�m a quadra
	 * @return Retorna o nome do bairro que cont�m a quadra
	 * @param p_PKQuadra N�mero de lote ctm da quadra
	 * @param p_Graficos Gr�fico que ser� gerada a imagem
	 * @param p_OwnerTabelaQuadra Indica o owner da tabela de quadra
	 * @param p_OwnerTabelaBairroPopular Indica o owner da tabela de bairro
	 */
	private String getNomeBairroDaQuadra(int p_PKQuadra, String p_NomeTabelaQuadra,
			String p_NomeTabelaBairroPopular,
                        String geometria_bairro_popular, String nome_bairro_popular,
                        String geometria_quadra,String quadra_ctm_numero_da_quadra) throws SQLException
			{
		/*String v_NomeTabelaQuadra = p_OwnerTabelaQuadra + "." + QUADRA_CTM;
		String v_NomeTabelaBairroPopular = p_OwnerTabelaBairroPopular + "." + BAIRRO_POPULAR;*/

		String v_NomeCampo = "NOME";
		String v_Consulta = "select b."+nome_bairro_popular+ " " + v_NomeCampo;
		v_Consulta += " from " + p_NomeTabelaQuadra + " q, " +  p_NomeTabelaBairroPopular + " b";
		v_Consulta += " where q."+quadra_ctm_numero_da_quadra+" = " + p_PKQuadra;
		v_Consulta += " AND sdo_relate(q."+geometria_quadra+", b."+geometria_bairro_popular+", 'mask=anyinteract') = 'TRUE'";
		v_Consulta += " AND rownum = 1";

		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(v_Consulta);

		String v_NomeBairro = "";
		if (v_ResultSet.next())
		{
			v_NomeBairro = v_ResultSet.getString(v_NomeCampo);
		}

		v_ResultSet.close();
		v_Statement.close();

		return v_NomeBairro;
			}

	/**
	 * Fun��o que retorna o indice cadastral da quadra
	 * @return Retorna o indice cadastral da quadra
	 * @param p_PKQuadra N�mero de lote ctm da quadra
	 * @param p_OwnerTabelaQuadraCtm Owner da tabela de quadra
	 * @param p_OwnerTabelaLoteCtm Owner da tabela de lote ctm
	 * @param p_OwnerTabelaQuadra Owner da tabela de quadra
	 * @param p_OwnerTabelaIptuCtmGeo Owner da tabela de iptu
	 */
	private String getIndiceCadastralDaQuadra(int p_PKQuadra,
			String p_NomeTabelaIptuCtmGeo,
                        String iptu_ctm_geo_indice_cadastral,String iptu_ctm_geo_numero_do_lote) throws SQLException, IndiceCadastralNaoEncontradaParaAQuadraCroquiException
			{
		/*String v_NomeTabelaIptuGeoCtm = p_OwnerTabelaIptuCtmGeo + "." + IPTU_CTM_GEO;*/
        String v_NomeCampoIndiceCadastral = "indice";

        String v_Consulta = "select i."+iptu_ctm_geo_indice_cadastral+ " " + v_NomeCampoIndiceCadastral;
        v_Consulta += " from "+p_NomeTabelaIptuCtmGeo+ " i ";
        v_Consulta += " where i."+iptu_ctm_geo_numero_do_lote+" like '"+p_PKQuadra+"%' ";
        v_Consulta += " and rownum = 1 ";

		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(v_Consulta);

		String v_IndiceCadastral = "";
		if (v_ResultSet.next())
		{
			v_IndiceCadastral = v_ResultSet.getString(v_NomeCampoIndiceCadastral);
		}
		v_ResultSet.close();
		v_Statement.close();

		if (v_IndiceCadastral == null || v_IndiceCadastral.equals(""))
		{
			throw new IndiceCadastralNaoEncontradaParaAQuadraCroquiException("Indice Cadastral nao encontrado para a quadra");
		}
		return v_IndiceCadastral;
			}

	/**
	 * Fun��o que retorna as cores poss�veis do logradouro
	 * @return Retorna as cores poss�veis do logradouro
	 */
	private static ArrayList<Color> GetCoresPossiveisDosLogradouros()
	{
		ArrayList<Color> v_CoresPossiveisDosLogradouros = new ArrayList<Color>();

		//os sete primeiros est�o definidos, os outros ser�o aleat�rios
		v_CoresPossiveisDosLogradouros.add(Color.MAGENTA);
		//v_CoresPossiveisDosLogradouros.add(Color.CYAN);
        v_CoresPossiveisDosLogradouros.add(new Color(51, 102, 102));
		v_CoresPossiveisDosLogradouros.add(Color.BLUE);
		v_CoresPossiveisDosLogradouros.add(Color.BLACK);
		v_CoresPossiveisDosLogradouros.add(new Color(153, 0, 102));
		//v_CoresPossiveisDosLogradouros.add(Color.PINK);
		v_CoresPossiveisDosLogradouros.add(new Color(153, 102, 0));
		v_CoresPossiveisDosLogradouros.add(Color.GREEN);

		int v_ValorMaximoDeCoresAGerar = 30;
		for(int c_Cor = 0; c_Cor < v_ValorMaximoDeCoresAGerar; c_Cor ++)
		{
			int v_Red = (int) (Math.random() *  10000) % 256;
			int v_Green =(int) (Math.random() *  10000) % 256;
			int v_Blue = (int) (Math.random() *  10000) % 256;

			Color v_Cor = new Color(v_Red, v_Green, v_Blue);

			if (!v_CoresPossiveisDosLogradouros.contains(v_Cor))
			{
				v_CoresPossiveisDosLogradouros.add(v_Cor);
			}
		}
		return v_CoresPossiveisDosLogradouros;
	}

	/**
	 * Procedimento que desenha o logradouro no croqui
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Grafico que sera gerada a imagem
	 * @param p_AlturaUtilizada Desvio da altura que se deve considerar para desenhar o croqui
	 * @param p_Consulta Consulta a ser feita para encontrar os logradouros
	 * @param p_DesvioParaCaberElementosAoRedor Indica o desvio que se faz se caber os elementos a redor de uma geometria, no caso da aplica��o, uma quadra.
	 * @exception SQLException
	 */
	private void preencherLogradouro(BufferedImage p_BufferedImagem, Graphics2D p_Graficos, int p_AlturaUtilizada,
			String p_Consulta, double p_DesvioParaCaberElementosAoRedor,String p_NomeTabelaTrecho,
                        String geometria_trecho,String trecho_id_logradouro,String trecho_mi_prinx) throws SQLException
			{

		String v_ConsultaMapeamentoDeCores = p_Consulta + " order by " + NULOG;
		gerarMapeamentoDeCores(v_ConsultaMapeamentoDeCores);
		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(v_ConsultaMapeamentoDeCores);

		while(v_ResultSet.next())
		{
            Integer v_ObjectId = v_ResultSet.getInt(OBJECTID);
			Color v_Cor = m_MapeamentoDasCoresDoLogradouro.get(v_ResultSet.getString(NULOG));

            String v_NovaConsulta = p_Consulta + " AND t."+trecho_mi_prinx+" = " + v_ObjectId;
            preencherGeometriaLinha(p_BufferedImagem, p_Graficos, p_AlturaUtilizada, v_Cor, null, null, null, null, v_NovaConsulta, null, p_DesvioParaCaberElementosAoRedor, null);


            String v_Consulta = "select "+trecho_id_logradouro+ " " + OBJECTID + ",";
	    v_Consulta += "sdo_cs.transform(SDO_LRS.CONVERT_TO_STD_GEOM(SDO_LRS.LOCATE_PT(SDO_LRS.CONVERT_TO_LRS_GEOM("+geometria_trecho+", 3), SDO_GEOM.SDO_LENGTH("+geometria_trecho+", 3) / 2)), 82301) " + PONTO + " from " + p_NomeTabelaTrecho;
	    v_Consulta += "  where "+trecho_mi_prinx+" = " + v_ObjectId;

            preencherPonto(p_BufferedImagem, p_Graficos, p_AlturaUtilizada, v_Cor, v_Consulta);

		}

		v_ResultSet.close();
		v_Statement.close();
			}

	/**
	 * Procedimento que gera o mapeamento de cores fazendo com que cada logradouro tenha sua pr�pria cor
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Gr�fico que será gerada a imagem
	 * @param p_AlturaUtilizada Desvio da altura que se deve considerar para desenhar o croqui
	 * @param p_Consulta Consulta a ser feita para encontrar os logradouros
	 * @exception SQLException
	 */
	private void gerarMapeamentoDeCores(String p_Consulta) throws SQLException
	{
		m_MapeamentoDasCoresDoLogradouro = new Hashtable<String, Color>();

		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(p_Consulta);

		int v_IndiceACor = 0;
		while(v_ResultSet.next())
		{
			String v_Nome = v_ResultSet.getString(NULOG);
			if (!m_MapeamentoDasCoresDoLogradouro.containsKey(v_Nome))
			{
				m_MapeamentoDasCoresDoLogradouro.put(v_Nome, m_CoresPossiveisDoLogradouros.get(v_IndiceACor));
				v_IndiceACor ++;
			}
		}
	}

	/**
	 * Procedimento que gera o mapeamento de cores fazendo com que cada logradouro tenha sua pr�pria cor
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Grafico que sera gerada a imagem
	 * @param p_DesvioAlturaUtilizada Desvio da altura que se deve considerar para desenhar o croqui
	 * @param p_CorPreenchimento Cor para preencher a geometria
	 * @param p_CorMoldura Cor para emoldurar a geometria
	 * @param p_CorCoordenadas Cor das coordenadas dos tamanhos dos segmentos
	 * @param p_CorDivisaoSegmentos Cor do ponto que mostra a divis�o entre os segmentos
	 * @param p_CorCentroid Cor do centroid da geometria
	 * @param p_CorValores Array de cores que s�o os valores da geometria da coluna VALOR
	 * @param p_Consulta Consulta a ser feita para encontrar as geometrias
	 * @param p_NomeTabelaParaMostrarAreaEComprimento Consulta a ser feita para encontrar os logradouros
	 * @param p_DesvioParaCaberElementosAoRedor Consulta a ser feita para encontrar os logradouros
	 * @param p_OwnerFuncaoGetDistancia Consulta a ser feita para encontrar os logradouros
	 * @exception SQLException
	 */
	private void preencherGeometria(BufferedImage p_BufferedImagem, Graphics2D p_Graficos, int p_DesvioAlturaUtilizada,
			Color p_CorPreenchimento, Color p_CorMoldura, Color p_CorCoordenadas, Color p_CorDivisaoSegmentos,
			Color p_CorCentroid, Color[] p_CorValores, String p_Consulta,
			String p_NomeTabelaParaMostrarAreaEComprimento, double p_DesvioParaCaberElementosAoRedor) throws SQLException
			{
		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(p_Consulta);

		while(v_ResultSet.next())
		{
			STRUCT v_EstruturaPrincipal = (oracle.sql.STRUCT) v_ResultSet.getObject(GEOLOC);
			JGeometry v_GeometriaPrincipal = JGeometry.load(v_EstruturaPrincipal);

			double[] v_Coordenadas = v_GeometriaPrincipal.getOrdinatesArray();

			for(int c_Coordenada = 0; c_Coordenada < v_Coordenadas.length; c_Coordenada ++)
			{
				v_Coordenadas[c_Coordenada] = Math.abs(v_Coordenadas[c_Coordenada]);
			}

			definirValores(p_BufferedImagem, v_Coordenadas, p_DesvioAlturaUtilizada, p_DesvioParaCaberElementosAoRedor);
			//definirMaiorEMenoresCoordenadas(v_Coordenadas, p_DesvioParaCaberElementosAoRedor);

			double[] v_X = new double[v_Coordenadas.length / 2];
			double[] v_Y = new double[v_Coordenadas.length / 2];

			for(int c_Coordenada = 0; c_Coordenada < v_Coordenadas.length; c_Coordenada = c_Coordenada + 2)
			{
				v_X[c_Coordenada / 2] = getCoordenadaX(p_BufferedImagem, v_Coordenadas[c_Coordenada]);
			}

			for(int c_Coordenada = 1; c_Coordenada < v_Coordenadas.length; c_Coordenada = c_Coordenada + 2)
			{
				v_Y[c_Coordenada / 2] = getCoordenadaY(p_BufferedImagem, v_Coordenadas[c_Coordenada], p_DesvioAlturaUtilizada);
			}

			Polygon v_Poligono = new Polygon();

			for(int c_Coordenada = 0; c_Coordenada < v_X.length; c_Coordenada ++)
			{
				v_Poligono.addPoint((int) Math.round(v_X[c_Coordenada]), (int) Math.round(v_Y[c_Coordenada]));
			}

			if (p_CorPreenchimento != null)
			{
				p_Graficos.setPaint(p_CorPreenchimento);
				p_Graficos.fill(v_Poligono);
			}

			if (p_CorMoldura != null)
			{
				p_Graficos.setPaint(p_CorMoldura);
				p_Graficos.draw(v_Poligono);
			}

			if (!"".equals(p_NomeTabelaParaMostrarAreaEComprimento) && p_NomeTabelaParaMostrarAreaEComprimento != null)
			{
				p_Graficos.setPaint(p_CorCentroid);

				STRUCT v_Centroid = (oracle.sql.STRUCT) v_ResultSet.getObject(CENTROID);
				JGeometry v_GeometriaCentroid = JGeometry.load(v_Centroid);
				double[] v_CoordenadasCentroid = v_GeometriaCentroid.getPoint();
				v_CoordenadasCentroid[0] = getCoordenadaX(p_BufferedImagem, v_CoordenadasCentroid[0]);
				v_CoordenadasCentroid[1] = getCoordenadaY(p_BufferedImagem, v_CoordenadasCentroid[1], p_DesvioAlturaUtilizada);

				Double v_AreaElemento = v_ResultSet.getDouble(AREA);

				DecimalFormat v_FormatoDecimalCentroid = new DecimalFormat("0.0");
				String v_StringCentroid = v_FormatoDecimalCentroid.format(v_AreaElemento);
				p_Graficos.drawString(v_StringCentroid, Math.round(v_CoordenadasCentroid[0]), Math.round(v_CoordenadasCentroid[1]));

				p_Graficos.setPaint(p_CorCoordenadas);
				String v_ConsultaCalculoDistancia = "";

				double[] v_CoordenadasOriginal = v_GeometriaPrincipal.getOrdinatesArray();

				for(int c_Coordenada = 2; c_Coordenada < v_CoordenadasOriginal.length; c_Coordenada = c_Coordenada + 2)
				{
					/*v_ConsultaCalculoDistancia = "select SDO_GEOM.SDO_DISTANCE(SDO_GEOMETRY(2001, 82301, ";
                    v_ConsultaCalculoDistancia += "SDO_POINT_TYPE(" + v_CoordenadasOriginal[c_Coordenada - 2] + ", " + v_CoordenadasOriginal[c_Coordenada - 1] + ", NULL),NULL,NULL), ";
                    v_ConsultaCalculoDistancia += "SDO_GEOMETRY(2001, 82301,SDO_POINT_TYPE(" + v_CoordenadasOriginal[c_Coordenada] + "," + v_CoordenadasOriginal[c_Coordenada + 1] + ", NULL),NULL,NULL), ";
                    v_ConsultaCalculoDistancia += "'5e-8','unit=M')";
                    v_ConsultaCalculoDistancia += " from dual, user_sdo_geom_metadata u";
					v_ConsultaCalculoDistancia += " where u.TABLE_NAME = '" + p_NomeTabelaParaMostrarAreaEComprimento + "' and u.COLUMN_NAME = 'GEOLOC'";*/

                    v_ConsultaCalculoDistancia = "select SDO_GEOM.SDO_DISTANCE(SDO_GEOMETRY(2001, 82301, ";
                    v_ConsultaCalculoDistancia += "SDO_POINT_TYPE(" + v_CoordenadasOriginal[c_Coordenada - 2] + ", " + v_CoordenadasOriginal[c_Coordenada - 1] + ", NULL),NULL,NULL), ";
                    v_ConsultaCalculoDistancia += "SDO_GEOMETRY(2001, 82301,SDO_POINT_TYPE(" + v_CoordenadasOriginal[c_Coordenada] + "," + v_CoordenadasOriginal[c_Coordenada + 1] + ", NULL),NULL,NULL), ";
                    v_ConsultaCalculoDistancia += "'5e-8','unit=M')";
                    v_ConsultaCalculoDistancia += " from dual";

					Statement v_StatementCalculoDistancia = m_Conexao.createStatement();
					ResultSet v_CalculoDistancia = v_StatementCalculoDistancia.executeQuery(v_ConsultaCalculoDistancia);

					v_CalculoDistancia.next();

					int v_PosicaoX = (int) Math.round(((v_X[c_Coordenada / 2] + v_X[(c_Coordenada / 2) - 1]) / 2));
					int v_PosicaoY = (int) Math.round(((v_Y[c_Coordenada / 2] + v_Y[(c_Coordenada / 2) - 1]) / 2));

					DecimalFormat v_FormatoDecimal = new DecimalFormat("0.0");

					String v_StringDistancia = v_FormatoDecimal.format(v_CalculoDistancia.getDouble(1));
					p_Graficos.drawString(v_StringDistancia, v_PosicaoX, v_PosicaoY);

					v_CalculoDistancia.close();
					v_StatementCalculoDistancia.close();
				}

				p_Graficos.setPaint(p_CorDivisaoSegmentos);

				for(int c_Coordenada = 0; c_Coordenada < v_Coordenadas.length; c_Coordenada = c_Coordenada + 2)
				{
					p_Graficos.drawString("*", Math.round(v_X[c_Coordenada / 2]), Math.round(v_Y[c_Coordenada / 2]) + CORRECAO_PARA_CARACTER_FICAR_EM_CIMA_DO_PONTO);
				}
			}

			if (p_CorValores != null)
			{
				STRUCT v_Centroid = (oracle.sql.STRUCT) v_ResultSet.getObject(CENTROID);
				String[] v_Valores = v_ResultSet.getString(VALOR).split(";");

				JGeometry v_GeometriaCentroid = JGeometry.load(v_Centroid);
				double[] v_CoordenadasCentroid = v_GeometriaCentroid.getPoint();
				v_CoordenadasCentroid[0] = getCoordenadaX(p_BufferedImagem, v_CoordenadasCentroid[0]);
				v_CoordenadasCentroid[1] = getCoordenadaY(p_BufferedImagem, v_CoordenadasCentroid[1], p_DesvioAlturaUtilizada);

				for(int c_Valor = 0; c_Valor < p_CorValores.length; c_Valor ++)
				{
					p_Graficos.setPaint(p_CorValores[c_Valor]);
					p_Graficos.drawString(v_Valores[c_Valor], Math.round(v_CoordenadasCentroid[0]), Math.round(v_CoordenadasCentroid[1]));
					v_CoordenadasCentroid[1] = v_CoordenadasCentroid[1] + PULO_DE_LINHA;
				}
			}

		}

		v_ResultSet.close();
		v_Statement.close();
			}


    /**
     * Procedimento que gera o mapeamento de cores fazendo com que cada logradouro tenha sua própria cor
     * @param p_BufferedImage Encapsula a imagem a ser gerada
     * @param p_Graficos Gráfico que será gerada a imagem
     * @param p_DesvioAlturaUtilizada Desvio da altura que se deve considerar para desenhar o croqui
     * @param p_CorPreenchimento Cor para preencher a geometria
     * @param p_CorMoldura Cor para emoldurar a geometria
     * @param p_CorCoordenadas Cor das coordenadas dos tamanhos dos segmentos
     * @param p_CorDivisaoSegmentos Cor do ponto que mostra a divisão entre os segmentos
     * @param p_CorCentroid Cor do centroid da geometria
     * @param p_CorValores Array de cores que são os valores da geometria da coluna VALOR
     * @param p_Consulta Consulta a ser feita para encontrar as geometrias do tipo linha
     * @param p_NomeTabelaParaMostrarAreaEComprimento Consulta a ser feita para encontrar os logradouros
     * @param p_DesvioParaCaberElementosAoRedor Consulta a ser feita para encontrar os logradouros
     * @param p_OwnerFuncaoGetDistancia Consulta a ser feita para encontrar os logradouros
     * @exception SQLException
     */
    private void preencherGeometriaLinha(BufferedImage p_BufferedImagem, Graphics2D p_Graficos, int p_DesvioAlturaUtilizada,
            Color p_CorMoldura, Color p_CorCoordenadas, Color p_CorDivisaoSegmentos,
            Color p_CorCentroid, Color[] p_CorValores, String p_Consulta,
            String p_NomeTabelaParaMostrarAreaEComprimento, double p_DesvioParaCaberElementosAoRedor,
            String p_OwnerFuncaoGetDistancia) throws SQLException {


        Statement v_Statement = m_Conexao.createStatement();
        ResultSet v_ResultSet = v_Statement.executeQuery(p_Consulta);

        while (v_ResultSet.next()) {
            STRUCT v_EstruturaPrincipal = (oracle.sql.STRUCT) v_ResultSet.getObject(GEOLOC);
            JGeometry v_GeometriaPrincipal = JGeometry.load(v_EstruturaPrincipal);

            double[] v_Coordenadas = v_GeometriaPrincipal.getOrdinatesArray();

            for (int c_Coordenada = 0; c_Coordenada < v_Coordenadas.length; c_Coordenada++) {
                v_Coordenadas[c_Coordenada] = Math.abs(v_Coordenadas[c_Coordenada]);
            }

            definirValores(p_BufferedImagem, v_Coordenadas, p_DesvioAlturaUtilizada, p_DesvioParaCaberElementosAoRedor);
            //definirMaiorEMenoresCoordenadas(v_Coordenadas, p_DesvioParaCaberElementosAoRedor);

            double[] v_X = new double[v_Coordenadas.length / 2];
            double[] v_Y = new double[v_Coordenadas.length / 2];

            for (int c_Coordenada = 0; c_Coordenada < v_Coordenadas.length; c_Coordenada = c_Coordenada + 2) {
                v_X[c_Coordenada / 2] = getCoordenadaX(p_BufferedImagem, v_Coordenadas[c_Coordenada]);
            }

            for (int c_Coordenada = 1; c_Coordenada < v_Coordenadas.length; c_Coordenada = c_Coordenada + 2) {
                v_Y[c_Coordenada / 2] = getCoordenadaY(p_BufferedImagem, v_Coordenadas[c_Coordenada], p_DesvioAlturaUtilizada);
            }

            Line2D v_Line = new Line2D.Double();

            for (int c_Coordenada = 0; c_Coordenada < v_X.length-1; c_Coordenada++) {
                if (p_CorMoldura != null) {
                    v_Line.setLine((int)Math.round(v_X[c_Coordenada]), (int)Math.round(v_Y[c_Coordenada]),
                            (int)Math.round(v_X[c_Coordenada+1]), (int)Math.round(v_Y[c_Coordenada+1]));
                    p_Graficos.setPaint(p_CorMoldura);
                    p_Graficos.draw(v_Line);
                }
            }

/*
            if (!"".equals(p_NomeTabelaParaMostrarAreaEComprimento) && p_NomeTabelaParaMostrarAreaEComprimento != null) {
                p_Graficos.setPaint(p_CorCentroid);

                STRUCT v_Centroid = (oracle.sql.STRUCT) v_ResultSet.getObject(CENTROID);
                JGeometry v_GeometriaCentroid = JGeometry.load(v_Centroid);
                double[] v_CoordenadasCentroid = v_GeometriaCentroid.getPoint();
                v_CoordenadasCentroid[0] = getCoordenadaX(p_BufferedImagem, v_CoordenadasCentroid[0]);
                v_CoordenadasCentroid[1] = getCoordenadaY(p_BufferedImagem, v_CoordenadasCentroid[1], p_DesvioAlturaUtilizada);

                Double v_AreaElemento = v_ResultSet.getDouble(AREA);

                DecimalFormat v_FormatoDecimalCentroid = new DecimalFormat("0.0");
                String v_StringCentroid = v_FormatoDecimalCentroid.format(v_AreaElemento);
                p_Graficos.drawString(v_StringCentroid, Math.round(v_CoordenadasCentroid[0]), Math.round(v_CoordenadasCentroid[1]));

                p_Graficos.setPaint(p_CorCoordenadas);
                String v_ConsultaCalculoDistancia = "";

                double[] v_CoordenadasOriginal = v_GeometriaPrincipal.getOrdinatesArray();

                for (int c_Coordenada = 2; c_Coordenada < v_CoordenadasOriginal.length; c_Coordenada = c_Coordenada + 2) {
                    v_ConsultaCalculoDistancia = "select " + p_OwnerFuncaoGetDistancia + ".GetDistancia(" + v_CoordenadasOriginal[c_Coordenada - 2] + "," + v_CoordenadasOriginal[c_Coordenada - 1] + ",";
                    v_ConsultaCalculoDistancia += v_CoordenadasOriginal[c_Coordenada] + "," + v_CoordenadasOriginal[c_Coordenada + 1] + ")";
                    v_ConsultaCalculoDistancia += " from dual, user_sdo_geom_metadata u";
                    v_ConsultaCalculoDistancia += " where u.TABLE_NAME = '" + p_NomeTabelaParaMostrarAreaEComprimento + "' and u.COLUMN_NAME = 'GEOLOC'";

                    Statement v_StatementCalculoDistancia = m_Conexao.createStatement();
                    ResultSet v_CalculoDistancia = v_StatementCalculoDistancia.executeQuery(v_ConsultaCalculoDistancia);

                    v_CalculoDistancia.next();

                    int v_PosicaoX = (int) Math.round(((v_X[c_Coordenada / 2] + v_X[(c_Coordenada / 2) - 1]) / 2));
                    int v_PosicaoY = (int) Math.round(((v_Y[c_Coordenada / 2] + v_Y[(c_Coordenada / 2) - 1]) / 2));

                    DecimalFormat v_FormatoDecimal = new DecimalFormat("0.0");
                    String v_StringDistancia = v_FormatoDecimal.format(v_CalculoDistancia.getDouble(1));
                    p_Graficos.drawString(v_StringDistancia, v_PosicaoX, v_PosicaoY);

                    v_CalculoDistancia.close();
                    v_StatementCalculoDistancia.close();
                }

                p_Graficos.setPaint(p_CorDivisaoSegmentos);

                for (int c_Coordenada = 0; c_Coordenada < v_Coordenadas.length; c_Coordenada = c_Coordenada + 2) {
                    p_Graficos.drawString("*", Math.round(v_X[c_Coordenada / 2]), Math.round(v_Y[c_Coordenada / 2]) + CORRECAO_PARA_CARACTER_FICAR_EM_CIMA_DO_PONTO);
                }
            }

            if (p_CorValores != null) {
                STRUCT v_Centroid = (oracle.sql.STRUCT) v_ResultSet.getObject(CENTROID);
                String[] v_Valores = v_ResultSet.getString(VALOR).split(";");

                JGeometry v_GeometriaCentroid = JGeometry.load(v_Centroid);
                double[] v_CoordenadasCentroid = v_GeometriaCentroid.getPoint();
                v_CoordenadasCentroid[0] = getCoordenadaX(p_BufferedImagem, v_CoordenadasCentroid[0]);
                v_CoordenadasCentroid[1] = getCoordenadaY(p_BufferedImagem, v_CoordenadasCentroid[1], p_DesvioAlturaUtilizada);

                for (int c_Valor = 0; c_Valor < p_CorValores.length; c_Valor++) {
                    p_Graficos.setPaint(p_CorValores[c_Valor]);
                    p_Graficos.drawString(v_Valores[c_Valor], Math.round(v_CoordenadasCentroid[0]), Math.round(v_CoordenadasCentroid[1]));
                    v_CoordenadasCentroid[1] = v_CoordenadasCentroid[1] + PULO_DE_LINHA;
                }
            }
*/
        }

        v_ResultSet.close();
        v_Statement.close();
    }


	/**
	 * Procedimento que gera o mapeamento de cores fazendo com que cada logradouro tenha sua pr�pria cor
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Graficos Gr�fico que ser� gerada a imagem
	 * @param p_DesvioAlturaUtilizada Desvio da altura que se deve considerar para desenhar o croqui
	 * @param p_Cor Cor do ponto
	 * @param p_Consulta Consulta a ser feita para encontrar o ponto
	 * @exception SQLException
	 */
	private void preencherPonto(BufferedImage p_BufferedImagem, Graphics2D p_Graficos, int p_DesvioAlturaUtilizada,
			Color p_Cor, String p_Consulta) throws SQLException
			{
		Statement v_Statement = m_Conexao.createStatement();
		ResultSet v_ResultSet = v_Statement.executeQuery(p_Consulta);

		v_ResultSet.next();
		String v_Id = v_ResultSet.getString(OBJECTID);
		Double[] v_Ponto = getPonto(p_BufferedImagem, p_DesvioAlturaUtilizada, v_ResultSet, true);

		double[] v_PontoConvertido = new double[2];
		v_PontoConvertido[0] = v_Ponto[0];
		v_PontoConvertido[1] = v_Ponto[1];

		if (v_PontoConvertido[0] > 0 && v_PontoConvertido[1] > 0)
		{
			p_Graficos.setPaint(p_Cor);
// mario - aqui escreve o id da linha
			p_Graficos.drawString(v_Id, Math.round(v_PontoConvertido[0]), Math.round(v_PontoConvertido[1]));
		}

		v_ResultSet.close();
		v_Statement.close();
			}

	/**
	 * Função que retorna um ponto de um ResultSet
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_DesvioAlturaUtilizada Desvio da altura que se deve considerar para desenhar o croqui
	 * @param p_ResultSet ResultSet que possui o ponto
	 * @param p_ConverterPontos Indica se � para converter o ponto de UTM para o valor do croqui ou se n�o converte
	 * @exception SQLException
	 */
	private Double[] getPonto(BufferedImage p_BufferedImagem, int p_DesvioAlturaUtilizada, ResultSet p_ResultSet, boolean p_ConverterPontos) throws SQLException
	{
		STRUCT v_Ponto = (oracle.sql.STRUCT)p_ResultSet.getObject(PONTO);
		JGeometry v_GeometriaCentroid = JGeometry.load(v_Ponto);
		double[] v_CoordenadasPonto = v_GeometriaCentroid.getPoint();

		if (p_ConverterPontos)
		{
			v_CoordenadasPonto[0] = getCoordenadaX(p_BufferedImagem, v_CoordenadasPonto[0]);
			v_CoordenadasPonto[1] = getCoordenadaY(p_BufferedImagem, v_CoordenadasPonto[1], p_DesvioAlturaUtilizada);
		}

		Double[] v_Retorno = new Double[2];
		v_Retorno[0] = new Double(v_CoordenadasPonto[0]);
		v_Retorno[1] = new Double(v_CoordenadasPonto[1]);

		return v_Retorno;
	}

	/**
	 * Fun��o que retorna o tamanho(Altura e Largura) da �rea para desenhar o croqui
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_DesvioAltura Desvio da altura que se deve considerar para desenhar o croqui
	 */
	private void definirTamanho(BufferedImage p_BufferedImagem, int p_DesvioAltura)
	{
		if (m_Tamanho != 0) return;

		m_Tamanho = p_BufferedImagem.getHeight() - p_DesvioAltura;

		if (m_Tamanho > p_BufferedImagem.getWidth() )
		{
                    m_Tamanho = p_BufferedImagem.getWidth() ;
		}
        m_Tamanho -= m_Margem; // cria uma area extra para desenho de 10 pixels
	}

	/**
	 * Fun��o que retorna a diferen�a maior entre o X e entre o Y
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 */
	private double getDiferencaMaior(BufferedImage p_BufferedImagem)
	{
		double v_DiferencaEntreMaiorEMenorY = m_MaiorY - m_MenorY;
		double v_DiferencaEntreMaiorEMenorX = m_MaiorX - m_MenorX;

		if (v_DiferencaEntreMaiorEMenorX > v_DiferencaEntreMaiorEMenorY)
		{
			return v_DiferencaEntreMaiorEMenorX;
		}

		return v_DiferencaEntreMaiorEMenorY;
	}

	/**
	 * Fun��o que converte a coordenada x
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_CoordenadaOriginal Coordenada original a ser convertida
	 */
	private double getCoordenadaX(BufferedImage p_BufferedImagem, double p_CoordenadaOriginal)
	{
		double v_Diferenca = getDiferencaMaior(p_BufferedImagem);

		double v_DiferencaEntreCoordenadaMenor = p_CoordenadaOriginal - m_MenorX;

		double v_Retorno =  ((m_Tamanho) * v_DiferencaEntreCoordenadaMenor) / v_Diferenca;

		return v_Retorno;
	}

	/**
	 * Fun��o que converte a coordenada y
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_CoordenadaOriginal Coordenada original a ser convertida
	 * @param p_DesvioAlturaUtilizada Desvio da altura utilizada
	 */
	private double getCoordenadaY(BufferedImage p_BufferedImagem, double p_CoordenadaOriginal, int p_DesvioAlturaUtilizada)
	{
		double v_Diferenca = getDiferencaMaior(p_BufferedImagem);

		double v_DiferencaEntreCoordenadaMaior = m_MaiorY  - p_CoordenadaOriginal;

		double v_Retorno = (((m_Tamanho)* v_DiferencaEntreCoordenadaMaior) / v_Diferenca) + p_DesvioAlturaUtilizada;

		return v_Retorno;
	}

	/**
	 * Procedimento que defini as coordenadas maiores e menores e o tamanho da imagem
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Coordenadas Todas as coordenadas da geometria a avaliar
	 * @param p_DesvioAltura Desvio da altura utilizada
	 * @param p_DesvioParaCaberElementosAoRedor Desvio utilizado para o bound da geometria ser aumentado, para caber outros elementos no entorno
	 */
	private void definirValores(BufferedImage p_BufferedImagem, double[] p_Coordenadas, int p_DesvioAltura, double p_DesvioParaCaberElementosAoRedor)
	{
		definirMaiorEMenoresCoordenadas(p_Coordenadas, p_DesvioParaCaberElementosAoRedor);
		definirTamanho(p_BufferedImagem, p_DesvioAltura);
	}

	/**
	 * Procedimento que defini as menores e maiores coordenadas X e Y
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_Coordenadas Todas as coordenadas da geometria a avaliar
	 * @param p_DesvioParaCaberElementosAoRedor Desvio utilizado para o bound da geometria ser aumentado, para caber outros elementos no entorno
	 */
	private void definirMaiorEMenoresCoordenadas(double[] p_Coordenadas, double p_DesvioParaCaberElementosAoRedor)
	{
		if (!m_MaiorX.isNaN()) return;

		m_MaiorX = p_Coordenadas[0];
		m_MaiorY = p_Coordenadas[1];
		m_MenorX = p_Coordenadas[0];
		m_MenorY = p_Coordenadas[1];

		for(int c_Coordenada = 2; c_Coordenada < p_Coordenadas.length; c_Coordenada = c_Coordenada + 2)
		{
			if (p_Coordenadas[c_Coordenada] > m_MaiorX)
			{
				m_MaiorX = p_Coordenadas[c_Coordenada];
			}

			if (p_Coordenadas[c_Coordenada] < m_MenorX)
			{
				m_MenorX =  p_Coordenadas[c_Coordenada];
			}
		}

		for(int c_Coordenada = 3; c_Coordenada < p_Coordenadas.length; c_Coordenada = c_Coordenada + 2)
		{
			if (p_Coordenadas[c_Coordenada] > m_MaiorY)
			{
				m_MaiorY =  p_Coordenadas[c_Coordenada];
			}

			if (p_Coordenadas[c_Coordenada] < m_MenorY)
			{
				m_MenorY =  p_Coordenadas[c_Coordenada];
			}
		}

		double v_DiferencaEntreX = m_MaiorX - m_MenorX;
		double v_DiferencaEntreY = m_MaiorY - m_MenorY;

		double v_DesvioParaX = v_DiferencaEntreX * (1 - p_DesvioParaCaberElementosAoRedor);
		double v_DesvioParaY = v_DiferencaEntreY * (1 - p_DesvioParaCaberElementosAoRedor);

		m_MaiorX = m_MaiorX + (v_DesvioParaX / 2);
		m_MenorX = m_MenorX - (v_DesvioParaX / 2);
		m_MaiorY = m_MaiorY + (v_DesvioParaY / 2);
		m_MenorY = m_MenorY - (v_DesvioParaY / 2);
	}

	/**
	 * Procedimento que defini as menores e maiores coordenadas X e Y
	 * @return Retorna a consulta padr�o de busca do logradouro
	 * @param p_BufferedImage Encapsula a imagem a ser gerada
	 * @param p_NomeTabelaTrecho Nome da tabela de endere�o
	 */
	private String getConsultaLogradouro(String p_NomeTabelaTrecho,String geometria_trecho,String trecho_id_logradouro,String trecho_mi_prinx,String trecho_tipo_logradouro,String trecho_numero_do_logradouro)
	{
		String v_ConsultaLogradouro = "select t."+trecho_mi_prinx+" " + OBJECTID + ", concat(t."+trecho_id_logradouro+", concat(' ', concat(t."+trecho_tipo_logradouro+", concat(' ', t."+trecho_numero_do_logradouro+")))) " + NULOG + ", t."+geometria_trecho+" " + GEOLOC + " from " + p_NomeTabelaTrecho + " t";
		v_ConsultaLogradouro += " WHERE SDO_FILTER(t."+geometria_trecho+", mdsys.sdo_geometry(2003,82301,NULL,";
		v_ConsultaLogradouro += "     mdsys.sdo_elem_info_array(1,1003,3),";
		v_ConsultaLogradouro += "     mdsys.sdo_ordinate_array(" + m_MenorX + "," + m_MenorY + "," + m_MaiorX + "," + m_MaiorY + "))) = 'TRUE'";

		return v_ConsultaLogradouro;
	}

	/**
	 * Procedimento que defini as menores e maiores coordenadas X e Y
	 * @return Retorna a consulta padrao de busca de lote
	 * @param p_NomeTabelaLoteCtm Nome da tabela de lote
	 * @param p_PKLote N�mero de lote ctm
	 */
	private String getConsultaLote(String p_NomeTabelaLoteCtm, String p_PKLote,
                String geometria_lote,String lote_ctm_numero_do_lote)
	{
		/*String v_ConsultaLote = "select l.num_lote_ctm " + OBJECTID + ",  l.geoloc " + GEOLOC + ",  sdo_geom.sdo_centroid(l.geoloc, u.DIMINFO) " + CENTROID + ", sdo_geom.sdo_area(l.geoloc,'5e-8','unit=sq_m') " + AREA;
		v_ConsultaLote += " from " + p_NomeTabelaLoteCtm + " l, user_sdo_geom_metadata u ";
		v_ConsultaLote += " where u.TABLE_NAME = '" +  (String) p_NomeTabelaLoteCtm.subSequence(p_NomeTabelaLoteCtm.indexOf(".") + 1, p_NomeTabelaLoteCtm.length()) + "' AND u.COLUMN_NAME = 'GEOLOC'";
		v_ConsultaLote += " AND l.num_lote_ctm = " + p_PKLote;*/

		String v_ConsultaLote = "select l."+lote_ctm_numero_do_lote+" " + OBJECTID + ",  l."+geometria_lote+" " + GEOLOC + ",  sdo_geom.sdo_centroid(l."+geometria_lote+", .05) " + CENTROID + ", sdo_geom.sdo_area(l."+geometria_lote+",'5e-8','unit=sq_m') " + AREA;
		v_ConsultaLote += " from " + p_NomeTabelaLoteCtm + " l ";
		v_ConsultaLote += " where l."+lote_ctm_numero_do_lote+" = " + p_PKLote;

		return v_ConsultaLote;
	}
}