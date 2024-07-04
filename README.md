# hacken

## Database
Uses postgres to start:
```
docker run --name tx-postgres -d --rm \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=tx \
  -p 5432:5432 \
  postgres:14.1
```

Execute init script:
```sql
create table TransactionEntity
(
    id               bigint not null,
    blockNumber      numeric(38, 0),
    chainId          bigint,
    txFrom           varchar(255),
    gas              numeric(38, 0),
    gasPrice         numeric(38, 0),
    nodeType         varchar(255),
    txTo             varchar(255),
    transactionIndex numeric(38, 0),
    txValue          numeric(38, 0),
    primary key (id)
);
create sequence TransactionEntity_SEQ start with 1 increment by 50;
```

## Demonstration 
Pulling tx from eth-mainnet:
![](./img/pull_tx_logs.png)

Db screenshot with saved tx:
![](./img/db_tx_select_all.png)

