(ns cards.cards)

(defmacro wait [secs & body]
  `(js/setTimeout
    (fn [] ~@body)
    (* 1000 ~secs)))
