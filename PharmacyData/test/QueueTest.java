
import com.shl.poc.strom.commons.event.command.CommandJson;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * User: yshuliga
 * Date: 06.01.14 17:16
 */
public class QueueTest {

	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;
		InitialContext context = null;
		try {
			Properties env = new Properties();
			env.put(Context.PROVIDER_URL, "mq://54.83.31.41:7676");
			context = new InitialContext(env);
			System.out.println("Context Created");
			QueueConnectionFactory connectionFactory = (QueueConnectionFactory)context.lookup("BSCA_POC");
			Queue queue = (Queue) context.lookup("jms/bsca_poc/DataEventQueue");
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(queue);

			ObjectMessage message = (ObjectMessage) consumer.receive();
			CommandJson command = (CommandJson) message.getObject();
			session.close();
			System.out.println("Message received: " + command);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null)try{connection.close();}
			catch (JMSException e){}
			if (context != null)try{context.close();}
			catch (NamingException e) {}
		}
	}

}
