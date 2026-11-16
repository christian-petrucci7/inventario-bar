
CREATE OR REPLACE FUNCTION gestisci_prelievo_scorte()
RETURNS TRIGGER AS $$
BEGIN

    IF NEW.quantita < 0 THEN
        NEW.quantita = 0; 
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_controllo_prelievo
BEFORE UPDATE ON inventario
FOR EACH ROW
EXECUTE FUNCTION gestisci_prelievo_scorte();


CREATE OR REPLACE FUNCTION audit_log_trigger_funzione() 
RETURNS TRIGGER AS $$ 
BEGIN 
INSERT INTO audit_log(nomeTabella, tipoOperazione, dataOperazione) 
VALUES (TG_TABLE_NAME, TG_OP, CURRENT_TIMESTAMP); 
RETURN NEW; 
END;
 $$ LANGUAGE plpgsql;

CREATE TRIGGER audit_log_trigger
AFTER INSERT OR UPDATE ON inventario
FOR EACH ROW 
EXECUTE FUNCTION audit_log_trigger_funzione();


CREATE OR REPLACE FUNCTION valida_scadenza()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.scadenza < CURRENT_DATE THEN
        RAISE EXCEPTION 'Data di scadenza non valida: %', NEW.scadenza;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_valida_scadenza
BEFORE INSERT OR UPDATE ON inventario
FOR EACH ROW
EXECUTE FUNCTION valida_scadenza();


CREATE OR REPLACE FUNCTION audit_log_trigger_funzione2() 
RETURNS TRIGGER AS $$ 
BEGIN 
INSERT INTO audit_log(nomeTabella, tipoOperazione, dataOperazione) 
VALUES (TG_TABLE_NAME, TG_OP, CURRENT_TIMESTAMP); 
RETURN NEW; 
END;
 $$ LANGUAGE plpgsql;

CREATE TRIGGER audit_log_trigger
AFTER INSERT OR UPDATE ON scorte
FOR EACH ROW 
EXECUTE FUNCTION audit_log_trigger_funzione2();

CREATE OR REPLACE FUNCTION calcola_prezzo_totale()
RETURNS TRIGGER AS $$
DECLARE
    prezzo_unitario NUMERIC(10, 2);
BEGIN

    SELECT sc.prezzo_unitario INTO prezzo_unitario
    FROM scorte sc
    WHERE sc.id_scorte = NEW.id_scorte;


    NEW.prezzo_totale := prezzo_unitario * NEW.quantita;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_calcola_prezzo_totale
BEFORE INSERT OR UPDATE ON ordini
FOR EACH ROW
EXECUTE FUNCTION calcola_prezzo_totale();
