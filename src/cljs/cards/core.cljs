(ns cards.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [cards.cards-backend :as cards]
   [cards.cards :as c]
   [cards.game :as game]))

(def welcome-message "Welcome to the Clojure Workshop!!")

(defn game-board []
  [cards/row
   [cards/heading welcome-message]
   [cards/grid]
   [cards/debug]])

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [game-board]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (mount-root))
