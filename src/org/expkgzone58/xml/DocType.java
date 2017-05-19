package org.expkgzone58.xml;
/* 
 * return a Xquery Map with the following keys if found
* name: 			The name of DTD; i.e., the name immediately following the DOCTYPE keyword.
* publicId 			The public identifier of the external subset.
* systemId   		The system identifier of the external subset.
* ---------currently ignored----- 
* Entities         A NamedNodeMap containing the general entities, both external and internal, declared in the DTD.
* NamedNodeMap 	getNotations() A NamedNodeMap containing the notations declared in the DTD.
 */

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.basex.query.QueryException;
import org.basex.query.value.item.Item;
import org.basex.query.value.item.Str;
import org.basex.query.value.map.Map;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocType {
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public DocType() {
        factory.setNamespaceAware(true);
        factory.setValidating(false);
    }

    public Map fromString(String xml) throws Exception {
        // http://stackoverflow.com/questions/33262/how-do-i-load-an-org-w3c-dom-document-from-xml-in-a-string/33283#33283
        return getDocType(new StringReader(xml));
    }

    public Map fromFile(String file) throws Exception {
        return getDocType(new FileReader(file));
    }

    private Map getDocType(Reader reader) throws ParserConfigurationException, SAXException, IOException, QueryException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        // http://stackoverflow.com/questions/155101/make-documentbuilder-parse-ignore-dtd-references/155353#155353
        builder.setEntityResolver(new EntityResolver() {
            public InputSource resolveEntity(java.lang.String publicId, java.lang.String systemId)
                    throws SAXException, java.io.IOException {
                return new InputSource(new StringReader(""));
            }
        });
        Document doc = builder.parse(new InputSource(reader));
        return result(doc);
    }

    private Map result(Document doc) throws QueryException {
        // @see
        // https://www.w3.org/2003/01/dom2-javadoc/org/w3c/dom/DocumentType.html
        DocumentType dt = doc.getDoctype();
        Map map = Map.EMPTY;
        
        if (dt != null) {
            Item name = Str.get("name");
            Item publicId = Str.get("publicId");
            Item systemId = Str.get("systemId");
            map=map.put(name,Str.get(dt.getName()),null)
                   .put(publicId,Str.get(dt.getPublicId()),null)
                   .put(systemId,Str.get(dt.getSystemId()),null);
          }
        return map;
    }

}
