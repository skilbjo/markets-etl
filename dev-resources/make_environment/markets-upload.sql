create temp table markets_stage (
  ticker           text
  asset_type       text,
  location         text,
  capitalization   text,
  investment_style text
);

\copy markets_stage (ticker, asset_type, location, capitalization, investment_style) from '/Users/skilbjo/dev/iris/dev-resources/make_environment/markets.csv' with csv header;

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
    markets_dim.ticker            = markets_stage.ticker            and
    markets_dim.asset_type        = markets_stage.asset_type        and
    markets_dim.location          = markets_stage.location          and
    markets_dim.capitalization    = markets_stage.capitalization    and
    markets_dim.investment_style  = markets_stage.investment_style

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
    left join dw.markets_dim on markets_dim.ticker           = markets_stage.ticker and
                                markets_dim.asset_type       = markets_stage.asset_type and
                                markets_dim.location         = markets_stage.location and
                                markets_dim.capitalization   = markets_stage.capitalization and
                                markets_dim.investment_style = markets_stage.investment_style
  where
    markets_dim.ticker is null;

commit;
