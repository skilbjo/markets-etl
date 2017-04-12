begin;
  create table dw.equities (
    dataset       text,
    ticker        text,
    date          date,
    open          decimal(10,2),
    close         decimal(10,2),
    low           decimal(10,2),
    high          decimal(10,2),
    volume        decimal(10,2),
    split_ratio   decimal(10,2),
    adj_open      decimal(10,2),
    adj_close     decimal(10,2),
    adj_low       decimal(10,2),
    adj_high      decimal(10,2),
    adj_volume    decimal(10,2),
    ex_dividend   decimal(10,2)
  );
  create table dw.real_estate (
    dataset         text,
    ticker          text,
    date            date,
    value           decimal(10,2),
    area_category   text,
    indicator_code  text,
    area            text  -- usually a zip(int), but could be city(text)
  );
commit;

begin;
  drop table if exists dw.equities;
  drop table if exists dw.real_estate;
commit;
