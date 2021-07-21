-- activation de h2gis http://www.h2gis.org/docs/1.5.0/quickstart/
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();

create table partenaire as
    SELECT cast(id as integer) as id, nom, adresse, telephone, quartier, ville, ST_GeomFromText(position) as position
    FROM CSVREAD('classpath:/partenaires.csv', NULL, 'fieldSeparator=;');

create spatial index idx_partenaires_localisation ON partenaire(position);
