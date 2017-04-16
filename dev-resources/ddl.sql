-- Dimension table
begin;
  insert into dw.markets values
  ('FRED',       'GDP',           'Gross domistic product, in billions of $'),
  ('FRED',       'M1',            'M1 money stock is funds that are readily accesible for spending, in billions of $'),
  ('FRED',       'DFF',           'Effective federal funds rate'),
  ('FRED',       'UNRATE',        'Civilian unemployment rate'),
  ('WIKI',       'FB',            'Facebook'),
  ('WIKI',       'AMZN',          'Amazon'),
  ('WIKI',       'GOOG',          'Google'),
  ('FED',        'RIFSPFF_N_D',   'Federal funds effective rate, daily'),
  ('USTREASURY', 'YIELD',         'Treasury yield curve rates'),
  ('USTREASURY', 'LONGTERMRATES', 'Treasury long term rates, 30-year constant maturity series'),
  ('ZILL',       'Z94108_A',      'Zillow home value index, all properties (condos, SFR) in zipcode'),
  ('CURRFX',     'EURUSD',        'Value of 1 EUR in USD'),
  ('CURRFX',     'GBPUSD',        'Value of 1 GBP in USD')
  ;
commit;

-- Fact tables
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
  create table dw.currency (
    dataset         text,
    ticker          text,
    currency        text,
    date            date,
    rate            decimal(24,14),
    high_est        decimal(24,14),
    low_est         decimal(24,14)
  );
  create table dw.economics (
    dataset         text,
    ticker          text,
    date            date,
    value           decimal(10,2)
  );
  create table dw.interest_rates (
    dataset         text,
    ticker          text,
    date            date,
    key             text,
    value           decimal(10,2)
  );
  create table dw.markets (
    dataset         text,
    ticker          text,
    description     text
  );
commit;

-- Primary key constraints
begin;
  alter table dw.markets add constraint markets_pk primary key (dataset, ticker);

  alter table dw.currency add constraint currency_pk primary key (dataset, ticker, date);
  alter table dw.economics add constraint economics_pk primary key (dataset, ticker, date);
  alter table dw.equities add constraint equities_pk primary key (dataset, ticker, date);
  alter table dw.interest_rates add constraint interest_rates_pk primary key (dataset, ticker, date, key);
  alter table dw.real_estate add constraint real_estate_pk primary key (dataset, ticker, date);
commit;

-- Foreign key constraints
begin;
  alter table dw.currency add constraint currency_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker);
  alter table dw.economics add constraint economics_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker);
  alter table dw.equities add constraint equities_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker);
  alter table dw.interest_rates add constraint interest_rates_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker);
  alter table dw.real_estate add constraint real_estate_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker);
commit;

-- Destructive
begin;
  drop table if exists dw.equities;
  drop table if exists dw.real_estate;
  drop table if exists dw.currency;
  drop table if exists dw.economics;
  drop table if exists dw.interest_rates;
  drop table if exists dw.markets;
commit;

