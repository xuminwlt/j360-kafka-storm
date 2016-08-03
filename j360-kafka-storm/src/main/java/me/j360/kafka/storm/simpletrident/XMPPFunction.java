package me.j360.kafka.storm.simpletrident;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

import javax.security.sasl.SaslException;
import java.io.IOException;
import java.util.Map;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:40
 * 说明：
 */
public class XMPPFunction extends BaseFunction {
    private static final Logger LOG =
            LoggerFactory.getLogger(XMPPFunction.class);
    public static final String XMPP_TO = "storm.xmpp.to";
    public static final String XMPP_USER = "storm.xmpp.user";
    public static final String XMPP_PASSWORD = "storm.xmpp.password";
    public static final String XMPP_SERVER = "storm.xmpp.server";
    private XMPPConnection xmppConnection;
    private String to;
    private MessageMapper mapper;

    public XMPPFunction(MessageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void prepare(Map conf,
                        TridentOperationContext context) {
        LOG.debug("Prepare: {}", conf);
        super.prepare(conf, context);
        this.to = (String) conf.get(XMPP_TO);

        ConnectionConfiguration config = new
                ConnectionConfiguration((String) conf.get(XMPP_SERVER));
        this.xmppConnection = new SimpleXMPPConnection(config);
        try {
            this.xmppConnection.connect();
            this.xmppConnection.login((String) conf.get(XMPP_USER),
                    (String) conf.get(XMPP_PASSWORD));
        } catch (XMPPException e) {
            LOG.warn("Error initializingXMPP Channel", e);
        } catch (SaslException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(TridentTuple tuple,
                        TridentCollector collector) {
        Message msg = new Message(this.to, Message.Type.normal);
        msg.setBody(this.mapper.toMessageBody(tuple));
        try {
            this.xmppConnection.sendPacket(msg);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
