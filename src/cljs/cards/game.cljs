(ns cards.game
  (:require-macros [cards.cards :as cards])
  (:require [cards.cards :as cards]))


(defn on-click
  "This function will be called when a grid item is clicked."
  [x y card grid values])



(defn on-turn-end
  "This function will be called when the turn changes."
  [grid values])

(defn on-game-end
  "This function will be called when the game ends."
  [grid values])
