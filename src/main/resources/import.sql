insert into partenaire (id, nom, adresse, telephone, url_gmaps, url_fb, quartier, ville, position) SELECT cast(id as bigint) as id, nom, adresse, telephone, url_gmaps, url_fb, quartier, ville, ST_GeomFromText(position) FROM CSVREAD('classpath:/partenaires.csv') as csv;

create spatial index idx_partenaires_localisation ON partenaire(position);
