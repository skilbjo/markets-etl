## m a r k e t s - e t l
: all the financial data

[CircleCI Builds](https://circleci.com/gh/skilbjo/markets-etl)
![CircleCI](https://circleci.com/gh/skilbjo/markets-etl/tree/master.svg?style=shield&circle_token=df58a0027114c540a956e9d1a075d58897ede76d)
[![quay.io](https://quay.io/repository/skilbjo/markets-etl/status "Docker Repository on Quay")](https://quay.io/repository/skilbjo/markets-etl)
[![codecov](https://codecov.io/gh/skilbjo/markets-etl/branch/master/graph/badge.svg)](https://codecov.io/gh/skilbjo/markets-etl)

[![markets-etl_sfo](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/ZBYwWgw3/markets-etl_sfo.svg)](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/ZBYwWgw3/markets-etl_sfo.svg)

### What

ETL project of financial data: equity prices, bond yields, risk free rate of return,
GDP data, currency prices, and real estate indecies by zip code.

[Quandl](https://www.quandl.com/) has this data available in an API, so this
project wraps for insertion to a target postgresql database.

See my talk from the February 2017 Clojure PDX meetup for more about the project
<https://github.com/skilbjo/articles/blob/master/talks/bare_metal_to_aws_lambda.md>

<img src='dev-resources/img/ns-hierarchy.png' width=900>

### TODOs

- [X] Generic key mapper (xform into postgres column names)
- [X] Generic value mapper (xform into postgres dates)
- [X] Upsert-multi function <<-- this was a hard one
- [X] Come up with a time/now - subtract 5 days
- [X] Look at JVM settings in profile.clj (does jar need profile?) & deploy/bin/run-job
- [X] Model economics / interest_rates appropriately
- [X] For currency, parse out usd currency and put in column (ie, EUR)
- [ ] Better naming, any ideas for refactoring

### environment variables
```bash
export quandl_api_key=''
export jdbc_db_uri=''
export test_jdbc_db_uri=$jdbc_db_uri
```

### Run manually

```bash
lein trampoline run -m jobs.equities
LEIN_FAST_TRAMPOLINE=true lein trampoline run -m jobs.equities
llein run -m jobs.equities
```

### lein

#### Dependencies
```bash
lein deps :tree 2>deps 1>/dev/null && vim deps
```

#### cljfmt
```bash
lein cljfmt check ; lein cljfmt fix
```

#### kibits
```bash
lein kibit >tmp ; vim tmp && rm tmp
```

### Git

#### Git remotes
```bash
git remote add pi-vpn ssh://skilbjo@router.:43/~/deploy/git/markets-etl.git
git remote add pi-home ssh://skilbjo@pi1/~/deploy/git/markets-etl.git
```

#### Pre-commit hook to update pom.xml
```bash
vim .git/hooks/pre-commit

#!/usr/bin/env bash

lein pom 2>&1 dev-resources/pom.xml
```
