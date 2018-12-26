create temp table portfolio_stage (
  _user             text,
  dataset           text,
  ticker            text,
  quantity          decimal(10,3),
  cost_per_share    decimal(10,3),
  total_cost        decimal(10,3),
  formula           text
);

\copy portfolio_stage (_user,dataset,ticker,quantity,cost_per_share,total_cost,formula) from '/Users/skilbjo/Documents/Dropbox/markets-etl/portfolio.csv' with csv header;

begin;

  update dw.portfolio_dim
  set
    _user          = portfolio_stage._user,
    dataset        = portfolio_stage.dataset,
    ticker         = portfolio_stage.ticker,
    quantity       = portfolio_stage.quantity,
    cost_per_share = portfolio_stage.cost_per_share
  from
    portfolio_stage
  where
    portfolio_dim._user   = portfolio_stage._user   and
    portfolio_dim.dataset = portfolio_stage.dataset and
    portfolio_dim.ticker  = portfolio_stage.ticker;

  insert into dw.portfolio_dim (
    _user,
    dataset,
    ticker,
    quantity,
    cost_per_share
  )
  select
    portfolio_stage._user,
    portfolio_stage.dataset,
    portfolio_stage.ticker,
    portfolio_stage.quantity,
    portfolio_stage.cost_per_share
  from
    portfolio_stage
    left join dw.portfolio_dim on portfolio_dim._user   = portfolio_stage._user   and
                                  portfolio_dim.dataset = portfolio_stage.dataset and
                                  portfolio_dim.ticker  = portfolio_stage.ticker
  where
    portfolio_dim.ticker is null;

commit;
