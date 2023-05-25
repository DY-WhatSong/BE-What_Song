#### PostgreSQL 세팅
```sql
# 순차적으로 하시면 됩니다.
# psql postgres 로 shell 접속.
    
// 계정 생성
CREATE ROLE root WITH LOGIN PASSWORD 'root1234';

// CREATEDB 권한 부여
ALTER USER root WITH CREATEDB;

// SUPERUSER 권한 부여
ALTER USER root WITH SUPERUSER;

// CREATEROLE 권한 부여
ALTER USER root WITH CREATEROLE;

//  DATABASE 생성
CREATE DATABASE whatsong;

// 권한 부여    
GRANT ALL PRIVILEGES ON DATABASE whatsong TO root;
```
