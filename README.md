# User-RBA-Access-Management-Project


**** My Project Test Cases ****

1. User Authentication role Wise

# http://localhost:8080/api/signup

input:-

{
    "userName":"priya",
    "email":"priya@gmail.com",
    "password":"123"
}

output:-

{
    "code": 201,
    "message": "Success",
    "object": {
        "id": "66ddfbca7c36a15bae8eaaec",
        "userName": "priya",
        "password": "$2a$$IE.Q9LptitJka9ohGCXJ2OMKWBOv4tIDNI7Ht5iV0XvxpCUhjAvdW",
        "email": "priya@gmail.com",
        "role": "ADMIN"
    }
}

# http://localhost:8080/api/auth

input:-

{  "email":"priya@gmail.com",
    "password":"123"}

output:-

{
    "code": 201,
    "message": "Success",
    "object": {
        "user": {
            "id": "66ddfbca7c36a15bae8eaaec",
            "userName": "priya",
            "password": "$2a$10$IE.Q9LptitJka9ohGCXJ2OMKWBOv4tIDNI7Ht5iV0XvxpCUhjAvdW",
            "email": "priya@gmail.com",
            "role": "ADMIN"
        },
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcml5YUBnbWFpbC5jb20iLCJleHAiOjE3MjU4NTk5ODYsImlhdCI6MTcyNTgyMzk4Nn0.fnAh-ekk8xPs3DLaZ_pEo0r85x0aBaYB8C83JpfM2Jw"
    }
}

------------------------------------

2. admin log in after access the add User and delete user

# http://localhost:8080/api/addUser

input:-

{
    "userName":"chirag",
    "email":"chirag@gmail.com",
    "password":"123"
}

output:-

{
    "code": 201,
    "message": "Success",
    "object": {
        "id": "66ddfc467c36a15bae8eaaed",
        "userName": "chirag",
        "password": "$2a$10$QYxyAlImCqDY9X4zRxNUluRggChR1cgskDk7RiXfGwuPCqYkCuFyK",
        "email": "chirag@gmail.com",
        "role": "USER"
    }
}

# http://localhost:8080/api/deleteUser/66dde9e8f0f48e26a9bb8f39

output:-

{
    "code": 200,
    "message": "User deleted successfully"
}

------------------------------------

3. user log in after access the list

# http://localhost:8080/api/auth

input:-

{  "email":"salu@gmail.com",
    "password":"123"}

output:-

{
    "code": 201,
    "message": "Success",
    "object": {
        "user": {
            "id": "66dde9c6f0f48e26a9bb8f37",
            "userName": "salu",
            "password": "$2a$10$Gc1cBHEqS0gMi/XAKI2VRuSOaZaCZaX2H6pboE9PZh5Pkp2ClDw02",
            "email": "salu@gmail.com",
            "role": "USER"
        },
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWx1QGdtYWlsLmNvbSIsImV4cCI6MTcyNTg2MDM1NywiaWF0IjoxNzI1ODI0MzU3fQ.eC1l7v5L5XOhpiMG0Ub6-_GNgVnLj-BYghagBwTQXwc"
    }
}

http://localhost:8080/api/getUser

output:-

{
    "code": 201,
    "message": "Success",
    "object": [
        {
            "id": "66dde9c6f0f48e26a9bb8f37",
            "userName": "salu",
            "password": "$2a$10$Gc1cBHEqS0gMi/XAKI2VRuSOaZaCZaX2H6pboE9PZh5Pkp2ClDw02",
            "email": "salu@gmail.com",
            "role": "USER"
        },
        {
            "id": "66dde9daf0f48e26a9bb8f38",
            "userName": "dhruv",
            "password": "$2a$10$n7BSuzkXz2gKUD4y2x121.ifwGB9jmD.fuHs7z0o0dnMibEHIya.y",
            "email": "dhruv@gmail.com",
            "role": "USER"
        },
        {
            "id": "66ddfc467c36a15bae8eaaed",
            "userName": "chirag",
            "password": "$2a$10$QYxyAlImCqDY9X4zRxNUluRggChR1cgskDk7RiXfGwuPCqYkCuFyK",
            "email": "chirag@gmail.com",
            "role": "USER"
        }
    ]
}
