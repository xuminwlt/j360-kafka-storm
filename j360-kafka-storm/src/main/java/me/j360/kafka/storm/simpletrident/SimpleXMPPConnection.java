package me.j360.kafka.storm.simpletrident;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.iqrequest.IQRequestHandler;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.PlainStreamElement;
import org.jivesoftware.smack.packet.Stanza;

/**
 * Package: me.j360.kafka.storm.simpletrident
 * User: min_xu
 * Date: 16/8/4 上午11:13
 * 说明：
 */
public class SimpleXMPPConnection implements XMPPConnection {
    @Override
    public String getServiceName() {
        return null;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getStreamId() {
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
    public boolean isUsingCompression() {
        return false;
    }

    @Override
    public void sendPacket(Stanza stanza) throws SmackException.NotConnectedException {

    }

    @Override
    public void sendStanza(Stanza stanza) throws SmackException.NotConnectedException {

    }

    @Override
    public void send(PlainStreamElement plainStreamElement) throws SmackException.NotConnectedException {

    }

    @Override
    public void addConnectionListener(ConnectionListener connectionListener) {

    }

    @Override
    public void removeConnectionListener(ConnectionListener connectionListener) {

    }

    @Override
    public PacketCollector createPacketCollectorAndSend(IQ iq) throws SmackException.NotConnectedException {
        return null;
    }

    @Override
    public PacketCollector createPacketCollectorAndSend(StanzaFilter stanzaFilter, Stanza stanza) throws SmackException.NotConnectedException {
        return null;
    }

    @Override
    public PacketCollector createPacketCollector(StanzaFilter stanzaFilter) {
        return null;
    }

    @Override
    public PacketCollector createPacketCollector(PacketCollector.Configuration configuration) {
        return null;
    }

    @Override
    public void removePacketCollector(PacketCollector packetCollector) {

    }

    @Override
    public void addPacketListener(StanzaListener stanzaListener, StanzaFilter stanzaFilter) {

    }

    @Override
    public boolean removePacketListener(StanzaListener stanzaListener) {
        return false;
    }

    @Override
    public void addSyncStanzaListener(StanzaListener stanzaListener, StanzaFilter stanzaFilter) {

    }

    @Override
    public boolean removeSyncStanzaListener(StanzaListener stanzaListener) {
        return false;
    }

    @Override
    public void addAsyncStanzaListener(StanzaListener stanzaListener, StanzaFilter stanzaFilter) {

    }

    @Override
    public boolean removeAsyncStanzaListener(StanzaListener stanzaListener) {
        return false;
    }

    @Override
    public void addPacketSendingListener(StanzaListener stanzaListener, StanzaFilter stanzaFilter) {

    }

    @Override
    public void removePacketSendingListener(StanzaListener stanzaListener) {

    }

    @Override
    public void addPacketInterceptor(StanzaListener stanzaListener, StanzaFilter stanzaFilter) {

    }

    @Override
    public void removePacketInterceptor(StanzaListener stanzaListener) {

    }

    @Override
    public long getPacketReplyTimeout() {
        return 0;
    }

    @Override
    public void setPacketReplyTimeout(long l) {

    }

    @Override
    public int getConnectionCounter() {
        return 0;
    }

    @Override
    public void setFromMode(FromMode fromMode) {

    }

    @Override
    public FromMode getFromMode() {
        return null;
    }

    @Override
    public <F extends ExtensionElement> F getFeature(String s, String s1) {
        return null;
    }

    @Override
    public boolean hasFeature(String s, String s1) {
        return false;
    }

    @Override
    public void sendStanzaWithResponseCallback(Stanza stanza, StanzaFilter stanzaFilter, StanzaListener stanzaListener) throws SmackException.NotConnectedException {

    }

    @Override
    public void sendStanzaWithResponseCallback(Stanza stanza, StanzaFilter stanzaFilter, StanzaListener stanzaListener, ExceptionCallback exceptionCallback) throws SmackException.NotConnectedException {

    }

    @Override
    public void sendStanzaWithResponseCallback(Stanza stanza, StanzaFilter stanzaFilter, StanzaListener stanzaListener, ExceptionCallback exceptionCallback, long l) throws SmackException.NotConnectedException {

    }

    @Override
    public void sendIqWithResponseCallback(IQ iq, StanzaListener stanzaListener) throws SmackException.NotConnectedException {

    }

    @Override
    public void sendIqWithResponseCallback(IQ iq, StanzaListener stanzaListener, ExceptionCallback exceptionCallback) throws SmackException.NotConnectedException {

    }

    @Override
    public void sendIqWithResponseCallback(IQ iq, StanzaListener stanzaListener, ExceptionCallback exceptionCallback, long l) throws SmackException.NotConnectedException {

    }

    @Override
    public void addOneTimeSyncCallback(StanzaListener stanzaListener, StanzaFilter stanzaFilter) {

    }

    @Override
    public IQRequestHandler registerIQRequestHandler(IQRequestHandler iqRequestHandler) {
        return null;
    }

    @Override
    public IQRequestHandler unregisterIQRequestHandler(IQRequestHandler iqRequestHandler) {
        return null;
    }

    @Override
    public IQRequestHandler unregisterIQRequestHandler(String s, String s1, IQ.Type type) {
        return null;
    }

    @Override
    public long getLastStanzaReceived() {
        return 0;
    }
}
