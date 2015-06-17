package org.opendaylight.protocol.pcep.pcc.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.opendaylight.protocol.pcep.spi.ObjectHeaderImpl;
import org.opendaylight.protocol.pcep.spi.PCEPDeserializerException;
import org.opendaylight.protocol.util.Ipv4Util;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.pcep.types.rev131005.ObjectHeader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.pcep.types.rev131005.endpoints.address.family.Ipv4Case;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.pcep.types.rev131005.endpoints.object.EndpointsObj;

public class PCCEndPointIpv4ObjectParserTest {

    private final static String ip1 = "1.2.3.4";
    private final static String ip2 = "1.2.3.5";

    @Test(expected=PCEPDeserializerException.class)
    public void testParseEmptyObject() throws PCEPDeserializerException {
        final ObjectHeader header = new ObjectHeaderImpl(false, false);
        final ByteBuf bytes = Unpooled.buffer();
        bytes.writeByte(4);
        new PCCEndPointIpv4ObjectParser().parseObject(header, bytes);
    }

    @Test
    public void testParseObject() throws PCEPDeserializerException {
        final ObjectHeader header = new ObjectHeaderImpl(false, false);
        final ByteBuf bytes = Unpooled.buffer();
        bytes.writeBytes(Ipv4Util.bytesForAddress(new Ipv4Address(this.ip1)));
        bytes.writeBytes(Ipv4Util.bytesForAddress(new Ipv4Address(this.ip2)));
        final EndpointsObj output = (EndpointsObj) new PCCEndPointIpv4ObjectParser().parseObject(header, bytes);

        assertEquals(this.ip1, ((Ipv4Case) output.getAddressFamily()).getIpv4().getSourceIpv4Address().getValue());
        assertEquals(this.ip2, ((Ipv4Case) output.getAddressFamily()).getIpv4().getDestinationIpv4Address().getValue());
        assertFalse(output.isIgnore());
        assertFalse(output.isProcessingRule());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullBytes() throws PCEPDeserializerException {
        final ObjectHeader header = new ObjectHeaderImpl(false, false);
        final ByteBuf bytes = null;
        new PCCEndPointIpv4ObjectParser().parseObject(header, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testEmptyBytes() throws PCEPDeserializerException {
        final ObjectHeader header = new ObjectHeaderImpl(false, false);
        final ByteBuf bytes = Unpooled.buffer();
        new PCCEndPointIpv4ObjectParser().parseObject(header, bytes);
    }
}