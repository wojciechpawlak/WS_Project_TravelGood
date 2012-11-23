/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.activation.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author mkucharek
 */
public class MarshallerUtility<T extends Object> {

    public StringDataSource marshal(T o) throws JAXBException {
        Marshaller m = JAXBContext.newInstance(o.getClass()).createMarshaller();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        m.marshal(o, os);
        StringDataSource dataSource = new StringDataSource(os.toByteArray());
        return dataSource;
    }

    public T unmarshal(DataSource resultGet) throws IOException,
            JAXBException {

        // type discovery
        // Since we KNOW this must be a ParameterizedType, we can cast
        ParameterizedType pType = (ParameterizedType) this.getClass().
                getGenericSuperclass();
        Type firstType = pType.getActualTypeArguments()[0];
        Class parameterClass = (Class) firstType;

        Unmarshaller um = JAXBContext.newInstance(parameterClass).
                createUnmarshaller();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                resultGet.getInputStream()));
        T or = (T) um.unmarshal(reader);
        return or;
    }

    public String readString(DataSource resultGet) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                resultGet.getInputStream()));
        return reader.readLine();
    }
}

class StringDataSource implements DataSource {

    byte[] input = {};

    StringDataSource(byte[] os) {
        this.input = os;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    public String getContentType() {
        return "text/plain";
    }

    public String getName() {
        return "input";
    }
}
