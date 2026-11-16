INSERT INTO personale (id_personale, sesso, codice_fiscale, nome, cognome, responsabilita, data_assunzione, data_nascita, indirizzo, civico, cap) VALUES
('PER0000001','M','RSSMRA80A01H501K','Mario','Rossi','Impiegato','2020-01-15','1980-05-20','Via Roma',10,80100),
('PER0000002','F','BNCLDA75C05F205P','Laura','Bianchi','Impiegato','2021-03-01','1975-12-10','Corso Italia',25,80185),
('PER0000003','M','VRDDVD90E10G478A','Davide','Verdi','Manager','2019-07-20','1990-01-01','Piazza Duomo',3,80121),
('PER0000004','F','MRCLDS85M20H703J','Alessandra','Marcelli','Impiegato','2018-05-05','1985-03-20','Via Dante',18,50123),
('PER0000005','M','GLLLCU95A01M100Z','Luca','Galli','Impiegato','2024-06-01','1995-02-15','Via della Repubblica',42,80144),
('PER0000006','M','GRGNCL93S25F839V','Nicola','Grassi','Manager','2023-11-01','1993-09-25','Via dei Giardini',12,10126),
('PER0000007','F','CSTRLS82P188963U','Alessia','Costa','Manager','2017-08-10','1982-06-18','Piazza San Marco',1,30124),
('PER0000008','M','MRTMCR98L05C615X','Marco','Martini','Manager','2024-03-15','1998-07-05','Via Garibaldi',55,40122),
('PER0000009','F','RCCNNA87R01D612Y','Anna','Ricci','Manager','2022-06-20','1987-10-01','Viale della Liberta',30,90143),
('PER0000010','M','RSSMRA80A01H501Z','Gianni','Neri','Impiegato','2022-01-10','1982-05-14','Via Milano',22,80100);

INSERT INTO account (id_personale, email, password) VALUES
('PER0000001','mario.rossi@example.com','pass001'),
('PER0000002','laura.bianchi@example.com','pass002'),
('PER0000003','davide.verdi@example.com','pass003'),
('PER0000004','alessandra.marcelli@example.com','pass004'),
('PER0000005','luca.galli@example.com','pass005'),
('PER0000006','nicola.grassi@example.com','pass006'),
('PER0000007','alessia.costa@example.com','pass007'),
('PER0000008','marco.martini@example.com','pass008'),
('PER0000009','anna.ricci@example.com','pass009'),
('PER0000010','gianni.neri@example.com','pass010');


INSERT INTO fornitori (nome_fornitore,id_fornitore,ubicazione_azienda) VALUES
('Alpha Distribuzioni','FORN000011','Milano'),
('Beta Import','FORN000012','Torino'),
('Gamma Supplies','FORN000013','Roma'),
('Delta Traders','FORN000014','Napoli'),
('Epsilon Logistics','FORN000015','Bologna'),
('Zeta Partners','FORN000016','Firenze'),
('Eta Wholesale','FORN000017','Genova'),
('Theta Group','FORN000018','Venezia'),
('Iota Solutions','FORN000019','Verona'),
('Kappa Industries','FORN000020','Palermo'),
('Lambda Export','FORN000021','Cagliari'),
('Mu Enterprise','FORN000022','Bari'),
('Nu Vendors','FORN000023','Padova'),
('Xi Distributors','FORN000024','Trieste'),
('Omicron Supplies','FORN000025','Parma'),
('Pi Commerce','FORN000026','Reggio Emilia'),
('Rho Logistics','FORN000027','Modena'),
('Sigma Import','FORN000028','Taranto'),
('Tau Traders','FORN000029','Perugia'),
('Upsilon Partners','FORN000030','Lecce'),
('Distillerie Reali','FORN000001','Highlands'),
('Bevande Fresche Italia','FORN000002','Roma'),
('Cantine Storiche Piemonte','FORN000003','Alba'),
('Liquorificio Artigianale','FORN000004','Campania'),
('Vignobles de France','FORN000006','Bordeaux'),
('Zero Proof Beverages','FORN000007','San Francisco'),
('Antiche Ricette Liquori','FORN000008','Amalfi'),
('Nordic Spirits AB','FORN000009','Stoccolma'),
('Beverage Solutions Ltd.','FORN000010','Londra'),
('Global Supply','FORN000005','Dublino');


INSERT INTO scorte (id_scorte, nome_produttore, tipo_prodotto, prezzo_unitario) VALUES
('SCORTE0001','EstaThe','Analcolico',12.50),
('SCORTE0002','Vietti','Vino',5.75),
('SCORTE0003','Limoncello','Liquore',8.20),
('SCORTE0004','Ginmare','Distillato',3.15),
('SCORTE0005','Hendrixx','Distillato',18.90),
('SCORTE0006','Fanta','Analcolico',9.40),
('SCORTE0007','Cocacola','Analcolico',6.00),
('SCORTE0008','Meloncello','Liquore',7.10),
('SCORTE0009','Acqua','Analcolico',4.55),
('SCORTE0010','Smirnoff','Distillato',22.30);

INSERT INTO inventario (id_inventario,id_scorte,quantita,scadenza) VALUES 
('I001','SCORTE0001','78','2029-11-10'),
('I001','SCORTE0002','22','2030-12-20'),
('I001','SCORTE0003','47','2029-10-09'),
('I001','SCORTE0004','54','2031-01-02'),
('I001','SCORTE0005','33','2030-10-20'),
('I001','SCORTE0006','75','2028-07-07'),
('I001','SCORTE0007','60','2027-01-03'),
('I001','SCORTE0008','72','2032-02-18'),
('I001','SCORTE0009','79','2030-01-01'),
('I001','SCORTE0010','36','2030-11-19');


INSERT INTO ordini (id_ordine, id_scorte, id_fornitore, data_fornitura, quantita, stoccaggio, prezzo_totale) VALUES
('ORD0000001','SCORTE0001','FORN000001','2024-05-01',10,FALSE, 125.00),
('ORD0000002','SCORTE0002','FORN000002','2024-05-01',10,FALSE, 57.50),
('ORD0000003','SCORTE0003','FORN000003','2024-05-01',10,FALSE, 82.00),
('ORD0000004','SCORTE0004','FORN000004','2024-05-01',10,FALSE, 31.50),
('ORD0000005','SCORTE0005','FORN000005','2024-05-01',10,FALSE, 189.00),
('ORD0000006','SCORTE0006','FORN000006','2024-05-01',10,FALSE, 94.00),
('ORD0000007','SCORTE0007','FORN000007','2024-05-01',10,FALSE, 60.00),
('ORD0000008','SCORTE0008','FORN000008','2024-05-01',10,FALSE, 71.00),
('ORD0000009','SCORTE0009','FORN000009','2024-05-01',10,FALSE, 45.50),
('ORD0000010','SCORTE0010','FORN000010','2024-05-01',10,FALSE, 223.00);
