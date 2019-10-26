package it.poste.postspy.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;

public class XMLUtility {
	
	public static File saveFileFromObject(Object object, String fileAbsolutePath) throws Exception {
		try {
			File savedFile = new File(fileAbsolutePath);
			savedFile.getParentFile().mkdirs();
			JAXBContext jc = JAXBContext.newInstance(object.getClass());
	        Marshaller m = jc.createMarshaller();
	        m.marshal(object, new FileOutputStream(savedFile));
			return savedFile;
		} catch(Exception e) {
			throw e;
		}
	}
	
	public static boolean validateXML(InputStream xmlInputStream, URL xsdUrl) throws Exception {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdUrl);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlInputStream));
            return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getObjFromXMLFile(File file, Class xmlClass) throws Exception {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document document = builder.parse(file);
			JAXBContext jc = JAXBContext.newInstance(xmlClass);
			Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
			Object object = jaxbUnmarshaller.unmarshal(document);
			return object;
		} catch(Exception e) {
			throw e;
		}
	}

}
