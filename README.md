# markets

## TODOs

- [X] Generic key mapper (xform into postgres column names)
- [X] Generic value mapper (xform into postgres dates)
- [ ] Better naming, any ideas for refactoring
- [X] Upsert-multi function <<-- this was a hard one

## Run manually

    lein trampoline run -m jobs.equities
    LEIN_FAST_TRAMPOLINE=true lein trampoline run -m jobs.equities
    llein run -m jobs.equities
