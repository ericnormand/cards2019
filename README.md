# cards

A simple card game framework.

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

### Editor

```
lein nightlight
```

Wait for the prompt, then visit [http://localhost:4000](http://localhost:4000).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
