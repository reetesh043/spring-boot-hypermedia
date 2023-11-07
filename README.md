# Spring-Boot-Hypermedia Example
Building a Hypermedia-Driven RESTful Web Service Using Spring Hateoas

```
Hypermedia is an important aspect of REST. It lets you build services that decouple client and server to a large extent and let them evolve independently. The representations returned for REST resources contain not only data but also links to related resources. Thus, the design of the representations is crucial to the design of the overall service.
```

### What We Will Build

We will build a hypermedia-driven REST service with Spring HATEOAS: a library of APIs that we can use to create links that point to Spring MVC controllers, build up resource representations, and control how they are rendered into supported hypermedia formats.

Postman Collection For Sample Request and Responses of Create, Update, Retrieve Product. 

```
{
	"info": {
		"_postman_id": "19e3024f-8c51-4c40-99ff-8a4902f498c2",
		"name": "Spring-Boot-Hypermedia",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "5347306"
	},
	"item": [
		{
			"name": "Create Product",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"name\": \"Smartphone\",\n    \"description\": \"High-end smartphone with advanced features\",\n    \"price\": 699.99\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/products",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"products"
					]
				}
			},
			"response": []
		},
		{
			"name": "Create Related Product",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"name\": \"Related Product A\",\n    \"description\": \"Description of Related Product A\",\n    \"price\": 49.99\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/products/1/related-products",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"products",
						"1",
						"related-products"
					]
				}
			},
			"response": []
		},
		{
			"name": "Update Product",
			"request": {
				"method": "PUT",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"name\": \"Updated Smartphone\",\n    \"description\": \"Updated high-end smartphone with advanced features\",\n    \"price\": 799.99\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/products/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"products",
						"1"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get related Product",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/products/1/related-products",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"products",
						"1",
						"related-products"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get Product",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/products/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"products",
						"1"
					]
				}
			},
			"response": []
		}
	]
}
```
