//package io.github.cargoshipping.infrastructure.messaging.jms;
//
//import antlr.debug.MessageListener;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import sun.plugin2.message.Message;
//
//import javax.jms.Message;
//import javax.jms.MessageListener;
//
//public class SimpleLoggingConsumer implements MessageListener {
//
//  private final Log logger = LogFactory.getLog(SimpleLoggingConsumer.class);
//
//  @Override
//  public void onMessage(Message message) {
//    logger.debug("Received JMS message: " + message);
//  }
//
//}
