/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties



cart-service/development/user.role
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties


Simuler un event hook
curl -v -H "X-Github-Event: push" -H "Content-Type: application/json" -X POST -d "{\"commits\": [{\"modified\": [\"cart-service.yml\"]}]}" http://localhost:8888/monitor
