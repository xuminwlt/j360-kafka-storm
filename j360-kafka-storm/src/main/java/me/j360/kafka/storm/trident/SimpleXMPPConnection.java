package me.j360.kafka.storm.trident;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;

import javax.security.sasl.SaslException;
import java.io.IOException;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/8/2 下午4:04
 * 说明：
 */
public class SimpleXMPPConnection extends XMPPConnection {
    protected SimpleXMPPConnection(ConnectionConfiguration configuration) {
        super(configuration);
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getConnectionID() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public boolean isSecureConnection() {
        return false;
    }

    @Override
    protected void sendPacketInternal(Packet packet) throws SmackException.NotConnectedException {

    }

    @Override
    public boolean isUsingCompression() {
        return false;
    }

    @Override
    protected void connectInternal() throws SmackException, IOException, XMPPException {

    }

    @Override
    public void login(String s, String s1, String s2) throws XMPPException, SmackException, SaslException, IOException {

    }

    @Override
    public void loginAnonymously() throws XMPPException, SmackException, SaslException, IOException {

    }

    @Override
    protected void shutdown() {

    }
}
