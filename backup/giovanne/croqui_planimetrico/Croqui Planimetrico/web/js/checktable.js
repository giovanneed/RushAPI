var oldInp=1;

function clickage(evt, elem)
{
    evt=(evt)?evt:event;

    var newInp = elem.rowIndex;
    
    var checkbox = document.getElementById("lotes").rows[newInp].cells[0].childNodes[0];

    if(!evt.shiftKey)
	{
        oldInp=newInp;
		return false;
	}

    checkbox.checked = true;

	var low=Math.min(newInp,oldInp);
	var high=Math.max(newInp,oldInp)
	var uncheck=1;

	for(i=low;i<=high;i++)
	{
		uncheck &= document.getElementById("lotes").rows[i].cells[0].childNodes[0].checked;
		document.getElementById("lotes").rows[i].cells[0].childNodes[0].checked=1;
	}

	if(uncheck)
	{
		for(i=low;i<=high;i++)
		{
			document.getElementById("lotes").rows[i].cells[0].childNodes[0].checked=0;
		}
	}
	return true;
}