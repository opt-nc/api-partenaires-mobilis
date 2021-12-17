-- l'ordre est le même que sur le fichier CSV pour éviter de lister les colonnes dans la commande COPY
create table partenaire (
  id bigint not null,
  nom varchar(255) not null,
  ridet varchar(255),
  adresse varchar(255) not null,
  telephone varchar(255),
  url_gmaps varchar(255) check(url_gmaps is null or url_gmaps like 'https://%'),
  url_fb varchar(255) check(url_fb is null or url_fb like 'https://%'),
  quartier varchar(255),
  code_insee varchar(5) not null,
  position varchar(255) not null,
  primary key (id)
);

create table commune (
  nom varchar(255) not null,
  code_postal varchar(5) not null,
  code_insee varchar(5) not null,
  position varchar(255) not null,
  primary key (code_insee)
);
