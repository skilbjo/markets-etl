-- Dimension table
begin;

  create schema if not exists dw;

  drop table if exists dw.markets_dim cascade;
  create table if not exists dw.markets_dim (
    dataset         text,
    ticker          text,
    description     text,

    dw_created_at   timestamp default now(),

    constraint markets_dim_pk primary key (dataset, ticker)
  );

  truncate dw.markets_dim cascade;
  insert into dw.markets_dim values
    (         'FRED',           'GDP', 'Gross domistic product, in billions of $'),
    (         'FRED',            'M1', 'M1 money stock is funds that are readily accesible for spending, in billions of $'),
    (         'FRED',           'DFF', 'Effective federal funds rate'),
    (         'FRED',        'UNRATE', 'Civilian unemployment rate'),
    (         'WIKI',            'FB', 'Facebook'),
    (         'WIKI',          'AMZN', 'Amazon'),
    (         'WIKI',          'GOOG', 'Google'),
    (         'WIKI',          'NVDA', 'Nvidia'),
    (          'FED',   'RIFSPFF_N_D', 'Federal funds effective rate, daily'),
    (   'USTREASURY',         'YIELD', 'Treasury yield curve rates'),
    (   'USTREASURY', 'LONGTERMRATES', 'Treasury long term rates, 30-year constant maturity series'),
    (         'ZILL',      'Z94108_A', 'Zillow home value index, all properties (condos, SFR) in zipcode'),
    (       'ZILLOW', 'Z94108_ZHVIAH', 'Zillow home value index, all homes (condos, SFR) in zipcode'),
    (       'CURRFX',        'EURUSD', 'Value of 1 EUR in USD'),
    (       'CURRFX',        'GBPUSD', 'Value of 1 GBP in USD'),

    (         'WIKI',            'CY', 'CYPRESS SEMICONDUCTOR CORP'),
    (         'WIKI',          'INTC', 'INTEL CORP'),
    (         'WIKI',           'TXN', 'TEXAS INSTRUMENTS INC'),
    (         'WIKI',             'V', 'VISA INC CLASS A'),
    (         'WIKI',          'AAPL', 'Apple Inc.'),
    (         'WIKI',          'TWTR', 'Twitter, Inc.'),

    (        'MSTAR',         'VEMAX', 'Vanguard Emerging Markets Stock Index Fund Admiral Shares'),
    (        'MSTAR',         'VEURX', 'Vanguard European Stock Index Fund Investor Shares'),
    (        'MSTAR',         'VEXPX', 'Vanguard Explorer Fund Investor Class'),
    (        'MSTAR',         'VGWAX', 'Vanguard Global Wellington Fund Admiral Shares'),
    (        'MSTAR',         'VITAX', 'Vanguard Information Technology Index Fund Admiral Shares'),
    (        'MSTAR',         'VIMAX', 'Vanguard Mid-Cap Index Fund Admiral Shares'),
    (        'MSTAR',         'VMRAX', 'Vanguard Morgan Growth Fund Admiral Shares'),
    (        'MSTAR',         'VPACX', 'Vanguard Pacific Stock Index Fund Investor Shares'),
    (        'MSTAR',         'VMMXX', 'Vanguard Prime Money Market Fund'),
    (        'MSTAR',         'VGSLX', 'Vanguard Real Estate Index Fund Admiral Shares'),
    (        'MSTAR',         'VTIAX', 'Vanguard Total International Stock Index Fund Admiral Shares'),
    (        'MSTAR',         'VTSAX', 'Vanguard Total Stock Market Index Fund Admiral Shares'),
    (        'MSTAR',         'VWINX', 'Vanguard Wellesley® Income Fund Investor Shares'),
    (        'MSTAR',         'VWENX', 'Vanguard Wellington Fund Admiral Shares'),
    (        'MSTAR',         'VWNDX', 'Vanguard Windsor Fund Investor Share'),
    (        'MSTAR',           'VFH', 'VANGUARD FINANCIALS ETF'),
    (        'MSTAR',           'VEA', 'VANGUARD FTSE DEVELOPED MKTS ETF'),
    (        'MSTAR',           'VWO', 'VANGUARD FTSE EMERGING MARKETS ETF'),
    (        'MSTAR',           'VHT', 'VANGUARD HEALTH CARE ETF'),
    (        'MSTAR',           'VGT', 'VANGUARD INFORMATION TECHNOLOGY ETF'),
    (        'MSTAR',         'BRK.B', 'BERKSHIRE HATHAWAY INC DE CL B NEW'),
    (        'MSTAR',          'AAPL', 'Apple Inc.'),
    (        'MSTAR',          'TWTR', 'Twitter, Inc.'),

    (        'MSTAR',           'TSM', 'TAIWAN SEMICONDUCTOR MANUFACTURING COMPANY LTD SPONS ADR'),
    (        'MSTAR',            'FB', 'Facebook'),
    (        'MSTAR',          'AMZN', 'Amazon'),
    (        'MSTAR',          'GOOG', 'Google'),
    (        'MSTAR',          'NVDA', 'Nvidia'),
    (        'MSTAR',            'CY', 'CYPRESS SEMICONDUCTOR CORP'),
    (        'MSTAR',          'INTC', 'INTEL CORP'),
    (        'MSTAR',           'TXN', 'TEXAS INSTRUMENTS INC'),
    (        'MSTAR',             'V', 'VISA INC CLASS A'),
    (        'MSTAR',         'VWIGX', 'Vanguard International Growth Fund Investor Shares'),
    (        'MSTAR',         'VINEX', 'Vanguard International Explorer Fund'),
    (        'MSTAR',         'VMMSX', 'Vanguard Emerging Markets Select Stock Fund'),
    (        'MSTAR',           'SAP', 'SAP SE ADR'),
    (        'MSTAR',            'SQ', 'Square Inc A'),
    (        'MSTAR',          'PYPL', 'PayPal Holdings Inc'),
    (        'MSTAR',       'LON:FCH', 'Funding Circle Holdings PLC'),
    (        'MSTAR',         'SFTBF', 'SoftBank Group Corp'),

    (       'TIINGO',         'VEMAX', 'Vanguard Emerging Markets Stock Index Fund Admiral Shares'),
    (       'TIINGO',         'VEURX', 'Vanguard European Stock Index Fund Investor Shares'),
    (       'TIINGO',         'VEXPX', 'Vanguard Explorer Fund Investor Class'),
    (       'TIINGO',         'VGWAX', 'Vanguard Global Wellington Fund Admiral Shares'),
    (       'TIINGO',         'VITAX', 'Vanguard Information Technology Index Fund Admiral Shares'),
    (       'TIINGO',         'VIMAX', 'Vanguard Mid-Cap Index Fund Admiral Shares'),
    (       'TIINGO',         'VMRAX', 'Vanguard Morgan Growth Fund Admiral Shares'),
    (       'TIINGO',         'VPACX', 'Vanguard Pacific Stock Index Fund Investor Shares'),
    (       'TIINGO',         'VMMXX', 'Vanguard Prime Money Market Fund'),
    (       'TIINGO',         'VGSLX', 'Vanguard Real Estate Index Fund Admiral Shares'),
    (       'TIINGO',         'VTIAX', 'Vanguard Total International Stock Index Fund Admiral Shares'),
    (       'TIINGO',         'VTSAX', 'Vanguard Total Stock Market Index Fund Admiral Shares'),
    (       'TIINGO',         'VWINX', 'Vanguard Wellesley® Income Fund Investor Shares'),
    (       'TIINGO',         'VWENX', 'Vanguard Wellington Fund Admiral Shares'),
    (       'TIINGO',         'VWNDX', 'Vanguard Windsor Fund Investor Share'),
    (       'TIINGO',           'VFH', 'VANGUARD FINANCIALS ETF'),
    (       'TIINGO',           'VEA', 'VANGUARD FTSE DEVELOPED MKTS ETF'),
    (       'TIINGO',           'VWO', 'VANGUARD FTSE EMERGING MARKETS ETF'),
    (       'TIINGO',           'VHT', 'VANGUARD HEALTH CARE ETF'),
    (       'TIINGO',           'VGT', 'VANGUARD INFORMATION TECHNOLOGY ETF'),
    (       'TIINGO',         'BRK-B', 'BERKSHIRE HATHAWAY INC DE CL B NEW'),
    (       'TIINGO',           'TSM', 'TAIWAN SEMICONDUCTOR MANUFACTURING COMPANY LTD SPONS ADR'),
    (       'TIINGO',            'FB', 'Facebook'),
    (       'TIINGO',          'AMZN', 'Amazon'),
    (       'TIINGO',          'GOOG', 'Google'),
    (       'TIINGO',          'NVDA', 'Nvidia'),
    (       'TIINGO',            'CY', 'CYPRESS SEMICONDUCTOR CORP'),
    (       'TIINGO',          'INTC', 'INTEL CORP'),
    (       'TIINGO',           'TXN', 'TEXAS INSTRUMENTS INC'),
    (       'TIINGO',             'V', 'VISA INC CLASS A'),
    (       'TIINGO',         'VWIGX', 'Vanguard International Growth Fund Investor Shares'),
    (       'TIINGO',         'VINEX', 'Vanguard International Explorer Fund'),
    (       'TIINGO',         'VMMSX', 'Vanguard Emerging Markets Select Stock Fund'),
    (       'TIINGO',           'SAP', 'SAP SE ADR'),
    (       'TIINGO',            'SQ', 'Square Inc A'),
    (       'TIINGO',          'PYPL', 'PayPal Holdings Inc'),
    (       'TIINGO',       'LON:FCH', 'Funding Circle Holdings PLC'),
    (       'TIINGO',         'SFTBF', 'SoftBank Group Corp'),
    (       'TIINGO',          'AAPL', 'Apple Inc.'),
    (       'TIINGO',          'TWTR', 'Twitter, Inc.'),

    (     'INTRINIO',            'FB', 'Facebook'),
    (     'INTRINIO',          'AMZN', 'Amazon'),
    (     'INTRINIO',          'GOOG', 'Google'),
    (     'INTRINIO',          'NVDA', 'Nvidia'),
    (     'INTRINIO',            'CY', 'CYPRESS SEMICONDUCTOR CORP'),
    (     'INTRINIO',          'INTC', 'INTEL CORP'),
    (     'INTRINIO',           'TXN', 'TEXAS INSTRUMENTS INC'),
    (     'INTRINIO',             'V', 'VISA INC CLASS A'),
    (     'INTRINIO',          'AAPL', 'Apple Inc.'),
    (     'INTRINIO',          'TWTR', 'Twitter, Inc.'),

    ('ALPHA-VANTAGE',         'VEMAX', 'Vanguard Emerging Markets Stock Index Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VEURX', 'Vanguard European Stock Index Fund Investor Shares'),
    ('ALPHA-VANTAGE',         'VEXPX', 'Vanguard Explorer Fund Investor Class'),
    ('ALPHA-VANTAGE',         'VGWAX', 'Vanguard Global Wellington Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VITAX', 'Vanguard Information Technology Index Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VIMAX', 'Vanguard Mid-Cap Index Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VMRAX', 'Vanguard Morgan Growth Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VPACX', 'Vanguard Pacific Stock Index Fund Investor Shares'),
    ('ALPHA-VANTAGE',         'VMMXX', 'Vanguard Prime Money Market Fund'),
    ('ALPHA-VANTAGE',         'VGSLX', 'Vanguard Real Estate Index Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VTIAX', 'Vanguard Total International Stock Index Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VTSAX', 'Vanguard Total Stock Market Index Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VWINX', 'Vanguard Wellesley® Income Fund Investor Shares'),
    ('ALPHA-VANTAGE',         'VWENX', 'Vanguard Wellington Fund Admiral Shares'),
    ('ALPHA-VANTAGE',         'VWNDX', 'Vanguard Windsor Fund Investor Share'),
    ('ALPHA-VANTAGE',           'VFH', 'VANGUARD FINANCIALS ETF'),
    ('ALPHA-VANTAGE',           'VEA', 'VANGUARD FTSE DEVELOPED MKTS ETF'),
    ('ALPHA-VANTAGE',           'VWO', 'VANGUARD FTSE EMERGING MARKETS ETF'),
    ('ALPHA-VANTAGE',           'VHT', 'VANGUARD HEALTH CARE ETF'),
    ('ALPHA-VANTAGE',           'VGT', 'VANGUARD INFORMATION TECHNOLOGY ETF'),
    ('ALPHA-VANTAGE',         'BRK.B', 'BERKSHIRE HATHAWAY INC DE CL B NEW'),
    ('ALPHA-VANTAGE',         'BRK-B', 'BERKSHIRE HATHAWAY INC DE CL B NEW'),
    ('ALPHA-VANTAGE',           'TSM', 'TAIWAN SEMICONDUCTOR MANUFACTURING COMPANY LTD SPONS ADR'),
    ('ALPHA-VANTAGE',            'FB', 'Facebook'),
    ('ALPHA-VANTAGE',          'AMZN', 'Amazon'),
    ('ALPHA-VANTAGE',          'GOOG', 'Google'),
    ('ALPHA-VANTAGE',          'NVDA', 'Nvidia'),
    ('ALPHA-VANTAGE',            'CY', 'CYPRESS SEMICONDUCTOR CORP'),
    ('ALPHA-VANTAGE',          'INTC', 'INTEL CORP'),
    ('ALPHA-VANTAGE',           'TXN', 'TEXAS INSTRUMENTS INC'),
    ('ALPHA-VANTAGE',             'V', 'VISA INC CLASS A'),
    ('ALPHA-VANTAGE',         'VWIGX', 'Vanguard International Growth Fund Investor Shares'),
    ('ALPHA-VANTAGE',         'VINEX', 'Vanguard International Explorer Fund'),
    ('ALPHA-VANTAGE',         'VMMSX', 'Vanguard Emerging Markets Select Stock Fund'),
    ('ALPHA-VANTAGE',           'SAP', 'SAP SE ADR'),
    ('ALPHA-VANTAGE',            'SQ', 'Square Inc A'),
    ('ALPHA-VANTAGE',          'PYPL', 'PayPal Holdings Inc'),
    ('ALPHA-VANTAGE',       'LON:FCH', 'Funding Circle Holdings PLC'),
    ('ALPHA-VANTAGE',         'SFTBF', 'SoftBank Group Corp'),
    ('ALPHA-VANTAGE',          'AAPL', 'Apple Inc.'),
    ('ALPHA-VANTAGE',          'TWTR', 'Twitter, Inc.'),

    ('ALPHA-VANTAGE',        'EURUSD', 'Value of 1 EUR in USD'),
    ('ALPHA-VANTAGE',        'GBPUSD', 'Value of 1 GBP in USD')

  on conflict (dataset,ticker) do update
  set
    dataset     = excluded.dataset,
    ticker      = excluded.ticker,
    description = excluded.description
  ;

  drop table if exists dw.portfolio_dim cascade;
  create table if not exists dw.portfolio_dim (
    _user              text          not null,
    dataset            text          not null,
    ticker             text          not null,
    quantity           decimal(10,4) not null,
    cost_per_share     decimal(6,2)  not null,

    dw_created_at      timestamp default now(),

    constraint portfolio_dim_pk primary key (dataset, ticker),
    constraint portfolio_dim_markets_dim_fk foreign key (dataset, ticker) references dw.markets_dim (dataset, ticker)
  );

