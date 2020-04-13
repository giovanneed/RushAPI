package controle;

/**
 *
 * @author bruno.finelli
 */

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.File;
import javax.servlet.http.HttpSession;
import javax.sql.PooledConnection;

public class SessionListener implements HttpSessionListener
{
    public void sessionCreated(HttpSessionEvent se)
    {
        HttpSession session = se.getSession();

        String output = null;
        if (System.getProperty("os.name").indexOf("Windows")!=-1){
           output  =  session.getServletContext().getRealPath("") + "\\" + session.getServletContext().getInitParameter("output_path") + "\\";
        }else{
            output =  session.getServletContext().getRealPath("") + "/" + session.getServletContext().getInitParameter("output_path") + "/";
        }

        PooledConnection pc = dao.ServicoDao.criarPool(session.getServletContext());
        session.setAttribute("pooledConnection", pc);

        // Define os parametros padroes
        session.setAttribute("codigo", "");
        session.setAttribute("radio", "iptu");
        session.setAttribute("folhaQuadra", "1");
        session.setAttribute("folhaLote", "1");

        String dir = output + session.getId();
        try
        {
            (new File(dir)).mkdir();
        }catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void sessionDestroyed(HttpSessionEvent se)
    {
        HttpSession session = se.getSession();

        String output = null;
        if (System.getProperty("os.name").indexOf("Windows")!=-1){
           output  =  session.getServletContext().getRealPath("") + "\\" + session.getServletContext().getInitParameter("output_path") + "\\";
        }else{
            output =  session.getServletContext().getRealPath("") + "/" + session.getServletContext().getInitParameter("output_path") + "/";
        }

        dao.ServicoDao.fecharPool((PooledConnection) session.getAttribute("pooledConnection"));
        session.removeAttribute("pooledConnection");

        session.removeAttribute("autorizado");

        File dir = new File(output + session.getId());

        if( dir.exists() )
        {
            File[] files = dir.listFiles();
            for(int i=0; i<files.length; i++)
            {
                files[i].delete();
            }
        }
        dir.delete();
    }
}