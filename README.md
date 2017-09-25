# file-exchanger
RESTful web service for storing user's files
##
<h4>To start application:</h4>
1) run <code>sudo docker-compose run -d -p 5432:5432 db</code>
</br>
2) run <code>mvn clean install</code>
3) Start application with <code>java -jar fileexchanger-0.0.1.jar</code>
##
<h4>Requests and responses example:</h4>

# <h4>1. Sending a file:</h4>
    Example request:
    POST http://host:8080/file
    
    Content-Type: multipart/formdata
    --Asrf456BGe4h
    .........
    Content-Disposition: form-data; name="file"
    file data ...
    --Asrf456BGe4h
    Content-Disposition: form-data; name="description" // it's optional
    "ololo"
    .........
   
    Example response:
    HttpStatus: 201 Created
    Body: 
    {
        "token": "c1b81c25-24c5-494d-8270-813b11a9df28"
    }
# <h4>2. Getting a file:</h4>
     Example request:
     GET http://host:8080/file/ec8bb948-86e2-4244-84a7-9b296c689566
     
     Example response:
     'The file is being downloaded to your computer'
     
     In case of errors: (wrong token)
     HttpStatus: 404 NotFound
     Body: 
     {
         "message": "Failed to access resource with id = ec8bb948-86e2-4244-84a7-9b296c689566"
     }

# <h4>3. Removing a file:</h4> 
    DELETE http://host:8080/file/ec8bb948-86e2-4244-84a7-9b296c689566
    
    Example response:
    200 OK
     
    In case of errors: (wrong token)
    HttpStatus: 404 NotFound
    Body: 
    {
        "message": "Failed to access resource with id = ec8bb948-86e2-4244-84a7-9b296c689566"
    }
    
# <h4>4. Adding a description to the existing file:</h4> 
    PUT http://host:8080/file/ec8bb948-86e2-4244-84a7-9b296c689566
    
    {
    	"description": "lol kek"
    }
    
    Example response:
    {
        "token": "a8da2c64-c5d4-4f5c-a49c-07bd019bd2b5",
        "description": "lol kek"
    }
     
    In case of errors: (wrong token)
    HttpStatus: 404 NotFound
    Body: 
    {
        "message": "Failed to access resource with id = ec8bb948-86e2-4244-84a7-9b296c689566"
    }

# <h4>5. Getting a description of the existing file:</h4> 
    Example request:
    GET http://host:8080/file/ec8bb948-86e2-4244-84a7-9b296c689566/description
    
    Example response:
        {
            "description": "lol kek"
        }