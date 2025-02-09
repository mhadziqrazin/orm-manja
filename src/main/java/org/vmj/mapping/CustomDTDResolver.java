package org.vmj.mapping;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import java.io.InputStream;

public class CustomDTDResolver implements EntityResolver {
    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        if (systemId.contains("custom-hibernate-mapping-3.0.dtd")) {
            InputStream dtdStream = getClass().getResourceAsStream("/custom-hibernate-mapping-3.0.dtd");
            return new InputSource(dtdStream);
        }
        return null; // Fall back to default resolver
    }
}
