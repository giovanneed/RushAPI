package chatjava;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ControlMQ {
    private TopicConnection connection = null;
    private TopicSession session = null;
    private TopicConnectionFactory connectionFactory = null;
    private Topic topic = null;
    private Context jndiContext = null;

    public void enviaMSG(String usuario, String msg,String chave){
        try{
        TopicPublisher publisher = getSession().createPublisher(getTopic());

        // Passo 5: criação e envio de uma mensagem de texto
        TextMessage message = getSession().createTextMessage();
        message.setText(msg);
        message.setStringProperty("user", usuario);
        if(chave!=null){
            message.setStringProperty("chave", chave);
        }

        publisher.send(message);
        System.out.println("Mensagem enviada");
        //getConnection().close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public ControlMQ(String topicName){
        
        try {
                jndiContext = new InitialContext();
        } catch (NamingException e) {
                e.printStackTrace();
        }
        try {
            connectionFactory = (TopicConnectionFactory)jndiContext.lookup("ConnectionFactory");
            topic = (Topic)jndiContext.lookup(topicName);
        } catch (NamingException e) {
                e.printStackTrace();
                System.exit(-1);
        }
        try {
            connection = connectionFactory.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
                e.printStackTrace();
                System.exit(-1);
        }
    }

    /**
     * @return the connection
     */
    public TopicConnection getConnection() {
        return connection;
    }

    /**
     * @return the session
     */
    public TopicSession getSession() {
        return session;
    }

    /**
     * @return the connectionFactory
     */
    public TopicConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * @return the topic
     */
    public Topic getTopic() {
        return topic;
    }
    public Context getContext(){
        return jndiContext;
    }
}
