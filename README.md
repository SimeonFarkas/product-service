# ProductService

Backend-tjänst för CloudStore som hanterar produkter via integration med FakeStore API.

## Teknikstack

- Java 17, Spring Boot
- Spring Security med JWT-verifiering (RSA, asymmetrisk)
- Docker / Docker Compose
- CI/CD med GitHub Actions, deploy till AWS EC2

## Arkitektur

ProductService är en av två mikrotjänster i CloudStore:

- **ShopUser** (port 8080) – hanterar användare, inloggning och beställningar. Är den publika ingångspunkten för frontend.
- **ProductService** (denna tjänst, port 8081) – proxar FakeStore API för produktdata. Anropas internt av ShopUser, exponeras inte direkt mot frontend.

ProductService tar emot JWT-tokens (signerade av ShopUser med en privat RSA-nyckel) och verifierar dem med motsvarande publika RSA-nyckel innan requests behandlas.
