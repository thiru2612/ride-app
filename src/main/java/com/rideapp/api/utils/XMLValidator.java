package com.rideapp.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XMLValidator {
    public static boolean validateXMLString(String xmlContent, String xsdFilePath) {
        try {
            File xsdFile;

            // Try loading from absolute path
            if (new File(xsdFilePath).exists()) {
                xsdFile = new File(xsdFilePath);
            } else {
                // Try loading from classpath dynamically
                URL xsdUrl = XMLValidator.class.getClassLoader().getResource(xsdFilePath);
                if (xsdUrl == null) {
                    System.out.println(""
                    		+ " XSD file not found: " + xsdFilePath);
                    return false;
                }
                xsdFile = new File(xsdUrl.toURI());
            }

            System.out.println(" Using XSD file: " + xsdFile.getAbsolutePath());

            // Create Schema & Validator
            FileReader fileReader = new FileReader(xsdFile);
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(fileReader));
            Validator validator = schema.newValidator();

            // Validate XML
            validator.validate(new StreamSource(new StringReader(xmlContent)));
            System.out.println(" XML validation passed!");
            return true;

        } catch (Exception e) {
            System.out.println(" XML Validation Failed: " + e.getMessage());
            return false;
        }
    }
}