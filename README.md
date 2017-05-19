# expkg-zone58:xml.doctype
Extract DocType information from XML files or XML source text. Returns a Map

```
import module namespace dt="java:org.expkgzone58.xml.DocType";
dt:fromString(
'<?xml version="1.0"?>
<!DOCTYPE book PUBLIC "-//NLM//DTD Book DTD v2.1 20050630//EN" "book.dtd">
<book/>'
)
(: result :)

map {
  "publicId": "-//NLM//DTD Book DTD v2.1 20050630//EN",
  "systemId": "book.dtd",
  "name": "book"
}
```
