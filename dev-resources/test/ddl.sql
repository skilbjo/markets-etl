-- Dimension table
begin;

  create schema if not exists dw;

  drop table if exists dw.markets cascade;
  create table if not exists dw.markets (
    dataset         text,
    ticker          text,
    description     text,

    constraint markets_pk primary key (dataset, ticker)
  );

  truncate dw.markets cascade;
  insert into dw.markets values
    ('FRED',       'GDP',             'Gross domistic product, in billions of $'),
    ('FRED',       'M1',              'M1 money stock is funds that are readily accesible for spending, in billions of $'),
    ('FRED',       'DFF',             'Effective federal funds rate'),
    ('FRED',       'UNRATE',          'Civilian unemployment rate'),
    ('WIKI',       'FB',              'Facebook'),
    ('WIKI',       'AMZN',            'Amazon'),
    ('WIKI',       'GOOG',            'Google'),
    ('WIKI',       'NVDA',            'Nvidia'),
    ('FED',        'RIFSPFF_N_D',     'Federal funds effective rate, daily'),
    ('USTREASURY', 'YIELD',           'Treasury yield curve rates'),
    ('USTREASURY', 'LONGTERMRATES',   'Treasury long term rates, 30-year constant maturity series'),
    ('ZILL',       'Z94108_A',        'Zillow home value index, all properties (condos, SFR) in zipcode'),
    ('ZILLOW',     'Z94108_ZHVIAH',   'Zillow home value index, all homes (condos, SFR) in zipcode'),
    ('CURRFX',     'EURUSD',          'Value of 1 EUR in USD'),
    ('CURRFX',     'GBPUSD',          'Value of 1 GBP in USD')
  ;
commit;

-- Fact tables
begin;

  drop table if exists dw.equities;
  create table if not exists dw.equities (
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
    ex_dividend   decimal(10,2),

    constraint equities_pk primary key (dataset, ticker, date),
    constraint equities_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker)
  );

  drop table if exists dw.real_estate;
  create table if not exists dw.real_estate (
    dataset         text,
    ticker          text,
    date            date,
    value           decimal(10,2),
    area_category   text,
    indicator_code  text,
    area            text,  -- usually a zip(int), but could be city(text)

    constraint real_estate_pk primary key (dataset, ticker, date),
    constraint real_estate_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker)
  );

  drop table if exists dw.currency;
  create table if not exists dw.currency (
    dataset         text,
    ticker          text,
    currency        text,
    date            date,
    rate            decimal(24,14),
    high_est        decimal(24,14),
    low_est         decimal(24,14),

    constraint currency_pk primary key (dataset, ticker, date),
    constraint currency_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker)
  );

  drop table if exists dw.economics;
  create table if not exists dw.economics (
    dataset         text,
    ticker          text,
    date            date,
    value           decimal(10,2),

    constraint economics_pk primary key (dataset, ticker, date),
    constraint economics_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker)
  );

  drop table if exists dw.interest_rates;
  create table if not exists dw.interest_rates (
    dataset         text,
    ticker          text,
    date            date,
    key             text,
    value           decimal(10,2),

    constraint interest_rates_pk primary key (dataset, ticker, date, key),
    constraint interest_rates_markets_fk foreign key (dataset, ticker) references dw.markets (dataset, ticker)
  );
commit;
