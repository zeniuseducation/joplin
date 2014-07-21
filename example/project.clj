(defproject joplin-example "0.1.5"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.h2database/h2 "1.3.160"]]
  :plugins [[joplin.lein "0.1.5"]]

  :source-paths ["src" "joplin"]

  :joplin {
           :migrators {:sql-mig "joplin/migrators/sql"
                       :es-mig "joplin/migrators/es"
                       :cass-mig "joplin/migrators/cass"
                       :dt-mig "joplin/migrators/datomic"}
           :seeds {:sql-seed "seeds.sql/run"
                   :es-seed "seeds.es/run"
                   :cass-seed "seeds.cass/run"
                   :dt-seed "seeds.dt/run"
                   :zk-seed "seeds.zk/run"}
           :databases {:sql-dev  {:type :jdbc, :url "jdbc:h2:mem:test"}
                       :sql-prod {:type :jdbc, :url "jdbc:h2:file:prod"}

                       :dt-dev {:type :dt, :url "datomic:mem://test"}

                       :cass-dev {:type :cass, :hosts ["127.0.0.1"], :keyspace "test"}

                       :es-dev   {:type :es, :host "localhost", :port 9300, :cluster "dev"}
                       :es-prod  {:type :es, :host "es-prod", :port 9300, :cluster "dev"}

                       :zk-dev   {:type :zk, :host "localhost", :port 2181, :client :curator}
                       :zk-prod  {:type :zk, :host "zk-prod", :port 2181, :client :exhibitor}
                       }

           :environments {:dev [{:db :sql-dev, :migrator :sql-mig, :seed :sql-seed}
                                {:db :es-dev, :migrator :es-mig, :seed :es-seed}
                                {:db :cass-dev, :migrator :cass-mig, :seed :cass-seed}
                                {:db :dt-dev, :migrator :dt-mig, :seed :dt-seed}
                                {:db :zk-dev, :seed :zk-seed}]
                          :prod [{:db :sql-prod, :migrator :sql-mig, :seed :sql-seed}
                                 {:db :es-prod, :migrator :es-mig}
                                 {:db :zk-prod}]}
           })