commit;

-- Fact tables
begin;

  drop table if exists dw.equities_fact;
  create table if not exists dw.equities_fact (
    dataset       text,
    ticker        text,
    date          date,
    open          decimal(10,2),
    close         decimal(10,2),
    low           decimal(10,2),
    high          decimal(10,2),
    volume        decimal(20,2),
    split_ratio   decimal(10,2),
    adj_open      decimal(10,2),
    adj_close     decimal(10,2),
    adj_low       decimal(10,2),
    adj_high      decimal(10,2),
    adj_volume    decimal(20,2),
    ex_dividend   decimal(10,2),

    dw_created_at timestamp default now(),

    constraint equities_fact_pk primary key (dataset, ticker, date),
    constraint equities_fact_markets_dim_fk foreign key (dataset, ticker) references dw.markets_dim (dataset, ticker)
  );

  drop table if exists dw.real_estate_fact;
  create table if not exists dw.real_estate_fact (
    dataset         text,
    ticker          text,
    date            date,
    value           decimal(10,2),
    area_category   text,
    indicator_code  text,
    area            text,  -- usually a zip(int), but could be city(text)

    dw_created_at   timestamp default now(),

    constraint real_estate_fact_pk primary key (dataset, ticker, date),
    constraint real_estate_fact_markets_dim_fk foreign key (dataset, ticker) references dw.markets_dim (dataset, ticker)
  );

  drop table if exists dw.currency_fact;
  create table if not exists dw.currency_fact (
    dataset         text,
    ticker          text,
    currency        text,
    date            date,
    rate            decimal(24,14),
    high            decimal(24,14),
    low             decimal(24,14),

    dw_created_at   timestamp default now(),

    constraint currency_fact_pk primary key (dataset, ticker, date),
    constraint currency_fact_markets_dim_fk foreign key (dataset, ticker) references dw.markets_dim (dataset, ticker)
  );

  drop table if exists dw.economics_fact;
  create table if not exists dw.economics_fact (
    dataset       text,
    ticker        text,
    date          date,
    value         decimal(10,2),

    dw_created_at timestamp default now(),

    constraint economics_fact_pk primary key (dataset, ticker, date),
    constraint economics_fact_markets_dim_fk foreign key (dataset, ticker) references dw.markets_dim (dataset, ticker)
  );

  drop table if exists dw.interest_rates_fact;
  create table if not exists dw.interest_rates_fact (
    dataset         text,
    ticker          text,
    date            date,
    key             text,
    value           decimal(10,2),

    dw_created_at   timestamp default now(),

    constraint interest_rates_fact_pk primary key (dataset, ticker, date, key),
    constraint interest_rates_fact_markets_dim_fk foreign key (dataset, ticker) references dw.markets_dim (dataset, ticker)
  );

commit;
