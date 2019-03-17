create temp table markets_stage (
  ticker           text,
  asset_type       text,
  location         text,
  capitalization   text,
  investment_style text
);

\copy markets_stage (ticker, asset_type, location, capitalization, investment_style) from '/Users/skilbjo/Documents/Dropbox/markets-etl/markets.csv' with csv header;

begin;

  -- we only want to update -- don't want to insert
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

commit;
