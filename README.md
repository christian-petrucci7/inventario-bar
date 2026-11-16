# 🍹 Bar Inventory Manager

Applicazione desktop sviluppata in **Java** e **PostgreSQL** per gestire l'inventario, gli ordini e le scorte di un bar. 

L'obiettivo del progetto è stato mettere in pratica la programmazione orientata agli oggetti (OOP) e la progettazione di database relazionali, costruendo un gestionale completo che automatizza il controllo delle giacenze e garantisce l'integrità dei dati direttamente lato database.

---

## 🚀 Cosa fa l'applicazione

* **Login e Gestione Permessi:** L'accesso è diviso in due ruoli. L'**Impiegato** può controllare le giacenze e prelevare i prodotti durante il servizio, mentre il **Manager** ha accesso completo per aggiungere nuove scorte, gestire i fornitori ed effettuare gli ordini.
* **Operazioni CRUD sull'Inventario:** Permette di inserire nuovi prodotti a catalogo, caricare in magazzino la merce appena arrivata (o aggiungerla manualmente), scalare le quantità consumate e rimuovere articoli.
* **Gestione Ordini e Fornitori:** Consente di creare nuovi ordini di rifornimento collegando i prodotti già censiti ai rispettivi fornitori.
* **Allarme Scorte Minime:** All'apertura, il programma controlla le quantità in magazzino e mostra un avviso se le scorte scendono sotto una soglia critica.
* **Logica e Automazioni lato Database (PostgreSQL):**
  * **Calcolo automatico totali:** Un trigger calcola il `prezzo_totale` dell'ordine moltiplicando la quantità per il prezzo unitario del prodotto.
  * **Controllo scadenze:** Un trigger blocca l'inserimento di prodotti con una data di scadenza già passata.
  * **Prelievi sicuri:** Se si prova a prelevare più merce di quella effettivamente presente, un trigger azzera la giacenza invece di mandarla in negativo.
  * **Storico modifiche (Audit):** Ogni inserimento o aggiornamento su inventario e scorte viene salvato automaticamente in una tabella `audit_log`.

---

## 🏗️ Come è strutturato il codice

Per mantenere il codice ordinato e separare l'interfaccia grafica dalle query SQL, ho diviso l'applicazione in 5 package seguendo il pattern **BCE + DAO** (*Boundary, Control, Entity + Data Access Object*):

* **`GUI` (Boundary):** Tutte le finestre e i pannelli dell'interfaccia utente realizzati con Java Swing (`LoginFrame`, `MainFrame`, ecc.).
* **`CONTROL` (Control):** Le classi (`LoginController`, `InventarioController`) che fanno da ponte tra l'interfaccia e il database, gestendo la logica delle operazioni.
* **`MODEL` (Entity):** Le classi Java che rappresentano le tabelle del database (`Personale`, `Account`, `Scorte`, `Inventario`, `Ordini`, `Fornitori`).
* **`DAO`:** Le interfacce e le classi che contengono le vere e proprie query SQL (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) eseguite tramite JDBC.
* **`MAIN`:** Contiene il `Main` per avviare il programma e `DBConnection`, una classe di utilità che crea e popola automaticamente il database al primo avvio.

---

## 🛠️ Tecnologie utilizzate

* **Java** (OOP + Swing per l'interfaccia desktop)
* **PostgreSQL** (tabelle, vincoli `CHECK`/`FOREIGN KEY` e trigger in `PL/pgSQL`)
* **JDBC** (driver per collegare Java a PostgreSQL)
* **Maven** (`pom.xml` per gestire le dipendenze del progetto)

---

## 📂 Struttura delle cartelle

```text
├── java/          # Codice sorgente (src/main/java) e file Maven (pom.xml)
├── sql/           # Script SQL con tabelle, vincoli, trigger e dati iniziali
