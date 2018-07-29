with _markets as (
  select
    dataset,
    ticker,
    description
  from
    dw.markets_dim
)
select *
from _markets

with _portfolio as (
  select
    dataset,
    ticker,
    cast(quantity as decimal(10,4))      as quantity,
    cast(cost_per_share as decimal(6,2)) as cost_per_share
  from
    dw.portfolio_dim
)
select *
from _portfolio

with _currency as (
  select
    dataset,
    ticker,
    currency,
    cast(date as date)               as date,
    cast(rate as decimal(24,14))     as rate,
    cast(high_est as decimal(24,14)) as high_est,
    cast(low_est as decimal(24,14))  as low_est
  from
    dw.currency_fact
)
select *
from _currency

with _economics as (
  select
    dataset,
    ticker,
    cast(date as date)               as date,
    cast(value as decimal(10,2))     as value
  from
    dw.economics_fact
)
select *
from _economics

with _equities as (
  select
    dataset,
    ticker,
    cast(date as date)                 as date,
    cast(open as decimal(10,2))        as open,
    cast(close as decimal(10,2))       as close,
    cast(low as decimal(10,2))         as low,
    cast(high as decimal(10,2))        as high,
    cast(volume as decimal(20,2))      as volume,
    cast(split_ratio as decimal(10,2)) as split_ratio,
    cast(adj_open as decimal(10,2))    as adj_open,
    cast(adj_close as decimal(10,2))   as adj_close,
    cast(adj_low as decimal(10,2))     as adj_low,
    cast(adj_volume as decimal(20,2))  as adj_volume,
    cast(ex_dividend as decimal(10,2)) as ex_dividend
  from
    dw.equities_fact
)
select *
from _equities

with _interest_rates as (
  select
    dataset,
    ticker,
    cast(date as date)           as date,
    key,
    cast(value as decimal(10,2)) as value
  from
    dw.interest_rates_fact
)
select *
from _interest_rates

with _real_estate as (
  select
    dataset,
    ticker,
    cast(date as date)               as date,
    cast(value as decimal(10,2))     as value,
    area_category   text,
    indicator_code  text,
    area            text
  from
    dw.real_estate_fact
)
select *
from _real_estate
