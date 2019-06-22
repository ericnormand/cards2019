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
  (cards/set-value :current-player 1)
  (cards/set-value [:score 1] 0)
  (let [scards (shuffle cards2)]
    (dotimes [i (count scards)]
      (let [x (mod i 4)
            y (quot i 4)]
        (cards/deal x y (nth scards i))))))

(defn on-click
  "This function will be called when a grid item is clicked."
  [x y card grid values]
  (if (not (up? card))
    (do
      (if (< (count (up-cards grid)) 2)
        (cards/flip x y))
      (if (= 1 (count (up-cards grid)))
        (cards/end-turn))))
  (prn grid))



(defn on-turn-end
  "This function will be called when the turn changes."
  [grid values]
  (let [pos-cards (up-cards grid)

        pos-card1 (first pos-cards)
        pos1 (key pos-card1)
        card1 (val pos-card1)
        picture1 (get card1 :picture)
        x1 (first pos1)
        y1 (second pos1)

        pos-card2 (second pos-cards)
        pos2 (key pos-card2)
        card2 (val pos-card2)
        picture2 (get card2 :picture)
        x2 (first pos2)
        y2 (second pos2)]
    (if (= picture1 picture2)
      (do
        (cards/wait 2
                    (cards/remove-card x1 y1)
                    (cards/remove-card x2 y2)))
      (do
        (cards/wait 2
                    (cards/flip x1 y1)
                    (cards/flip x2 y2)
                    (if (= 1 (get values :current-player))
                      (cards/set-value :current-player 2)
                      (cards/set-value :current-player 1)))))))

(defn on-game-end
  "This function will be called when the game ends."
  [grid values])
