# Project
Calls a the spotify service https://api.spotify.com/v1/search  pulls the query from 
:query-params in ~/clj-spotify/resources/config.edn  and outputs the results to std out.

## Motivation
A simple clojure project that uses a spotify public API to try out:
- Stuart Sierra's component design.
- Http-Kit
- Core Async
- Immuconf for configuration.

With this kind of test run of those components, these components can be used to 
build much larger applications.

Easiest way to run things is to execute start-app function in main.clj

## Getting Started
### Prerequisites ###

- Leiningen


### Installing

From working leiningen installation 
- lein install

### Running tests
Tests written using clojre.test
Tests can be found under clj-spotify/test
- Run tests with lein test


## License

Copyright © 2016 Eric Zimmerman

Distributed under the Eclipse Public License version 1.0.
