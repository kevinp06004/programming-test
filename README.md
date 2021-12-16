# Table of Content
- [Table of Content](#table-of-content)
- [Install & Run](#install--run)
- [API Reference](#api-reference)
  - [Encrypt](#encrypt)
    - [Request](#request)
    - [Response](#response)
  - [Decrypt](#decrypt)
    - [Request](#request-1)
    - [Response](#response-1)
- [Error](#error)
- [Test](#test)

# Install & Run
To run the application, run the following commend at the project root directory.
```
$ ./gradlew bootRun
```

# API Reference
## Encrypt
### Request
- Method: **POST**
- URL: ```/aes/encrypt```
- Headers： ```Content-Type:application/json```
- Body:
```
{
    "plain_text": "Apple",
    "aes_key": "404D635166546A576E5A723475377721"
}
```
### Response
- Headers： ```Content-Type:application/json```
- Body:
```
{
    "cipher_text": "C9E461E80EC3047944ACAE96A9896BC3"
}
```

## Decrypt
### Request
- Method: **POST**
- URL: ```/aes/decrypt```
- Headers： ```Content-Type:application/json```
- Body:
```
{
    "cipher_text": "C9E461E80EC3047944ACAE96A9896BC3",
    "aes_key": "404D635166546A576E5A723475377721"
}
```
### Response
- Headers： ```Content-Type:application/json```
- Body:
```
{
    "plain_text": "Apple",
    "base64": "QXBwbGU="
}
```

# Error
| Status Code |                                                                                           Description |
| :---------- | ----------------------------------------------------------------------------------------------------: |
| 400         |                                                                                Input field is missing |
| 404         |                                                                                        Path not found |
| 500         | Error whiling encrypting/decrypting string <br/> Please check message in the response for more detail |

# Test
- Junit Test: ```./src/test/java/com/kevin/aes/AesControllerTest.java```
- Postman Test: ```./AES.postman_collection.json```