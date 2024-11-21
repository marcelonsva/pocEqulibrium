
# PocEquilibirum

Este projeto consiste em uma aplicação com três serviços principais (`cadastro_service`, `cadastro_web`, `processo_service`) e um banco de dados Oracle. Ele utiliza Docker para simplificar o ambiente de execução e orquestração dos serviços.

## Estrutura do Projeto

```
PocEquilibirum/
|-- cadastro_service/        # Serviço Java (Spring Boot) para Cadastro
|-- cadastro_web/            # Aplicação Web baseada no Tomcat
|-- processo_service/        # Serviço Java (Spring Boot) para Processos
|-- create_schema/            # Scripts de inicialização para o banco Oracle
|-- docker-compose.yml       # Arquivo de configuração do Docker Compose
|-- Dockerfiles/             # Dockerfiles para cada serviço
```

---

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

1. **Java 17+**
2. **Maven**
3. **Docker**
4. **Docker Compose**

---

## Como Configurar e Executar

### 1. Compilar os Serviços

Certifique-se de compilar cada serviço para gerar os artefatos necessários (`.jar` ou `.war`):

1. **Compilar o `cadastro_service`**:
   ```bash
   mvn clean package -f cadastro_service/pom.xml
   ```

2. **Compilar o `cadastro_web`**:
   ```bash
   mvn clean package -f cadastro_web/pom.xml
   ```

3. **Compilar o `processo_service`**:
   ```bash
   mvn clean package -f processo_service/pom.xml
   ```

---

### 2. Subir os Contêineres com Docker Compose

Após a compilação, use o Docker Compose para levantar o ambiente:

1. Execute o comando abaixo na raiz do projeto:
   ```bash
   docker-compose up --build
   ```

2. O Compose irá:
   - Criar e iniciar um contêiner para o banco de dados Oracle.
   - Criar e iniciar contêineres para os serviços `cadastro_service`, `cadastro_web` e `processo_service`.

---

### 3. Acessar os Serviços

Os serviços estarão disponíveis nas seguintes portas:

- **Oracle Database**: `localhost:1521`
- **Cadastro Service (API)**: `http://localhost:8084`
- **Cadastro Web**: `http://localhost:8082`
- **Processo Service (API)**: `http://localhost:8085`

---

### 4. Banco de Dados Oracle

O contêiner do Oracle Database executa automaticamente os scripts SQL em `create_schema/` para configurar o esquema e criar as tabelas.

- **Usuário**: `C##PROJETO`
- **Senha**: `oracle_password`
- **Conexão JDBC**: `jdbc:oracle:thin:@oracle-db:1521/XE`

#### Verificar as Tabelas no Oracle:

Conecte-se ao banco de dados no contêiner do Oracle:

```bash
docker exec -it oracle-db sqlplus C##PROJETO/oracle_password@XE
```

No console SQL*Plus, execute:

```sql
SELECT * FROM user_tables;
```

---
 
