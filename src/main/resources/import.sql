insert into commune (nom, code_postal, code_insee, position) SELECT nom, code_postal, code_insee, ST_GeomFromText(position) as position FROM CSVREAD('classpath:/communes.csv') as csv;
insert into partenaire (id, nom, ridet, adresse, telephone, url_gmaps, url_fb, quartier, code_insee, position) select cast(id as bigint) as id, nom, ridet, adresse, telephone, url_gmaps, url_fb, quartier, code_insee, ST_GeomFromText(position) as position FROM CSVREAD('classpath:/partenaires.csv') as csv;
alter table partenaire add foreign key (code_insee) references commune(code_insee);

-- index pour la recherche spaciale
create spatial index idx_communes_localisation ON commune(position);
create spatial index idx_partenaires_localisation ON partenaire(position);

-- indexation full-text (majuscule de rigeur)
CALL FTL_CREATE_INDEX('PUBLIC', 'COMMUNE', 'NOM,CODE_POSTAL,CODE_INSEE');
CALL FTL_CREATE_INDEX('PUBLIC', 'PARTENAIRE', 'NOM,ADRESSE,TELEPHONE,QUARTIER,CODE_INSEE');
