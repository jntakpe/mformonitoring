INSERT INTO public.application (lock, active, artifact_id, environment, group_id, name, url, version) VALUES (0, false, 'EERS', 'DEVELOPPEMENT', 'com.bforbank', 'Entrée en relation', 'https://fra.herokuapp.com/rest/manage/health', '0.0.1-SNAPSHOT');
INSERT INTO public.application (lock, active, artifact_id, environment, group_id, name, url, version) VALUES (0, false, 'EERS', 'ASSEMBLAGE', 'com.bforbank', 'Entrée en relation', 'https://fra.herokuapp.com/rest/manage/health2', '0.0.1-SNAPSHOT');
INSERT INTO public.application (lock, active, artifact_id, environment, group_id, name, url, version) VALUES (0, false, 'EC', 'DEVELOPPEMENT', 'com.bforbank', 'Espace client', 'https://fra.herokuapp.com/rest/manage/health3', '0.0.1-SNAPSHOT');
INSERT INTO public.application (lock, active, artifact_id, environment, group_id, name, url, version) VALUES (0, false, 'BSS', 'DEVELOPPEMENT', 'com.bforbank', 'BSS', 'https://fra.herokuapp.com/rest/manage/health4', '0.0.1-SNAPSHOT');

INSERT INTO public.partner (created_at, last_modified_at, lock, name, status, url) VALUES (null, null, 0, 'selClient', 'UP', 'jdbc:oracle:thin:@10.199.160.20:49404/bnkdev');
INSERT INTO public.partner (created_at, last_modified_at, lock, name, status, url) VALUES (null, null, 0, 'selCrm', 'DOWN', 'jdbc:oracle:thin:@10.199.160.20:49405/bnkdev');

INSERT INTO public.partner_application (partner_id, application_id) VALUES (1, 4);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (1, 3);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (1, 1);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (1, 2);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (2, 4);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (2, 3);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (2, 1);
INSERT INTO public.partner_application (partner_id, application_id) VALUES (2, 2);
