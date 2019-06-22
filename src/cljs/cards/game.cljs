(ns cards.game
  (:require-macros [cards.cards :as cards])
  (:require [cards.cards :as cards]))

(def cards ["A" "B" "C" "D"])

(defn start-game []
  (cards/deal 0 0 "X" :front)
  (cards/deal 0 1 "Y"))

(defn on-click
  "This function will be called when a grid item is clicked."
  [x y card grid values])



(defn on-turn-end
  "This function will be called when the turn changes."
  [grid values])

(defn on-game-end
  "This function will be called when the game ends."
  [grid values])
