(ns cards.cards
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))

(defn flip
  "Flip the card at position (x, y)"
  [x y]
  (re-frame/dispatch [:cards/flip x y]))

(defn deal
  "Deal one card with picture at (x, y). If side is :front,
  the card will be face up. If side is :back, the card will
  be face down. side defaults to :back"
  ([x y picture]
   (deal x y picture :back))
  ([x y picture side]
   (re-frame/dispatch [:cards/set-card x y picture side])))

(defn remove-all-cards
  "Remove all cards from the game."
  []
  (re-frame/dispatch [:cards/empty-grid]))

(defn remove-card
  "Remove the card at (x, y) from the grid."
  [x y]
  (re-frame/dispatch [:cards/remove-card x y]))

(defn set-value
  "Set a game value, named by name, to value."
  [name value]
  (re-frame/dispatch [:cards/set-value name value]))

(defn delete-value
  "Delete a game value, named by name."
  [name]
  (re-frame/dispatch [:cards/delete-value name]))

(defn display-value
  "Get the value of a set value by name. Only to be used
  inside of a rendered component."
  ([name]
   (display-value name nil))
  ([name default] 
   (deref (re-frame/subscribe [:cards/get-value name default]))))

(defn end-turn
  "End the turn."
  []
  (re-frame/dispatch [:cards/end-turn]))
