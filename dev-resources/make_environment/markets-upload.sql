create temp table markets_stage (
  ticker           text,
  asset_type       text,
  location         text,
  capitalization   text,
  investment_style text
);

\copy markets_stage (ticker, asset_type, location, capitalization, investment_style) from '/Users/skilbjo/Documents/Dropbox/markets-etl/markets.csv' with csv header;

begin;

  update dw.markets_dim
  set
    ticker              = markets_stage.ticker,
    asset_type          = markets_stage.asset_type,
    location            = markets_stage.location,
    capitalization      = markets_stage.capitalization,
    investment_style    = markets_stage.investment_style
  from
    markets_stage
  where
    markets_dim.ticker = markets_stage.ticker;

  insert into dw.markets_dim (
    ticker,
    asset_type,
    location,
    capitalization,
    investment_style
  )
  select
    markets_stage.ticker,
    markets_stage.asset_type,
    markets_stage.location,
    markets_stage.capitalization,
    markets_stage.investment_style
  from
    markets_stage
    left join dw.markets_dim on markets_dim.ticker = markets_stage.ticker
  where
    markets_dim.ticker is null;

commit;
