package com.solvd.airline.xml.jaxb;

import com.solvd.airline.entity.jaxb.Fleet;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

/**
 * JAXB marshaller / unmarshaller for the {@link Fleet} root.
 *
 * <p>The {@link JAXBContext} is expensive to build and thread-safe after
 * construction — it is cached here as a static field. Marshallers and
 * Unmarshallers are cheap and single-threaded; create a fresh one per call.
 */
public final class FleetMarshaller {

    private static final JAXBContext CONTEXT;
    static {
        try {
            CONTEXT = JAXBContext.newInstance(Fleet.class);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private FleetMarshaller() { }

    /** Read a {@code Fleet} from an XML file on disk. */
    public static Fleet unmarshal(Path xmlPath) throws JAXBException {
        Unmarshaller u = CONTEXT.createUnmarshaller();
        return (Fleet) u.unmarshal(xmlPath.toFile());
    }

    /** Read a {@code Fleet} from an XML string. */
    public static Fleet unmarshal(String xml) throws JAXBException {
        Unmarshaller u = CONTEXT.createUnmarshaller();
        return (Fleet) u.unmarshal(new StringReader(xml));
    }

    /** Write a {@code Fleet} to an XML file. */
    public static void marshal(Fleet fleet, Path outputPath) throws JAXBException {
        Marshaller m = newFormattingMarshaller();
        m.marshal(fleet, outputPath.toFile());
    }

    /** Write a {@code Fleet} to an XML string (used by round-trip tests). */
    public static String marshalToString(Fleet fleet) throws JAXBException {
        Marshaller m = newFormattingMarshaller();
        StringWriter buf = new StringWriter();
        m.marshal(fleet, buf);
        return buf.toString();
    }

    private static Marshaller newFormattingMarshaller() throws JAXBException {
        Marshaller m = CONTEXT.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        return m;
    }
}
