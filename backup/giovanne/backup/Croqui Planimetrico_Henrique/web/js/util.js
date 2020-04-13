var count_buscarLotes = 0;
var count_enviarLotes = 0;
var count_salvarLotes = 0;

// Remove os espaços em branco do inicio e final da string
function trim (str)
{
    return str.replace(/^\s+|\s+$/g, '');
}

function buscarLotes()
{
    if (count_buscarLotes == 0)
    {
        codigo = trim(document.buscaLotes.codigo.value);

        document.buscaLotes.codigo.value = codigo;

        if (document.buscaLotes.tipo[0].checked) // iptu
        {
            if (codigo.length < 6)
            {
                alert("Por favor entre com pelo menos 6 digitos!");
            }
            else
            {
                count_buscarLotes = 1;
                document.getElementById("td_tabela").innerHTML = '<img src="images/carregando.gif"/>';

                document.buscaLotes.submit();
            }
        } else
        if (document.buscaLotes.tipo[1].checked) // ctm
        {
            if (codigo.length < 7)
            {
                alert("Por favor entre com pelo menos 7 digitos!");
            }
            else
            {
                count_buscarLotes = 1;
                document.getElementById("td_tabela").innerHTML = '<img src="images/carregando.gif"/>';

                document.buscaLotes.submit();
            }
        }
    }
}

function enviarLotes()
{
    if (count_enviarLotes == 0)
    {
        rows = document.getElementById("lotes").rows;

        var lotes = "";
        var lotesIptu = "";
        var quadra = "";
        var quadraIptu = "";

        for (i = 1; i < rows.length; i++)
        {
            if (rows[i].cells[0].childNodes[0].checked == true)
            {
                lotes = lotes + rows[i].cells[2].innerHTML + ";";
                quadra = rows[i].cells[2].innerHTML;

                lotesIptu = lotesIptu + rows[i].cells[1].innerHTML + ";";
                quadraIptu = rows[i].cells[1].innerHTML;
            }
        }

        if (lotes != "")
        {
            count_enviarLotes = 1;
            document.getElementById("td_croqui").innerHTML = '<img src="images/carregando.gif"/>';

            document.enviaLotes.lotes.value = lotes;
            document.enviaLotes.quadra.value = quadra.substr(0, 7);
            document.enviaLotes.lotesIptu.value = lotesIptu;
            document.enviaLotes.quadraIptu.value = quadraIptu.substr(0, 7);

            document.enviaLotes.submit();
        }
        else
        {
            alert("Por favor selecionar ao menos um lote!");
        }
    }
}

function checkLote(lote)
{
    rows = document.getElementById("lotes").rows;

    for (i = 1; i < rows.length; i++)
        if (rows[i].cells[2].innerHTML == lote)
            rows[i].cells[0].childNodes[0].checked = true;
}

function autorizar()
{
    document.form_autorizar.submit();
}