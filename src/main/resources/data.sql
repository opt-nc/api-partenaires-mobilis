-- activation de h2gis http://www.h2gis.org/docs/1.5.0/quickstart/
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();

-- activation de la recherche full-text
CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "nc.opt.mobile.api.mobilis.partenaires.util.FullTextLucene.init";
CALL FTL_INIT();
