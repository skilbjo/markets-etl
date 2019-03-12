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

  insert into dw.portfolio_dim
  select
    _user,
    dataset,
    ticker,
    quantity,
    cost_per_share
  from
    portfolio_stage
  on conflict (_user, dataset, ticker) do update set
    _user          = excluded. _user,
    dataset        = excluded.dataset,
    ticker         = excluded.ticker,
    quantity       = excluded.quantity,
    cost_per_share = excluded.cost_per_share
  ;

commit;
