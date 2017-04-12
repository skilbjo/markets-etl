# markets

[CircleCI Builds](https://circleci.com/gh/skilbjo/markets-etl)

![CircleCI](https://circleci.com/gh/skilbjo/markets-etl/tree/master.svg?style=shield&circle_token=df58a0027114c540a956e9d1a075d58897ede76d)

[![quay.io](https://quay.io/repository/skilbjo/markets-etl/status "Docker Repository on Quay")](https://quay.io/repository/skilbjo/markets-etl)

## TODOs

- [X] Generic key mapper (xform into postgres column names)
- [X] Generic value mapper (xform into postgres dates)
- [ ] Better naming, any ideas for refactoring
- [X] Upsert-multi function <<-- this was a hard one

## Run manually

    lein trampoline run -m jobs.equities
    LEIN_FAST_TRAMPOLINE=true lein trampoline run -m jobs.equities
    llein run -m jobs.equities
