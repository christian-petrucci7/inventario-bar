CREATE TABLE IF NOT EXISTS personale (
    id_personale VARCHAR(10) NOT NULL,
    sesso CHAR(1) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    responsabilita VARCHAR(10) NOT NULL,
    data_assunzione DATE,
    data_nascita DATE,
    indirizzo VARCHAR(50),
    civico INTEGER,
    cap INTEGER,
    CONSTRAINT personale_pkey PRIMARY KEY (id_personale),
    CONSTRAINT personale_codice_fiscale_key UNIQUE (codice_fiscale),
    CONSTRAINT sessovalido CHECK (sesso IN ('M', 'F')),
    CONSTRAINT datanascita CHECK (data_nascita < CURRENT_DATE),
    CONSTRAINT codicefiscalelength CHECK (length(codice_fiscale) = 16),
    CONSTRAINT capvalido CHECK (cap >= 10000 AND cap <= 99999),
    CONSTRAINT idpersonalelength CHECK (length(id_personale) = 10),
    CONSTRAINT responsabilitavalida CHECK (responsabilita IN ('Impiegato', 'Manager'))
);
CREATE TABLE IF NOT EXISTS account (
    id_personale VARCHAR(10),
    email VARCHAR(50),
    password VARCHAR(50) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (password),
    CONSTRAINT account_email_key UNIQUE (email),
    CONSTRAINT fk_account FOREIGN KEY (id_personale)
        REFERENCES personale (id_personale)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS fornitori (
    nome_fornitore VARCHAR(50) NOT NULL,
    id_fornitore VARCHAR(10) NOT NULL,
    ubicazione_azienda VARCHAR(50) NOT NULL,
    CONSTRAINT fornitori_pkey PRIMARY KEY (id_fornitore),
    CONSTRAINT idfornitorelength CHECK (length(id_fornitore) = 10)
);

CREATE TABLE IF NOT EXISTS scorte (
    id_scorte VARCHAR(15) NOT NULL,
    nome_produttore VARCHAR(50) NOT NULL,
    tipo_prodotto VARCHAR(50) NOT NULL,
    prezzo_unitario NUMERIC(10,2) NOT NULL,
    CONSTRAINT scorte_pkey PRIMARY KEY (id_scorte),
    CONSTRAINT prezzopositivo CHECK (prezzo_unitario >= 0),
    CONSTRAINT idscortelength CHECK (length(id_scorte) = 10)
);

CREATE TABLE IF NOT EXISTS inventario (
    id_inventario VARCHAR(4) NOT NULL DEFAULT 'I001',
    id_scorte VARCHAR(10) NOT NULL,
    quantita INTEGER,
    scadenza DATE,
    CONSTRAINT inventario_pkey PRIMARY KEY (id_scorte),
    CONSTRAINT fk_inventario FOREIGN KEY (id_scorte)
        REFERENCES scorte (id_scorte)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT idinventariolength CHECK (length(id_inventario) = 4),
    CONSTRAINT quantita_non_negativa CHECK (quantita >= 0)
);

CREATE TABLE IF NOT EXISTS public.inventario
(
    id_inventario character varying(4) NOT NULL DEFAULT 'I001',
    id_scorte character varying(10) NOT NULL,
    quantita integer,
    scadenza date,
    CONSTRAINT inventario_pkey PRIMARY KEY (id_scorte),
    CONSTRAINT fk_inventario FOREIGN KEY (id_scorte)
        REFERENCES public.scorte (id_scorte) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT idinventariolength CHECK (length(id_inventario::text) = 4),
    CONSTRAINT quantita_non_negativa CHECK (quantita >= 0)
);

CREATE TABLE IF NOT EXISTS ordini (
    id_ordine VARCHAR(10) NOT NULL,
    id_scorte VARCHAR(15) NOT NULL,
    id_fornitore VARCHAR(10),
    data_fornitura DATE,
    quantita INTEGER,
    stoccaggio BOOLEAN,
    prezzo_totale NUMERIC(12,2),
    CONSTRAINT ordini_pkey PRIMARY KEY (id_ordine),
    CONSTRAINT ordini_id_fornitori_fkey FOREIGN KEY (id_fornitore)
        REFERENCES fornitori (id_fornitore)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ordini_id_scorte_fkey FOREIGN KEY (id_scorte)
        REFERENCES scorte (id_scorte)
        ON UPDATE NO ACTION
        ON DELETE RESTRICT,
    CONSTRAINT idordinilength CHECK (length(id_ordine) = 10),
    CONSTRAINT quantita_non_negativa CHECK (quantita >= 0)
);

CREATE TABLE IF NOT EXISTS audit_log (
    id SERIAL PRIMARY KEY,
    nometabella VARCHAR,
    tipooperazione TEXT,
    dataoperazione TIMESTAMP
);
