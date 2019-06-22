(ns cards.game
  (:require-macros [cards.cards :as cards])
  (:require [cards.cards :as cards]))

(def cards ["A" "B" "C" "D"])
(def cards2 (concat cards cards))

(defn start-game []
  (cards/remove-all-cards)
  (let [scards (shuffle cards2)]
    (dotimes [i (count scards)]
      (let [x (mod i 4)
            y (quot i 4)]
        (cards/deal x y (nth scards i))))))

(defn on-click
  "This function will be called when a grid item is clicked."
  [x y card grid values])



(defn on-turn-end
  "This function will be called when the turn changes."
  [grid values])

(defn on-game-end
  "This function will be called when the game ends."
  [grid values])
