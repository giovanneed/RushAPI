<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://maps.google.com/maps?file=api&v=2&key=ABQIAAAA_ATXoZkh-wHjOkEgsaCqlhQeDqxLG4IyDX1Tz0AFNOKheMcL-xQRI6T-rElHUmpSyfnbtABF9rnWWw" type="text/javascript"></script>

    <link rel="stylesheet"  href="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.css" />
    <link rel="stylesheet" href="docs/_assets/css/jqm-docs.css" />

    <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a4/jquery.mobile-1.0a4.min.js"></script>
    
    <script type="text/javascript" src="docs/_assets/js/jqm-docs.js"></script>





</script>

  </head>

  <body onLoad="criarMapa()">
      <div data-role="page" id="principal01">

         <div data-role="header">
    	       <a href="index01.html"  data-icon="back"  data-transition="slide">Back</a>
               <h1>index01</h1>
         </div>

          <div data-role="content">

              <div id="meuMapa" ></div>
              <input type="button" onClick="localizar()" value="Localizar">

          </div>

          <div data-role="footer">

              <div data-role="navbar">
                   <ul>

			<li>
                            <a href="listar01.html" id="chat" data-icon="grid" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a" data-transition="pop">
                             <span class="ui-btn-text">LISTAR</span>
                            </a>
                        </li>
                        <li>
                            <a href="criar.html" id="chat" data-icon="plus" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a"  data-transition="pop">
                             <span class="ui-btn-text">CRIAR</span>

                            </a>
                        </li>
                        <li>
                            <a href="#" id="chat" data-icon="home" data-theme="a" class="ui-btn ui-btn-icon-top ui-btn-up-a">
                             <span class="ui-btn-text">MEUS EVE</span>
                            </a>
                        </li>
		   </ul>

              </div>

          </div>

      </div>




      
    TODO write content
  </body>
</html>
