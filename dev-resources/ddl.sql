begin;
  create table equities (
    datasource    text,
    ticker        text,
    open          decimal(10,2),
    close         decimal(10,2),
    low           decimal(10,2),
    high          decimal(10,2),
    volume        decimal(10,2),
    split_ratio   decimal(10,2),
    adj_open      decimal(10,2),
    adj_close     decimal(10,2),
    adj_high      decimal(10,2),
    adj_low       decimal(10,2),
    ex_dividend   decimal(10,2)
  );
commit;
