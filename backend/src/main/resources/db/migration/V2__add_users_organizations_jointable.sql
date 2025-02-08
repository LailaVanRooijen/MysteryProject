CREATE TABLE IF NOT EXISTS public.users
(
    id uuid NOT NULL,
    display_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_display_name_key UNIQUE (display_name),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_role_check CHECK (role::text = ANY (ARRAY['ADMIN'::character varying, 'TUTOR'::character varying, 'USER'::character varying]::text[]))
);

CREATE TABLE IF NOT EXISTS public.organizations
(
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT organizations_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users_organizations
(
    organization_role smallint,
    id bigint NOT NULL,
    organization_id bigint,
    user_id uuid,
    CONSTRAINT users_organizations_pkey PRIMARY KEY (id),
    CONSTRAINT fkcx7c5yo5xkeunti8qv07f6gjk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmr2o7bfoblu76nlj9qk23r1en FOREIGN KEY (organization_id)
        REFERENCES public.organizations (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT users_organizations_organization_role_check CHECK (organization_role >= 0 AND organization_role <= 1)
);

CREATE TABLE IF NOT EXISTS public.organizations_users
(
    organizations_id bigint NOT NULL,
    users_id uuid NOT NULL,
    CONSTRAINT organizations_users_users_id_key UNIQUE (users_id),
    CONSTRAINT fk3i8oatq66u0tvmg2eb02pkxdi FOREIGN KEY (organizations_id)
        REFERENCES public.organizations (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkma8cpw7qaamdx4swhv97tjv4m FOREIGN KEY (users_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
