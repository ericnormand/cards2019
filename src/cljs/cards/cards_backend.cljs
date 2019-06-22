(ns cards.cards-backend
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [cards.game :as game]
            [clojure.pprint :as pprint]))

(re-frame/reg-sub
 :db
 (fn [db _]
   db))

(re-frame/reg-sub
 :cards/grid
 (fn [db _]
   (:grid db)))

(re-frame/reg-sub
 :cards/values
 (fn [db _]
   (:values db)))

(re-frame/reg-sub
 :cards/card-at
 (fn [db [_]]
   (re-frame/subscribe [:cards/grid]))
 (fn [grid [_ x y]]
   (get grid [x y])))

(re-frame/reg-sub
 :cards/get-value
 (fn [db [_ name default]]
   (get-in db [:values name] default)))

(re-frame/reg-event-db
 :cards/empty-grid
 (fn [db _]
   (assoc db :grid {})))

(re-frame/reg-event-db
 :cards/set-card
 (fn [db [_ x y picture side]]
   (assoc-in db [:grid [x y]] {:picture picture :side side})))

(re-frame/reg-event-db
 :cards/remove-card
 (fn [db [_ x y]]
   (update db :grid dissoc [x y])))

(re-frame/reg-event-db
 :cards/on-click
 (fn [db [_ x y]]
   (let [grid (get db :grid)
         card (get grid [x y])
         values (get db :values)]
     (game/on-click x y card grid values)
     db)))

(re-frame/reg-event-db
 :cards/set-value
 (fn [db [_ name value]]
   (assoc-in db [:values name] value)))

(re-frame/reg-event-db
 :cards/delete-value
 (fn [db [_ name value]]
   (update db :values dissoc name)))

(defn flip [side]
  (if (= :front side)
    :back
    :front))

(defn flip-card [card]
  (when (some? card)
    (update card :side flip)))

(re-frame/reg-event-db
 :cards/flip
 (fn [db [_ x y]]
   (update-in db [:grid [x y]] flip-card)))

(re-frame/reg-event-db
 :cards/end-turn
 (fn [db [_]]
   (game/on-turn-end (:grid db) (:values db))
   db))

(re-frame/reg-event-db
 :cards/end-game
 (fn [db [_]]
   (game/on-game-end (:grid db) (:values db))
   db))

(re-frame/reg-event-db
 :cards/move-onscreen
 (fn [db [_ x y]]
   (update-in db [:grid [x y]] dissoc :position)))

(defn grid-element [x y]
  (if-some [{:keys [picture side]} @(re-frame/subscribe [:cards/card-at x y])]
    [:div.card-space
     [:div {:class (str "flip-container "
                        (if (= side :front) "up" "down"))}
      [:div {:class "flipper"
             :on-click (fn []
                         (re-frame/dispatch [:cards/on-click x y]))}
       [:div {:class "front card-picture"}
        picture]
       [:div.back {:class "card-picture"}]]]]
    [:div.card-space {:on-click (fn []
                                  (re-frame/dispatch [:cards/on-click x y]))}]))

(defn grid []
  [:div
   (doall
    (for [y (range 10)]
      [:div {:key (str y)}
       (doall
        (for [x (range 10)]
          [:span {:key (str x)}
           [grid-element x y]]))]))])

(defn button [label f]
  (let [grid   @(re-frame/subscribe [:cards/grid])
        values @(re-frame/subscribe [:cards/values])]
    [:button {:on-click (when f
                          (fn []
                            (f grid values)))
              :style {:margin-right 10}}
     label]))

(defn label [& text]
  (into [:div {:style {:display "inline-block" :margin-right 10}}] text))

(defn row [& elements]
  (into [:div {:style {:margin-bottom 10}}] elements))

(defn heading [text]
  [:h1 text])

(defn debug []
  [:pre [:code (with-out-str (pprint/pprint @(re-frame/subscribe [:db])))]])
