insert into partenaire (id, nom, ridet, adresse, telephone, url_gmaps, url_fb, quartier, ville, position) SELECT cast(id as bigint) as id, nom, ridet, adresse, telephone, url_gmaps, url_fb, quartier, ville, ST_GeomFromText(position) FROM CSVREAD('classpath:/partenaires.csv') as csv;

-- index pour la recherche spaciale
create spatial index idx_partenaires_localisation ON partenaire(position);

-- indexation full-text (majuscule de rigeur)
CALL FTL_CREATE_INDEX('PUBLIC', 'PARTENAIRE', 'NOM,ADRESSE,TELEPHONE,QUARTIER,VILLE');
