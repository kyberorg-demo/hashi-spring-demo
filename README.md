# Getting Started

### About:Vault
Dokumentation: https://docs.spring.io/spring-vault/docs/current/reference/html

#### Configuration
* Vault URL and Token are configured in application.properties.
* Token can be customized using env var: VAULT_TOKEN or with `-Dvault.token=my-token-12344-000`
* Vault URL can be customized with `-Dvault.url=http://my.host:8200`

A java configuration and Beans can be found from `vault/configuration` package. 

#### Rest Service
`VaultController` is REST API for manipulating with users. Users are stored in Vault.

User object (`UserRecord`) is a username, password and custom value.

There are 4 REST Endpoints:

* `GET /users/userName` - getting user by username `userName`

* `POST /users/` - creates new user

* `PUT /users/userName` - updates user `userName` 

* `DELETE /users/userName` - deletes user `userName`

### About: Consul
Dokumentation: https://www.baeldung.com/spring-cloud-consul

Configuration: `bootstrap.yml`

Service Discovery: `DiscoveryClientController`

KV: `DistributedPropertiesController`

HealthCheck: `HealthCheckController`
