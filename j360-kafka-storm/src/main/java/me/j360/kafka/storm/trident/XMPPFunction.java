package me.j360.kafka.storm.trident;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:40
 * 说明：
 */
public class XMPPFunction extends BaseFunction {
    private static final Logger LOG =
            LoggerFactory.getLogger(XMPPFunction.class);
    public static final String XMPP_TO ="storm.xmpp.to";
    public static final String XMPP_USER ="storm.xmpp.user";
    public static final String XMPP_PASSWORD ="storm.xmpp.password";
    public static final String XMPP_SERVER=  "storm.xmpp.server";
    private XMPPConnection xmppConnection;
    private String to;
    private MessageMapper mapper;
    public XMPPFunction(MessageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void prepare(Map conf,
                        TridentOperationContextcontext) {
        LOG.debug("Prepare: {}",conf);
        super.prepare(conf, context);
        this.to = (String) conf.get(XMPP_TO);

        ConnectionConfiguration config = new
                ConnectionConfiguration((String)  conf.get(XMPP_SERVER));
        this.xmppConnection = newXMPPConnection(config);
        try {
            this.xmppConnection.connect();
            this.xmppConnection.login((String)conf.get(XMPP_USER),
                    (String)conf.get(XMPP_PASSWORD));
        } catch (XMPPException e) {
            LOG.warn("Error initializingXMPP Channel", e);
        }
    }
    public void execute(TridentTuple tuple,
                        TridentCollectorcollector) {
        Message msg = new Message(this.to,Message.Type.normal);
        msg.setBody(this.mapper.toMessageBody(tuple));
        this.xmppConnection.sendPacket(msg);
    }
}
