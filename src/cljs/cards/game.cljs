(ns cards.game
  (:require-macros [cards.cards :as cards])
  (:require [cards.cards :as cards]))

(def cards ["A" "B" "C" "D"])
(def cards2 (concat cards cards))

(defn up? [card]
  (= :front (get card :side)))

(defn up-cards [grid]
  (filter (fn [pos-card]
            (let [card (val pos-card)
                  pos  (key pos-card)]
              (up? card)))
          grid))

(defn start-game []
  (cards/remove-all-cards)
  (let [scards (shuffle cards2)]
    (dotimes [i (count scards)]
      (let [x (mod i 4)
            y (quot i 4)]
        (cards/deal x y (nth scards i))))))

(defn on-click
  "This function will be called when a grid item is clicked."
  [x y card grid values]
  (if (not (up? card))
    (cards/flip x y))
  (prn grid))



(defn on-turn-end
  "This function will be called when the turn changes."
  [grid values])

(defn on-game-end
  "This function will be called when the game ends."
  [grid values])
