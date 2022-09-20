## Spring Boot SOAP + MongoDB
Example of some CRUD operations and exception handling using SOAP and MongoDB 

- XSD file in resources contains the description of the models and operations.  
- WebServiceConfig class is used to configure: the servlet for SOAP messages, the wsdl to expose the xsd 
file and the exception resolver for custom exceptions.
- StudentEndpoint class is used as the service endpoint to handle SOAP requests, request and
response objects are the ones auto generated and described in the xsd.