# markets

[CircleCI Builds](https://circleci.com/gh/skilbjo/markets-etl)

![CircleCI](https://circleci.com/gh/skilbjo/markets-etl/tree/master.svg?style=shield&circle_token=df58a0027114c540a956e9d1a075d58897ede76d)
[![quay.io](https://quay.io/repository/skilbjo/markets-etl/status "Docker Repository on Quay")](https://quay.io/repository/skilbjo/markets-etl)

## TODOs

- [X] Generic key mapper (xform into postgres column names)
- [X] Generic value mapper (xform into postgres dates)
- [X] Upsert-multi function <<-- this was a hard one
- [X] Come up with a time/now - subtract 5 days
- [X] Look at JVM settings in profile.clj (does jar need profile?) & deploy/bin/run-job
- [X] Model economics / interest_rates appropriately
- [X] For currency, parse out usd currency and put in column (ie, EUR)
- [ ] Better naming, any ideas for refactoring

## environment variables
```bash
export quandl_api_key=''
export jdbc_db_uri=''
export test_jdbc_db_uri=$jdbc_db_uri
```

## Run manually

```bash
lein trampoline run -m jobs.equities
LEIN_FAST_TRAMPOLINE=true lein trampoline run -m jobs.equities
llein run -m jobs.equities
```

## lein

```bash
lein deps :tree 2>deps 1>/dev/null && vim deps
```

## Git remotes

```bash
git remote add pi-vpn ssh://skilbjo@router.:43/~/deploy/git/markets-etl.git
git remote add pi-home ssh://skilbjo@pi1/~/deploy/git/markets-etl.git
```
