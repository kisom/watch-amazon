# watch-amazon

This is a command line program that watches Amazon for book prices.

## Usage

```
lein run <trigger price> <title>
```

The trigger price is the price at which the book will cause the system
to send out an email. It expects environment variables specifying the
mail server and recipient. See `sample-env.sh` for an example.

## License

Copyright © 2013 Kyle Isom <coder@kyleisom.net>

Distributed under the ISC license.
