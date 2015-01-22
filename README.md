# CXFMultipartExample

Three *maven* projects highlighting the issue of the Spring *restTemplate* not supporting multipart restful responses.  The only method of interest is **/document/1/render**, the others are just there to make the examples a bit more useful.

* MultipartServer - A CXF server which hosts several endpoints to retrieve documents and simulate rendering them
* MultipartCXFClient - A CXF restful client which itself hosts endpoints and hands off work to the MultipartServer
* MultipartSpringClient - A spring boot restful client which itself hosts endpoints and hands off work to the MultipartServer

## MultipartServer
1. [http://localhost:9000/document](http://localhost:9000/document)
  * GET request which returns a list of all documents
2. [http://localhost:9000/document](http://localhost:9000/document)
  * POST request which takes a number in the body and returns that document
3. [http://localhost:9000/document/render](http://localhost:9000/document/render)
  * POST request which takes a number in the body and returns a simulated rendering of the document
  * If the document doesn't exist then an error message is returned
  * Uses multipart to respond with both binary and text attachments

## MultipartCXFClient
1. [http://localhost:8090/document](http://localhost:8090/document)
  * GET request which returns a list of all documents
2. [http://localhost:8090/document/1](http://localhost:8090/document/1)
  * GET request which returns an individual document
3. [http://localhost:8090/document/1/render](http://localhost:8090/document/1/render)
  * GET request which simulates the rendering of a document if the id is valid, or returns an error string.  The rendering is converted into a string just for rest purposes, but in a real system, the binary image would be returned.  The multipart attachments that are received from the server are correctly converted back into their underlying types for further processing

## MultipartSpringClient
1. [http://localhost:8080/document](http://localhost:8080/document)
  * GET request which returns a list of all documents
2. [http://localhost:8080/document/1](http://localhost:8080/document/1)
  * GET request which returns an individual document
3. [http://localhost:8080/document/1/render](http://localhost:8080/document/1/render)
  * GET request which simulates the rendering of a document if the id is valid, or returns an error string.  The issue with this implementation is that the spring *restTemplate* is not able to handle a multipart attachments so cannot elegantly extract the binary image or text depending on what the server sent.  As a test, the response from the server is just returned as a string so it can be analysed to see how multiparts work.  Some sample responses follow:

### Spring restTemplate Binary Multipart Attachement Response
*[http://localhost:8080/document/1/render](http://localhost:8080/document/1/render)*

The contents of the message is just a string representation of the binary image.
```
--uuid:f9d68791-ea4e-476f-901d-2f0677800b09
Content-Type: application/octet-stream
Content-Transfer-Encoding: binary
Content-ID: <root>

¶aG¾xÃªÁ} iüìlü¾X$abË§·ÄÑ¦Ó@qúÌ«[~Ýfçæôn¯±:¿Æªø#çbÌìÄn×5Õ)ã9¨øÒxé9s<îÕEÔ±Q	ç,³­Y
--uuid:f9d68791-ea4e-476f-901d-2f0677800b09--
```

### Spring restTemplate Text Multipart Attachement Response
*[http://localhost:8080/document/3/render](http://localhost:8080/document/1/render)*
```
--uuid:b6d486af-3d85-4177-9c55-6fc4b591c095
Content-Type: text/plain
Content-Transfer-Encoding: binary
Content-ID: <root>

Cannot find document 3
--uuid:b6d486af-3d85-4177-9c55-6fc4b591c095--
```

### Conclusion
Obviously it would be possible to write a parser which could extract the relevant parts of the message, but it wouldn't be robust and wouldn't be portable.
